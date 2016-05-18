package com.example.tacademy.bikee.lister.reservation;

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
import com.example.tacademy.bikee.common.interfaces.OnAdapterClickListener;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Reserve;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.common.content.ContentActivity;
import com.example.tacademy.bikee.etc.utils.RefinementUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListerReservationsFragment extends Fragment implements OnAdapterClickListener {
    @Bind(R.id.fragment_lister_requested_bicycle_list_list_view)
    RecyclerView recyclerView;

    private Intent intent;
    private ListerReservationAdapter adapter;

    public static final int from = 6;
    private static final String TAG = "LISTER_RESERVATIONS_F";

    public ListerReservationsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lister_requested_bicycle_list, container, false);

        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        adapter = new ListerReservationAdapter();
        adapter.setOnAdapterClickListener(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        init();

        return view;
    }

    @Override
    public void onAdapterClick(View view, Object item) {
        intent = new Intent(getActivity(), ContentActivity.class);
        intent.putExtra("FROM", from);
        intent.putExtra("BICYCLE_ID", ((ListerReservationItem) item).getBicycleId());
        intent.putExtra("BICYCLE_LATITUDE", ((ListerReservationItem) item).getBicycleLatitude());
        intent.putExtra("BICYCLE_LONGITUDE", ((ListerReservationItem) item).getBicycleLongitude());
        intent.putExtra("RENTER_IMAGE", ((ListerReservationItem) item).getRenterImageURL());
        intent.putExtra("RENTER_NAME", ((ListerReservationItem) item).getRenterName());
        intent.putExtra("RENTER_PHONE", ((ListerReservationItem) item).getRenterPhone());
        intent.putExtra("RESERVATION_ID", ((ListerReservationItem) item).getReservationId());
        intent.putExtra("RESERVATION_STATUS", ((ListerReservationItem) item).getReservationStatus());
        intent.putExtra("RESERVATION_START_DATE", ((ListerReservationItem) item).getReservationStartDate());
        intent.putExtra("RESERVATION_END_DATE", ((ListerReservationItem) item).getReservationEndDate());
        getActivity().startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private void init() {
        NetworkManager.getInstance().selectRequestedBicycle(
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();

                        if (receiveObject.isSuccess()) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "selectRequestedBicycle onResponse success : " + receiveObject.isSuccess());
                            List<Result> results = receiveObject.getResult();
                            for (Result result : results)
                                for (Reserve reserve : result.getReserve()) {
                                    adapter.addFirst(
                                            new ListerReservationItem(
                                                    result.getBike().get_id(),
                                                    result.getBike().getTitle(),
                                                    result.getBike().getLoc().getCoordinates().get(1),
                                                    result.getBike().getLoc().getCoordinates().get(0),
                                                    RefinementUtil.getUserImageURLStringFromReserve(reserve),
                                                    reserve.getRenter().getName(),
                                                    // reserve.getRenter().getPhone() // TODO : 렌터의 전화번호 가져오기, 요청 필요
                                                    "010-1234-1234",
                                                    reserve.get_id(),
                                                    reserve.getStatus(),
                                                    reserve.getRentStart(),
                                                    reserve.getRentEnd(),
                                                    result.getBike().getPrice().getMonth(),
                                                    result.getBike().getPrice().getDay(),
                                                    result.getBike().getPrice().getHour()
                                            )
                                    );
                                }
                        } else {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "selectRequestedBicycle onResponse success : " + receiveObject.isSuccess());
                        }
                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "selectRequestedBicycle onFailure", t);
                    }
                });
    }
}
