package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.Toast;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.Util;
import com.example.tacademy.bikee.etc.dao.Comment;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FilteredBicycleDetailInformationActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Intent intent;
    private GoogleMap googleMap;
    private String bicycleId;
    private String bicycleImageURL;
    private String listerPhone;
    private String type;
    private String height;
    private double latitude;
    private double longitude;
    private int price;
    @Bind(R.id.bicycle_picture_lister_information_bicycle_picture_image_view) ImageView bicyclePicture;
    @Bind(R.id.lister_information_lister_picture_image_view) ImageView listerPicture;
    @Bind(R.id.lister_information_lister_name_text_view) TextView listerName;
    @OnClick(R.id.lister_information_call_with_lister_button) void call() {
        intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + listerPhone));
        startActivity(intent);
    }
    @Bind(R.id.bicycle_description_bicycle_name_text_view) TextView bicycleName;
    @Bind(R.id.bicycle_description_bicycle_introduction_text_view) TextView bicycleIntro;
    @Bind(R.id.bicycle_detail_information_bicycle_type_text_view) TextView bicycleType;
    @Bind(R.id.bicycle_detail_information_bicycle_height_text_view) TextView bicycleHeight;
    @Bind(R.id.bicycle_detail_information_bicycle_component_text_view) TextView bicycleComponent;
    @Bind(R.id.bicycle_detail_information_bicycle_location_text_view) TextView rentalPlaceText;
    @Bind(R.id.bicycle_detail_information_reservation_period_start_date_text_view) TextView startDate;
    @Bind(R.id.bicycle_detail_information_reservation_period_end_date_text_view) TextView endDate;
    @Bind(R.id.bicycle_detail_information_bicycle_postscript_renter_picture_image_view) ImageView postscriptPicture;
    @Bind(R.id.bicycle_detail_information_bicycle_postscript_renter_name_text_view) TextView postscriptName;
    @Bind(R.id.bicycle_detail_information_bicycle_postscript_create_date_text_view) TextView postscriptDate;
    @Bind(R.id.bicycle_detail_information_bicycle_postscript_renter_comment_text_view) TextView postscriptComment;
    @Bind(R.id.bicycle_detail_information_bicycle_postscript_rating_bar) RatingBar postscriptPoint;
    @OnClick(R.id.bicycle_detail_information_bicycle_postscript_button) void morePostScript() {
        intent = new Intent(FilteredBicycleDetailInformationActivity.this, BicyclePostScriptListActivity.class);
        intent.putExtra("ID", bicycleId);
        startActivity(intent);
    }
    @OnClick(R.id.activity_filtered_bicycle_detail_information_button) void detail() {
        intent = new Intent(FilteredBicycleDetailInformationActivity.this, FinallyRequestReservationActivity.class);
        intent.putExtra("ID", bicycleId);
        intent.putExtra("IMAGEURL", bicycleImageURL);
        intent.putExtra("BICYCLETYPE", type);
        intent.putExtra("BICYCLEHEIGHT", height);
        intent.putExtra("BICYCLELATITUDE", latitude);
        intent.putExtra("BICYCLELONGITUDE", longitude);
        intent.putExtra("BICYCLEPRICE", price);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_bicycle_detail_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_filtered_bicycle_detail_information_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.renter_main_tool_bar);
        ButterKnife.bind(this);

        intent = getIntent();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_filtered_bicycle_detail_information_small_map);
        mapFragment.getMapAsync(this);
        init();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setScrollGesturesEnabled(false);

        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.target(new LatLng(intent.getDoubleExtra("LATITUDE", 1.0), intent.getDoubleExtra("LONGITUDE", 1.0)));
        builder.zoom(15);
        CameraPosition position = builder.build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
        this.googleMap.moveCamera(update);

        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(intent.getDoubleExtra("LATITUDE", 1.0), intent.getDoubleExtra("LONGITUDE", 1.0)));
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.rider_main_bike_b_icon));
        options.anchor(0.5f, 0.5f);
        this.googleMap.addMarker(options);
    }

    private void init() {
        bicycleId = intent.getStringExtra("ID");
        NetworkManager.getInstance().selectBicycleDetail(bicycleId, new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                Log.i("result", "onResponse Success");
                Result result = receiveObject.getResult().get(0);
                // TODO image, type, height, latitude, longitude, price, renterName, postScript
                String bicycleImageURL;
                if ((null == result.getImage())
                        || (null == result.getImage().getCdnUri())
                        || (null == result.getImage().getFiles())
                        || (null == result.getImage().getFiles().get(0))) {
                    bicycleImageURL = "";
                } else {
                    bicycleImageURL = result.getImage().getCdnUri() + "/detail_" + result.getImage().getFiles().get(0);
                }
                String listerImageURL;
                if ((null == result.getUser().getImage())
                        || (null == result.getUser().getImage().getCdnUri())
                        || (null == result.getUser().getImage().getFiles())
                        || (null == result.getUser().getImage().getFiles().get(0))) {
                    listerImageURL = "";
                } else {
                    listerImageURL = result.getUser().getImage().getCdnUri() + "/detail_" + result.getUser().getImage().getFiles().get(0);
                }
                Log.i("result", "onResponse imageURL : " + bicycleImageURL
                                + ", BicycleType : " + result.getType()
                                + ", BicycleHeight : " + result.getHeight()
                                + ", BicycleLatitude : " + result.getLoc().getCoordinates().get(1)
                                + ", BicycleLongitude : " + result.getLoc().getCoordinates().get(0)
                                + ", BicyclePrice : " + result.getPrice().getMonth()
                );
                FilteredBicycleDetailInformationActivity.this.bicycleImageURL = bicycleImageURL;
                listerPhone = result.getUser().getPhone();
                type = result.getType();
                height = result.getHeight();
                latitude = result.getLoc().getCoordinates().get(1);
                longitude = result.getLoc().getCoordinates().get(0);
                price = result.getPrice().getMonth();
                Util.setRoundRectangleImageFromURL(MyApplication.getmContext(), bicycleImageURL, 6, bicyclePicture);
                Util.setCircleImageFromURL(MyApplication.getmContext(), listerImageURL, 0, listerPicture);
                listerName.setText(result.getUser().getName());
                bicycleName.setText(result.getTitle());
                bicycleIntro.setText(result.getIntro());
                switch (type) {
                    case "A": bicycleType.setText("보급형"); break;
                    case "B": bicycleType.setText("산악용"); break;
                    case "C": bicycleType.setText("하이브리드"); break;
                    case "D": bicycleType.setText("픽시"); break;
                    case "E": bicycleType.setText("폴딩"); break;
                    case "F": bicycleType.setText("미니벨로"); break;
                    case "G": bicycleType.setText("전기자전거"); break;
                    default: bicycleType.setText(""); break;
                }
                switch (height) {
                    case "01": bicycleHeight.setText("~ 145cm"); break;
                    case "02": bicycleHeight.setText("145cm ~ 155cm"); break;
                    case "03": bicycleHeight.setText("155cm ~ 165cm"); break;
                    case "04": bicycleHeight.setText("165cm ~ 175cm"); break;
                    case "05": bicycleHeight.setText("175cm ~ 185cm"); break;
                    case "06": bicycleHeight.setText("185cm ~"); break;
                    default: bicycleHeight.setText(""); break;
                }
                rentalPlaceText.setText(findAddress(latitude, longitude));

                NetworkManager.getInstance().selectBicycleComment(bicycleId, new Callback<ReceiveObject>() {
                    @Override
                    public void success(ReceiveObject receiveObject, Response response) {
                        Log.i("result", "onResponse Success");
                        Result result = receiveObject.getResult().get(0);
                        if (null != result) {
                            View view = findViewById(R.id.activity_filtered_bicycle_detail_information_bicycle_post_script_layout);
                            view.setVisibility(View.VISIBLE);
                            Comment comment = result.getComments().get(result.getComments().size() - 1);
                            String imageURL;
                            if ((null == comment.getWriter().getImage())
                                    || (null == comment.getWriter().getImage().getCdnUri())
                                    || (null == comment.getWriter().getImage().getFiles())
                                    || (null == comment.getWriter().getImage().getFiles().get(0))) {
                                imageURL = "";
                            } else {
                                imageURL = comment.getWriter().getImage().getCdnUri() + "/detail_" + comment.getWriter().getImage().getFiles().get(0);
                            }
                            Log.i("result", "onResponse ImageURL : " + imageURL
                                            + ", WriterName : " + comment.getWriter().getName()
                                            + ", Point : " + comment.getPoint()
                                            + ", PostScript : " + comment.getBody()
                            );
                            Util.setCircleImageFromURL(MyApplication.getmContext(), imageURL, 0, postscriptPicture);
                            postscriptName.setText("" + comment.getWriter().getName());
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy.MM.dd. HH:mm");
                            postscriptDate.setText("" + simpleDateFormat.format(comment.getCreatedAt()));
                            postscriptComment.setText("" + comment.getBody());
                            postscriptPoint.setRating((null != comment.getPoint()) ? comment.getPoint() : 0);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("error", "onFailure Error : " + error.toString());
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", "onFailure Error : " + error.toString());
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

    private String findAddress(double lat, double lng) {
        StringBuffer bf = new StringBuffer();
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                // 세번째 인수는 최대결과값인데 하나만 리턴받도록 설정했다
                address = geocoder.getFromLocation(lat, lng, 1);
                // 설정한 데이터로 주소가 리턴된 데이터가 있으면
                if (address != null && address.size() > 0) {
                    // address.get(0).getAddressLine(0);

                    bf.append(address.get(0).getAdminArea())
                            .append(" " + address.get(0).getLocality())
                            .append(" " + address.get(0).getLocality())
                            .append(" " + address.get(0).getThoroughfare());
                }
            }
        } catch (IOException e) {
            Toast.makeText(this, "주소취득 실패", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return bf.toString();
    }
}
