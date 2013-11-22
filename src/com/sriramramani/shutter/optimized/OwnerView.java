/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.optimized;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.sriramramani.shutter.R;
import com.sriramramani.shutter.optimized.LoadContactTask.OnLoadContactPhotoListener;

public class OwnerView extends ImageView implements OnLoadContactPhotoListener {

    private final Paint mPaint;
    private final Paint mPressedPaint;
    private final Shader mProfileShader;
    
    private LoadContactTask mTask;
    private String mPicture;
    private boolean mIsReset;
    private boolean mIsPressed;

    public OwnerView(Context context) {
        this (context, null);
    }

    public OwnerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OwnerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setWillNotDraw(false);
        
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xFFAABBCC);
        
        mPressedPaint = new Paint(mPaint);
        mPressedPaint.setColor(0x66000000);
        
        mIsReset = true;
        
        final Drawable drawable = getResources().getDrawable(R.drawable.profile);
        if (drawable instanceof BitmapDrawable) {
            final BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            mProfileShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        } else {
            mProfileShader = null;
        }
        
        setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                android.util.Log.i("Sriram", "clicked");
                
            }
        });
    }

    @Override
    public void onDraw(Canvas canvas) {
        final int width = getWidth();
        final int height = getHeight();
        
        if (mIsReset) {
            mPaint.setShader(null);
            canvas.drawCircle(width/2, height/2, width/2, mPaint);
            mPaint.setShader(mProfileShader);
        }

        canvas.drawCircle(width/2, height/2, width/2, mPaint);

        if (mIsPressed) {
            canvas.drawCircle(width/2, height/2, width/2, mPressedPaint);
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mIsPressed = (event.getActionMasked() == MotionEvent.ACTION_DOWN);
        invalidate();

        return super.onTouchEvent(event);
    }
    
    public void setOwnerImage(String picture) {
        if (mTask != null) {
            mTask.cancel(true);
        }

        mPicture = picture;
        mIsReset = true;

        Bitmap bitmap = LoadContactTask.getContact(picture);
        if (bitmap != null) {
            onLoadContactPhoto(picture, bitmap);
        } else {
            mTask = new LoadContactTask(getResources().getAssets(), this);
            mTask.execute(picture);
        }
    }

    @Override
    public void onLoadContactPhoto(String picture, Bitmap bitmap) {
        if (TextUtils.equals(mPicture, picture)) {
            mPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.REPEAT));
            mIsReset = false;
            invalidate();
            mTask = null;
        }
    }
}
