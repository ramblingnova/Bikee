package com.bikee.www.lister.sidemenu.bicycle.register.page4.gallery.picture;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bikee.www.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016-02-23.
 */
public class GalleryPictureFragment extends Fragment implements OnGalleryPictureAdapterClickListener {
    private List<String> checkedPathList;
    private List<GalleryPictureItem> list;
    private GalleryPictureAdapter galleryPictureAdapter;
    private String folderName = null;
    private final static int SPAN_COUNT = 4;
    private int lastRow;
    private boolean full;

    public static GalleryPictureFragment newInstance(ArrayList<String> checkedPathList) {
        GalleryPictureFragment galleryPictureFragment = new GalleryPictureFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("LIST", checkedPathList);
        galleryPictureFragment.setArguments(bundle);

        return galleryPictureFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        String orderBy = MediaStore.Images.Media._ID;
        Cursor cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
        int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        int count = cursor.getCount();

        if (initialized)
            checkedPathList = (ArrayList<String>) getArguments().getSerializable("LIST");
        list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            cursor.moveToPosition(i);
            String path = cursor.getString(dataColumnIndex);
            boolean exist = false;

            for (String checkedPath : checkedPathList)
                if (checkedPath.equals(path)) {
                    list.add(new GalleryPictureItem(path, true));
                    exist = true;
                    break;
                }

            if (!exist)
                list.add(new GalleryPictureItem(path));
        }
        galleryPictureAdapter = new GalleryPictureAdapter(list);
        galleryPictureAdapter.setOnGalleryPictureAdapterClickListener(this);

        lastRow = (count / SPAN_COUNT) + ((count % SPAN_COUNT) == 0 ? 0 : 1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_picture, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_gallery_picture_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), SPAN_COUNT, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(galleryPictureAdapter);
        int space = getResources().getDimensionPixelSize(R.dimen.view_holder_gallery_picture_item_space);
        recyclerView.addItemDecoration(new GalleryPictureDecoration(lastRow, SPAN_COUNT, space));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (folderName != null) {
            galleryPictureAdapter.enterFolder(folderName);
        }
    }

    @Override
    public void onGalleryPictureAdapterClick(View view, int position) {
        if ((getGalleryPicturePathListener != null) && !full) {
            getGalleryPicturePathListener.getGalleryPicturePath(list.get(position).getPicturePath());
            checkedPathList.add(list.get(position).getPicturePath());
        }
    }

    private GetGalleryPicturePathListener getGalleryPicturePathListener;

    public void setGetGalleryPicturePathListener(GetGalleryPicturePathListener getGalleryPicturePathListener) {
        this.getGalleryPicturePathListener = getGalleryPicturePathListener;
    }

    public void notifyListIsFull() {
        full = true;
        galleryPictureAdapter.notifyListIsFull();
    }

    public void notifyListIsNotFull() {
        full = false;
        galleryPictureAdapter.notifyListIsNotFull();
    }

    private boolean initialized = true;

    public void unCheck(String path) {
        initialized = false;
        galleryPictureAdapter.unCheck(path);

        int size = checkedPathList.size();
        for (int i = 0; i < size; i++) {
            if (path.equals(checkedPathList.get(i))) {
                checkedPathList.remove(i);
                break;
            }
        }
    }

    public void enterFolder(String folderName) {
        this.folderName = folderName;
    }
}
