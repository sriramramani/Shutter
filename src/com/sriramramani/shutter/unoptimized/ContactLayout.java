/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.unoptimized;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.sriramramani.shutter.R;
import com.sriramramani.shutter.unoptimized.LoadContactTask.OnLoadContactPhotoListener;
import com.sriramramani.shutter.unoptimized.PhotoAdapter.Contact;

public class ContactLayout extends LinearLayout {
    
    private static LayoutParams sParams;
    
    private List<LoadContactTask> mTasks;

    public ContactLayout(Context context) {
        this (context, null);
    }

    public ContactLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ContactLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        mTasks = new ArrayList<LoadContactTask>();

        if (sParams == null) {
            final int size = getResources().getDimensionPixelSize(R.dimen.contact_size);
            final int margin = getResources().getDimensionPixelSize(R.dimen.contact_margin);
            sParams = new LayoutParams(size, size);
            sParams.leftMargin = margin;
            sParams.rightMargin = margin;
        }
    }

    public void setContacts(Contact[] contacts) {
        removeAllViews();
        for (LoadContactTask task : mTasks) {
            task.cancel(true);
        }

        int i = 0;
        final AssetManager assets = getResources().getAssets();
        for (Contact contact : contacts) {
            final int index = i;
            LoadContactTask task = new LoadContactTask(assets, new OnLoadContactPhotoListener() {
                @Override
                public void onLoadContactPhoto(String picture, Bitmap bitmap) {
                    setContact(index, bitmap);
                }
            });
            mTasks.add(task);
            task.execute(contact.picture);

            final ContactView view = new ContactView(getContext(), null);
            view.setLayoutParams(sParams);
            view.setContactImage(null);
            addView(view);
            i++;
        }
    }

    private void setContact(int index, Bitmap bitmap) {
        ((ContactView) this.getChildAt(index)).setContactImage(bitmap);
    }
}
