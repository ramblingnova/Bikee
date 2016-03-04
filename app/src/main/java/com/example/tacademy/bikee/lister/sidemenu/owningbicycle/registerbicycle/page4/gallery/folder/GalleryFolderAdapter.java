package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle.page4.gallery.folder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.bikee.R;

import java.util.List;

/**
 * Created by User on 2016-02-26.
 */
public class GalleryFolderAdapter extends RecyclerView.Adapter<GalleryFolderViewHolder> {
    private OnGalleryFolderAdapterClickListener onGalleryFolderAdapterClickListener;
    private List<GalleryFolderItem> list;

    public GalleryFolderAdapter(List<GalleryFolderItem> list) {
        this.list = list;
    }

    @Override
    public GalleryFolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_gallery_folder, parent, false);

        GalleryFolderViewHolder holder = new GalleryFolderViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(GalleryFolderViewHolder holder, final int position) {
        holder.setView(list.get(position));
        holder.setOnGalleryFolderClickListener(new OnGalleryFolderClickListener() {
            @Override
            public void onGalleryFolderClick(View view) {
                onGalleryFolderAdapterClickListener.onGalleryFolderAdapterClick(list.get(position).getFolderName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnGalleryFolderAdapterClickListener(OnGalleryFolderAdapterClickListener onGalleryFolderAdapterClickListener) {
        this.onGalleryFolderAdapterClickListener = onGalleryFolderAdapterClickListener;
    }
}
