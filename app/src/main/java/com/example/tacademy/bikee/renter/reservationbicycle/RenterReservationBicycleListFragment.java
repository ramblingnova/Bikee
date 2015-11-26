package com.example.tacademy.bikee.renter.reservationbicycle;

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
import com.example.tacademy.bikee.etc.dao.Comment;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.ReceiveObject1;
import com.example.tacademy.bikee.etc.dao.Reserve;
import com.example.tacademy.bikee.etc.dao.Reserve1;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.dao.Result1;
import com.example.tacademy.bikee.etc.manager.NetworkManager;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RenterReservationBicycleListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private Intent intent;
    private ListView lv;
    private RenterReservationBicycleAdapter adapter;

    public RenterReservationBicycleListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_renter_reservation_bicycle_list, container, false);

        lv = (ListView) v.findViewById(R.id.view_renter_reservation_bicycle_item_list_view);
        adapter = new RenterReservationBicycleAdapter();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        initData();

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RenterReservationBicycleItem item = (RenterReservationBicycleItem) lv.getItemAtPosition(position);
        Toast.makeText(getContext().getApplicationContext(), "position : " + position, Toast.LENGTH_SHORT).show();
        intent = new Intent(getActivity(), RenterReservationBicycleDetailInformationActivity.class);
        intent.putExtra("ID", item.getBicycleId());
        intent.putExtra("STATUS", item.getStatus());
        intent.putExtra("ENDDATE", item.getEndDate());
        getActivity().startActivity(intent);
    }

    private void initData() {
        NetworkManager.getInstance().selectReservationBicycle(new Callback<ReceiveObject1>() {
            @Override
            public void success(ReceiveObject1 receiveObject, Response response) {
                Log.i("result", "onResponse Success");
                List<Result1> results = receiveObject.getResult();
                for (Result1 result : results)
                    for (Reserve1 reserve : result.getReserve()) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm");
                        Log.i("result", "onResponse Bike ID : " + result.getBike().get_id()
                                        + ", Bike Image : " + result.getBike().getImage().getCdnUri() + "/mini_" + result.getBike().getImage().getFiles().get(0)
                                        + ", Status : " + reserve.getStatus()
                                        + ", Bike Name : " + result.getBike().getTitle()
                                        + ", Start Date : " + simpleDateFormat.format(reserve.getRentStart())
                                        + ", End Date : " + simpleDateFormat.format(reserve.getRentEnd())
                                        + ", Payment : " + result.getBike().getPrice().getMonth()
                        );
                        adapter.add(result.getBike().get_id(),
                                result.getBike().getImage().getCdnUri() + "/mini_" + result.getBike().getImage().getFiles().get(0),
                                result.getBike().getTitle(),
                                reserve.getStatus(),
                                simpleDateFormat.format(reserve.getRentStart()),
                                simpleDateFormat.format(reserve.getRentEnd()),
                                result.getBike().getPrice().getMonth()
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
