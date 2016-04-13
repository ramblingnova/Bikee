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

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.example.tacademy.bikee.renter.searchresult.SearchResultListItem;
import com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.FilteredBicycleDetailInformationActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchResultListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {
    // TODO : handle filter result, modify UI
    private SwipeRefreshLayout refreshLayout;
    @Bind(R.id.view_search_result_item_list_view)
    ListView lv;
    private SearchResultListAdapter adapter;
    private boolean lastItem = false;
    private String latitude = null;
    private String longitude = null;
    private int index;

    private static final String TAG = "SEARCH_R...T_ACTIVITY";

    public SearchResultListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result_list, container, false);

        ButterKnife.bind(this, view);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.view_search_result_item_refresh_list_view);
        refreshLayout.setOnRefreshListener(SearchResultListFragment.this);

        lv.setOnScrollListener(SearchResultListFragment.this);
        adapter = new SearchResultListAdapter();
        lv.setAdapter(adapter);

        index = 0;

        requestData();

        return view;
    }

    @Override
    public void onRefresh() {
        // 세션클리어안에서 리스트클리어, 다시 요청
        requestData();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (lastItem && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            requestData();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount > 0 && (firstVisibleItem + visibleItemCount >= totalItemCount - 1)) {
            lastItem = true;
        } else {
            lastItem = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @OnItemClick(R.id.view_search_result_item_list_view)
    void onClickItem(AdapterView<?> parent, View view, int position, long id) {
        SearchResultListItem item = (SearchResultListItem) parent.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), FilteredBicycleDetailInformationActivity.class);
        intent.putExtra("ID", item.getBicycleId());
        intent.putExtra("LATITUDE", item.getLatitude());
        intent.putExtra("LONGITUDE", item.getLongitude());
        getActivity().startActivity(intent);
    }

    public void onResponseLocation(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private void requestData() {
        // 전체자전거조회
        if ((null == latitude) || (null == longitude)) {
            latitude = PropertyManager.getInstance().getLatitude();
            longitude = PropertyManager.getInstance().getLongitude();
        }
        String lat = latitude;
        String lon = longitude;
        String filter = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        String start = simpleDateFormat.format(new Date());
        String end = simpleDateFormat.format(new Date());
        String type = "";
        String height = "";
        String component = "";
        Boolean smartlock = new Boolean(true);
//        filter = "{\"start\":\"" + start + "\",\"end\":\"" + end + "\",\"type\":\"B\",\"height\":\"04\"" + "}";
        // TODO : last index -1 입력 해결 필요
        filter = null;
                NetworkManager.getInstance().selectAllListBicycle(
                lon, lat, "" + index, filter, new Callback<ReceiveObject>() {
                    @Override
                    public void success(ReceiveObject receiveObject, Response response) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onResponse Index : " + receiveObject.getLastindex()
                                            + ", Code : " + receiveObject.getCode()
                                            + ", Success : " + receiveObject.isSuccess()
                                            + ", Msg : " + receiveObject.getMsg()
                                            + ", Error : "
                            );
                        index = receiveObject.getLastindex();
                        List<Result> results = receiveObject.getResult();
                        String imageURL;
                        for (Result result : results) {
                            if ((null == result.getImage().getCdnUri())
                                    || (null == result.getImage().getFiles())) {
                                imageURL = "";
                            } else {
                                imageURL = result.getImage().getCdnUri() + "/detail_" + result.getImage().getFiles().get(0);
                            }
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "List!! onResponse Id : " + result.get_id()
                                                + ", ImageURL : " + imageURL
                                                + ", Name : " + result.getTitle()
                                                + ", Type : " + result.getType()
                                                + ", Height : " + result.getHeight()
                                                + ", Price.month : " + result.getPrice().getMonth()
                                                + ", lat : " + result.getLoc().getCoordinates().get(1)
                                                + ", lon : " + result.getLoc().getCoordinates().get(0)
                                                + ", distance : " + result.getDistance()
                                );
                            adapter.add(
                                    result.get_id(),
                                    imageURL,
                                    result.getTitle(),
                                    result.getHeight(),
                                    result.getType(),
                                    "" + result.getPrice().getMonth(),
                                    "",
                                    result.getLoc().getCoordinates().get(1),
                                    result.getLoc().getCoordinates().get(0)
                            );
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
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onFailure Error : " + error.toString());
                    }
                });
    }
}
