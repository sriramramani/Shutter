/**
 * Copyright: Sriram Ramani 2012-13.
 */

package com.sriramramani.shutter.optimized;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sriramramani.shutter.R;

public class PhotoAdapter extends BaseAdapter {

    public static class Contact {
        final String name;
        final String handle;
        final String picture;
        
        public Contact(String name, String handle, String picture) {
            this.name = name;
            this.handle = handle;
            this.picture = picture;
        }
    }
    
    private static class Photo {
        public String picture;
        public Contact owner;
        public int likes;
        public int comments;
        public String time;
        public String place;
        public boolean isLiked;
        public boolean isPublic;
        public Contact[] contacts;
    }

    private final Resources mResources;
    private List<Photo> mPhotos;
    private final List<Contact> mContacts;
    
    private final GetPhotosTask mPhotosTask = new GetPhotosTask();

    public PhotoAdapter(Resources resources) {
        mResources = resources;

        mContacts = new ArrayList<Contact>();
        mContacts.add(new Contact("Sriram Ramani", "sriramramani", "c1.png"));
        mContacts.add(new Contact("Sruthi Ramani", "sruthiramani", "c2.png"));
        mContacts.add(new Contact("Ayashok Photography", "ayashok", "c3.png"));
        mContacts.add(new Contact("Sanchit Gupta", "sanchitgupta", "c4.png"));
        mContacts.add(new Contact("Ramya Ashok", "rummy", "c5.png"));
        mContacts.add(new Contact("Steve Wildlife", "steveseesmonkey", "c6.png"));
        
        mPhotosTask.execute();
    }
    
    @Override
    public int getCount() {
        return (mPhotos == null ? 0 : mPhotos.size());
    }

    @Override
    public Object getItem(int position) {
        return (mPhotos == null ? null : mPhotos.get(position));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PhotoItemView view;
        if (convertView == null) {
            view = new PhotoItemView(parent.getContext(), null);
            convertView = view;
        } else {
            view = (PhotoItemView) convertView;
        }

        final Photo photo = (Photo) getItem(position);

        view.image.loadPhoto(photo.picture);

        final Contact owner = photo.owner;
        view.ownerView.setOwnerImage(owner.picture);

        view.nameView.setValues(owner.name, owner.handle);
        view.infoView.setValues(photo.isPublic, photo.time, photo.place);
        
        view.statsView.setStats(photo.likes, photo.comments);

        view.contactLayout.setContacts(photo.contacts);

        view.likeButton.setLiked(photo.isLiked);
        final LikeButton likeButton = view.likeButton;
        view.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo.isLiked = !photo.isLiked;
                likeButton.setLiked(photo.isLiked);
            } 
        });

        return view;
    }

    private class GetPhotosTask extends AsyncTask<Void, Void, List<Photo>> {

        @Override
        protected List<Photo> doInBackground(Void... params) {
            List<Photo> photosList = new ArrayList<Photo>();
            BufferedReader reader = null;
            try {
                String json = "";
                String line = "";
                InputStream stream = mResources.openRawResource(R.raw.photos);
                reader = new BufferedReader(new InputStreamReader(stream));
                while ((line = reader.readLine()) != null) {
                    json += line;
                }

                JSONObject object = (JSONObject) new JSONTokener(json).nextValue();
                JSONArray photos = object.getJSONArray("photos");
                final int length = photos.length();
                for (int i = 0; i < length; i++) {
                    JSONObject onePhoto = photos.getJSONObject(i);
                    Photo photo = new Photo();
                    photo.picture = onePhoto.getString("photo");
                    photo.owner = mContacts.get(onePhoto.getInt("owner") - 1);
                    photo.likes = onePhoto.getInt("likes");
                    photo.comments = onePhoto.getInt("comments");
                    photo.time = onePhoto.getString("time");
                    photo.place = onePhoto.getString("place");
                    photo.isLiked = onePhoto.getBoolean("isLiked");
                    photo.isPublic = onePhoto.getBoolean("isPublic");

                    JSONArray contacts = onePhoto.getJSONArray("contacts");
                    final int contactsLength = contacts.length();
                    if (contactsLength > 0) {
                        photo.contacts = new Contact[contactsLength];
                        for (int j = 0; j < contactsLength; j++) {
                            photo.contacts[j] = mContacts.get(contacts.getInt(j) - 1);
                        }
                    }

                    photosList.add(photo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) { }
                }
            }

            return photosList;
        }
        
        @Override
        protected void onPostExecute(List<Photo> photos) {
            mPhotos = photos;
            notifyDataSetChanged();
        }
    }
}
