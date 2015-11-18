package com.example.tacademy.bikee.renter.searchresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.renter.searchresult.filter.FilterActivity;
import com.example.tacademy.bikee.renter.searchresult.list.SearchResultListFragment;
import com.example.tacademy.bikee.renter.searchresult.map.SearchResultMapFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchResultFragment extends Fragment implements SearchSwitchView.OnCheckedListener, View.OnClickListener, SearchResultINF {
    private static final String SEARCH_RESULT_LIST_FRAGMENT_TAG = "search_result_list_fragment";
    private static final String SEARCH_RESULT_MAP_FRAGMENT_TAG = "search_result_map_fragment";

    private Fragment currentFragment;
    private SearchResultListFragment searchResultListFragment;
    private SearchResultMapFragment searchResultMapFragment;
    private SearchSwitchView searchSwitchView;
    private ImageView imageView;
    private List<SearchResultItem> list;
    private boolean b = false;

    public SearchResultFragment() {
        Toast.makeText(MyApplication.getmContext(), "SearchResultFragment() Constructor", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_result, container, false);

        searchResultListFragment = new SearchResultListFragment();
        searchResultListFragment.setSearchResultINF(this);
        searchResultMapFragment = new SearchResultMapFragment();
        searchResultMapFragment.setSearchResultINF(this);

        searchSwitchView = (SearchSwitchView) v.findViewById(R.id.search_bar_switch);
        searchSwitchView.setOnCheckedListener(this);

        imageView = (ImageView) v.findViewById(R.id.fragment_search_result_filter_image_view);
        imageView.setOnClickListener(this);

        init();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!searchSwitchView.isChecked())
            fragmentChange(false);
    }

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

    @Override
    public void onChecked(boolean isChecked) {
        fragmentChange(isChecked);
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
    public List<SearchResultItem> getData() {
        return list;
    }

    private void fragmentChange(boolean isChecked) {
        Toast.makeText(getContext().getApplicationContext(), "isChecked : " + isChecked, Toast.LENGTH_SHORT).show();
        String CURRENT_TAG = (isChecked) ? SEARCH_RESULT_LIST_FRAGMENT_TAG : SEARCH_RESULT_MAP_FRAGMENT_TAG;
        Fragment old = getChildFragmentManager().findFragmentByTag(CURRENT_TAG);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

        if (currentFragment != null && currentFragment != old) {
            ft.detach(currentFragment);
        }

        if (old == null) {
            currentFragment = (isChecked) ? searchResultListFragment : searchResultMapFragment;
            ft.add(R.id.container, currentFragment, CURRENT_TAG);
        } else if (currentFragment != old) {
            ft.attach(currentFragment = old);
        }

        ft.commit();
    }

    private void init() {
        Toast.makeText(MyApplication.getmContext(), "SearchResultFragment().sessionClear()", Toast.LENGTH_SHORT).show();
        NetworkManager.getInstance().sessionClear(new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                Log.i("result", "onResponse Code : " + receiveObject.getCode()
                                + ", Success : " + receiveObject.isSuccess()
                                + ", Msg : " + receiveObject.getMsg()
                                + ", Error : "
                );
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", "onFailure Error : " + error.toString());
            }
        });

        list = new ArrayList<>();
    }
}
