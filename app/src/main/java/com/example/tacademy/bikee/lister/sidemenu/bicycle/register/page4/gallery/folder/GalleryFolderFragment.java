package com.example.tacademy.bikee.lister.sidemenu.bicycle.register.page4.gallery.folder;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.bikee.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016-02-23.
 */
public class GalleryFolderFragment extends Fragment {
    private List<GalleryFolderItem> list;
    private GalleryFolderAdapter galleryFolderAdapter;

    public static GalleryFolderFragment newInstance() {
        return new GalleryFolderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        String orderBy = MediaStore.Images.Media._ID;
        Cursor cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
        int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        int count = cursor.getCount();

        list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            cursor.moveToPosition(i);
            String filePath = cursor.getString(dataColumnIndex);
            boolean exist = false;

            for (GalleryFolderItem item : list) {
                if (item.getFolderName().equals(filePath.split("/")[filePath.split("/").length - 2])) {
                    item.addNumber();
                    exist = true;
                    break;
                }
            }

            if (!exist) {
                list.add(new GalleryFolderItem(filePath));
            }
        }
        cursor.close();

        galleryFolderAdapter = new GalleryFolderAdapter(list);
        galleryFolderAdapter.setOnGalleryFolderAdapterClickListener(new OnGalleryFolderAdapterClickListener() {
            @Override
            public void onGalleryFolderAdapterClick(String folderName) {
                getGalleryFolderNameListener.getGalleryFolderName(folderName);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_folder, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_gallery_folder_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(galleryFolderAdapter);
        int space = getResources().getDimensionPixelSize(R.dimen.view_holder_gallery_folder_item_space);
        recyclerView.addItemDecoration(new GalleryFolderDecoration(list.size(), space));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private GetGalleryFolderNameListener getGalleryFolderNameListener;

    public void setGetGalleryFolderNameListener(GetGalleryFolderNameListener getGalleryFolderNameListener) {
        this.getGalleryFolderNameListener = getGalleryFolderNameListener;
    }
}
