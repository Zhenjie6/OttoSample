package org.study.ottosample;

import android.app.Fragment;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.otto.Subscribe;

import java.net.URL;


public class LocationMapFragment extends Fragment {
    private ImageView mImageView;
    private final String URL = "http://api.map.baidu.com/staticimage?width=1000&height=1000&center=%s,%s&zoom=15";

    private DownloadTask mTask;

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mImageView = new ImageView(getActivity());

        return mImageView;
    }

    private class ImageAvailableEvent {
        public Drawable image;
        public ImageAvailableEvent(Drawable img) {
            image = img;
        }
    }

    @Subscribe
    public void onLocationMoveEvent(LocationMoveEvent event) {
        mTask = new DownloadTask();
        String downloadUrl = String.format(URL, event.longitude, event.latitude);
        mTask.execute(downloadUrl);
    }

    @Subscribe
    public void onImageAvailableEvent(ImageAvailableEvent event) {
        if(event.image != null) {
            mImageView.setImageDrawable(event.image);
        }
    }


    private class DownloadTask extends AsyncTask<String, Void, Drawable> {

        @Override
        protected Drawable doInBackground(String... params) {
            String downloadUrl = params[0];
            try {
                return BitmapDrawable.createFromStream(new URL(downloadUrl).openStream(), "bitmap.jpg");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);
            //show drawable
            //mImageView.setImageDrawable(drawable);
            //post Image available event
            BusProvider.getInstance().post(new ImageAvailableEvent(drawable));
        }
    }
}
