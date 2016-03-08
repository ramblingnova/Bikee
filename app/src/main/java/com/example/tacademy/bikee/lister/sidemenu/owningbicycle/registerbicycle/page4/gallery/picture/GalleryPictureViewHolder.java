package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page4.gallery.picture;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.utils.ImageUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 2016-02-24.
 */
public class GalleryPictureViewHolder extends RecyclerView.ViewHolder {
    private OnGalleryPictureClickListener onGalleryPictureClickListener;
    @Bind(R.id.view_holder_gallery_picture_item_image_view)
    ImageView itemImageVIew;
    @Bind(R.id.view_holder_gallery_picture_check_image_view)
    ImageView checkImageView;
    private View view;

    public GalleryPictureViewHolder(View view) {
        super(view);

        this.view = view;

        ButterKnife.bind(this, view);
    }

    public void setView(GalleryPictureItem item) {
        ImageUtil.setRectangleImageFromURL(view.getContext(), item.getPicturePath(), itemImageVIew);
        if (item.isSelected())
            checkImageView.setVisibility(View.VISIBLE);
        else
            checkImageView.setVisibility(View.GONE);
    }

    public View getView() {
        return view;
    }

    @OnClick(R.id.view_holder_gallery_picture_recycler_layout)
    void onClick(View view) {
        if (onGalleryPictureClickListener != null) {
            onGalleryPictureClickListener.onGalleryPictureClick(view);
        }
    }

    public void setOnGalleryPictureClickListener(OnGalleryPictureClickListener onGalleryPictureClickListener) {
        this.onGalleryPictureClickListener = onGalleryPictureClickListener;
    }
}
