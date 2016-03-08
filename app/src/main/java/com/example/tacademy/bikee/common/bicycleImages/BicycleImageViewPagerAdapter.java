package com.example.tacademy.bikee.common.bicycleImages;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tacademy.bikee.R;
import com.example.tacademy.bikee.etc.MyApplication;
import com.example.tacademy.bikee.etc.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016-03-09.
 */
public class BicycleImageViewPagerAdapter extends PagerAdapter {
    private List<String> list;

    public BicycleImageViewPagerAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_bicycle_image_view_pager, null);

        ImageView imageVIew = (ImageView) view.findViewById(R.id.view_bicycle_image_view_pager);

        ImageUtil.setRectangleImageFromURL(
                MyApplication.getmContext(),
                list.get(position),
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
}
