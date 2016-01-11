package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 2016-01-07.
 */
public class BicycleAdditoryComponentView extends FrameLayout {
    @Bind(R.id.register_bicycle_additory_component_image_view)
    ImageView componentImage;
    @Bind(R.id.register_bicycle_additory_component_text_view)
    TextView componentText;
    @Bind(R.id.register_bicycle_additory_component_check_box)
    CheckBox componentCheckBox;
    private OnItemClickListener onItemClickListener;

    public BicycleAdditoryComponentView(Context context) {
        super(context);
        inflate(getContext(), R.layout.register_bicycle_additory_component_item, this);
        ButterKnife.bind(this);
    }

    public void setView(BicycleAdditoryComponentItem item) {
        componentImage.setImageResource(item.getComponentImage());
        componentText.setText(item.getComponentText());
        componentCheckBox.setChecked(item.isComponentCheckBox());
    }

    @OnClick({R.id.register_bicycle_additory_component_item_layout,
            R.id.register_bicycle_additory_component_check_box})
    void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.register_bicycle_additory_component_item_layout:
                componentCheckBox.setChecked(!componentCheckBox.isChecked());
            case R.id.register_bicycle_additory_component_check_box:
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick();
                break;
        }
    }

    public interface OnItemClickListener {
        void onItemClick();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
