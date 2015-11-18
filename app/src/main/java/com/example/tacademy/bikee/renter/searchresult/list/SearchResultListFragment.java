package com.example.tacademy.bikee.renter.searchresult.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.renter.searchresult.SearchResultINF;
import com.example.tacademy.bikee.renter.searchresult.SearchResultItem;
import com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.FilteredBicycleDetailInformationActivity;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchResultListFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {
    private SwipeRefreshLayout refreshLayout;
    private ListView lv;
    private SearchResultListAdapter adapter;
    private SearchResultINF searchResultINF;
    private boolean isLastItem = false;

    public void setSearchResultINF(SearchResultINF searchResultINF) {
        this.searchResultINF = searchResultINF;
    }

    public SearchResultListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result_list, container, false);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.view_search_result_item_refresh_list_view);
        refreshLayout.setOnRefreshListener(SearchResultListFragment.this);

        lv = (ListView) view.findViewById(R.id.view_search_result_item_list_view);
        lv.setOnScrollListener(SearchResultListFragment.this);
        lv.setOnItemClickListener(SearchResultListFragment.this);
        adapter = new SearchResultListAdapter();
        lv.setAdapter(adapter);

        getData();

        return view;
    }

    @Override
    public void onRefresh() {
        // 세션클리어안에서 리스트클리어, 다시 요청
        requestData();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (isLastItem && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            requestData();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount > 0 && (firstVisibleItem + visibleItemCount >= totalItemCount - 1)) {
            isLastItem = true;
        } else {
            isLastItem = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SearchResultItem item = (SearchResultItem) lv.getItemAtPosition(position);
        Toast.makeText(getContext().getApplicationContext(), "position : " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), FilteredBicycleDetailInformationActivity.class);
        getActivity().startActivity(intent);
    }

    private void getData() {
        if (searchResultINF != null) {
            if (searchResultINF.getData().size() != 0) {
                for (SearchResultItem searchResultItem : searchResultINF.getData()) {
                    adapter.add(searchResultItem.getBicycle_name(),
                            searchResultItem.getHeight(),
                            searchResultItem.getType(),
                            "",
                            "",
                            searchResultItem.getLatitude(),
                            searchResultItem.getLongitude()
                    );
                }
            } else {
                requestData();
            }
        }
    }

    private void requestData() {
        // 전체자전거조회
        String lat = "37.468501";
        String lon = "126.957913";
        String start = "2015/11/08 20:14:43";
        String end = "2015/11/12 20:14";
        String type = "03";
        String height = "A";
        String component = "01,02,03,04";
        Boolean smartlock = new Boolean(true);
        NetworkManager.getInstance().selectAllBicycle(
                lat, lon, start,
                end, type, height,
                component, smartlock, new Callback<ReceiveObject>() {
                    @Override
                    public void success(ReceiveObject receiveObject, Response response) {
                        Log.i("result", "onResponse Code : " + receiveObject.getCode()
                                        + ", Success : " + receiveObject.isSuccess()
                                        + ", Msg : " + receiveObject.getMsg()
                                        + ", Error : "
                        );
                        List<Result> results = receiveObject.getResult();
                        List<SearchResultItem> list = searchResultINF.getData();
                        for (Result result : results) {
                            Log.i("result", "onResponse Id : " + result.get_id()
                                            + ", Type : " + result.getType()
                                            + ", Height : " + result.getHeight()
                                            + ", Price.month : " + result.getPrice().getMonth()
                                            + ", lat : " + result.getLoc().getCoordinates().get(1)
                                            + ", lon : " + result.getLoc().getCoordinates().get(0)
                            );
                            if (searchResultINF != null) {
                                list.add(
                                        new SearchResultItem(
                                                result.getTitle(),
                                                result.getHeight(),
                                                result.getType(),
                                                "",
                                                "",
                                                result.getLoc().getCoordinates().get(1),
                                                result.getLoc().getCoordinates().get(0)
                                        )
                                );
                                adapter.add(
                                        result.getTitle(),
                                        result.getHeight(),
                                        result.getType(),
                                        "",
                                        "",
                                        result.getLoc().getCoordinates().get(1),
                                        result.getLoc().getCoordinates().get(0)
                                );
                            }
                        }
                        adapter.notifyDataSetChanged();
                        refreshLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.setRefreshing(false);
                            }
                        }, 2000);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("error", "onFailure Error : " + error.toString());
                    }
                });
    }
}
