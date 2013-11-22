/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.unoptimized;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sriramramani.shutter.R;
import com.sriramramani.shutter.unoptimized.LoadContactTask.OnLoadContactPhotoListener;

public class OwnerView extends FrameLayout implements OnLoadContactPhotoListener {

    private final ImageView mOwner;
    private LoadContactTask mTask;
    private String mPicture;

    public OwnerView(Context context) {
        this (context, null);
    }

    public OwnerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OwnerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater.from(context).inflate(R.layout.unoptimized_owner_view, this);
        mOwner = (ImageView) findViewById(R.id.owner_image);
    }
    
    public void setOwnerImage(String picture) {
        if (mTask != null) {
            mTask.cancel(true);
        }

        mPicture = picture;
        mOwner.setImageResource(R.drawable.profile);
        mTask = new LoadContactTask(getResources().getAssets(), this);
        mTask.execute(picture);
    }

    @Override
    public void onLoadContactPhoto(String picture, Bitmap bitmap) {
        if (TextUtils.equals(mPicture, picture)) {
            mOwner.setImageBitmap(bitmap);
            mTask = null;
        }
    }
}
