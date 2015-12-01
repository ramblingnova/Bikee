package com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.common.SmallMapActivity;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.Util;
import com.example.tacademy.bikee.etc.dao.Comment;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.Result;
import com.example.tacademy.bikee.etc.manager.NetworkManager;
import com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.finallyrequestreservation.FinallyRequestReservationActivity;
import com.example.tacademy.bikee.renter.searchresult.bicycledetailinformation.postscription.BicyclePostScriptListActivity;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FilteredBicycleDetailInformationActivity extends AppCompatActivity {
    private Intent intent;
    private String bicycleId;
    private String bicycleImageURL;
    private String type;
    private String height;
    private double latitude;
    private double longitude;
    private int price;
    @Bind(R.id.activity_filtered_bicycle_detail_information_bicycle_picture) ImageView bicycleImage;
    @Bind(R.id.bicycle_type_check_box1) CheckBox typeCheck1;
    @Bind(R.id.bicycle_type_check_box2) CheckBox typeCheck2;
    @Bind(R.id.bicycle_type_check_box3) CheckBox typeCheck3;
    @Bind(R.id.bicycle_type_check_box4) CheckBox typeCheck4;
    @Bind(R.id.bicycle_type_check_box5) CheckBox typeCheck5;
    @Bind(R.id.bicycle_type_check_box6) CheckBox typeCheck6;
    @Bind(R.id.bicycle_type_check_box7) CheckBox typeCheck7;
    @Bind(R.id.bicycle_recommendation_height_check_box1) CheckBox heightCheck1;
    @Bind(R.id.bicycle_recommendation_height_check_box2) CheckBox heightCheck2;
    @Bind(R.id.bicycle_recommendation_height_check_box3) CheckBox heightCheck3;
    @Bind(R.id.bicycle_recommendation_height_check_box4) CheckBox heightCheck4;
    @Bind(R.id.bicycle_recommendation_height_check_box5) CheckBox heightCheck5;
    @Bind(R.id.bicycle_recommendation_height_check_box6) CheckBox heightCheck6;
    @Bind(R.id.bicycle_rental_place_real_position_text_view) TextView rentalPlaceText;
    @Bind(R.id.bicycle_post_script_user_image_view) ImageView postsciptImage;
    @Bind(R.id.bicycle_post_script_rating_bar) RatingBar postsciptPoint;
    @Bind(R.id.bicycle_post_script_user_name) TextView postsciptName;
    @Bind(R.id.bicycle_post_script_user_body) TextView postsciptBody;
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
        initData();
    }

    private void initData() {
        intent = getIntent();
        bicycleId = intent.getStringExtra("ID");
        NetworkManager.getInstance().selectBicycleDetail(bicycleId, new Callback<ReceiveObject>() {
            @Override
            public void success(ReceiveObject receiveObject, Response response) {
                Log.i("result", "onResponse Success");
                Result result = receiveObject.getResult().get(0);
                // TODO image, type, height, latitude, longitude, price, renterName, postScript
                String imageURL;
                if ((null == result.getImage())
                        || (null == result.getImage().getCdnUri())
                        || (null == result.getImage().getFiles())
                        || (null == result.getImage().getFiles().get(0))) {
                    imageURL = "";
                } else {
                    imageURL = result.getImage().getCdnUri() + "/mini_" + result.getImage().getFiles().get(0);
                }
                Log.i("result", "onResponse imageURL : " + imageURL
                                + ", BicycleType : " + result.getType()
                                + ", BicycleHeight : " + result.getHeight()
                                + ", BicycleLatitude : " + result.getLoc().getCoordinates().get(1)
                                + ", BicycleLongitude : " + result.getLoc().getCoordinates().get(0)
                                + ", BicyclePrice : " + result.getPrice().getMonth()
                );
                bicycleImageURL = imageURL;
                type = result.getType();
                height = result.getHeight();
                latitude = result.getLoc().getCoordinates().get(1);
                longitude = result.getLoc().getCoordinates().get(0);
                price = result.getPrice().getMonth();
                Util.setRoundRectangleImageFromURL(MyApplication.getmContext(), imageURL, 6, bicycleImage);
                switch (type) {
                    case "A": typeCheck1.setChecked(true); break;
                    case "B": typeCheck2.setChecked(true); break;
                    case "C": typeCheck3.setChecked(true); break;
                    case "D": typeCheck4.setChecked(true); break;
                    case "E": typeCheck5.setChecked(true); break;
                    case "F": typeCheck6.setChecked(true); break;
                    case "G": typeCheck7.setChecked(true); break;
                    default:  typeCheck1.setChecked(true); break;
                }
                switch (height) {
                    case "01": heightCheck1.setChecked(true); break;
                    case "02": heightCheck2.setChecked(true); break;
                    case "03": heightCheck3.setChecked(true); break;
                    case "04": heightCheck4.setChecked(true); break;
                    case "05": heightCheck5.setChecked(true); break;
                    case "06": heightCheck6.setChecked(true); break;
                    default:  heightCheck1.setChecked(true); break;
                }
                rentalPlaceText.setText("위도 : " + latitude + ", 경도 : " + longitude);

                NetworkManager.getInstance().selectBicycleComment(bicycleId, new Callback<ReceiveObject>() {
                    @Override
                    public void success(ReceiveObject receiveObject, Response response) {
                        Log.i("result", "onResponse Success");
                        Result result = receiveObject.getResult().get(0);
                        if (null != result) {
                            Comment comment = result.getComments().get(result.getComments().size() - 1);
                            String imageURL;
                            if ((null == comment.getWriter().getImage())
                                    || (null == comment.getWriter().getImage().getCdnUri())
                                    || (null == comment.getWriter().getImage().getFiles())
                                    || (null == comment.getWriter().getImage().getFiles().get(0))) {
                                imageURL = "";
                            } else {
                                imageURL = comment.getWriter().getImage().getCdnUri() + "/mini_" + comment.getWriter().getImage().getFiles().get(0);
                            }
                            Log.i("result", "onResponse ImageURL : " + imageURL
                                            + ", WriterName : " + comment.getWriter().getName()
                                            + ", Point : " + comment.getPoint()
                                            + ", PostScript : " + comment.getBody()
                            );
                            Util.setCircleImageFromURL(MyApplication.getmContext(), imageURL, 0, postsciptImage);
                            int point = (null != comment.getPoint()) ? comment.getPoint() : 0;
                            postsciptPoint.setRating(point);
                            postsciptName.setText("" + comment.getWriter().getName());
                            postsciptBody.setText("" + comment.getBody());
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
}
