package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page4;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.utils.ImageUtil;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.RegisterBicycleINF;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page4.camera.CameraActivity;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page4.gallery.GalleryActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterBicyclePictureFragment extends Fragment {
    private Intent intent;
    private ArrayList<File> list;
    private final static int MAXIMUM_LIST_SIZE = 5;
    @Bind(R.id.fragment_register_bicycle_picture_warning_image_view)
    ImageView warningImageView;
    @Bind(R.id.fragment_register_bicycle_picture_warning_text_view)
    TextView warningTextView;
    @Bind(R.id.thumbnail_item1_image_view)
    ImageView item1ImageView;
    @Bind(R.id.thumbnail_item2_image_view)
    ImageView item2ImageView;
    @Bind(R.id.thumbnail_item3_image_view)
    ImageView item3ImageView;
    @Bind(R.id.thumbnail_item4_image_view)
    ImageView item4ImageView;
    @Bind(R.id.thumbnail_item5_image_view)
    ImageView item5ImageView;
    @Bind(R.id.thumbnail_item1_title_text_view)
    TextView item1TextView;
    @Bind(R.id.thumbnail_item2_title_text_view)
    TextView item2TextView;
    @Bind(R.id.thumbnail_item3_title_text_view)
    TextView item3TextView;
    @Bind(R.id.thumbnail_item4_title_text_view)
    TextView item4TextView;
    @Bind(R.id.thumbnail_item5_title_text_view)
    TextView item5TextView;
    private RegisterBicycleINF registerBicycleINF;

    public static RegisterBicyclePictureFragment newInstance() {
        return new RegisterBicyclePictureFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (list == null) {
            list = new ArrayList<>();
            for (int i = 0; i < MAXIMUM_LIST_SIZE; i++)
                list.add(null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_bicycle_picture, container, false);

        ButterKnife.bind(this, view);

        item1ImageView.setImageResource(R.drawable.img_01);
        item2ImageView.setImageResource(R.drawable.img_02);
        item3ImageView.setImageResource(R.drawable.img_03);
        item4ImageView.setImageResource(R.drawable.img_04);
        item5ImageView.setImageResource(R.drawable.img_05);

        if (Build.VERSION.SDK_INT < 23) {
            item1TextView.setTextColor(getResources().getColor(R.color.bikeeBlack));
            item2TextView.setTextColor(getResources().getColor(R.color.bikeeBlack));
            item3TextView.setTextColor(getResources().getColor(R.color.bikeeBlack));
            item4TextView.setTextColor(getResources().getColor(R.color.bikeeBlack));
            item5TextView.setTextColor(getResources().getColor(R.color.bikeeBlack));
        } else {
            item1TextView.setTextColor(getResources().getColor(R.color.bikeeBlack, null));
            item2TextView.setTextColor(getResources().getColor(R.color.bikeeBlack, null));
            item3TextView.setTextColor(getResources().getColor(R.color.bikeeBlack, null));
            item4TextView.setTextColor(getResources().getColor(R.color.bikeeBlack, null));
            item5TextView.setTextColor(getResources().getColor(R.color.bikeeBlack, null));
        }

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();

        int count = 0;

        if (list.get(0) != null) {
            ImageUtil.setRoundRectangleImageFromFile(
                    MyApplication.getmContext(),
                    list.get(0),
                    R.drawable.img_01,
                    item1ImageView,
                    8
            );
            count++;
        } else
            item1ImageView.setImageResource(R.drawable.img_01);
        if (list.get(1) != null) {
            ImageUtil.setRoundRectangleImageFromFile(
                    MyApplication.getmContext(),
                    list.get(1),
                    R.drawable.img_02,
                    item2ImageView,
                    8
            );
            count++;
        } else
            item2ImageView.setImageResource(R.drawable.img_02);
        if (list.get(2) != null) {
            ImageUtil.setRoundRectangleImageFromFile(
                    MyApplication.getmContext(),
                    list.get(2),
                    R.drawable.img_03,
                    item3ImageView,
                    8
            );
            count++;
        } else
            item3ImageView.setImageResource(R.drawable.img_03);
        if (list.get(3) != null) {
            ImageUtil.setRoundRectangleImageFromFile(
                    MyApplication.getmContext(),
                    list.get(3),
                    R.drawable.img_04,
                    item4ImageView,
                    8
            );
            count++;
        } else
            item4ImageView.setImageResource(R.drawable.img_04);
        if (list.get(4) != null) {
            ImageUtil.setRoundRectangleImageFromFile(
                    MyApplication.getmContext(),
                    list.get(4),
                    R.drawable.img_05,
                    item5ImageView,
                    8
            );
            count++;
        } else
            item5ImageView.setImageResource(R.drawable.img_05);

        if (count == MAXIMUM_LIST_SIZE) {
            warningImageView.setVisibility(View.INVISIBLE);
            warningTextView.setVisibility(View.INVISIBLE);
            if ((null != registerBicycleINF) && (!registerBicycleINF.getEnable())) {
                registerBicycleINF.setEnable(true);
            }
        } else {
            warningImageView.setVisibility(View.VISIBLE);
            warningTextView.setVisibility(View.VISIBLE);
            if ((null != registerBicycleINF) && (registerBicycleINF.getEnable())) {
                registerBicycleINF.setEnable(false);
            }
        }
    }

    @OnClick({R.id.fragment_register_bicycle_picture_camera_image_view,
            R.id.fragment_register_bicycle_picture_gallery_image_view})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_register_bicycle_picture_camera_image_view:
                intent = new Intent(getActivity(), CameraActivity.class);
                intent.putExtra("LIST", list);
                startActivityForResult(intent, 1);
                break;
            case R.id.fragment_register_bicycle_picture_gallery_image_view:
                intent = new Intent(getActivity(), GalleryActivity.class);
                intent.putExtra("LIST", list);
                startActivityForResult(intent, 2);
                break;
        }
    }

    @OnClick({R.id.thumbnail_item1_cancel_image_view,
            R.id.thumbnail_item2_cancel_image_view,
            R.id.thumbnail_item3_cancel_image_view,
            R.id.thumbnail_item4_cancel_image_view,
            R.id.thumbnail_item5_cancel_image_view})
    void cancel(View view) {
        warningImageView.setVisibility(View.VISIBLE);
        warningTextView.setVisibility(View.VISIBLE);
        if ((null != registerBicycleINF) && (registerBicycleINF.getEnable())) {
            registerBicycleINF.setEnable(false);
        }

        switch (view.getId()) {
            case R.id.thumbnail_item1_cancel_image_view:
                if (list.get(0) != null) {
                    item1ImageView.setImageResource(R.drawable.img_01);
                    list.set(0, null);
                }
                break;
            case R.id.thumbnail_item2_cancel_image_view:
                if (list.get(1) != null) {
                    item2ImageView.setImageResource(R.drawable.img_02);
                    list.set(1, null);
                }
                break;
            case R.id.thumbnail_item3_cancel_image_view:
                if (list.get(2) != null) {
                    item3ImageView.setImageResource(R.drawable.img_03);
                    list.set(2, null);
                }
                break;
            case R.id.thumbnail_item4_cancel_image_view:
                if (list.get(3) != null) {
                    item4ImageView.setImageResource(R.drawable.img_04);
                    list.set(3, null);
                }
                break;
            case R.id.thumbnail_item5_cancel_image_view:
                if (list.get(4) != null) {
                    item5ImageView.setImageResource(R.drawable.img_05);
                    list.set(4, null);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (((requestCode == 2) || (requestCode == 1)) && (resultCode == Activity.RESULT_OK)) {
            list = (ArrayList) data.getSerializableExtra("LIST");
        }
    }

    public List<File> getFiles() {
        return list;
    }

    public void setRegisterBicycleINF(RegisterBicycleINF registerBicycleINF) {
        this.registerBicycleINF = registerBicycleINF;
    }
}