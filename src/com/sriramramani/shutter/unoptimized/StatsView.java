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

public class StatsView extends LinearLayout {

    private final TextView mLikes;
    private final TextView mComments;

    public StatsView(Context context) {
        this (context, null);
    }

    public StatsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public StatsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater.from(context).inflate(R.layout.unoptimized_stats_view, this);
        mLikes = (TextView) findViewById(R.id.likes);
        mComments = (TextView) findViewById(R.id.comments);
    }
    
    public void setLikes(int likes) {
        mLikes.setText("" + likes);
    }
    
    public void setComments(int comments) {
        mComments.setText("" + comments);
    }

}
