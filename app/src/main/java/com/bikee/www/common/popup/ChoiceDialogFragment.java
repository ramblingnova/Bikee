package com.bikee.www.common.popup;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bikee.www.etc.dao.Price;
import com.bikee.www.etc.dao.Reserve;
import com.bikee.www.BuildConfig;
import com.bikee.www.R;
import com.bikee.www.etc.dao.Bike;
import com.bikee.www.etc.dao.Loc;
import com.bikee.www.etc.dao.ReceiveObject;
import com.bikee.www.etc.manager.NetworkManager;
import com.bikee.www.lister.sidemenu.bicycle.register.RegisterBicycleItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 2016-05-10.
 */
public class ChoiceDialogFragment extends DialogFragment {
    // TODO : android api 버전 23이상은 필요한 권한을 체크해야 함(주의 : 특수한 경우에 Network통신을 함)
    // INTERNET : Network통신을 하기 위함
    @Bind(R.id.fragment_choice_dialog_text_view)
    TextView dialogTextView;
    @Bind(R.id.fragment_choice_dialog_bottom_buttons_left_button)
    Button bottomButtonsLeftButton;
    @Bind(R.id.fragment_choice_dialog_bottom_buttons_right_button)
    Button bottomButtonsRightButton;

    private PlainDialogFragment plainDialogFragment;
    private int from;
    private String reservationId;
    private String bicycleId;
    private String reservationStatus;
    private RegisterBicycleItem registerBicycleItem;
    private Date startDate;
    private Date endDate;
    private OnApplicationFinish onApplicationFinish;

    public static final int RENTER_RESERVATION_CANCEL = 1;
    public static final int LISTER_RESERVATION_CANCEL = 2;
    public static final int LISTER_RESERVATION_APPROVAL = 3;
    public static final int RENTER_RESERVATION_PAYMENT = 4;
    public static final int RENTER_RESERVATION_PAYMENT_CANCEL = 5;
    public static final int RENTER_RESERVATION_DATE_CHOICE = 6;
    public static final int LISTER_BICYCLE_REGISTER = 7;
    public static final int RENTER_APPLICATION_FINISH = 8;
    public static final int LISTER_APPLICATION_FINISH = 9;
    private static final String TAG = "CHOICE_DIALOG_F";

    public static ChoiceDialogFragment newInstance(
            int from,
            String bicycleId,
            Date startDate,
            Date endDate) {
        ChoiceDialogFragment choiceDialogFragment = new ChoiceDialogFragment();

        Bundle args = new Bundle();
        args.putInt("FROM", from);
        args.putString("BICYCLE_ID", bicycleId);
        args.putSerializable("START_DATE", startDate);
        args.putSerializable("END_DATE", endDate);
        choiceDialogFragment.setArguments(args);

        return choiceDialogFragment;
    }

    public static ChoiceDialogFragment newInstance(
            int from,
            String reservationId,
            String bicycleId,
            String reservationStatus) {
        ChoiceDialogFragment choiceDialogFragment = new ChoiceDialogFragment();

        Bundle args = new Bundle();
        args.putInt("FROM", from);
        args.putString("RESERVATION_ID", reservationId);
        args.putString("BICYCLE_ID", bicycleId);
        args.putString("RESERVATION_STATUS", reservationStatus);
        choiceDialogFragment.setArguments(args);

        return choiceDialogFragment;
    }

    public static ChoiceDialogFragment newInstance(
            int from,
            RegisterBicycleItem registerBicycleItem) {
        ChoiceDialogFragment choiceDialogFragment = new ChoiceDialogFragment();

        Bundle args = new Bundle();
        args.putInt("FROM", from);
        args.putSerializable("ITEM", registerBicycleItem);
        choiceDialogFragment.setArguments(args);

        return choiceDialogFragment;
    }

    public static ChoiceDialogFragment newInstance(
            int from) {
        ChoiceDialogFragment choiceDialogFragment = new ChoiceDialogFragment();

        Bundle args = new Bundle();
        args.putInt("FROM", from);
        choiceDialogFragment.setArguments(args);

        return choiceDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BikeeDialog);

        Bundle args = getArguments();
        from = args.getInt("FROM");
        switch (from) {
            case RENTER_RESERVATION_CANCEL:
            case LISTER_RESERVATION_CANCEL:
            case LISTER_RESERVATION_APPROVAL:
            case RENTER_RESERVATION_PAYMENT_CANCEL:
                reservationId = args.getString("RESERVATION_ID");
                bicycleId = args.getString("BICYCLE_ID");
                reservationStatus = args.getString("RESERVATION_STATUS");
                break;
            case RENTER_RESERVATION_DATE_CHOICE:
                bicycleId = args.getString("BICYCLE_ID");
                startDate = (Date) args.getSerializable("START_DATE");
                endDate = (Date) args.getSerializable("END_DATE");
                break;
            case RENTER_RESERVATION_PAYMENT:
                break;
            case LISTER_BICYCLE_REGISTER:
                registerBicycleItem = (RegisterBicycleItem) args.getSerializable("ITEM");
                break;
            case RENTER_APPLICATION_FINISH:
            case LISTER_APPLICATION_FINISH:
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice_dialog, container, false);

        ButterKnife.bind(this, view);

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

    public void init() {
        switch (from) {
            case RENTER_RESERVATION_CANCEL:
                dialogTextView.setText("자전거 예약을 취소하시겠습니까?");
                bottomButtonsLeftButton.setText("아니오");
                bottomButtonsRightButton.setText("예");
                break;
            case LISTER_RESERVATION_CANCEL:
                dialogTextView.setText("자전거 예약 요청을 거절하시겠습니까?");
                bottomButtonsLeftButton.setText("아니오");
                bottomButtonsRightButton.setText("예");
                break;
            case LISTER_RESERVATION_APPROVAL:
                dialogTextView.setText("자전거 예약 요청을 승인하시겠습니까?");
                bottomButtonsLeftButton.setText("아니오");
                bottomButtonsRightButton.setText("예");
                break;
            case RENTER_RESERVATION_PAYMENT:
                dialogTextView.setText("결제하시겠습니까?");
                bottomButtonsLeftButton.setText("아니오");
                bottomButtonsRightButton.setText("예");
            case RENTER_RESERVATION_PAYMENT_CANCEL:
                dialogTextView.setText("이미 결제된 자전거 예약을 취소하시겠습니까?");
                bottomButtonsLeftButton.setText("아니오");
                bottomButtonsRightButton.setText("예");
                break;
            case RENTER_RESERVATION_DATE_CHOICE:
                dialogTextView.setText("자전거 주인장에게 예약 신청하시겠습니까?");
                bottomButtonsLeftButton.setText("아니오");
                bottomButtonsRightButton.setText("예");
                break;
            case LISTER_BICYCLE_REGISTER:
                dialogTextView.setText("자전거를 등록하시겠습니까?");
                bottomButtonsLeftButton.setText("아니오");
                bottomButtonsRightButton.setText("예");
                break;
            case RENTER_APPLICATION_FINISH:
            case LISTER_APPLICATION_FINISH:
                dialogTextView.setText("Bikee를 종료하시겠습니까?");
                bottomButtonsLeftButton.setText("아니오");
                bottomButtonsRightButton.setText("예");
                break;
        }
    }

    @OnClick({R.id.fragment_choice_dialog_dismiss_view,
            R.id.fragment_choice_dialog_bottom_buttons_left_button,
            R.id.fragment_choice_dialog_bottom_buttons_right_button})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_choice_dialog_dismiss_view:
                dismiss();
                break;
            case R.id.fragment_choice_dialog_bottom_buttons_left_button:
                switch (from) {
                    case RENTER_RESERVATION_CANCEL:
                    case LISTER_RESERVATION_CANCEL:
                    case LISTER_RESERVATION_APPROVAL:
                    case RENTER_RESERVATION_PAYMENT:
                    case RENTER_RESERVATION_PAYMENT_CANCEL:
                    case RENTER_RESERVATION_DATE_CHOICE:
                    case LISTER_BICYCLE_REGISTER:
                    case RENTER_APPLICATION_FINISH:
                    case LISTER_APPLICATION_FINISH: {
                        dismiss();
                        break;
                    }
                }
                break;
            case R.id.fragment_choice_dialog_bottom_buttons_right_button:
                switch (from) {
                    case RENTER_RESERVATION_CANCEL:
                    case RENTER_RESERVATION_PAYMENT_CANCEL: {
                        NetworkManager.getInstance().reserveStatus(
                                reservationId,
                                bicycleId,
                                reservationStatus,
                                null,
                                new Callback<ReceiveObject>() {
                                    @Override
                                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                        ReceiveObject receiveObject = response.body();
                                        if (receiveObject.isSuccess()) {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "reserveStatus onResponse Success : " + receiveObject.isSuccess()
                                                                + ", Code : " + receiveObject.getCode()
                                                                + ", Msg : " + receiveObject.getMsg()
                                                );
                                            plainDialogFragment
                                                    = PlainDialogFragment.newInstance(
                                                    PlainDialogFragment.DESTINATION_IS_RENTER_MAIN_ACTIVITY,
                                                    receiveObject.getMsg()
                                            );
                                            plainDialogFragment.show(getActivity().getSupportFragmentManager(), TAG);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "reserveStatus onFailure", t);
                                    }
                                });
                        break;
                    }
                    case LISTER_RESERVATION_CANCEL:
                    case LISTER_RESERVATION_APPROVAL: {
                        NetworkManager.getInstance().reserveStatus(
                                reservationId,
                                bicycleId,
                                reservationStatus,
                                null,
                                new Callback<ReceiveObject>() {
                                    @Override
                                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                        ReceiveObject receiveObject = response.body();
                                        if (receiveObject.isSuccess()) {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "reserveStatus onResponse Success : " + receiveObject.isSuccess()
                                                                + ", Code : " + receiveObject.getCode()
                                                                + ", Msg : " + receiveObject.getMsg()
                                                );
                                            plainDialogFragment
                                                    = PlainDialogFragment.newInstance(
                                                    PlainDialogFragment.DESTINATION_IS_LISTER_MAIN_ACTIVITY,
                                                    receiveObject.getMsg()
                                            );
                                            plainDialogFragment.show(getActivity().getSupportFragmentManager(), TAG);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "reserveStatus onFailure", t);
                                    }
                                });

                        break;
                    }
                    case RENTER_RESERVATION_DATE_CHOICE: {
                        Reserve reserve = new Reserve();
                        reserve.setRentStart(startDate);
                        reserve.setRentEnd(endDate);
                        NetworkManager.getInstance().insertReservation(
                                bicycleId,
                                reserve,
                                null,
                                new Callback<ReceiveObject>() {
                                    @Override
                                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                        ReceiveObject receiveObject = response.body();
                                        if (receiveObject.isSuccess()) {
                                            if (receiveObject.isSuccess()) {
                                                if (BuildConfig.DEBUG)
                                                    Log.d(TAG, "insertReservation onResponse Success : " + receiveObject.isSuccess()
                                                                    + ", Code : " + receiveObject.getCode()
                                                                    + ", Msg : " + receiveObject.getMsg()
                                                    );
                                                plainDialogFragment
                                                        = PlainDialogFragment.newInstance(
                                                        PlainDialogFragment.DESTINATION_IS_RENTER_MAIN_ACTIVITY,
                                                        receiveObject.getMsg()
                                                );
                                                plainDialogFragment.show(getActivity().getSupportFragmentManager(), TAG);
                                            } else {
                                                if (BuildConfig.DEBUG)
                                                    Log.d(TAG, "insertReservation onResponse Success : " + receiveObject.isSuccess());
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "onFailure Error : " + t.toString());
                                    }
                                });
                        break;
                    }
                    case LISTER_BICYCLE_REGISTER: {
                        Bike bike = new Bike();

                        bike.setType(registerBicycleItem.getType());
                        bike.setComponents(registerBicycleItem.getComponents());
                        bike.setHeight(registerBicycleItem.getHeight());

                        List<Double> coordinates = new ArrayList<>();
                        coordinates.add(registerBicycleItem.getLongitude());
                        coordinates.add(registerBicycleItem.getLatitude());
                        Loc loc = new Loc();
                        loc.setCoordinates(coordinates);
                        bike.setLoc(loc);

                        bike.setTitle(registerBicycleItem.getName());
                        bike.setIntro(registerBicycleItem.getIntroduction());

                        List<MultipartBody.Part> multi = new ArrayList<>();
                        for (File file : registerBicycleItem.getFiles()) {
                            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
                            multi.add(MultipartBody.Part.createFormData("files", file.getName(), fileReqBody));
                        }

                        Price price = new Price();
                        price.setHour(registerBicycleItem.getHour());
                        price.setDay(registerBicycleItem.getDay());
                        price.setMonth(registerBicycleItem.getMonth());
                        bike.setPrice(price);

                        NetworkManager.getInstance().insertBicycle(
                                multi,
                                bike,
                                null,
                                new Callback<ReceiveObject>() {
                                    @Override
                                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                        ReceiveObject receiveObject = response.body();
                                        if (receiveObject.isSuccess()) {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "insertBicycle onResponse Success : " + receiveObject.isSuccess());
                                            plainDialogFragment
                                                    = PlainDialogFragment.newInstance(
                                                    PlainDialogFragment.DESTINATION_IS_BICYCLES_ACTIVITY,
                                                    receiveObject.getMsg()
                                            );
                                            plainDialogFragment.show(getActivity().getSupportFragmentManager(), TAG);
                                        } else {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "insertBicycle onResponse Success : " + receiveObject.isSuccess());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                        Log.d(TAG, "insertBicycle onFailure", t);
                                    }
                                });
                        break;
                    }
                    case RENTER_APPLICATION_FINISH:
                    case LISTER_APPLICATION_FINISH:
                        if (onApplicationFinish != null) {
                            dismiss();
                            onApplicationFinish.onApplicationFinish();
                        }
                        break;
                }
                break;
        }
    }

    public void setOnApplicationFinish(OnApplicationFinish onApplicationFinish) {
        this.onApplicationFinish = onApplicationFinish;
    }
}
