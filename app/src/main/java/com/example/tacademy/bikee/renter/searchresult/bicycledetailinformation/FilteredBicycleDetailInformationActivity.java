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

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FilteredBicycleDetailInformationActivity extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;
    private String bicycleId;
    private String bicycleImageURL;
    private String type;
    private String height;
    private double latitude;
    private double longitude;
    private int price;
    private ImageView bicycleImage;
    private CheckBox typeCheck1;
    private CheckBox typeCheck2;
    private CheckBox typeCheck3;
    private CheckBox typeCheck4;
    private CheckBox typeCheck5;
    private CheckBox typeCheck6;
    private CheckBox typeCheck7;
    private CheckBox heightCheck1;
    private CheckBox heightCheck2;
    private CheckBox heightCheck3;
    private CheckBox heightCheck4;
    private CheckBox heightCheck5;
    private CheckBox heightCheck6;
    private TextView rentalPlaceText;

    private ImageView postsciptImage;
    private RatingBar postsciptPoint;
    private TextView postsciptName;
    private TextView postsciptBody;

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

        Button btn = (Button) findViewById(R.id.bicycle_post_script_see_more_post_script);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.activity_filtered_bicycle_detail_information_button);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.activity_filtered_bicycle_detail_information_small_map_button);
        btn.setOnClickListener(this);
        bicycleImage = (ImageView) findViewById(R.id.activity_filtered_bicycle_detail_information_bicycle_picture);
        typeCheck1 = (CheckBox) findViewById(R.id.bicycle_type_check_box1);
        typeCheck1.setClickable(false);
        typeCheck2 = (CheckBox) findViewById(R.id.bicycle_type_check_box2);
        typeCheck2.setClickable(false);
        typeCheck3 = (CheckBox) findViewById(R.id.bicycle_type_check_box3);
        typeCheck3.setClickable(false);
        typeCheck4 = (CheckBox) findViewById(R.id.bicycle_type_check_box4);
        typeCheck4.setClickable(false);
        typeCheck5 = (CheckBox) findViewById(R.id.bicycle_type_check_box5);
        typeCheck5.setClickable(false);
        typeCheck6 = (CheckBox) findViewById(R.id.bicycle_type_check_box6);
        typeCheck6.setClickable(false);
        typeCheck7 = (CheckBox) findViewById(R.id.bicycle_type_check_box7);
        typeCheck7.setClickable(false);
        heightCheck1 = (CheckBox) findViewById(R.id.bicycle_recommendation_height_check_box1);
        heightCheck1.setClickable(false);
        heightCheck2 = (CheckBox) findViewById(R.id.bicycle_recommendation_height_check_box2);
        heightCheck2.setClickable(false);
        heightCheck3 = (CheckBox) findViewById(R.id.bicycle_recommendation_height_check_box3);
        heightCheck3.setClickable(false);
        heightCheck4 = (CheckBox) findViewById(R.id.bicycle_recommendation_height_check_box4);
        heightCheck4.setClickable(false);
        heightCheck5 = (CheckBox) findViewById(R.id.bicycle_recommendation_height_check_box5);
        heightCheck5.setClickable(false);
        heightCheck6 = (CheckBox) findViewById(R.id.bicycle_recommendation_height_check_box6);
        heightCheck6.setClickable(false);
        rentalPlaceText = (TextView) findViewById(R.id.bicycle_rental_place_real_position_text_view);

        postsciptImage = (ImageView) findViewById(R.id.bicycle_post_script_user_image_view);
        postsciptPoint = (RatingBar) findViewById(R.id.bicycle_post_script_rating_bar);
        postsciptName = (TextView) findViewById(R.id.bicycle_post_script_user_name);
        postsciptBody = (TextView) findViewById(R.id.bicycle_post_script_user_body);

        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bicycle_post_script_see_more_post_script:
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
            case R.id.activity_filtered_bicycle_detail_information_small_map_button:
                intent = new Intent(FilteredBicycleDetailInformationActivity.this, SmallMapActivity.class);
                startActivity(intent);
                break;
        }
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
