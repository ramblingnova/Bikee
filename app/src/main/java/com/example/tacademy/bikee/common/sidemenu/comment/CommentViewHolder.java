package com.example.tacademy.bikee.common.sidemenu.comment;

import android.os.Build;
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
 * Created by Tacademy on 2015-11-03.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.view_holder_comment_user_picture_image_view)
    ImageView userPictureImageView;
    @Bind(R.id.view_holder_comment_user_name_text_view)
    TextView userNameTextView;
    @Bind(R.id.view_holder_comment_bicycle_name_text_view)
    TextView bicycleNameTextView;
    @Bind(R.id.view_holder_comment_date_text_view)
    TextView dateTextView;
    @Bind(R.id.view_holder_comment_comment_text_view)
    TextView commentTextView;
    @Bind(R.id.view_holder_comment_point_rating_bar)
    RatingBar pointRatingBar;

    public CommentViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setView(CommentItem item) {
        ImageUtil.setCircleImageFromURL(
                MyApplication.getmContext(),
                item.getUserImageURL(),
                R.drawable.noneimage,
                7,
                userPictureImageView
        );
        userNameTextView.setText(item.getUserName());
        if (item.getBicycleName() == null) {
            if (Build.VERSION.SDK_INT < 23) {
                userNameTextView.setTextColor(
                        MyApplication.getmContext()
                                .getResources()
                                .getColor(R.color.bikeeBlack)
                );
            } else {
                userNameTextView.setTextColor(
                        MyApplication.getmContext()
                                .getResources()
                                .getColor(
                                        R.color.bikeeBlack,
                                        MyApplication.getmContext().getTheme()
                                )
                );
            }
            bicycleNameTextView.setVisibility(View.GONE);
        } else {
            if (Build.VERSION.SDK_INT < 23) {
                userNameTextView.setTextColor(
                        MyApplication.getmContext()
                                .getResources()
                                .getColor(R.color.bikeeBlue)
                );
            } else {
                userNameTextView.setTextColor(
                        MyApplication.getmContext()
                                .getResources()
                                .getColor(
                                        R.color.bikeeBlue,
                                        MyApplication.getmContext().getTheme()
                                )
                );
            }
            bicycleNameTextView.setVisibility(View.VISIBLE);
            bicycleNameTextView.setText(item.getBicycleName());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd HH:mm", java.util.Locale.getDefault());
        dateTextView.setText(simpleDateFormat.format(item.getDate()));
        commentTextView.setText(item.getComment());
        pointRatingBar.setRating(item.getPoint());
        pointRatingBar.setClickable(false);
    }
}
