/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.unoptimized;

import java.io.IOException;
import java.lang.ref.WeakReference;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;

public class LoadPhotoTask extends AsyncTask<String, Void, Bitmap> {
    private final AssetManager mAssets;
    private final WeakReference<PhotoView> mView;
    private String mName;

    public LoadPhotoTask(AssetManager assets, PhotoView view) {
        mAssets = assets;
        mView = new WeakReference<PhotoView>(view);
    }

    @Override
    protected void onPreExecute() {
        PhotoView image = mView.get();
        if (image != null) {
            image.setPhoto(null);
            image.showProgress();
        }   
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        mName = params[0];
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(mAssets.open(mName));
            final int scaledWidth = (bitmap.getWidth() * 400) / bitmap.getHeight();
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, scaledWidth, 400, true);
            bitmap.recycle();
            return scaled;
        } catch (IOException e) {
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        PhotoView image = mView.get();
        if (image != null) {
            // Ensure that the tag is same, as the view might have been recycled.
            final String picture = (String) image.getTag();

            if (TextUtils.equals(picture, mName)) {
                image.setPhoto(bitmap);
                image.hideProgress();
            }
        }
    }
}