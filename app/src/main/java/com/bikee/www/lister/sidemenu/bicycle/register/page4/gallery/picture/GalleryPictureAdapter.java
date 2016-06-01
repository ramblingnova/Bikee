package com.bikee.www.lister.sidemenu.bicycle.register.page4.gallery.picture;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bikee.www.R;

import java.util.List;

/**
 * Created by User on 2016-02-24.
 */
public class GalleryPictureAdapter extends RecyclerView.Adapter<GalleryPictureViewHolder> {
    private OnGalleryPictureAdapterClickListener onGalleryPictureAdapterClickListener;
    private List<GalleryPictureItem> list;
    private boolean full;

    public GalleryPictureAdapter(List<GalleryPictureItem> list) {
        this.list = list;
        full = false;
    }

    @Override
    public GalleryPictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_gallery_picture, parent, false);

        GalleryPictureViewHolder holder = new GalleryPictureViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(GalleryPictureViewHolder holder, final int position) {
        holder.setView(list.get(position));
        holder.setOnGalleryPictureClickListener(new OnGalleryPictureClickListener() {
            @Override
            public void onGalleryPictureClick(View view) {
                if ((onGalleryPictureAdapterClickListener != null) && !full && !list.get(position).isSelected()) {
                    onGalleryPictureAdapterClickListener.onGalleryPictureAdapterClick(view, position);
                    list.get(position).setSelected(true);
                    notifyDataSetChanged();

                    ImageView checkImageView = (ImageView) view.findViewById(R.id.view_holder_gallery_picture_check_image_view);
                    checkImageView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnGalleryPictureAdapterClickListener(OnGalleryPictureAdapterClickListener onGalleryPictureAdapterClickListener) {
        this.onGalleryPictureAdapterClickListener = onGalleryPictureAdapterClickListener;
    }

    public void notifyListIsFull() {
        full = true;
    }

    public void notifyListIsNotFull() {
        full = false;
    }

    public void unCheck(String path) {
        for (GalleryPictureItem item : list)
            if (path.equals(item.getPicturePath())) {
                item.setSelected(false);
                notifyDataSetChanged();
                break;
            }
    }

    public void enterFolder(String folderName) {
        final int size = list.size();
        int batchCount = 0;

        for (int i = size - 1; i >= 0; i--) {
            if (list.get(i).getFolderName().equals(folderName) == false) {
                list.remove(i);
                batchCount++;
            } else if (batchCount != 0) {
                notifyItemRangeRemoved(i + 1, batchCount);
                batchCount = 0;
            }
        }

        if (batchCount != 0) {
            notifyItemRangeRemoved(0, batchCount);
        }
    }
}
