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

public class ListerRequestedBicycleListFragment extends Fragment {

    private Intent intent;
    private ListView lv;
    private ListerRequestedBicycleAdapter adapter;

    public ListerRequestedBicycleListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lister_requested_bicycle_list, container, false);
        lv = (ListView) v.findViewById(R.id.fragment_lister_requested_bicycle_list_list_view);
        adapter = new ListerRequestedBicycleAdapter();
        lv.setAdapter(adapter);
        initData();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListerRequestedBicycleItem item = (ListerRequestedBicycleItem) lv.getItemAtPosition(position);
                Toast.makeText(getContext().getApplicationContext(), "position : " + position, Toast.LENGTH_SHORT).show();

                intent = new Intent(getActivity(), ListerRequestedBicycleDetailInformationActivity.class);
                intent.putExtra("STATE", 2);
                getActivity().startActivity(intent);
            }
        });

        return v;
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
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm");
                        String imageURL;
                        if ((null == reserve.getRenter().getImage().getCdnUri()) || (null == result.getRenter().getImage().getFiles())) {
                            imageURL = "";
                        } else {
                            imageURL = "https://" + result.getRenter().getImage().getCdnUri() + "/mini_" + result.getRenter().getImage().getFiles().get(0);
                        }
                        Log.i("result", "onResponse Renter Image : " + imageURL
                                        + ", Status : " + reserve.getStatus()
                                        + ", Renter Name : " + result.getRenter().getName()
                                        + ", Bike Name : " + result.getBike().getTitle()
                                        + ", Start Date : " + simpleDateFormat.format(reserve.getRentStart())
                                        + ", End Date : " + simpleDateFormat.format(reserve.getRentEnd())
                                        + ", Payment : " + result.getBike().getPrice().getMonth()
                        );
                        adapter.add(imageURL,
                                reserve.getStatus(),
                                result.getRenter().getName(),
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
