package com.bigtion.bikee.common.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigtion.bikee.etc.MyApplication;
import com.bigtion.bikee.R;
import com.bigtion.bikee.etc.utils.ImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016-03-09.
 */
public class BicycleImageViewPagerAdapter extends PagerAdapter {
    private List<String> URLList;
    private List<File> fileList;

    public BicycleImageViewPagerAdapter() {
        this.URLList = new ArrayList<>();
        this.fileList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return (URLList.size() > 0) ? URLList.size() : fileList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_bicycle_image_view_pager, null);

        ImageView imageVIew = (ImageView) view.findViewById(R.id.view_bicycle_image_view_pager);

        if (URLList.size() > 0)
            ImageUtil.setRectangleImageFromURL(
                    MyApplication.getmContext(),
                    URLList.get(position),
                    R.drawable.detailpage_bike_image_noneimage,
                    imageVIew
            );
        else if (fileList.size() > 0)
            ImageUtil.setRectangleImageFromURL(
                    MyApplication.getmContext(),
                    fileList.get(position).getPath(),
                    R.drawable.detailpage_bike_image_noneimage,
                    imageVIew
            );

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void addAllURLs(List<String> list) {
        this.URLList.addAll(list);
        notifyDataSetChanged();
    }

    public void addAllFiles(List<File> list) {
        this.fileList.addAll(list);
        notifyDataSetChanged();
    }
}
