package com.example.tacademy.bikee.lister.requestedbicycle;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.renter.reservationbicycle.RenterReservationBicycleDetailInformationActivity;

public class ListerRequestedBicycleListFragment extends Fragment {

    private Intent intent;
    private ListView lv;
    private ListerRequestedBicycleAdapter adapter;

    public ListerRequestedBicycleListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lister_requested_bicycle_list, container, false);
        lv = (ListView) v.findViewById(R.id.fragment_lister_requested_bicycle_list_list_view);
        adapter = new ListerRequestedBicycleAdapter();
        lv.setAdapter(adapter);
        initData();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListerRequestedBicycleItem item = (ListerRequestedBicycleItem) lv.getItemAtPosition(position);
                Toast.makeText(getContext().getApplicationContext(), "position : " + position, Toast.LENGTH_SHORT).show();

                intent = new Intent(getActivity(), ListerRequestedBicycleDetailInformationActivity.class);
                intent.putExtra("STATE", 2);
                getActivity().startActivity(intent);
            }
        });

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            adapter.add("" + i, "" + i, "" + i, "" + i, "" + i);
        }
    }
}
