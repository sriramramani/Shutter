/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.unoptimized;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sriramramani.shutter.R;

public class NameView extends LinearLayout {

    private final TextView mName;
    private final TextView mHandle;

    public NameView(Context context) {
        this (context, null);
    }

    public NameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public NameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater.from(context).inflate(R.layout.unoptimized_name_view, this);
        mName = (TextView) findViewById(R.id.name);
        mHandle = (TextView) findViewById(R.id.handle);
    }
    
    public void setName(String name) {
        mName.setText(name);
    }
    
    public void setHandle(String handle) {
        mHandle.setText(" @" + handle);
    }

}
