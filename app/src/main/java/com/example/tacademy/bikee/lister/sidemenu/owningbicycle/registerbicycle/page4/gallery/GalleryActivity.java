package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page4.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.utils.ImageUtil;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page4.gallery.folder.GalleryFolderFragment;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page4.gallery.folder.GetGalleryFolderNameListener;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page4.gallery.picture.GalleryPictureFragment;
import com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page4.gallery.picture.GetGalleryPicturePathListener;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GalleryActivity extends AppCompatActivity implements GetGalleryPicturePathListener, GetGalleryFolderNameListener {
    private Intent intent;
    private int currentFragmentState;
    private final static int GALLERY_PICTURE_STATE = 1;
    private final static int GALLERY_FOLDER_STATE = 2;
    private GalleryPictureFragment galleryPictureFragment;
    private GalleryFolderFragment galleryFolderFragment;
    private ArrayList<File> list;
    private final static int MAXIMUM_LIST_SIZE = 5;
    @Bind(R.id.gallery_tool_bar_picture_button_image_view)
    ImageView pictureImageView;
    @Bind(R.id.gallery_tool_bar_folder_button_image_view)
    ImageView folderImageView;
    @Bind(R.id.activity_gallery_warning_image_view)
    ImageView warningImageView;
    @Bind(R.id.activity_gallery_warning_text_view)
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_owning_bicycle_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.gallery_tool_bar);

        ButterKnife.bind(this);

        item1ImageView.setImageResource(R.drawable.bike_img_01);
        item2ImageView.setImageResource(R.drawable.bike_img_02);
        item3ImageView.setImageResource(R.drawable.bike_img_03);
        item4ImageView.setImageResource(R.drawable.bike_img_04);
        item5ImageView.setImageResource(R.drawable.bike_img_05);

        if (Build.VERSION.SDK_INT < 23) {
            item1TextView.setTextColor(getResources().getColor(R.color.bikeeWhite));
            item2TextView.setTextColor(getResources().getColor(R.color.bikeeWhite));
            item3TextView.setTextColor(getResources().getColor(R.color.bikeeWhite));
            item4TextView.setTextColor(getResources().getColor(R.color.bikeeWhite));
            item5TextView.setTextColor(getResources().getColor(R.color.bikeeWhite));
        } else {
            item1TextView.setTextColor(getResources().getColor(R.color.bikeeWhite, null));
            item2TextView.setTextColor(getResources().getColor(R.color.bikeeWhite, null));
            item3TextView.setTextColor(getResources().getColor(R.color.bikeeWhite, null));
            item4TextView.setTextColor(getResources().getColor(R.color.bikeeWhite, null));
            item5TextView.setTextColor(getResources().getColor(R.color.bikeeWhite, null));
        }

        intent = getIntent();
        list = (ArrayList) intent.getSerializableExtra("LIST");
        ArrayList<String> checkedPathList = new ArrayList<>();

        if (list.get(0) != null) {
            ImageUtil.setRoundRectangleImageFromFile(
                    MyApplication.getmContext(),
                    list.get(0),
                    R.drawable.bike_img_01,
                    item1ImageView,
                    8
            );
            checkedPathList.add(list.get(0).getPath());
        }
        if (list.get(1) != null) {
            ImageUtil.setRoundRectangleImageFromFile(
                    MyApplication.getmContext(),
                    list.get(1),
                    R.drawable.bike_img_02,
                    item2ImageView,
                    8
            );
            checkedPathList.add(list.get(1).getPath());
        }
        if (list.get(2) != null) {
            ImageUtil.setRoundRectangleImageFromFile(
                    MyApplication.getmContext(),
                    list.get(2),
                    R.drawable.bike_img_03,
                    item3ImageView,
                    8
            );
            checkedPathList.add(list.get(2).getPath());
        }
        if (list.get(3) != null) {
            ImageUtil.setRoundRectangleImageFromFile(
                    MyApplication.getmContext(),
                    list.get(3),
                    R.drawable.bike_img_04,
                    item4ImageView,
                    8
            );
            checkedPathList.add(list.get(3).getPath());
        }
        if (list.get(4) != null) {
            ImageUtil.setRoundRectangleImageFromFile(
                    MyApplication.getmContext(),
                    list.get(4),
                    R.drawable.bike_img_05,
                    item5ImageView,
                    8
            );
            checkedPathList.add(list.get(4).getPath());
        }

        currentFragmentState = GALLERY_PICTURE_STATE;
        pictureImageView.setImageResource(R.drawable.image_icon_b);

        galleryPictureFragment = GalleryPictureFragment.newInstance(checkedPathList);
        galleryFolderFragment = GalleryFolderFragment.newInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_gallery_container, galleryPictureFragment).commit();
        }

        galleryPictureFragment.setGetGalleryPicturePathListener(this);
        galleryFolderFragment.setGetGalleryFolderNameListener(this);

        if (getPrimaryEmptyPosition() == (MAXIMUM_LIST_SIZE)) {
            warningImageView.setVisibility(View.INVISIBLE);
            warningTextView.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.gallery_tool_bar_back_button_layout)
    void back(View view) {
        // TODO : show popup
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick({R.id.gallery_tool_bar_picture_button_image_view,
            R.id.gallery_tool_bar_folder_button_image_view})
    void type(View view) {
        switch (view.getId()) {
            case R.id.gallery_tool_bar_picture_button_image_view:
                if (currentFragmentState == GALLERY_FOLDER_STATE) {
                    currentFragmentState = GALLERY_PICTURE_STATE;
                    pictureImageView.setImageResource(R.drawable.image_icon_b);
                    folderImageView.setImageResource(R.drawable.folder_icon_w);
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_gallery_container, galleryPictureFragment).commit();
                }
                break;
            case R.id.gallery_tool_bar_folder_button_image_view:
                if (currentFragmentState == GALLERY_PICTURE_STATE) {
                    currentFragmentState = GALLERY_FOLDER_STATE;
                    pictureImageView.setImageResource(R.drawable.image_icon_w);
                    folderImageView.setImageResource(R.drawable.folder_icon_b);
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_gallery_container, galleryFolderFragment).commit();
                }
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
        galleryPictureFragment.notifyListIsNotFull();
        switch (view.getId()) {
            case R.id.thumbnail_item1_cancel_image_view:
                if (list.get(0) != null) {
                    item1ImageView.setImageResource(R.drawable.bike_img_01);
                    galleryPictureFragment.unCheck(list.get(0).getPath());
                    list.set(0, null);
                }
                break;
            case R.id.thumbnail_item2_cancel_image_view:
                if (list.get(1) != null) {
                    item2ImageView.setImageResource(R.drawable.bike_img_02);
                    galleryPictureFragment.unCheck(list.get(1).getPath());
                    list.set(1, null);
                }
                break;
            case R.id.thumbnail_item3_cancel_image_view:
                if (list.get(2) != null) {
                    item3ImageView.setImageResource(R.drawable.bike_img_03);
                    galleryPictureFragment.unCheck(list.get(2).getPath());
                    list.set(2, null);
                }
                break;
            case R.id.thumbnail_item4_cancel_image_view:
                if (list.get(3) != null) {
                    item4ImageView.setImageResource(R.drawable.bike_img_04);
                    galleryPictureFragment.unCheck(list.get(3).getPath());
                    list.set(3, null);
                }
                break;
            case R.id.thumbnail_item5_cancel_image_view:
                if (list.get(4) != null) {
                    item5ImageView.setImageResource(R.drawable.bike_img_05);
                    galleryPictureFragment.unCheck(list.get(4).getPath());
                    list.set(4, null);
                }
                break;
        }
    }

    @Override
    public void getGalleryPicturePath(String filePath) {
        int position = getPrimaryEmptyPosition();
        if (position < MAXIMUM_LIST_SIZE) {
            File file = new File(filePath);
            list.set(position, file);
            switch (position) {
                case 0:
                    ImageUtil.setRoundRectangleImageFromFile(
                            MyApplication.getmContext(),
                            list.get(0),
                            R.drawable.bike_img_01,
                            item1ImageView,
                            8
                    );
                    break;
                case 1:
                    ImageUtil.setRoundRectangleImageFromFile(
                            MyApplication.getmContext(),
                            list.get(1),
                            R.drawable.bike_img_02,
                            item2ImageView,
                            8
                    );
                    break;
                case 2:
                    ImageUtil.setRoundRectangleImageFromFile(
                            MyApplication.getmContext(),
                            list.get(2),
                            R.drawable.bike_img_03,
                            item3ImageView,
                            8
                    );
                    break;
                case 3:
                    ImageUtil.setRoundRectangleImageFromFile(
                            MyApplication.getmContext(),
                            list.get(3),
                            R.drawable.bike_img_04,
                            item4ImageView,
                            8
                    );
                    break;
                case 4:
                    ImageUtil.setRoundRectangleImageFromFile(
                            MyApplication.getmContext(),
                            list.get(4),
                            R.drawable.bike_img_05,
                            item5ImageView,
                            8
                    );
                    break;
            }
        }
        if (getPrimaryEmptyPosition() >= MAXIMUM_LIST_SIZE) {
            warningImageView.setVisibility(View.INVISIBLE);
            warningTextView.setVisibility(View.INVISIBLE);
            galleryPictureFragment.notifyListIsFull();
        }
    }

    @Override
    public void getGalleryFolderName(String folderName) {
        currentFragmentState = GALLERY_PICTURE_STATE;
        pictureImageView.setImageResource(R.drawable.image_icon_b);
        folderImageView.setImageResource(R.drawable.folder_icon_w);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_gallery_container, galleryPictureFragment).commit();
        galleryPictureFragment.enterFolder(folderName);
    }

    private int getPrimaryEmptyPosition() {
        int position = 0;

        for (File file : list) {
            if (file == null) {
                return position;
            }
            position++;
        }

        return position;
    }

    @Override
    public void onBackPressed() {
        // TODO : show popup
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
