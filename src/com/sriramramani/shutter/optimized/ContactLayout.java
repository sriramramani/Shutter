/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.optimized;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.sriramramani.shutter.R;
import com.sriramramani.shutter.optimized.LoadContactTask.OnLoadContactPhotoListener;
import com.sriramramani.shutter.optimized.PhotoAdapter.Contact;

public class ContactLayout extends View {
    
    private static Matrix sHalfMatrix;
    
    static {
        sHalfMatrix = new Matrix();
        sHalfMatrix.setScale(0.5f, 0.5f);
    }
    
    private List<LoadContactTask> mTasks;
    
    private Contact[] mContacts;
    private Shader[] mShaders;
    
    private final Paint mPaint;
    
    private final LinearGradient mAlpha;
    private final BitmapShader mProfileShader;
    
    private final int mChildSize;
    private final int mChildMargin;
    private final int mChildCorner;
    private final RectF mChildBounds;

    public ContactLayout(Context context) {
        this (context, null);
    }

    public ContactLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        mTasks = new ArrayList<LoadContactTask>();
        mChildSize = getResources().getDimensionPixelSize(R.dimen.contact_size);
        mChildMargin = getResources().getDimensionPixelSize(R.dimen.contact_margin);
        mChildCorner = getResources().getDimensionPixelSize(R.dimen.contact_corner);
        mChildBounds = new RectF(0, 0, mChildSize, mChildSize);
        
        mAlpha = new LinearGradient(0, 0,
                                    mChildSize, mChildSize,
                                    0xB4FFFFFF, 0xB4FFFFFF,
                                    Shader.TileMode.CLAMP);

        final Drawable drawable = getResources().getDrawable(R.drawable.profile);
        if (drawable instanceof BitmapDrawable) {
            final BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            mProfileShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mProfileShader.setLocalMatrix(sHalfMatrix);
        } else {
            mProfileShader = null;
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xFFAABBCC);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        final int length = (mContacts == null ? 0 : mContacts.length);
        final int childWidth = mChildSize + mChildMargin + mChildMargin;
        final int width = getPaddingLeft() + getPaddingRight() + (childWidth * length);

        setMeasuredDimension(width, getMeasuredHeight());
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        
        // Translate the canvas as per padding.
        canvas.translate(getPaddingLeft(), ((getHeight() - mChildSize)/2));

        final int length = mShaders.length;
        for (int i = 0; i < length; i++) {
            // Left margin.
            canvas.translate(mChildMargin, 0);

            if (mShaders[i] == mProfileShader) {
                mPaint.setShader(null);
                canvas.drawRoundRect(mChildBounds, mChildCorner, mChildCorner, mPaint);
            }

            mPaint.setShader(mShaders[i]);
            canvas.drawRoundRect(mChildBounds, mChildCorner, mChildCorner, mPaint);
            
            // Width + Right margin.
            canvas.translate(mChildMargin + mChildSize, 0);
        }

        canvas.restore();
    }

    public void setContacts(Contact[] contacts) {
        mContacts = contacts;
        mShaders = new Shader[contacts.length];

        for (LoadContactTask task : mTasks) {
            task.cancel(true);
        }

        int i = 0;
        final AssetManager assets = getResources().getAssets();
        for (Contact contact : contacts) {
            final int index = i;
            Bitmap bitmap = LoadContactTask.getContact(contact.picture);
            if (bitmap != null) {
                setContact(index, bitmap);
            } else {
                LoadContactTask task = new LoadContactTask(assets, new OnLoadContactPhotoListener() {
                    @Override
                    public void onLoadContactPhoto(String picture, Bitmap bitmap) {
                        setContact(index, bitmap);
                    }
                });
                mTasks.add(task);
                task.execute(contact.picture);
                mShaders[i] = mProfileShader;
            }

            i++;
        }
    }

    private void setContact(int index, Bitmap bitmap) {
        final BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(sHalfMatrix);
        mShaders[index] = new ComposeShader(shader, mAlpha, PorterDuff.Mode.DST_IN);
        invalidate();
    }
}
