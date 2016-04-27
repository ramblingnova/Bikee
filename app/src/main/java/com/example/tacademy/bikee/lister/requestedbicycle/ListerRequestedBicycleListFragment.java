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

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Reserve;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.manager.NetworkManager;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListerRequestedBicycleListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private Intent intent;
    private ListView lv;
    private ListerRequestedBicycleAdapter adapter;

    private static final String TAG = "LISTER_R_B_L_FRAGMENT";

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
        intent = new Intent(getActivity(), ListerRequestedBicycleDetailInformationActivity.class);
        intent.putExtra("ID", item.getBikeId());
        intent.putExtra("RESERVE", item.getReserveId());
        intent.putExtra("STATUS", item.getStatus());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        intent.putExtra("ENDDATE", simpleDateFormat.format(item.getEndDate()));
        getActivity().startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private void initData() {
        NetworkManager.getInstance().selectRequestedBicycle(
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();
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
                                    renterImageURL = "https://" + reserve.getRenter().getImage().getCdnUri() + reserve.getRenter().getImage().getFiles().get(0);
                                }
                                String bicycleImageURL;
                                if ((null == result.getBike().getImage())
                                        || (null == result.getBike().getImage().getCdnUri())
                                        || (null == result.getBike().getImage().getFiles())) {
                                    bicycleImageURL = "";
                                } else {
                                    bicycleImageURL = result.getBike().getImage().getCdnUri() + result.getBike().getImage().getFiles().get(0);
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

                                adapter.add(result.get_id(),
                                        renterImageURL,
                                        reserve.getStatus(),
                                        reserve.getRenter().getName(),
                                        result.getBike().getTitle(),
                                        reserve.getRentStart(),
                                        reserve.getRentEnd(),
                                        "" + result.getBike().getPrice().getMonth(),
                                        reserve.get_id()
                                );
                            }
                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onFailure Error : " + t.toString());
                    }
                });
    }
}
