/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.optimized;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sriramramani.shutter.R;

public class PhotoInfoView extends TextView {
    
    private static ImageSpan sDotSpan;
    private static ImageSpan sPublicSpan;
    private static ImageSpan sPrivateSpan;

    public PhotoInfoView(Context context) {
        this (context, null);
    }

    public PhotoInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoInfoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        if (sDotSpan == null) {
            sDotSpan = new ImageSpan(context, R.drawable.dot, ImageSpan.ALIGN_BASELINE);
            sPublicSpan = new ImageSpan(context, R.drawable.ic_public, ImageSpan.ALIGN_BASELINE);
            sPrivateSpan = new ImageSpan(context, R.drawable.ic_private, ImageSpan.ALIGN_BASELINE);
        }
    }
    
    public void setValues(boolean isPublic, String time, String place) {
        final int timeLength = time.length();
        SpannableStringBuilder builder = new SpannableStringBuilder("  " + time + "   " + place);
        builder.setSpan(isPublic ? sPublicSpan : sPrivateSpan, 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(sDotSpan, timeLength + 3, timeLength + 4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        setText(builder, TextView.BufferType.SPANNABLE);
    }

}
