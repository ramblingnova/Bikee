package com.example.tacademy.bikee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchResultMapFragment extends Fragment {

    public SearchResultMapFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result_map, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
