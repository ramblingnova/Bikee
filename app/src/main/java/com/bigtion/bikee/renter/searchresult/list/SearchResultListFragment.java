package com.bigtion.bikee.renter.searchresult.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigtion.bikee.common.content.ContentActivity;
import com.bigtion.bikee.etc.interfaces.OnAdapterClickListener;
import com.bigtion.bikee.etc.manager.NetworkManager;
import com.bigtion.bikee.etc.manager.PropertyManager;
import com.bigtion.bikee.renter.searchresult.SearchResultItem;
import com.bigtion.bikee.renter.searchresult.filter.FilterActivity;
import com.bigtion.bikee.BuildConfig;
import com.bigtion.bikee.R;
import com.bigtion.bikee.etc.dao.ReceiveObject;
import com.bigtion.bikee.etc.dao.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnAdapterClickListener {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SearchResultAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private boolean lastItem = false;
    private String latitude = null;
    private String longitude = null;
    private int index;
    private String filter;

    public static final int from = 4;
    private static final String TAG = "SEARCH_RESULT_LIST_F";

    public SearchResultListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result_list, container, false);

        ButterKnife.bind(this, view);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.view_search_result_item_refresh_list_view);
        refreshLayout.setOnRefreshListener(SearchResultListFragment.this);

        recyclerView = (RecyclerView) view.findViewById(R.id.view_search_result_item_list_view);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new SearchResultAdapter();
        adapter.setOnAdapterClickListener(this);

        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(onScrollListener);

        index = 0;

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.clear();
        index = 0;
        requestData();
    }

    @Override
    public void onRefresh() {
        // 세션클리어안에서 리스트클리어, 다시 요청
        requestData();
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (lastItem && newState == RecyclerView.SCROLL_STATE_IDLE) {
                requestData();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if ((layoutManager.findLastVisibleItemPosition() == adapter.getItemCount() - 1)
                    && recyclerView.getChildCount() > 0) {
                lastItem = true;
            } else {
                lastItem = false;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == FilterActivity.FILTER_ACTIVITY) {
            Log.d(TAG, "onActivityResult");

            List<String> f = new ArrayList<String>();
            latitude = data.getStringExtra("LATITUDE");
            longitude = data.getStringExtra("LONGITUDE");

            filter = "{";
            if (data.getStringExtra("START_DATE") != null) {
                f.add("\"start\":\"" + data.getStringExtra("START_DATE") + "\"");
            }
            if (data.getStringExtra("END_DATE") != null)
                f.add("\"end\":\"" + data.getStringExtra("END_DATE") + "\"");
            if (data.getStringExtra("TYPE") != null)
                f.add("\"type\":\"" + data.getStringExtra("TYPE") + "\"");
            if (data.getStringExtra("HEIGHT") != null)
                f.add("\"height\":\"" + data.getStringExtra("HEIGHT") + "\"");
            f.add("\"smartlock\":" + data.getBooleanExtra("SMART_LOCK", false));
            for (int i = 0; i < f.size(); i++)
                filter += (i == 0 ? "" : ",") + f.get(i);
            filter += "}";

            index = 0;
        }
    }

    @Override
    public void onAdapterClick(View view, Object item) {
        Intent intent = new Intent(getActivity(), ContentActivity.class);
        intent.putExtra("FROM", from);
        intent.putExtra("BICYCLE_ID", ((SearchResultItem) item).getBicycleId());
        intent.putExtra("BICYCLE_LATITUDE", ((SearchResultItem) item).getLatitude());
        intent.putExtra("BICYCLE_LONGITUDE", ((SearchResultItem) item).getLongitude());
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
        NetworkManager.getInstance().selectAllListBicycle(
                lon,
                lat,
                "" + index,
                filter,
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onResponse Index : " + receiveObject.getLastindex()
                                            + ", Code : " + receiveObject.getCode()
                                            + ", Success : " + receiveObject.isSuccess()
                                            + ", Msg : " + receiveObject.getMsg()
                                            + ", Error : "
                            );
                        List<Result> results = receiveObject.getResult();
                        String imageURL;
                        for (Result result : results) {
                            index++;
                            if ((null == result.getImage().getCdnUri())
                                    || (null == result.getImage().getFiles())) {
                                imageURL = "";
                            } else {
                                imageURL = result.getImage().getCdnUri() + result.getImage().getFiles().get(0);
                            }
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "List!! onResponse Id : " + result.get_id()
                                                + ", ImageURL : " + imageURL
                                                + ", Name : " + result.getTitle()
                                                + ", Type : " + result.getType()
                                                + ", Height : " + result.getHeight()
                                                + ", Price.day : " + result.getPrice().getDay()
                                                + ", lat : " + result.getLoc().getCoordinates().get(1)
                                                + ", lon : " + result.getLoc().getCoordinates().get(0)
                                                + ", distance : " + result.getDistance()
                                );
                            adapter.add(
                                    new SearchResultItem(
                                            result.get_id(),
                                            imageURL,
                                            result.getTitle(),
                                            result.getHeight(),
                                            result.getType(),
                                            "" + result.getPrice().getDay(),
                                            result.getDistance(),
                                            result.getLoc().getCoordinates().get(1),
                                            result.getLoc().getCoordinates().get(0)
                                    )
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
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onFailure Error : " + t.toString());
                    }
                });
    }
}
