package com.example.tacademy.bikee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SearchResultFragment extends Fragment {

    private static final String SEARCH_RESULT_LIST_FRAGMENT_TAG = "search_result_list_fragment";
    private static final String SEARCH_RESULT_MAP_FRAGMENT_TAG = "search_result_map_fragment";
    Fragment searchResultMapFragmnet, searchResultListFragmnet, current;
    Switch sw;

    public SearchResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_search_result, container, false);
        searchResultMapFragmnet = new SearchResultMapFragment();
        searchResultListFragmnet = new SearchResultListFragment();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.container, searchResultMapFragmnet, SEARCH_RESULT_MAP_FRAGMENT_TAG);
        ft.commit();
        current = searchResultMapFragmnet;
        sw = (Switch) v.findViewById(R.id.search_bar_switch);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getContext().getApplicationContext(), "isChecked : " + isChecked, Toast.LENGTH_SHORT).show();
                if (!isChecked) {
                    Fragment old = getChildFragmentManager().findFragmentByTag(SEARCH_RESULT_MAP_FRAGMENT_TAG);
                    if (old == null) {
                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                        if (current != null) {
                            ft.detach(current);
                        }
                        ft.add(R.id.container, searchResultMapFragmnet, SEARCH_RESULT_MAP_FRAGMENT_TAG);
                        ft.commit();
                        current = searchResultMapFragmnet;
                    } else if (current != searchResultMapFragmnet) {
                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                        ft.detach(current);
                        ft.attach(searchResultMapFragmnet);
                        ft.commit();
                        current = searchResultMapFragmnet;
                    }
                } else {
                    Fragment old = getChildFragmentManager().findFragmentByTag(SEARCH_RESULT_LIST_FRAGMENT_TAG);
                    if (old == null) {
                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                        if (current != null) {
                            ft.detach(current);
                        }
                        ft.add(R.id.container, searchResultListFragmnet, SEARCH_RESULT_LIST_FRAGMENT_TAG);
                        ft.commit();
                        current = searchResultListFragmnet;
                    } else if (current != searchResultListFragmnet) {
                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                        ft.detach(current);
                        ft.attach(searchResultListFragmnet);
                        ft.commit();
                        current = searchResultListFragmnet;
                    }
                }
            }
        });
//        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Toast.makeText(getContext().getApplicationContext(), "isChecked : " + isChecked, Toast.LENGTH_SHORT).show();
//                if (!isChecked) {
//                    Fragment old = getChildFragmentManager().findFragmentByTag(SEARCH_RESULT_MAP_FRAGMENT_TAG);
//                    if (old == null) {
//                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//                        if (current != null) {
//                            ft.detach(current);
//                        }
//                        ft.add(R.id.container, searchResultMapFragmnet, SEARCH_RESULT_MAP_FRAGMENT_TAG);
//                        ft.commit();
//                        current = searchResultMapFragmnet;
//                    } else if (old != current) {
//                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//                        ft.detach(current);
//                        ft.attach(searchResultMapFragmnet);
//                        ft.commit();
//                        current = searchResultMapFragmnet;
//                    }
//                } else {
//                    Fragment old = getChildFragmentManager().findFragmentByTag(SEARCH_RESULT_LIST_FRAGMENT_TAG);
//                    if (old == null) {
//                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//                        if (current != null) {
//                            ft.detach(current);
//                        }
//                        ft.add(R.id.container, searchResultListFragmnet, SEARCH_RESULT_LIST_FRAGMENT_TAG);
//                        ft.commit();
//                        current = searchResultListFragmnet;
//                    } else if (current != searchResultListFragmnet) {
//                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//                        ft.detach(current);
//                        ft.attach(searchResultListFragmnet);
//                        ft.commit();
//                        current = searchResultListFragmnet;
//                    }
//                }
//            }
//        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
