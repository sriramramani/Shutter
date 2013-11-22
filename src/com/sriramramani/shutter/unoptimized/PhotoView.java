/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.unoptimized;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.sriramramani.shutter.R;

public class PhotoView extends FrameLayout {

    private final ImageView mPhoto;
    private final ProgressBar mProgress;
    private LoadPhotoTask mTask;

    public PhotoView(Context context) {
        this (context, null);
    }

    public PhotoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setClickable(true);

        LayoutInflater.from(context).inflate(R.layout.unoptimized_photo_view, this);
        mPhoto = (ImageView) findViewById(R.id.photo);
        mProgress = (ProgressBar) findViewById(R.id.spinner);
    }
    
    public void setPhoto(Bitmap bitmap) {
        mPhoto.setImageBitmap(bitmap);
    }
    
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }
    
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }
        
    public void loadPhoto(String picture) {
        if (mTask != null) {
            mTask.cancel(true);
        }

        setTag(picture);
        mTask = new LoadPhotoTask(getResources().getAssets(), this);
        mTask.execute(picture);
    }
}
