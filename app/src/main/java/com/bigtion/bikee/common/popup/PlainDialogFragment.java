package com.bigtion.bikee.common.popup;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.bigtion.bikee.renter.RenterMainActivity;
import com.bigtion.bikee.R;
import com.bigtion.bikee.lister.ListerMainActivity;
import com.bigtion.bikee.lister.sidemenu.bicycle.BicyclesActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2016-05-10.
 */
public class PlainDialogFragment extends DialogFragment {
    @Bind(R.id.fragment_plain_dialog_text_view)
    TextView dialogTextView;

    private Intent intent;
    private int destination;
    private String message;

    public static final int DESTINATION_IS_RENTER_MAIN_ACTIVITY = 1;
    public static final int DESTINATION_IS_LISTER_MAIN_ACTIVITY = 2;
    public static final int DESTINATION_IS_BICYCLES_ACTIVITY = 3;
    private static final String TAG = "PLAIN_DIALOG_F";

    public static PlainDialogFragment newInstance(int destination, String message) {
        PlainDialogFragment plainDialogFragment = new PlainDialogFragment();

        Bundle args = new Bundle();
        args.putInt("DESTINATION", destination);
        args.putString("MESSAGE", message);
        plainDialogFragment.setArguments(args);

        return plainDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, R.style.BikeeDialog);

        Bundle args = getArguments();
        destination = args.getInt("DESTINATION");
        message = args.getString("MESSAGE");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plain_dialog, container, false);

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
        switch (destination) {
            case DESTINATION_IS_RENTER_MAIN_ACTIVITY:
                dialogTextView.setText(message);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        intent = new Intent(getContext().getApplicationContext(), RenterMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }, 1500);
                break;
            case DESTINATION_IS_LISTER_MAIN_ACTIVITY:
                dialogTextView.setText(message);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        intent = new Intent(getContext().getApplicationContext(), ListerMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }, 1500);
                break;
            case DESTINATION_IS_BICYCLES_ACTIVITY:
                dialogTextView.setText(message);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        intent = new Intent(getContext().getApplicationContext(), BicyclesActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }, 1500);
                break;
        }
    }
}
