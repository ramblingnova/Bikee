package com.bigtion.bikee.lister.sidemenu.bicycle.register.page5;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigtion.bikee.R;
import com.bigtion.bikee.lister.sidemenu.bicycle.register.RegisterBicycleINF;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;

public class RegisterBicycleFeeFragment extends Fragment implements TextWatcher {
    @Bind(R.id.fragment_register_bicycle_fee_animation_image_view)
    ImageView img;
    @Bind(R.id.fragment_register_bicycle_fee_hour_edit_text)
    EditText hour;
    @Bind(R.id.fragment_register_bicycle_fee_day_edit_text)
    EditText day;
    @Bind(R.id.fragment_register_bicycle_fee_month_edit_text)
    EditText month;
    @Bind(R.id.fragment_register_bicycle_fee_hour_hint_text_view2)
    TextView hourHint1;
    @Bind(R.id.fragment_register_bicycle_fee_hour_hint_text_view1)
    TextView hourHint2;
    @Bind(R.id.fragment_register_bicycle_fee_day_hint_text_view2)
    TextView dayHint1;
    @Bind(R.id.fragment_register_bicycle_fee_day_hint_text_view1)
    TextView dayHint2;
    @Bind(R.id.fragment_register_bicycle_fee_month_hint_text_view2)
    TextView monthHint1;
    @Bind(R.id.fragment_register_bicycle_fee_month_hint_text_view1)
    TextView monthHint2;

    private DecimalFormat decimalFormat = new DecimalFormat("###,###.####");
    private String result;
    private RegisterBicycleINF registerBicycleINF;
    private InputMethodManager imm;

    public static RegisterBicycleFeeFragment newInstance() {
        return new RegisterBicycleFeeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_bicycle_fee, container, false);

        ButterKnife.bind(this, view);

        img.setImageResource(R.drawable.bicycle_animation_list);
        hour.addTextChangedListener(this);
        day.addTextChangedListener(this);
        month.addTextChangedListener(this);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!hour.getText().toString().equals("")) {
            hourHint1.setVisibility(View.GONE);
            hourHint2.setVisibility(View.GONE);
        }
        if (!day.getText().toString().equals("")) {
            dayHint1.setVisibility(View.GONE);
            dayHint2.setVisibility(View.GONE);
        }
        if (!month.getText().toString().equals("")) {
            monthHint1.setVisibility(View.GONE);
            monthHint2.setVisibility(View.GONE);
        }

        if ((hour.getText().length() > 0)
                && (day.getText().length() > 0)
                && (month.getText().length() > 0)) {
            if ((null != registerBicycleINF)
                    && (!registerBicycleINF.getEnable())) {
                registerBicycleINF.setEnable(true);
            }
        } else {
            if ((null != registerBicycleINF)
                    && (registerBicycleINF.getEnable())) {
                registerBicycleINF.setEnable(false);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals(result)) {
            if (s.length() > 0)
                result = decimalFormat.format(Long.parseLong(s.toString().replaceAll(",", "")));
            if (s.hashCode() == hour.getText().hashCode()) {
                hour.setText(result);
                hour.setSelection(result.length());
            } else if (s.hashCode() == day.getText().hashCode()) {
                day.setText(result);
                day.setSelection(result.length());
            } else if (s.hashCode() == month.getText().hashCode()) {
                month.setText(result);
                month.setSelection(result.length());
            }
        }

        if ((hour.getText().length() > 0) && (day.getText().length() > 0) && (month.getText().length() > 0)) {
            if ((null != registerBicycleINF) && (!registerBicycleINF.getEnable())) {
                registerBicycleINF.setEnable(true);
            }
        } else {
            if ((null != registerBicycleINF) && (registerBicycleINF.getEnable())) {
                registerBicycleINF.setEnable(false);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @OnClick({R.id.fragment_register_bicycle_fee_hour_input_layout,
            R.id.fragment_register_bicycle_fee_day_input_layout,
            R.id.fragment_register_bicycle_fee_month_input_layout})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_register_bicycle_fee_hour_input_layout:
                hourHint1.setVisibility(View.GONE);
                hourHint2.setVisibility(View.GONE);
                hour.requestFocus();
                imm.showSoftInput(hour, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.fragment_register_bicycle_fee_day_input_layout:
                dayHint1.setVisibility(View.GONE);
                dayHint2.setVisibility(View.GONE);
                day.requestFocus();
                imm.showSoftInput(day, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.fragment_register_bicycle_fee_month_input_layout:
                monthHint1.setVisibility(View.GONE);
                monthHint2.setVisibility(View.GONE);
                month.requestFocus();
                imm.showSoftInput(month, InputMethodManager.SHOW_FORCED);
                break;
        }
    }

    @OnFocusChange({R.id.fragment_register_bicycle_fee_hour_edit_text,
            R.id.fragment_register_bicycle_fee_day_edit_text,
            R.id.fragment_register_bicycle_fee_month_edit_text})
    void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()) {
            case R.id.fragment_register_bicycle_fee_hour_edit_text:
                if (hasFocus) {
                    imm.showSoftInput(hour, InputMethodManager.SHOW_FORCED);
                    hourHint1.setVisibility(View.GONE);
                    hourHint2.setVisibility(View.GONE);
                    if (day.getText().toString().equals("")) {
                        dayHint1.setVisibility(View.VISIBLE);
                        dayHint2.setVisibility(View.VISIBLE);
                    }
                    if (month.getText().toString().equals("")) {
                        monthHint1.setVisibility(View.VISIBLE);
                        monthHint2.setVisibility(View.VISIBLE);
                    }
                    // http://www.androidpub.com/1104990
                }
                break;
            case R.id.fragment_register_bicycle_fee_day_edit_text:
                if (hasFocus) {
                    imm.showSoftInput(day, InputMethodManager.SHOW_FORCED);
                    dayHint1.setVisibility(View.GONE);
                    dayHint2.setVisibility(View.GONE);
                    if (hour.getText().toString().equals("")) {
                        hourHint1.setVisibility(View.VISIBLE);
                        hourHint2.setVisibility(View.VISIBLE);
                    }
                    if (month.getText().toString().equals("")) {
                        monthHint1.setVisibility(View.VISIBLE);
                        monthHint2.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.fragment_register_bicycle_fee_month_edit_text:
                if (hasFocus) {
                    imm.showSoftInput(month, InputMethodManager.SHOW_FORCED);
                    monthHint1.setVisibility(View.GONE);
                    monthHint2.setVisibility(View.GONE);
                    if (hour.getText().toString().equals("")) {
                        hourHint1.setVisibility(View.VISIBLE);
                        hourHint2.setVisibility(View.VISIBLE);
                    }
                    if (day.getText().toString().equals("")) {
                        dayHint1.setVisibility(View.VISIBLE);
                        dayHint2.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }

    @OnEditorAction({R.id.fragment_register_bicycle_fee_hour_edit_text,
            R.id.fragment_register_bicycle_fee_day_edit_text,
            R.id.fragment_register_bicycle_fee_month_edit_text})
    boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        switch (view.getId()) {
            case R.id.fragment_register_bicycle_fee_hour_edit_text:
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    day.requestFocus();
                    return false;
                }
                break;
            case R.id.fragment_register_bicycle_fee_day_edit_text:
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    month.requestFocus();
                    return false;
                }
                break;
            case R.id.fragment_register_bicycle_fee_month_edit_text:
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    return false;
                }
                break;
        }
        return true;
    }

    public int getHour() {
        return Integer.parseInt(hour.getText().toString().replace(",", ""));
    }

    public int getDay() {
        return Integer.parseInt(day.getText().toString().replace(",", ""));
    }

    public int getMonth() {
        return Integer.parseInt(month.getText().toString().replace(",", ""));
    }

    public void setRegisterBicycleINF(RegisterBicycleINF registerBicycleINF) {
        this.registerBicycleINF = registerBicycleINF;
    }
}
