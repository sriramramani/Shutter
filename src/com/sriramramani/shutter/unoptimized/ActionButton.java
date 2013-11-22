/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.unoptimized;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ActionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setClickable(true);

        LayoutInflater.from(context).inflate(R.layout.unoptimized_action_button, this);
        final ImageView image = (ImageView) findViewById(R.id.action_button_image);
        final TextView text = (TextView) findViewById(R.id.action_button_text);
        
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ActionButton);
        int imageId = a.getResourceId(R.styleable.ActionButton_image, 0);
        if (imageId != 0) {
            image.setImageResource(imageId);
        } else {
            image.setVisibility(View.GONE);
        }
        
        int textId = a.getResourceId(R.styleable.ActionButton_text, 0);
        if (textId != 0) {
            text.setText(textId);
        } else {
            text.setText("");
        }
        a.recycle();
    }
}
