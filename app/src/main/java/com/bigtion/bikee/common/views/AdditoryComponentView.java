package com.bigtion.bikee.common.views;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigtion.bikee.R;
import com.bigtion.bikee.etc.utils.RefinementUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2016-04-29.
 */
public class AdditoryComponentView extends FrameLayout {
    @Bind(R.id.view_additory_component_image_view)
    ImageView componentImageView;
    @Bind(R.id.view_additory_component_text_view)
    TextView componentTextView;

    public AdditoryComponentView(Context context) {
        super(context);
        inflate(context, R.layout.view_additory_component, this);
        ButterKnife.bind(this);
    }

    public void setView(String component) {
        switch (component) {
            case "A":
                componentImageView.setImageResource(R.drawable.detailpage_icon_addition1);
                break;
            case "B":
                componentImageView.setImageResource(R.drawable.detailpage_icon_addition1);
                break;
            case "C":
                componentImageView.setImageResource(R.drawable.detailpage_icon_addition1);
                break;
            case "D":
                componentImageView.setImageResource(R.drawable.detailpage_icon_addition1);
                break;
            case "E":
                componentImageView.setImageResource(R.drawable.detailpage_icon_addition1);
                break;
            case "F":
                componentImageView.setImageResource(R.drawable.detailpage_icon_addition1);
                break;
            case "G":
                componentImageView.setImageResource(R.drawable.detailpage_icon_addition1);
                break;
            default:
                componentImageView.setImageResource(R.drawable.detailpage_icon_addition1);
                break;
        }
        componentTextView.setText(RefinementUtil.getBicycleComponentStringFromBicycleComponent(component));
    }
}
