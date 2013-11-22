/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.optimized;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sriramramani.shutter.R;

public class NameView extends TextView {
    
    private final TextAppearanceSpan mSpan;

    public NameView(Context context) {
        this (context, null);
    }

    public NameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mSpan = new TextAppearanceSpan(context, R.style.TextAppearance_Handle);
    }
    
    public void setValues(String name, String handle) {
        final int nameLength = name.length();
        final int handleLength = handle.length();

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(name + " @" + handle);        
        builder.setSpan(mSpan, nameLength, nameLength + handleLength + 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        setText(builder, TextView.BufferType.SPANNABLE);
    }

}
