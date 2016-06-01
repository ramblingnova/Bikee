package com.bikee.www.renter.searchresult;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bikee.www.etc.MyApplication;
import com.bikee.www.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2015-11-14.
 */
public class SearchSwitchView extends FrameLayout {
    @Bind(R.id.view_search_switch_map_image_view) ImageView iv1;
    @Bind(R.id.view_search_switch_list_image_view) ImageView iv2;
    private boolean checked;
    private OnCheckedListener onCheckedListener;

    public SearchSwitchView(Context context) {
        super(context);
        init();
    }

    public SearchSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_search_switch, this);
        ButterKnife.bind(this);

        checked = false;
        changeImage();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = !checked;
                changeImage();
                if (onCheckedListener != null)
                    onCheckedListener.onChecked(checked);
            }
        });
    }

    public boolean isChecked() {
        return checked;
    }

    private void changeImage() {
        if (checked) {
            iv1.setImageDrawable(MyApplication.getmContext().getResources().getDrawable(R.drawable.rider_main_mode_m_in));
            iv2.setImageDrawable(MyApplication.getmContext().getResources().getDrawable(R.drawable.rider_main_mode_l_in));
        } else {
            iv1.setImageDrawable(MyApplication.getmContext().getResources().getDrawable(R.drawable.rider_main_mode_m));
            iv2.setImageDrawable(MyApplication.getmContext().getResources().getDrawable(R.drawable.rider_main_mode_l));
        }
    }

    public interface OnCheckedListener {
        void onChecked(boolean isChecked);
    }

    public void setOnCheckedListener(OnCheckedListener onCheckedListener) {
        this.onCheckedListener = onCheckedListener;
    }
}
