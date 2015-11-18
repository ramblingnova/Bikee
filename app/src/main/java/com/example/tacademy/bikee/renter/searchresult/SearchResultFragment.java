package com.example.tacademy.bikee.renter.searchresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.renter.searchresult.filter.FilterActivity;
import com.example.tacademy.bikee.renter.searchresult.list.SearchResultListFragment;
import com.example.tacademy.bikee.renter.searchresult.map.SearchResultMapFragment;

public class SearchResultFragment extends Fragment implements View.OnClickListener {

    private static final String SEARCH_RESULT_LIST_FRAGMENT_TAG = "search_result_list_fragment";
    private static final String SEARCH_RESULT_MAP_FRAGMENT_TAG = "search_result_map_fragment";

    Fragment current;
    SearchSwitchView searchSwitchView;
    SearchResultListFragment searchResultListFragment;
    SearchResultMapFragment searchResultMapFragment;

    public SearchResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_result, container, false);

        searchResultListFragment = new SearchResultListFragment();
        searchResultMapFragment = new SearchResultMapFragment();

        searchSwitchView = (SearchSwitchView)v.findViewById(R.id.search_bar_switch);
        searchSwitchView.setOnCheckedListener(new SearchSwitchView.OnCheckedListener() {
            @Override
            public void onChecked(boolean isChecked) {
                fragmentChange(isChecked);
            }
        });
        ImageView iv = (ImageView) v.findViewById(R.id.fragment_search_result_filter_image_view);
        iv.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_search_result_filter_image_view: {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                getActivity().startActivityForResult(intent, 0);
                break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!searchSwitchView.isChecked())
            fragmentChange(false);
    }

    public void fragmentChange(boolean isChecked) {
        Toast.makeText(getContext().getApplicationContext(), "isChecked : " + isChecked, Toast.LENGTH_SHORT).show();
        String CURRENT_TAG = (isChecked) ? SEARCH_RESULT_LIST_FRAGMENT_TAG : SEARCH_RESULT_MAP_FRAGMENT_TAG;
        Fragment old = getChildFragmentManager().findFragmentByTag(CURRENT_TAG);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

        if (current != null && current != old) {
            ft.detach(current);
        }

        if (old == null) {
            current = (isChecked) ? searchResultListFragment : searchResultMapFragment;
            ft.add(R.id.container, current, CURRENT_TAG);
        } else if (current != old) {
            ft.attach(current = old);
        }

        ft.commit();
    }

    boolean b = false;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case FilterActivity.RESULT_OK:
                b = true;
                break;
            case FilterActivity.RESULT_CANCEL:
                break;
        }
    }
}
