/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.optimized;

import android.content.Context;
import android.util.AttributeSet;

import com.sriramramani.shutter.R;

public class LikeButton extends ActionButton {
    
    private static final int[] STATE_LIKED = { R.attr.state_liked };
    
    private boolean mIsLiked;

    public LikeButton(Context context) {
        this (context, null);
    }

    public LikeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

        if (mIsLiked) {
            mergeDrawableStates(drawableState, STATE_LIKED);
        }

        return drawableState;
    }
    
    public void setLiked(boolean isLiked) {
        if (mIsLiked != isLiked) {
            mIsLiked = isLiked;
            refreshDrawableState();
        }
    }
}
