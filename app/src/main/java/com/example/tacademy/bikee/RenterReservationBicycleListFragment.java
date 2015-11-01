package com.example.tacademy.bikee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RenterReservationBicycleListFragment extends Fragment {

    ListView lv;
    SearchResultAdapter adapter;

    public RenterReservationBicycleListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_renter_reservation_bicycle_list, container, false);
        lv = (ListView)v.findViewById(R.id.view_renter_reservation_bicycle_item_list_view);
        adapter = new SearchResultAdapter();
        lv.setAdapter(adapter);
        initData();
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private void initData() {
        for(int i = 0; i < 10; i++) {
            adapter.add("" + i, "" + i, "" + i);
        }
    }
}
