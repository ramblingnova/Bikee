package com.bigtion.bikee.common.popup;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bigtion.bikee.BuildConfig;
import com.bigtion.bikee.R;
import com.bigtion.bikee.common.interfaces.OnAdapterClickListener;
import com.bigtion.bikee.etc.dao.GetGeoLocationReceiveObject;
import com.bigtion.bikee.etc.manager.DaumNetworkManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 2016-06-03.
 */
public class AddressDialogFragment extends DialogFragment {
    @Bind(R.id.fragment_address_dialog_recycler_view)
    RecyclerView recyclerView;

    private AddressAdapter adapter;
    private String addressKeyword;
    private OnAddressDialogFragmentClickListener onAddressDialogFragmentClickListener;

    private static final String TAG = "ADDRESS_DIALOG_F";

    public static AddressDialogFragment newInstance(String addressKeyword) {
        AddressDialogFragment addressDialogFragment = new AddressDialogFragment();

        Bundle args = new Bundle();
        args.putString("ADDRESS_KEYWORD", addressKeyword);
        addressDialogFragment.setArguments(args);

        return addressDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BikeeDialog);

        Bundle args = getArguments();
        addressKeyword = args.getString("ADDRESS_KEYWORD");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_dialog, container, false);

        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);

        List<AddressItem> list = new ArrayList<>();

        adapter = new AddressAdapter(list);
        adapter.setOnAdapterClickListener(
                new OnAdapterClickListener() {
                    @Override
                    public void onAdapterClick(View view, Object item) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "address : " + ((AddressItem) item).getAddress()
                                            + "\nlatitude : " + ((AddressItem) item).getLatitude()
                                            + "\nlongitude : " + ((AddressItem) item).getLongitude()
                            );
                        if (onAddressDialogFragmentClickListener != null)
                            onAddressDialogFragmentClickListener.onAddressDialogFragmentClick(
                                    view,
                                    ((AddressItem) item).getAddress(),
                                    ((AddressItem) item).getLatitude(),
                                    ((AddressItem) item).getLongitude()
                            );
                        dismiss();
                    }
                });

        recyclerView.setAdapter(adapter);

        init();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Dialog dialog = getDialog();
        dialog.getWindow().setBackgroundDrawableResource(R.color.bikeeTransParent);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        params.x = 0;
        params.y = 0;
        dialog.getWindow().setAttributes(params);
    }

    @OnClick(R.id.fragment_address_dialog_dismiss_view)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_address_dialog_dismiss_view:
                dismiss();
                break;
        }
    }

    private void init() {
        // TODO : addressKeyword에 아무것도 들어있지 않은 경우, 키워드가 불적절한 경우에 대해 처리
        DaumNetworkManager.getInstance().getGeoLocation(
                addressKeyword,
                null,
                new Callback<GetGeoLocationReceiveObject>() {
                    @Override
                    public void onResponse(Call<GetGeoLocationReceiveObject> call, Response<GetGeoLocationReceiveObject> response) {
                        GetGeoLocationReceiveObject getGeoLocationReceiveObject = response.body();

                        for (GetGeoLocationReceiveObject.Channel.Item item : getGeoLocationReceiveObject.getChannel().getItem()) {
                            if (BuildConfig.DEBUG)
                                Log.d(TAG, "title : " + item.getTitle()
                                        + "\nlat : " + item.getLat()
                                        + "\nlng : " + item.getLng());
                            adapter.add(
                                    new AddressItem(
                                            item.getTitle(),
                                            item.getLat(),
                                            item.getLng()
                                    )
                            );
                        }
                    }

                    @Override
                    public void onFailure(Call<GetGeoLocationReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "getGeoLocation onFailure", t);
                    }
                }
        );
    }

    public void setOnAddressDialogFragmentClickListener(OnAddressDialogFragmentClickListener onAddressDialogFragmentClickListener) {
        this.onAddressDialogFragmentClickListener = onAddressDialogFragmentClickListener;
    }
}
