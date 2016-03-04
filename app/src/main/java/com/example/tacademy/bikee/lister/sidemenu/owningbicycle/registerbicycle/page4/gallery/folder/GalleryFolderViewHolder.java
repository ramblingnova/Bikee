package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page4.gallery.folder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 2016-02-26.
 */
public class GalleryFolderViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.view_holder_gallery_folder_represent_item_image_view)
    ImageView imageView;
    @Bind(R.id.view_holder_gallery_folder_title_text_view)
    TextView titleTextView;
    @Bind(R.id.view_holder_gallery_folder_number_text_view)
    TextView numberTextView;
    private View view;

    public GalleryFolderViewHolder(View view) {
        super(view);

        this.view = view;

        ButterKnife.bind(this, view);
    }

    public void setView(GalleryFolderItem item) {
        Util.setRectangleImageFromURI(view.getContext(), item.getRepresentativeFilePath(), imageView);
        titleTextView.setText(item.getFolderName());
        numberTextView.setText("" + item.getNumberOfPicture());
    }

    @OnClick(R.id.view_holder_gallery_folder_recycler_layout)
    void onClick(View view) {
        onGalleryFolderClickListener.onGalleryFolderClick(view);
    }

    private OnGalleryFolderClickListener onGalleryFolderClickListener;

    public void setOnGalleryFolderClickListener(OnGalleryFolderClickListener onGalleryFolderClickListener) {
        this.onGalleryFolderClickListener = onGalleryFolderClickListener;
    }
}
