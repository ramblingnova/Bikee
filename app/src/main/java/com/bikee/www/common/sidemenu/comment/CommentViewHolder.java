package com.bikee.www.common.sidemenu.comment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bikee.www.R;
import com.bikee.www.etc.MyApplication;
import com.bikee.www.etc.utils.ImageUtil;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.view_holder_comment_picture_image_view)
    ImageView pictureImageView;
    @Bind(R.id.view_holder_comment_user_name_text_view)
    TextView userNameTextView;
    @Bind(R.id.view_holder_comment_bicycle_title_text_view)
    TextView bicycleTitleTextView;
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
        switch (item.getType()) {
            case CommentsActivity.RENTER_COMMENT:
                pictureImageView.getLayoutParams().width
                        = MyApplication.getmContext()
                        .getResources()
                        .getDimensionPixelSize(
                                R.dimen.view_holder_comment_picture_image_view_width
                        );
                pictureImageView.getLayoutParams().height
                        = MyApplication.getmContext()
                        .getResources()
                        .getDimensionPixelSize(
                                R.dimen.view_holder_comment_picture_image_view_height
                        );
                ImageUtil.setRoundRectangleImageFromURL(
                        MyApplication.getmContext(),
                        item.getImageURL(),
                        R.drawable.detailpage_bike_image_noneimage,
                        pictureImageView,
                        MyApplication.getmContext()
                                .getResources()
                                .getDimension(
                                        R.dimen.view_holder_comment_picture_image_view_round_radius
                                )
                );
                userNameTextView.setVisibility(View.GONE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bicycleTitleTextView.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_TOP, R.id.view_holder_comment_picture_image_view);
                params.setMargins(
                        MyApplication.getmContext()
                                .getResources()
                                .getDimensionPixelOffset(
                                        R.dimen.view_holder_comment_bicycle_title_text_view_left_margin
                                ),
                        0,
                        0,
                        0
                );
                params.addRule(RelativeLayout.END_OF, R.id.view_holder_comment_picture_image_view);
                params.addRule(RelativeLayout.RIGHT_OF, R.id.view_holder_comment_picture_image_view);
                bicycleTitleTextView.setText(item.getTitle1());
                break;
            case CommentsActivity.BICYCLE_COMMENT:
                ImageUtil.setCircleImageFromURL(
                        MyApplication.getmContext(),
                        item.getImageURL(),
                        R.drawable.noneimage,
                        pictureImageView
                );
                userNameTextView.setText(item.getTitle1());
                bicycleTitleTextView.setVisibility(View.GONE);
                break;
            case CommentsActivity.LISTER_COMMENT:
                ImageUtil.setCircleImageFromURL(
                        MyApplication.getmContext(),
                        item.getImageURL(),
                        R.drawable.noneimage,
                        pictureImageView
                );
                userNameTextView.setText(item.getTitle1());
                bicycleTitleTextView.setText(item.getTitle2());
                break;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd hh:mm", java.util.Locale.getDefault());
        dateTextView.setText(simpleDateFormat.format(item.getDate()));
        commentTextView.setText(item.getComment());
        pointRatingBar.setRating(item.getPoint());
        pointRatingBar.setClickable(false);
    }
}
