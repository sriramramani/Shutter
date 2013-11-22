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

import com.sriramramani.shutter.R;

public class PhotoItemView extends LinearLayout {
    
    public final PhotoView image;
    public final OwnerView ownerView;
    public final NameView nameView;
    public final PhotoInfoView infoView;
    public final StatsView statsView;
    public final ContactLayout contactLayout;
    public final LikeButton likeButton;

    public PhotoItemView(Context context) {
        this (context, null);
    }

    public PhotoItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public PhotoItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setOrientation(LinearLayout.VERTICAL);
        setBackgroundResource(R.drawable.photo_background);

        LayoutInflater.from(context).inflate(R.layout.unoptimized_photo_item, this);
        image = (PhotoView) findViewById(R.id.photo_view);
        ownerView = (OwnerView) findViewById(R.id.owner);
        nameView = (NameView) findViewById(R.id.name_info);
        infoView = (PhotoInfoView) findViewById(R.id.photo_info);
        statsView = (StatsView) findViewById(R.id.stats);
        contactLayout = (ContactLayout) findViewById(R.id.contacts);
        likeButton = (LikeButton) findViewById(R.id.like);
    }
}
