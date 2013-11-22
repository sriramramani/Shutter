/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.unoptimized;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sriramramani.shutter.R;

public class ContactView extends FrameLayout {

    private final ImageView mContact;

    public ContactView(Context context) {
        this (context, null);
    }

    public ContactView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater.from(context).inflate(R.layout.unoptimized_contact_view, this);
        mContact = (ImageView) findViewById(R.id.contact_image);

        setAlpha(0.7f);
    }

    public void setContactImage(Bitmap bitmap) {
        if (bitmap == null) {
            mContact.setImageResource(R.drawable.profile);
        } else {
            mContact.setImageBitmap(bitmap);
        }
    }

}
