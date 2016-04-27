package com.example.tacademy.bikee.renter.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.dao.ReservationReceiveObject;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.renter.reservation.content.RenterReservationContentActivity;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RenterReservationsFragment extends Fragment implements OnRenterReservationAdapterClickListener {
    private Intent intent;
    private RenterReservationAdapter adapter;

    private static final String TAG = "RESERVATIONS_FRAGMENT";

    public RenterReservationsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_renter_reservation_bicycle_list, container, false);

        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.fragment_renter_reservation_bicycle_list_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recycler.setLayoutManager(layoutManager);

        adapter = new RenterReservationAdapter();
        adapter.setOnRenterReservationAdapterClickListener(this);

        recycler.setAdapter(adapter);

        init();

        return view;
    }

    @Override
    public void onRenterReservationAdapterClick(View view, RenterReservationItem item) {
        intent = new Intent(getActivity(), RenterReservationContentActivity.class);
        intent.putExtra("BICYCLE_ID", item.getBicycleId());
        intent.putExtra("BICYCLE_LATITUDE", item.getLatitude());
        intent.putExtra("BICYCLE_LONGITUDE", item.getLongitude());
        intent.putExtra("RESERVATION_ID", item.getReserveId());
        intent.putExtra("RESERVATION_STATUS", item.getStatus());
        intent.putExtra("RESERVATION_START_DATE", item.getStartDate());
        intent.putExtra("RESERVATION_END_DATE", item.getEndDate());
        getActivity().startActivity(intent);
    }

    private void init() {
        NetworkManager.getInstance().selectReservationBicycle(
                null,
                new Callback<ReservationReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReservationReceiveObject> call, Response<ReservationReceiveObject> response) {
                        ReservationReceiveObject receiveObject = response.body();
                        Log.i("result", "selectReservationBicycle onResponse");
                        List<ReservationReceiveObject.Result> results = receiveObject.getResult();
                        for (ReservationReceiveObject.Result result : results)
                            for (ReservationReceiveObject.Result.Reserve reserve : result.getReserve()) {
                                Log.i("result", "onResponse Bike ID : " + result.getBike().get_id()
                                                + ", Bike Image : " + result.getBike().getImage().getCdnUri() + result.getBike().getImage().getFiles().get(0)
                                                + ", Status : " + reserve.getStatus()
                                                + ", Bike Name : " + result.getBike().getTitle()
                                                + ", Start Date : " + reserve.getRentStart()
                                                + ", End Date : " + reserve.getRentEnd()
                                                + ", Payment : " + result.getBike().getPrice().getMonth()
                                );
                                adapter.add(
                                        new RenterReservationItem(
                                                result.getBike().get_id(),
                                                result.getBike().getImage().getCdnUri() + result.getBike().getImage().getFiles().get(0),
                                                result.getBike().getTitle(),
                                                reserve.getStatus(),
                                                reserve.getRentStart(),
                                                reserve.getRentEnd(),
                                                result.getBike().getPrice().getMonth(),
                                                reserve.get_id(),
                                                result.getBike().getLoc().getCoordinates().get(1),
                                                result.getBike().getLoc().getCoordinates().get(0)
                                        )
                                );
                            }
                    }

                    @Override
                    public void onFailure(Call<ReservationReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "selectReservationBicycle onFailure : ", t);
                    }
                });
    }
}
