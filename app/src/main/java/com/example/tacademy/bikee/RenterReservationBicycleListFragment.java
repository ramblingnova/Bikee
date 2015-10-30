package com.example.tacademy.bikee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RenterReservationBicycleListFragment extends Fragment {

    public RenterReservationBicycleListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_renter_reservation_bicycle_list, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
