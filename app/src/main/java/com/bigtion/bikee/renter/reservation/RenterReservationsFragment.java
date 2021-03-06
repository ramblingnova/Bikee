package com.bigtion.bikee.renter.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigtion.bikee.common.content.ContentActivity;
import com.bigtion.bikee.etc.interfaces.OnAdapterClickListener;
import com.bigtion.bikee.etc.dao.ReservationReceiveObject;
import com.bigtion.bikee.etc.manager.NetworkManager;
import com.bigtion.bikee.BuildConfig;
import com.bigtion.bikee.R;

import java.util.List;
import java.util.Stack;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RenterReservationsFragment extends Fragment implements OnAdapterClickListener {
    @Bind(R.id.fragment_renter_reservation_bicycle_list_recycler_view)
    RecyclerView recyclerView;

    private Intent intent;
    private Stack<Call> callStack;
    private RenterReservationAdapter adapter;

    public static final int from = 5;
    private static final String TAG = "RENTER_RESERVATIONS_F";

    public RenterReservationsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_renter_reservation_bicycle_list, container, false);

        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        adapter = new RenterReservationAdapter();
        adapter.setOnAdapterClickListener(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RenterReservationDecoration());

        callStack = new Stack<>();

        init();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (!callStack.isEmpty())
            for (Call call : callStack)
                if (!call.isCanceled())
                    call.cancel();

        android.os.Debug.stopMethodTracing();
    }

    @Override
    public void onAdapterClick(View view, Object item) {
        intent = new Intent(getActivity(), ContentActivity.class);
        intent.putExtra("FROM", from);
        intent.putExtra("BICYCLE_ID", ((RenterReservationItem) item).getBicycleId());
        intent.putExtra("BICYCLE_LATITUDE", ((RenterReservationItem) item).getBicycleLatitude());
        intent.putExtra("BICYCLE_LONGITUDE", ((RenterReservationItem) item).getBicycleLongitude());
        intent.putExtra("RESERVATION_ID", ((RenterReservationItem) item).getReservationId());
        intent.putExtra("RESERVATION_STATUS", ((RenterReservationItem) item).getReservationStatus());
        intent.putExtra("RESERVATION_START_DATE", ((RenterReservationItem) item).getReservationStartDate());
        intent.putExtra("RESERVATION_END_DATE", ((RenterReservationItem) item).getReservationEndDate());
        getActivity().startActivity(intent);
    }

    private void init() {
        NetworkManager.getInstance().selectReservationBicycle(
                callStack,
                new Callback<ReservationReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReservationReceiveObject> call, Response<ReservationReceiveObject> response) {
                        ReservationReceiveObject receiveObject = response.body();
                        if (receiveObject.isSuccess()) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "selectReservationBicycle onResponse success : " + receiveObject.isSuccess());

                            List<ReservationReceiveObject.Result> results = receiveObject.getResult();
                            for (ReservationReceiveObject.Result result : results)
                                adapter.add(
                                        new RenterReservationItem(
                                                result.getBike().get_id(),
                                                result.getBike().getImage().getCdnUri() + result.getBike().getImage().getFiles().get(0),
                                                result.getBike().getTitle(),
                                                result.getBike().getLoc().getCoordinates().get(1),
                                                result.getBike().getLoc().getCoordinates().get(0),
                                                result.getReserve().get_id(),
                                                result.getReserve().getStatus(),
                                                result.getReserve().getRentStart(),
                                                result.getReserve().getRentEnd(),
                                                result.getBike().getPrice().getMonth(),
                                                result.getBike().getPrice().getDay(),
                                                result.getBike().getPrice().getHour()
                                        )
                                );
                        } else {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "selectReservationBicycle onResponse success : " + receiveObject.isSuccess());
                        }
                    }

                    @Override
                    public void onFailure(Call<ReservationReceiveObject> call, Throwable t) {
                        if (call.isCanceled()) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "selectReservationBicycle isCanceled");
                        } else {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "selectReservationBicycle onFailure", t);
                        }
                    }
                });
    }
}
