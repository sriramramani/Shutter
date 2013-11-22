/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.optimized;

import java.io.IOException;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;

/**
 * AsyncTask to load contact photos.
 */
public class LoadContactTask extends AsyncTask<String, Void, Bitmap> {
    private final AssetManager mAssets;
    private final OnLoadContactPhotoListener mListener;
    private String mPicture;
    
    private static LruCache<String, Bitmap> sBitmapCache = new LruCache<String, Bitmap>(1 * 1024 * 1024) {
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }
    };
    
    public static Bitmap getContact(String picture) {
        synchronized(sBitmapCache) {
            return sBitmapCache.get(picture);
        }
    }

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
        synchronized(sBitmapCache) {
            sBitmapCache.put(mPicture, bitmap);
        }
        mListener.onLoadContactPhoto(mPicture, bitmap);
    }
}