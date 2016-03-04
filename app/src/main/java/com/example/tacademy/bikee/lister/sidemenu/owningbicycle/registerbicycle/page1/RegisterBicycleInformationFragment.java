package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.RegisterBicycleINF;

import java.util.ArrayList;
import java.util.List;

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
    private String type;
    private String height;
    private List<String> components;
    @Bind(R.id.activity_main_item1_image_view)
    ImageView item1;
    @Bind(R.id.activity_main_item2_image_view)
    ImageView item2;
    @Bind(R.id.activity_main_item3_layout)
    ImageView item3;
    @Bind(R.id.activity_main_minimum_height_text_view)
    TextView minimum;
    @Bind(R.id.activity_main_maximum_height_text_view)
    TextView maximum;
    private MyCircularList list;

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
        components = new ArrayList<>();
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

        list = new MyCircularList(0);
        height = list.getCurrent();
        activeNextButton();

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
                if (component1 == true) {
                    component1 = false;
                    components.remove(components.indexOf("A"));
                } else {
                    component1 = true;
                    components.add("A");
                }
                break;
            case 2:
                if (component2 == true) {
                    component2 = false;
                    components.remove(components.indexOf("B"));
                } else {
                    component2 = true;
                    components.add("B");
                }
                break;
            case 3:
                if (component3 == true) {
                    component3 = false;
                    components.remove(components.indexOf("C"));
                } else {
                    component3 = true;
                    components.add("C");
                }
                break;
            case 4:
                if (component4 == true) {
                    component4 = false;
                    components.remove(components.indexOf("D"));
                } else {
                    component4 = true;
                    components.add("D");
                }
                break;
            case 5:
                if (component5 == true) {
                    component5 = false;
                    components.remove(components.indexOf("E"));
                } else {
                    component5 = true;
                    components.add("E");
                }
                break;
            case 6:
                if (component6 == true) {
                    component6 = false;
                    components.remove(components.indexOf("F"));
                } else {
                    component6 = true;
                    components.add("F");
                }
                break;
            case 7:
                if (component7 == true) {
                    component7 = false;
                    components.remove(components.indexOf("A"));
                } else {
                    component7 = true;
                    components.add("A");
                }
                break;
            case 8:
                if (component8 == true) {
                    component8 = false;
                    components.remove(components.indexOf("G"));
                } else {
                    component8 = true;
                    components.add("G");
                }
                break;
        }
        activeNextButton();
    }

    @OnClick({R.id.activity_main_prev_button_image_view,
            R.id.activity_main_next_button_image_view})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_prev_button_image_view:
                list.movePrev();
                break;
            case R.id.activity_main_next_button_image_view:
                list.moveNext();
                break;
        }
        height = list.getCurrent();
    }

    private class MyCircularList {
        private List<MyCircularListItem> list;
        private int position;

        public MyCircularList(int position) {
            this.list = new ArrayList<>();
            this.position = position;

            initList();
            initImage();
        }

        private void initList() {
            this.list.add(new MyCircularListItem(R.drawable.main_character1, R.drawable.sub_character1, "이하", "145cm"));
            this.list.add(new MyCircularListItem(R.drawable.main_character2, R.drawable.sub_character2, "145cm", "155cm"));
            this.list.add(new MyCircularListItem(R.drawable.main_character3, R.drawable.sub_character3, "155cm", "165cm"));
            this.list.add(new MyCircularListItem(R.drawable.main_character4, R.drawable.sub_character4, "165cm", "175cm"));
            this.list.add(new MyCircularListItem(R.drawable.main_character5, R.drawable.sub_character5, "175cm", "185cm"));
            this.list.add(new MyCircularListItem(R.drawable.main_character6, R.drawable.sub_character6, "185cm", "이상"));
        }

        private void initImage() {
            item1.setImageResource(this.list.get((6 + (position - 1)) % 6).getSub());
            item2.setImageResource(this.list.get(position).getMain());
            item3.setImageResource(this.list.get((position + 1) % 6).getSub());
            minimum.setText(this.list.get(position).getMinimumHeight());
            maximum.setText(this.list.get(position).getMaximumHeight());
        }

        public void movePrev() {
            position = (6 + (position - 1)) % 6;
            initImage();
        }

        public void moveNext() {
            position = (position + 1) % 6;
            initImage();
        }

        public String getCurrent() {
            return "0" + (position + 1);
        }

        private class MyCircularListItem {
            private Integer main;
            private Integer sub;
            private String maximumHeight;
            private String minimumHeight;

            public MyCircularListItem(Integer main, Integer sub, String minimumHeight, String maximumHeight) {
                this.main = main;
                this.sub = sub;
                this.maximumHeight = maximumHeight;
                this.minimumHeight = minimumHeight;
            }

            public Integer getMain() {
                return main;
            }

            public Integer getSub() {
                return sub;
            }

            public String getMaximumHeight() {
                return maximumHeight;
            }

            public String getMinimumHeight() {
                return minimumHeight;
            }
        }
    }

    private void activeNextButton() {
        if ((null != type) && (0 < components.size()) && (null != height)) {
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

    public List<String> getComponents() {
        return components;
    }

    public String getHeight() {
        return height;
    }

    RegisterBicycleINF registerBicycleINF;

    public void setRegisterBicycleINF(RegisterBicycleINF registerBicycleINF) {
        this.registerBicycleINF = registerBicycleINF;
    }
}
