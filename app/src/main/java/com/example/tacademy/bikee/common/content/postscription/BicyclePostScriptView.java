package com.example.tacademy.bikee.common.content.postscription;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.utils.ImageUtil;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2015-11-02.
 */
public class BicyclePostScriptView extends RecyclerView.ViewHolder{
    @Bind(R.id.view_bicycle_post_script_item_user_image1)
    ImageView renterImage;
    @Bind(R.id.view_bicycle_post_script_item_user_name1)
    TextView renterName;
    @Bind(R.id.view_bicycle_post_script_item_rating_bar)
    RatingBar point;
    @Bind(R.id.view_bicycle_post_script_item_user_post_script1)
    TextView postscript;
    @Bind(R.id.view_bicycle_post_script_item_date1)
    TextView createAt;

    public BicyclePostScriptView(View view) {
        super(view);
        ButterKnife.bind(this, view);
        point.setClickable(false);
    }

    public void setView(BicyclePostScriptItem item) {
        ImageUtil.setCircleImageFromURL(
                MyApplication.getmContext(),
                item.getImageURL(),
                R.drawable.noneimage,
                0,
                renterImage
        );
        renterName.setText("" + item.getRenterName());
        point.setRating(item.getPoint());
        postscript.setText("" + item.getPostScript());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm");
        createAt.setText(simpleDateFormat.format(item.getCreateAt()));
    }
}
