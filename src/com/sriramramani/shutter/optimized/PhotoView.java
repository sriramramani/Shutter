/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.optimized;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class PhotoView extends ImageView {

    private LoadPhotoTask mTask;
    
    private boolean mIsPressed;

    public PhotoView(Context context) {
        this (context, null);
    }

    public PhotoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.save();
        super.onDraw(canvas);

        if (mIsPressed) {
            canvas.drawColor(0x66000000);
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mIsPressed = (event.getActionMasked() == MotionEvent.ACTION_DOWN);
        invalidate();

        return super.onTouchEvent(event);
    }
    
    public void setPhoto(Bitmap bitmap) {
        setImageBitmap(bitmap);
    }
    
    public void showProgress() {
        setBackgroundColor(0xFFAAAAAA);
        //mProgress.setVisibility(View.VISIBLE);
    }
    
    public void hideProgress() {
        setBackgroundColor(0);
        //mProgress.setVisibility(View.GONE);
    }
        
    public void loadPhoto(String picture) {
        if (mTask != null) {
            mTask.cancel(true);
        }

        final Bitmap bitmap = LoadPhotoTask.getPhoto(picture);
        if (bitmap != null) {
            setImageBitmap(bitmap);
        } else {
            setTag(picture);
            mTask = new LoadPhotoTask(getResources().getAssets(), this);
            mTask.execute(picture);
        }
    }
}
