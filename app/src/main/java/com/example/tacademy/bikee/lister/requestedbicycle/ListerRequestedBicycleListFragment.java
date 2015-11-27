package com.example.tacademy.bikee.lister.requestedbicycle;


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
import com.example.tacademy.bikee.etc.dao.ReceiveObject1;
import com.example.tacademy.bikee.etc.dao.Reserve;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.dao.Result1;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.renter.reservationbicycle.RenterReservationBicycleDetailInformationActivity;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ListerRequestedBicycleListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private Intent intent;
    private ListView lv;
    private ListerRequestedBicycleAdapter adapter;
    private String status;
    private String imageURL;
    private String type;
    private String height;
    private double latitude;
    private double longitude;
    private String startDate;
    private String endDate;
    private int price;

    public ListerRequestedBicycleListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lister_requested_bicycle_list, container, false);

        lv = (ListView) v.findViewById(R.id.fragment_lister_requested_bicycle_list_list_view);
        adapter = new ListerRequestedBicycleAdapter();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        initData();

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListerRequestedBicycleItem item = (ListerRequestedBicycleItem) lv.getItemAtPosition(position);
        Toast.makeText(getContext().getApplicationContext(), "position : " + position, Toast.LENGTH_SHORT).show();

        intent = new Intent(getActivity(), ListerRequestedBicycleDetailInformationActivity.class);
        intent.putExtra("BICYCLEURL", imageURL);
        intent.putExtra("TYPE", type);
        intent.putExtra("HEIGHT", height);
        intent.putExtra("LATITUDE", latitude);
        intent.putExtra("LONGITUDE", longitude);
        intent.putExtra("STARTDATE", startDate);
        intent.putExtra("ENDDATE", endDate);
        intent.putExtra("PRICE", price);
        intent.putExtra("STATUS", status);
        getActivity().startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private void initData() {
        NetworkManager.getInstance().selectRequestedBicycle(new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                Log.i("result", "onResponse Success");
                List<Result> results = receiveObject.getResult();
                for (Result result : results)
                    for (Reserve reserve : result.getReserve()) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                        String renterImageURL;
                        if ((null == reserve.getRenter().getImage())
                                || (null == reserve.getRenter().getImage().getCdnUri())
                                || (null == reserve.getRenter().getImage().getFiles())) {
                            renterImageURL = "";
                        } else {
                            renterImageURL = "https://" + reserve.getRenter().getImage().getCdnUri() + "/mini_" + reserve.getRenter().getImage().getFiles().get(0);
                        }
                        String bicycleImageURL;
                        if ((null == result.getBike().getImage())
                                || (null == result.getBike().getImage().getCdnUri())
                                || (null == result.getBike().getImage().getFiles())) {
                            bicycleImageURL = "";
                        } else {
                            bicycleImageURL = result.getBike().getImage().getCdnUri() + "/mini_" + result.getBike().getImage().getFiles().get(0);
                        }
                        Log.i("result", "onResponse Renter Image : " + renterImageURL
                                        + ", Status : " + reserve.getStatus()
                                        + ", Renter Name : " + reserve.getRenter().getName()
                                        + ", Bike Name : " + result.getBike().getTitle()
                                        + ", Start Date : " + simpleDateFormat.format(reserve.getRentStart())
                                        + ", End Date : " + simpleDateFormat.format(reserve.getRentEnd())
                                        + ", Payment : " + result.getBike().getPrice().getMonth()
                                        + ", Bike Image : " + bicycleImageURL
                                        + ", Type : " + result.getBike().getType()
                                        + ", Height : " + result.getBike().getHeight()
                                        + ", Latitude : " + result.getBike().getLoc().getCoordinates().get(1)
                                        + ", Longitude : " + result.getBike().getLoc().getCoordinates().get(0)
                        );
                        status = reserve.getStatus();
                        imageURL = bicycleImageURL;
                        type = result.getBike().getType();
                        height = result.getBike().getHeight();
                        latitude = result.getBike().getLoc().getCoordinates().get(1);
                        longitude = result.getBike().getLoc().getCoordinates().get(0);
                        startDate = simpleDateFormat.format(reserve.getRentStart());
                        endDate = simpleDateFormat.format(reserve.getRentEnd());
                        price = result.getBike().getPrice().getMonth();

                        adapter.add(renterImageURL,
                                reserve.getStatus(),
                                reserve.getRenter().getName(),
                                result.getBike().getTitle(),
                                simpleDateFormat.format(reserve.getRentStart()),
                                simpleDateFormat.format(reserve.getRentEnd()),
                                "" + result.getBike().getPrice().getMonth()
                        );
                    }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", "onFailure Error : " + error.toString());
            }
        });
    }
}
