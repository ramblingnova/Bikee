package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page4;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.Util;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.RegisterBicycleINF;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page4.camera.CameraActivity;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page4.gallery.GalleryActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    private List<File> files;

    public static final int REQ_CODE_SELECT_IMAGE = 100;

    public static RegisterBicyclePictureFragment newInstance() {
        return new RegisterBicyclePictureFragment();
    }

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<>();
        for (int i = 0; i < MAXIMUM_LIST_SIZE; i++)
            list.add(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register_bicycle_picture, container, false);
        ButterKnife.bind(this, view);
//        Button btn = (Button) view.findViewById(R.id.fragment_register_bicycle_picture_input_image_button);
//        btn.setOnClickListener(this);

        files = new ArrayList<>();

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
    public void onResume() {
        super.onResume();

        int count = 0;

        for (File file : list)
            if (file != null)
                count++;

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

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
////            case R.id.fragment_register_bicycle_picture_input_image_button:
////                intent = new Intent(Intent.ACTION_PICK);
////                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
////                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
////                break;
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(MyApplication.getmContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();

        if (((requestCode == 2) || (requestCode == 1)) && (resultCode == Activity.RESULT_OK)) {
            list = (ArrayList) data.getSerializableExtra("LIST");

            if (list.get(0) != null)
                Util.setRoundRectangleImageFromFile(MyApplication.getmContext(), R.drawable.img_01, list.get(0), 8, item1ImageView);
            else
                item1ImageView.setImageResource(R.drawable.img_01);
            if (list.get(1) != null)
                Util.setRoundRectangleImageFromFile(MyApplication.getmContext(), R.drawable.img_02, list.get(1), 8, item2ImageView);
            else
                item2ImageView.setImageResource(R.drawable.img_02);
            if (list.get(2) != null)
                Util.setRoundRectangleImageFromFile(MyApplication.getmContext(), R.drawable.img_03, list.get(2), 8, item3ImageView);
            else
                item3ImageView.setImageResource(R.drawable.img_03);
            if (list.get(3) != null)
                Util.setRoundRectangleImageFromFile(MyApplication.getmContext(), R.drawable.img_04, list.get(3), 8, item4ImageView);
            else
                item4ImageView.setImageResource(R.drawable.img_04);
            if (list.get(4) != null)
                Util.setRoundRectangleImageFromFile(MyApplication.getmContext(), R.drawable.img_05, list.get(4), 8, item5ImageView);
            else
                item5ImageView.setImageResource(R.drawable.img_05);
        }

//        if (requestCode == REQ_CODE_SELECT_IMAGE) {
//            if (resultCode == Activity.RESULT_OK) {
//                try {
//                    //Uri에서 이미지 이름을 얻어온다.
//                    String name_Str = getRealPathFromURI(data.getData());
//                    //이미지 데이터를 비트맵으로 받아온다.
//                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(MyApplication.getmContext().getContentResolver(), data.getData());
//                    ImageView image = (ImageView) view.findViewById(R.id.fragment_register_bicycle_picture_input_image_image_view);
//                    //배치해놓은 ImageView에 set
//                    image.setImageBitmap(image_bitmap);
//                    //Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();
//                    files.add(new File(name_Str));
//                    files.add(new File(name_Str));
//                    files.add(new File(name_Str));
//
////                    if (files.size() != 0) {
////                        if ((null != registerBicycleINF) && (!registerBicycleINF.getEnable())) {
////                            registerBicycleINF.setEnable(true);
////                        }
////                    } else {
////                        if ((null != registerBicycleINF) && (registerBicycleINF.getEnable())) {
////                            registerBicycleINF.setEnable(false);
////                        }
////                    }
//                } catch (FileNotFoundException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

//    public String getRealPathFromURI(Uri contentUri) {
//        String res = null;
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = MyApplication.getmContext().getContentResolver().query(contentUri, proj, null, null, null);
//        if (cursor.moveToFirst()) {
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            res = cursor.getString(column_index);
//        }
//        cursor.close();
//        return res;
//    }

    public List<File> getFiles() {
        return list;
    }

    RegisterBicycleINF registerBicycleINF;

    public void setRegisterBicycleINF(RegisterBicycleINF registerBicycleINF) {
        this.registerBicycleINF = registerBicycleINF;
    }
}
