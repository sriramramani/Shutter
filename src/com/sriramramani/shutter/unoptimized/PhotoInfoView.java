/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.unoptimized;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sriramramani.shutter.R;

public class PhotoInfoView extends LinearLayout {

    private final ImageView mVisibility;
    private final TextView mTime;
    private final TextView mPlace;

    public PhotoInfoView(Context context) {
        this (context, null);
    }

    public PhotoInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public PhotoInfoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater.from(context).inflate(R.layout.unoptimized_photo_info_view, this);
        mVisibility = (ImageView) findViewById(R.id.visibility);
        mTime = (TextView) findViewById(R.id.time);
        mPlace = (TextView) findViewById(R.id.place);
    }
    
    public void setVisbility(boolean isPublic) {
        mVisibility.setImageResource(isPublic ? R.drawable.ic_public : R.drawable.ic_private);
    }
    
    public void setTime(String time) {
        mTime.setText(time);
    }
    
    public void setPlace(String place) {
        mPlace.setText(place);
    }

}
