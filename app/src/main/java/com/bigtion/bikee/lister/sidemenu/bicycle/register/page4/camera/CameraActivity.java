package com.bigtion.bikee.lister.sidemenu.bicycle.register.page4.camera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigtion.bikee.R;
import com.bigtion.bikee.etc.MyApplication;
import com.bigtion.bikee.etc.utils.ImageUtil;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private Intent intent;
    @SuppressWarnings("deprecation")
    private Camera camera;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private String filePath;
    private File file;
    private Bitmap bitmap;
    private ArrayList<File> list;
    private  final static int MAXIMUM_LIST_SIZE = 5;
    private final static int BACK_STATE = 1;
    private final static int SAVE_STATE = 2;
    private int state = BACK_STATE;
    @SuppressWarnings("deprecation")
    private List<Camera.Size> previewList;
    private int previewSize_index;
    @Bind(R.id.activity_camera_image_view)
    ImageView freezeImageView;
    @Bind(R.id.activity_camera_freeze_button)
    Button freezeButton;
    @Bind(R.id.activity_camera_back_save_button)
    Button backSaveButton;
    @Bind(R.id.activity_camera_cancel_button)
    Button cancelButton;
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
    @Bind(R.id.thumbnail_item1_cancel_image_view)
    ImageView item1CancelImageView;
    @Bind(R.id.thumbnail_item2_cancel_image_view)
    ImageView item2CancelImageView;
    @Bind(R.id.thumbnail_item3_cancel_image_view)
    ImageView item3CancelImageView;
    @Bind(R.id.thumbnail_item4_cancel_image_view)
    ImageView item4CancelImageView;
    @Bind(R.id.thumbnail_item5_cancel_image_view)
    ImageView item5CancelImageView;
    @SuppressWarnings("deprecation")
    private Camera.AutoFocusCallback mAutoFocus = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            camera.takePicture(null, null, jpegCallback);
        }
    };
    @SuppressWarnings("deprecation")
    private Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            FileOutputStream outputStream = null;
            OutputStream out = null;

            try {
                File folder = new File(Environment.getExternalStorageDirectory().getPath()+"/bikee");
                if(!folder.exists())
                    folder.mkdir();
                filePath = String.format(Environment.getExternalStorageDirectory().getPath()+"/bikee/%d.jpg", System.currentTimeMillis());
                outputStream = new FileOutputStream(filePath);
                outputStream.write(data);

                Display display = getWindowManager().getDefaultDisplay();
                int rotation = 0;
                switch (display.getRotation()) {
                    case Surface.ROTATION_0:
                        rotation = 90;
                        break;
                    case Surface.ROTATION_90:
                        rotation = 0;
                        break;
                    case Surface.ROTATION_180:
                        rotation = 270;
                        break;
                    case Surface.ROTATION_270:
                        rotation = 180;
                        break;
                }
                bitmap = toBitmap(data);
                bitmap = rotate(bitmap, rotation);

                file = new File(filePath);
                file.createNewFile();
                out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    outputStream.close();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            freezeImageView.setImageBitmap(bitmap);
            freezeImageView.setVisibility(View.VISIBLE);
            surfaceView.setVisibility(View.INVISIBLE);

            backSaveButton.setBackgroundResource(R.drawable.camera_save_button);
            state = SAVE_STATE;
            backSaveButton.setEnabled(true);
            cancelButton.setEnabled(true);
        }
    };

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ButterKnife.bind(this);

        surfaceView = (SurfaceView) findViewById(R.id.activity_camera_surface_view);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

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
            item1TextView.setTextColor(getResources().getColor(R.color.bikeeWhite, getTheme()));
            item2TextView.setTextColor(getResources().getColor(R.color.bikeeWhite, getTheme()));
            item3TextView.setTextColor(getResources().getColor(R.color.bikeeWhite, getTheme()));
            item4TextView.setTextColor(getResources().getColor(R.color.bikeeWhite, getTheme()));
            item5TextView.setTextColor(getResources().getColor(R.color.bikeeWhite, getTheme()));
        }

        intent = getIntent();
        list = (ArrayList) intent.getSerializableExtra("LIST");

        if (list.get(0) != null)
            ImageUtil.setRoundRectangleImageFromURL(
                    MyApplication.getmContext(),
                    list.get(0).getPath(),
                    R.drawable.bike_img_01,
                    item1ImageView,
                    8
            );
        if (list.get(1) != null)
            ImageUtil.setRoundRectangleImageFromURL(
                    MyApplication.getmContext(),
                    list.get(1).getPath(),
                    R.drawable.bike_img_02,
                    item2ImageView,
                    8
            );
        if (list.get(2) != null)
            ImageUtil.setRoundRectangleImageFromURL(
                    MyApplication.getmContext(),
                    list.get(2).getPath(),
                    R.drawable.bike_img_03,
                    item3ImageView,
                    8
            );
        if (list.get(3) != null)
            ImageUtil.setRoundRectangleImageFromURL(
                    MyApplication.getmContext(),
                    list.get(3).getPath(),
                    R.drawable.bike_img_04,
                    item4ImageView,
                    8
            );
        if (list.get(4) != null)
            ImageUtil.setRoundRectangleImageFromURL(
                    MyApplication.getmContext(),
                    list.get(4).getPath(),
                    R.drawable.bike_img_05,
                    item5ImageView,
                    8
            );

        if (getPrimaryEmptyPosition() == MAXIMUM_LIST_SIZE) {
            freezeButton.setEnabled(false);
            freezeButton.setBackgroundResource(R.drawable.camera_inactive_button);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= 23)
            if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    || (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    || (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                StringBuilder stringBuilder = new StringBuilder();
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
                        || shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    stringBuilder.append("저장소 읽기/쓰기");
                }
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    if (stringBuilder.length() > 0)
                        stringBuilder.append(", ");
                    stringBuilder.append("카메라");
                }
                stringBuilder.append(" 권한이 있어야 앱이 올바르게 작동합니다.");

                Toast.makeText(CameraActivity.this, stringBuilder, Toast.LENGTH_SHORT).show();

                onBackPressed();
            }
    }

    @OnClick({R.id.activity_camera_surface_view,
            R.id.activity_camera_back_save_button,
            R.id.activity_camera_freeze_button,
            R.id.activity_camera_cancel_button,
            R.id.thumbnail_item1_cancel_image_view,
            R.id.thumbnail_item2_cancel_image_view,
            R.id.thumbnail_item3_cancel_image_view,
            R.id.thumbnail_item4_cancel_image_view,
            R.id.thumbnail_item5_cancel_image_view})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_camera_surface_view:
                camera.autoFocus(null);
                break;
            case R.id.activity_camera_back_save_button:
                if (state == BACK_STATE) {
                    // TODO : 팝업?
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    int position = getPrimaryEmptyPosition();
                    switch (position) {
                        case 0:
                            ImageUtil.setRoundRectangleImageFromURL(
                                    MyApplication.getmContext(),
                                    file.getPath(),
                                    R.drawable.bike_img_01,
                                    item1ImageView,
                                    getResources().getDimensionPixelOffset(
                                            R.dimen.fragment_register_bicycle_picture_thumbnail_item_image_view_round_radius
                                    )
                            );
                            break;
                        case 1:
                            ImageUtil.setRoundRectangleImageFromURL(
                                    MyApplication.getmContext(),
                                    file.getPath(),
                                    R.drawable.bike_img_02,
                                    item2ImageView,
                                    getResources().getDimensionPixelOffset(
                                            R.dimen.fragment_register_bicycle_picture_thumbnail_item_image_view_round_radius
                                    )
                            );
                            break;
                        case 2:
                            ImageUtil.setRoundRectangleImageFromURL(
                                    MyApplication.getmContext(),
                                    file.getPath(),
                                    R.drawable.bike_img_03,
                                    item3ImageView,
                                    getResources().getDimensionPixelOffset(
                                            R.dimen.fragment_register_bicycle_picture_thumbnail_item_image_view_round_radius
                                    )
                            );
                            break;
                        case 3:
                            ImageUtil.setRoundRectangleImageFromURL(
                                    MyApplication.getmContext(),
                                    file.getPath(),
                                    R.drawable.bike_img_04,
                                    item4ImageView,
                                    getResources().getDimensionPixelOffset(
                                            R.dimen.fragment_register_bicycle_picture_thumbnail_item_image_view_round_radius
                                    )
                            );
                            break;
                        case 4:
                            ImageUtil.setRoundRectangleImageFromURL(
                                    MyApplication.getmContext(),
                                    file.getPath(),
                                    R.drawable.bike_img_05,
                                    item5ImageView,
                                    getResources().getDimensionPixelOffset(
                                            R.dimen.fragment_register_bicycle_picture_thumbnail_item_image_view_round_radius
                                    )
                            );
                            break;
                    }
                    list.set(position, file);

                    surfaceView.setVisibility(View.VISIBLE);
                    freezeImageView.setVisibility(View.INVISIBLE);

                    backSaveButton.setBackgroundResource(R.drawable.camera_back_button);
                    state = BACK_STATE;
                    if (getPrimaryEmptyPosition() != MAXIMUM_LIST_SIZE) {
                        freezeButton.setEnabled(true);
                        freezeButton.setBackgroundResource(R.drawable.camera_active_button);
                    }
                    cancelButton.setEnabled(false);
                    cancelButton.setBackgroundResource(R.drawable.camera_cancel_button);

                    item1CancelImageView.setEnabled(true);
                    item2CancelImageView.setEnabled(true);
                    item3CancelImageView.setEnabled(true);
                    item4CancelImageView.setEnabled(true);
                    item5CancelImageView.setEnabled(true);
                }
                break;
            case R.id.activity_camera_freeze_button:
                freezeButton.setEnabled(false);
                backSaveButton.setEnabled(false);

                freezeButton.setBackgroundResource(R.drawable.camera_inactive_button);
                camera.autoFocus(mAutoFocus);

                item1CancelImageView.setEnabled(false);
                item2CancelImageView.setEnabled(false);
                item3CancelImageView.setEnabled(false);
                item4CancelImageView.setEnabled(false);
                item5CancelImageView.setEnabled(false);
                break;
            case R.id.activity_camera_cancel_button:
                surfaceView.setVisibility(View.VISIBLE);
                freezeImageView.setVisibility(View.INVISIBLE);

                backSaveButton.setBackgroundResource(R.drawable.camera_back_button);
                state = BACK_STATE;
                freezeButton.setEnabled(true);
                freezeButton.setBackgroundResource(R.drawable.camera_active_button);
                cancelButton.setEnabled(false);
                cancelButton.setBackgroundResource(R.drawable.camera_cancel_button);

                item1CancelImageView.setEnabled(true);
                item2CancelImageView.setEnabled(true);
                item3CancelImageView.setEnabled(true);
                item4CancelImageView.setEnabled(true);
                item5CancelImageView.setEnabled(true);

                file.delete();
                break;
            case R.id.thumbnail_item1_cancel_image_view:
                freezeButton.setEnabled(true);
                freezeButton.setBackgroundResource(R.drawable.camera_active_button);
                if (list.get(0) != null) {
                    item1ImageView.setImageResource(R.drawable.bike_img_01);
                    list.set(0, null);
                }
                break;
            case R.id.thumbnail_item2_cancel_image_view:
                freezeButton.setEnabled(true);
                freezeButton.setBackgroundResource(R.drawable.camera_active_button);
                if (list.get(1) != null) {
                    item2ImageView.setImageResource(R.drawable.bike_img_02);
                    list.set(1, null);
                }
                break;
            case R.id.thumbnail_item3_cancel_image_view:
                freezeButton.setEnabled(true);
                freezeButton.setBackgroundResource(R.drawable.camera_active_button);
                if (list.get(2) != null) {
                    item3ImageView.setImageResource(R.drawable.bike_img_03);
                    list.set(2, null);
                }
                break;
            case R.id.thumbnail_item4_cancel_image_view:
                freezeButton.setEnabled(true);
                freezeButton.setBackgroundResource(R.drawable.camera_active_button);
                if (list.get(3) != null) {
                    item4ImageView.setImageResource(R.drawable.bike_img_04);
                    list.set(3, null);
                }
                break;
            case R.id.thumbnail_item5_cancel_image_view:
                freezeButton.setEnabled(true);
                freezeButton.setBackgroundResource(R.drawable.camera_active_button);
                if (list.get(4) != null) {
                    item5ImageView.setImageResource(R.drawable.bike_img_05);
                    list.set(4, null);
                }
                break;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();
        camera.stopPreview();

        previewList = getCorrectlySupportedPictureSizes();
        previewSize_index = previewList.size() - 1;
        if (null != previewList) {
            Camera.Size previewSize = previewList.get(previewSize_index);
            Camera.Parameters param = camera.getParameters();
            param.setPreviewSize(previewSize.width, previewSize.height);
            param.setPictureSize(previewSize.width, previewSize.height);
            camera.setParameters(param);
            camera.setDisplayOrientation(90);

            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    @SuppressWarnings("deprecation")
    private List<Camera.Size> getCorrectlySupportedPictureSizes() {
        if (camera == null)
            return null;

        List<Camera.Size> pictureSizes = camera.getParameters().getSupportedPictureSizes();
        List<Camera.Size> previewSizes = camera.getParameters().getSupportedPreviewSizes();
        Camera.Size pictureSize;
        Camera.Size previewSize;
        double pictureRatio;
        double previewRatio;
        final double aspectTolerance = 0.05;
        boolean isUsablePicture;

        for (int indexOfPicture = pictureSizes.size() - 1; indexOfPicture >= 0; --indexOfPicture) {
            pictureSize = pictureSizes.get(indexOfPicture);
            pictureRatio = (double) pictureSize.width / (double) pictureSize.height;
            isUsablePicture = false;

            for (int indexOfPreview = previewSizes.size() - 1; indexOfPreview >= 0; --indexOfPreview) {
                previewSize = previewSizes.get(indexOfPreview);

                previewRatio = (double) previewSize.width / (double) previewSize.height;

                if (Math.abs(pictureRatio - previewRatio) < aspectTolerance) {
                    isUsablePicture = true;
                    break;
                }
            }

            if (!isUsablePicture) {
                pictureSizes.remove(indexOfPicture);
            }
        }

        return pictureSizes;
    }

    private Bitmap toBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    private Bitmap rotate(Bitmap in, int angle) {
        Matrix mat = new Matrix();
        mat.postRotate(angle);
        return Bitmap.createBitmap(in, 0, 0, in.getWidth(), in.getHeight(), mat, true);
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
        // TODO : 팝업?
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
