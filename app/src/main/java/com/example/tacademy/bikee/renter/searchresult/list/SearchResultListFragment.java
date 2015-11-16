package com.example.tacademy.bikee.renter.searchresult.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.FilteredBicycleDetailInformationActivity;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchResultListFragment extends Fragment {

    ListView lv;
    SearchResultAdapter adapter;

    public SearchResultListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_result_list, container, false);
        lv = (ListView) v.findViewById(R.id.view_search_result_item_list_view);
        adapter = new SearchResultAdapter();
        lv.setAdapter(adapter);
        initData();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchResultItem item = (SearchResultItem) lv.getItemAtPosition(position);
                Toast.makeText(getContext().getApplicationContext(), "position : " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), FilteredBicycleDetailInformationActivity.class);
                // TODO
                getActivity().startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private void initData() {
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
                lat,
                lon,
                start,
                end,
                type,
                height,
                component,
                smartlock,
                new Callback<ReceiveObject>() {
                    @Override
                    public void success(ReceiveObject receiveObject, Response response) {
                        Log.i("result", "onResponse Code : " + receiveObject.getCode() + ", Success : " + receiveObject.isSuccess() + ", Msg : " + receiveObject.getMsg() + ", Error : ");
                        List<Result> results = receiveObject.getResult();
                        for (Result result : results) {
                            Log.i("result", "onResponse Id : " + result.get_id() + ", Type : " + result.getType() + ", Height : " + result.getHeight() + ", Price.month : " + result.getPrice().getMonth());
                            adapter.add(result.getTitle(), result.getHeight(), result.getType(), "", "");
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("error", "onFailure Error : " + error.toString());
                    }
                });
    }
}
