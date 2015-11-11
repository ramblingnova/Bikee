package com.example.tacademy.bikee.renter.reservationbicycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.renter.searchresult.list.SearchResultAdapter;
import com.example.tacademy.bikee.renter.searchresult.list.SearchResultItem;

public class RenterReservationBicycleListFragment extends Fragment {

    ListView lv;
    RenterReservationBicycleAdapter adapter;

    public RenterReservationBicycleListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_renter_reservation_bicycle_list, container, false);
        lv = (ListView)v.findViewById(R.id.view_renter_reservation_bicycle_item_list_view);
        adapter = new RenterReservationBicycleAdapter();
        lv.setAdapter(adapter);
        initData();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RenterReservationBicycleItem item = (RenterReservationBicycleItem) lv.getItemAtPosition(position);
                Toast.makeText(getContext().getApplicationContext(), "position : " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), RenterReservationBicycleDetailInformationActivity.class);
                // TODO
                intent.putExtra("STATE", 1);
                getActivity().startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private void initData() {
        for(int i = 0; i < 30; i++) {
            adapter.add("" + i, "" + i, "" + i, (i%3));
        }
    }
}
