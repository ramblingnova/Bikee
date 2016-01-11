package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Toast;

import com.example.tacademy.bikee.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterBicycleInformationFragment extends Fragment implements BicycleAdditoryComponentAdapter.OnItemClickListener {
    private GridView gridView;
    private BicycleAdditoryComponentAdapter adapter;
    @Bind(R.id.bicycle_type_check_box1)
    CheckBox type1;
    @Bind(R.id.bicycle_type_check_box2)
    CheckBox type2;
    @Bind(R.id.bicycle_type_check_box3)
    CheckBox type3;
    @Bind(R.id.bicycle_type_check_box4)
    CheckBox type4;
    @Bind(R.id.bicycle_type_check_box5)
    CheckBox type5;
    @Bind(R.id.bicycle_type_check_box6)
    CheckBox type6;
    @Bind(R.id.bicycle_type_check_box7)
    CheckBox type7;
    private boolean component1;
    private boolean component2;
    private boolean component3;
    private boolean component4;
    private boolean component5;
    private boolean component6;
    private boolean component7;
    private boolean component8;
    @Bind(R.id.bicycle_recommendation_height_check_box1)
    CheckBox height1;
    @Bind(R.id.bicycle_recommendation_height_check_box2)
    CheckBox height2;
    @Bind(R.id.bicycle_recommendation_height_check_box3)
    CheckBox height3;
    @Bind(R.id.bicycle_recommendation_height_check_box4)
    CheckBox height4;
    @Bind(R.id.bicycle_recommendation_height_check_box5)
    CheckBox height5;
    @Bind(R.id.bicycle_recommendation_height_check_box6)
    CheckBox height6;
    private String type;
    private String component;
    private String height;

    public static RegisterBicycleInformationFragment newInstance() {
        return new RegisterBicycleInformationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component1 = false;
        component2 = false;
        component3 = false;
        component4 = false;
        component5 = false;
        component6 = false;
        component7 = false;
        component8 = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_bicycle_information, container, false);

        gridView = (GridView) view.findViewById(R.id.register_bicycle_additory_component_grid_view);
        adapter = new BicycleAdditoryComponentAdapter();
        gridView.setAdapter(adapter);
        gridView.setHorizontalSpacing(getResources().getDimensionPixelSize(R.dimen.register_bicycle_additory_component_item_horizontal_space));
        gridView.setVerticalSpacing(getResources().getDimensionPixelSize(R.dimen.register_bicycle_additory_component_item_vertical_space));
        adapter.setOnItemClickListener(this);
        initAdditoryComponent();
        activeNextButton();

        ButterKnife.bind(this, view);

        return view;
    }

    private void initAdditoryComponent() {
        adapter.add(R.drawable.component_icon1, "자물쇠", component1);
        adapter.add(R.drawable.component_icon2, "라이트", component2);
        adapter.add(R.drawable.component_icon3, "반사판", component3);
        adapter.add(R.drawable.component_icon4, "장갑", component4);
        adapter.add(R.drawable.component_icon5, "바구니", component5);
        adapter.add(R.drawable.component_icon6, "트레일러", component6);
        adapter.add(R.drawable.component_icon7, "백미러", component7);
        adapter.add(R.drawable.component_icon8, "헬멧", component8);
    }

    @OnClick({R.id.bicycle_type_check_box1,
            R.id.bicycle_type_check_box2,
            R.id.bicycle_type_check_box3,
            R.id.bicycle_type_check_box4,
            R.id.bicycle_type_check_box5,
            R.id.bicycle_type_check_box6,
            R.id.bicycle_type_check_box7})
    void selectType(View view) {
        type1.setChecked(false);
        type2.setChecked(false);
        type3.setChecked(false);
        type4.setChecked(false);
        type5.setChecked(false);
        type6.setChecked(false);
        type7.setChecked(false);
        switch (view.getId()) {
            case R.id.bicycle_type_check_box1:
                type1.setChecked(true);
                type = "A";
                break;
            case R.id.bicycle_type_check_box2:
                type2.setChecked(true);
                type = "A";
                break;
            case R.id.bicycle_type_check_box3:
                type3.setChecked(true);
                type = "B";
                break;
            case R.id.bicycle_type_check_box4:
                type4.setChecked(true);
                type = "C";
                break;
            case R.id.bicycle_type_check_box5:
                type5.setChecked(true);
                type = "D";
                break;
            case R.id.bicycle_type_check_box6:
                type6.setChecked(true);
                type = "E";
                break;
            case R.id.bicycle_type_check_box7:
                type7.setChecked(true);
                type = "F";
                break;
        }
        activeNextButton();
    }

    @Override
    public void onItemClick(int position) {
//        Toast.makeText(getContext(), "position" + (position + 1), Toast.LENGTH_SHORT).show();
        switch (position + 1) {
            case 1:
                component = "" + position;
                component1 = component1 ? false : true;
                break;
            case 2:
                component = "" + position;
                component2 = component2 ? false : true;
                break;
            case 3:
                component = "" + position;
                component3 = component3 ? false : true;
                break;
            case 4:
                component = "" + position;
                component4 = component4 ? false : true;
                break;
            case 5:
                component = "" + position;
                component5 = component5 ? false : true;
                break;
            case 6:
                component = "" + position;
                component6 = component6 ? false : true;
                break;
            case 7:
                component = "" + position;
                component7 = component7 ? false : true;
                break;
            case 8:
                component = "" + position;
                component8 = component8 ? false : true;
                break;
        }
        activeNextButton();
    }

    @OnClick({R.id.bicycle_recommendation_height_check_box1,
            R.id.bicycle_recommendation_height_check_box2,
            R.id.bicycle_recommendation_height_check_box3,
            R.id.bicycle_recommendation_height_check_box4,
            R.id.bicycle_recommendation_height_check_box5,
            R.id.bicycle_recommendation_height_check_box6})
    void selectHeight(View view) {
        height1.setChecked(false);
        height2.setChecked(false);
        height3.setChecked(false);
        height4.setChecked(false);
        height5.setChecked(false);
        height6.setChecked(false);
        switch (view.getId()) {
            case R.id.bicycle_recommendation_height_check_box1:
                height1.setChecked(true);
                height = "01";
                break;
            case R.id.bicycle_recommendation_height_check_box2:
                height2.setChecked(true);
                height = "02";
                break;
            case R.id.bicycle_recommendation_height_check_box3:
                height3.setChecked(true);
                height = "03";
                break;
            case R.id.bicycle_recommendation_height_check_box4:
                height4.setChecked(true);
                height = "04";
                break;
            case R.id.bicycle_recommendation_height_check_box5:
                height5.setChecked(true);
                height = "05";
                break;
            case R.id.bicycle_recommendation_height_check_box6:
                height6.setChecked(true);
                height = "06";
                break;
        }
        activeNextButton();
    }

    private void activeNextButton() {
        if ((null != type) && (null != component) && (null != height)) {
            if ((null != registerBicycleINF) && (!registerBicycleINF.getEnable())) {
                registerBicycleINF.setEnable(true);
            }
        } else {
            if ((null != registerBicycleINF) && (registerBicycleINF.getEnable())) {
                registerBicycleINF.setEnable(false);
            }
        }
    }

    public String getType() {
        return type;
    }

    public String getComponent() {
        return component;
    }

    public String getHeight() {
        return height;
    }

    RegisterBicycleINF registerBicycleINF;

    public void setRegisterBicycleINF(RegisterBicycleINF registerBicycleINF) {
        this.registerBicycleINF = registerBicycleINF;
    }
}
