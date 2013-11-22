/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.optimized;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.sriramramani.shutter.R;
import com.sriramramani.shutter.optimized.LoadContactTask.OnLoadContactPhotoListener;

public class OwnerViewPorterDuff extends ImageView implements OnLoadContactPhotoListener {

    private final Paint mPaint;
    private final PorterDuffXfermode mMode;

    private LoadContactTask mTask;
    private String mPicture;

    public OwnerViewPorterDuff(Context context) {
        this (context, null);
    }

    public OwnerViewPorterDuff(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OwnerViewPorterDuff(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setWillNotDraw(false);
        
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStyle(Paint.Style.FILL);
        mMode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    public void draw(Canvas canvas) {
        // Save the canvas. All PorterDuff operations should be done in a offscreen bitmap.
        final int width = getWidth();
        final int height = getHeight();
        final int count = canvas.saveLayer(0, 0, width, height, null,
                                     Canvas.MATRIX_SAVE_FLAG |
                                     Canvas.CLIP_SAVE_FLAG |
                                     Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                                     Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                                     Canvas.CLIP_TO_LAYER_SAVE_FLAG);

        super.draw(canvas);

        // Allocate a bitmap and draw the masking/clipping path.
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        (new Canvas(bitmap)).drawCircle(width/2, height/2, width/2, mPaint);
        mPaint.setXfermode(mMode);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);

        bitmap.recycle();

        // Restore the canvas.
        canvas.restoreToCount(count);
    }
    
    public void setOwnerImage(String picture) {
        if (mTask != null) {
            mTask.cancel(true);
        }

        mPicture = picture;
        setImageResource(R.drawable.profile);
        setBackgroundColor(0xFFAABBCC);
        mTask = new LoadContactTask(getResources().getAssets(), this);
        mTask.execute(picture);
    }

    @Override
    public void onLoadContactPhoto(String picture, Bitmap bitmap) {
        if (TextUtils.equals(mPicture, picture)) {
            setImageBitmap(bitmap);
            setBackgroundColor(0);
            mTask = null;
        }
    }
}
