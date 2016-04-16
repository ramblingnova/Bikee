package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tacademy.bikee.BuildConfig;
import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.chatting.room.ConversationActivity;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.SendBirdHelper;
import com.example.tacademy.bikee.etc.manager.PropertyManager;
import com.example.tacademy.bikee.etc.utils.ImageUtil;
import com.example.tacademy.bikee.etc.dao.Comment;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.etc.utils.RefinementUtil;
import com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.finallyrequestreservation.FinallyRequestReservationActivity;
import com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.postscription.BicyclePostScriptListActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilteredBicycleDetailInformationActivity extends AppCompatActivity implements OnMapReadyCallback {
    // TODO : handle filter result, modify UI, need UnFilteredBycycleDetailInformation activity
    private Intent intent;
    private String listerEmail;
    private String bicycleId;
    private String bicycleImageURL;
    private String listerPhone;
    private String type;
    private List<String> components;
    private String height;
    private double latitude;
    private double longitude;
    private int price;
    @Bind(R.id.bicycle_picture_lister_information_bicycle_picture_image_view)
    ImageView bicyclePicture;
    @Bind(R.id.lister_information_lister_picture_image_view)
    ImageView listerPicture;
    @Bind(R.id.lister_information_lister_name_text_view)
    TextView listerName;
    @Bind(R.id.bicycle_description_bicycle_name_text_view)
    TextView bicycleName;
    @Bind(R.id.bicycle_description_bicycle_introduction_text_view)
    TextView bicycleIntro;
    @Bind(R.id.bicycle_detail_information_bicycle_type_text_view)
    TextView bicycleType;
    @Bind(R.id.bicycle_detail_information_bicycle_height_text_view)
    TextView bicycleHeight;
    @Bind(R.id.bicycle_detail_information_bicycle_component_text_view)
    TextView bicycleComponent;
    @Bind(R.id.bicycle_detail_information_bicycle_location_text_view)
    TextView rentalPlaceText;
    @Bind(R.id.bicycle_detail_information_reservation_period_start_date_text_view)
    TextView startDate;
    @Bind(R.id.bicycle_detail_information_reservation_period_end_date_text_view)
    TextView endDate;
    @Bind(R.id.bicycle_detail_information_bicycle_postscript_renter_picture_image_view)
    ImageView postscriptPicture;
    @Bind(R.id.bicycle_detail_information_bicycle_postscript_renter_name_text_view)
    TextView postscriptName;
    @Bind(R.id.bicycle_detail_information_bicycle_postscript_create_date_text_view)
    TextView postscriptDate;
    @Bind(R.id.bicycle_detail_information_bicycle_postscript_renter_comment_text_view)
    TextView postscriptComment;
    @Bind(R.id.bicycle_detail_information_bicycle_postscript_rating_bar)
    RatingBar postscriptPoint;
    @Bind(R.id.activity_filtered_bicycle_detail_information_bicycle_post_script_layout)
    View postScriptView;

    private static final String TAG = "FILTERED_B_D_I_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_bicycle_detail_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_filtered_bicycle_detail_information_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_backable_tool_bar);
        ButterKnife.bind(this);

        intent = getIntent();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_filtered_bicycle_detail_information_small_map);
        mapFragment.getMapAsync(this);
        init();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap gm = googleMap;
        gm.getUiSettings().setScrollGesturesEnabled(false);
        gm.getUiSettings().setZoomControlsEnabled(true);

        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.target(
                new LatLng(
                        intent.getDoubleExtra("LATITUDE", 1.0),
                        intent.getDoubleExtra("LONGITUDE", 1.0)
                )
        );
        builder.zoom(15);
        CameraPosition position = builder.build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
        gm.moveCamera(update);

        MarkerOptions options = new MarkerOptions();
        options.position(
                new LatLng(
                        intent.getDoubleExtra("LATITUDE", 1.0),
                        intent.getDoubleExtra("LONGITUDE", 1.0)
                )
        );
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.rider_main_bike_b_icon));
        options.anchor(0.5f, 0.5f);
        gm.addMarker(options);

        gm.getUiSettings().setZoomGesturesEnabled(false);
    }

    @OnClick({R.id.renter_backable_tool_bar_back_button_layout,
            R.id.lister_information_call_with_lister_button,
            R.id.lister_information_chat_with_lister_button,
            R.id.bicycle_detail_information_bicycle_postscript_button,
            R.id.activity_filtered_bicycle_detail_information_button})
    void back(View view) {
        switch (view.getId()) {
            case R.id.renter_backable_tool_bar_back_button_layout:
                super.onBackPressed();
                break;
            case R.id.lister_information_call_with_lister_button:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + listerPhone));
                startActivity(intent);
                break;
            case R.id.lister_information_chat_with_lister_button:
                // TODO : chatting start
                intent = new Intent(this, ConversationActivity.class);
                intent.putExtra("TARGET_USER_NAME", "Tester3");
                intent.putExtra("TARGET_USER_ID", listerEmail);
                intent.putExtra("APP_ID", "2E377FE1-E1AD-4484-A66F-696AF1306F58");
                intent.putExtra("USER_ID", PropertyManager.getInstance().getEmail());
                intent.putExtra("USER_NAME", PropertyManager.getInstance().getName());
                intent.putExtra("BICYCLE_ID", bicycleId);
                intent.putExtra("START", true);
                startActivity(intent);
                break;
            case R.id.bicycle_detail_information_bicycle_postscript_button:
                intent = new Intent(FilteredBicycleDetailInformationActivity.this, BicyclePostScriptListActivity.class);
                intent.putExtra("ID", bicycleId);
                startActivity(intent);
                break;
            case R.id.activity_filtered_bicycle_detail_information_button:
                intent = new Intent(FilteredBicycleDetailInformationActivity.this, FinallyRequestReservationActivity.class);
                intent.putExtra("ID", bicycleId);
                intent.putExtra("IMAGEURL", bicycleImageURL);
                intent.putExtra("BICYCLETYPE", type);
                intent.putExtra("BICYCLEHEIGHT", height);
                intent.putExtra("BICYCLELATITUDE", latitude);
                intent.putExtra("BICYCLELONGITUDE", longitude);
                intent.putExtra("BICYCLEPRICE", price);
                startActivity(intent);
                break;
        }
    }

    private void init() {
        bicycleId = intent.getStringExtra("ID");
        NetworkManager.getInstance().selectBicycleDetail(
                bicycleId,
                null,
                new Callback<ReceiveObject>() {
                    @Override
                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                        ReceiveObject receiveObject = response.body();
                        // TODO image, type, height, latitude, longitude, price, renterName, postScript
                        if (BuildConfig.DEBUG)
                            Log.d("result", "onResponse Success");
                        Result result = receiveObject.getResult().get(0);
                        if (BuildConfig.DEBUG)
                            Log.d("result", "onResponse imageURL : " + RefinementUtil.getBicycleImageURLStringFromResult(result)
                                            + ", BicycleType : " + result.getType()
                                            + ", BicycleHeight : " + result.getHeight()
                                            + ", BicycleLatitude : " + result.getLoc().getCoordinates().get(1)
                                            + ", BicycleLongitude : " + result.getLoc().getCoordinates().get(0)
                                            + ", BicyclePrice : " + result.getPrice().getMonth()
                            );
                        bicycleImageURL = RefinementUtil.getBicycleImageURLStringFromResult(result);
                        ImageUtil.setRoundRectangleImageFromURL(
                                MyApplication.getmContext(),
                                RefinementUtil.getBicycleImageURLStringFromResult(result),
                                R.drawable.detailpage_bike_image_noneimage,
                                bicyclePicture,
                                6
                        );
                        ImageUtil.setCircleImageFromURL(
                                MyApplication.getmContext(),
                                RefinementUtil.getUserImageURLStringFromResult(result),
                                R.drawable.noneimage,
                                0,
                                listerPicture
                        );
                        listerEmail = result.getUser().getEmail();
                        listerName.setText(result.getUser().getName());
                        listerPhone = result.getUser().getPhone();
                        bicycleName.setText(result.getTitle());
                        bicycleIntro.setText(result.getIntro());
                        height = RefinementUtil.getBicycleHeightStringFromBicycleHeight(result.getHeight());
                        bicycleType.setText(
                                RefinementUtil.getBicycleHeightStringFromBicycleHeight(
                                        result.getHeight()
                                )
                        );
                        height = RefinementUtil.getBicycleTypeStringFromBicycleType(result.getType());
                        bicycleHeight.setText(
                                RefinementUtil.getBicycleTypeStringFromBicycleType(
                                        result.getType()
                                )
                        );
                        components = RefinementUtil.getComponentListFromResult(result);
                        latitude = result.getLoc().getCoordinates().get(1);
                        longitude = result.getLoc().getCoordinates().get(0);
                        rentalPlaceText.setText(
                                RefinementUtil.findAddress(
                                        MyApplication.getmContext(),
                                        result.getLoc().getCoordinates().get(1),
                                        result.getLoc().getCoordinates().get(0)
                                )
                        );
                        price = result.getPrice().getMonth();

                        NetworkManager.getInstance().selectBicycleComment(
                                bicycleId,
                                null,
                                new Callback<ReceiveObject>() {
                                    @Override
                                    public void onResponse(Call<ReceiveObject> call, Response<ReceiveObject> response) {
                                        ReceiveObject receiveObject = response.body();
                                        if (BuildConfig.DEBUG)
                                            Log.d("result", "onResponse Success");
                                        Result result = receiveObject.getResult().get(0);
                                        if (null != result) {
                                            postScriptView.setVisibility(View.VISIBLE);
                                            Comment comment = result.getComments().get(result.getComments().size() - 1);
                                            String imageURL = RefinementUtil.getUserImageURLStringFromComment(comment);
                                            ImageUtil.setCircleImageFromURL(
                                                    MyApplication.getmContext(),
                                                    imageURL,
                                                    R.drawable.noneimage,
                                                    0,
                                                    postscriptPicture
                                            );
                                            if (BuildConfig.DEBUG)
                                                Log.d("result", "onResponse ImageURL : " + imageURL
                                                                + ", WriterName : " + comment.getWriter().getName()
                                                                + ", Point : " + comment.getPoint()
                                                                + ", PostScript : " + comment.getBody()
                                                );
                                            postscriptName.setText("" + comment.getWriter().getName());
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy.MM.dd. HH:mm");
                                            postscriptDate.setText("" + simpleDateFormat.format(comment.getCreatedAt()));
                                            postscriptComment.setText("" + comment.getBody());
                                            postscriptPoint.setRating((null != comment.getPoint()) ? comment.getPoint() : 0);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                                        if (BuildConfig.DEBUG)
                                            Log.d(TAG, "onFailure Error : " + t.toString());
                                    }
                                });
                    }

                    @Override
                    public void onFailure(Call<ReceiveObject> call, Throwable t) {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "onFailure Error : " + t.toString());
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
