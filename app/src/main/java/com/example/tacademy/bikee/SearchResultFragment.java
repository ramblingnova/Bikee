package com.example.tacademy.bikee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SearchResultFragment extends Fragment implements View.OnClickListener {

    private static final String SEARCH_RESULT_LIST_FRAGMENT_TAG = "search_result_list_fragment";
    private static final String SEARCH_RESULT_MAP_FRAGMENT_TAG = "search_result_map_fragment";

    Fragment current;
    Switch sw;

    public SearchResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_result, container, false);
        sw = (Switch) v.findViewById(R.id.search_bar_switch);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fragmentChange(isChecked);
            }
        });
        Button btn = (Button)v.findViewById(R.id.fragment_search_result_filter_button);
        btn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_search_result_filter_button: {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                getActivity().startActivityForResult(intent, 0);
                break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!sw.isChecked())
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
            current = (isChecked) ? new SearchResultListFragment() : new SearchResultMapFragment();
            ft.add(R.id.container, current, CURRENT_TAG);
        } else if (current != old) {
            ft.attach(current = old);
        }

        ft.commit();
    }

    boolean b = false;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (resultCode){
            case FilterActivity.RESULT_OK:
                b = true;
                break;
            case FilterActivity.RESULT_CANCEL:
                break;
        }
    }
}
