/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.unoptimized;

import java.io.IOException;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * AsyncTask to load contact photos.
 */
public class LoadContactTask extends AsyncTask<String, Void, Bitmap> {
    private final AssetManager mAssets;
    private final OnLoadContactPhotoListener mListener;
    private String mPicture;

    public static interface OnLoadContactPhotoListener {
        public void onLoadContactPhoto(String picture, Bitmap bitmap);
    }

    public LoadContactTask(AssetManager assets, OnLoadContactPhotoListener listener) {
        mAssets = assets;
        mListener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        mPicture = params[0];
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(mAssets.open(mPicture));
        } catch (IOException e) {
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        mListener.onLoadContactPhoto(mPicture, bitmap);
    }
}