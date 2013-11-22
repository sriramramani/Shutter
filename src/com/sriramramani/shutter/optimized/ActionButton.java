/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.optimized;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sriramramani.shutter.R;

public class ActionButton extends LinearLayout {

    public ActionButton(Context context) {
        this (context, null);
    }

    public ActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater.from(context).inflate(R.layout.optimized_action_button, this);
        final TextView text = (TextView) findViewById(R.id.action_button_text);
        
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ActionButton);
        int imageId = a.getResourceId(R.styleable.ActionButton_image, 0);
        Drawable drawable = context.getResources().getDrawable(imageId);
        
        int textId = a.getResourceId(R.styleable.ActionButton_text, 0);
        if (textId != 0) {
            text.setText(textId);
        } else {
            text.setText("");
        }
        
        text.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        a.recycle();
    }
}
