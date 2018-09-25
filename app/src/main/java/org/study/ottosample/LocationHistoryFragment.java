package org.study.ottosample;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class LocationHistoryFragment extends ListFragment {
    private final List<String> locationEvents = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, locationEvents);
        setListAdapter(adapter);
    }

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

    @Subscribe
    public void onLocationMoveEvent(LocationMoveEvent event) {
        float lng = event.longitude;
        float lat = event.latitude;
        locationEvents.add(String.format("[%s, %s]", lng, lat));
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onLocationClearEvent(LocationClearEvent event) {
        locationEvents.clear();
        adapter.notifyDataSetChanged();
    }
}
