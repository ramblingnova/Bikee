package com.bikee.www.renter.sidemenu.creditcard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bikee.www.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2016-05-19.
 */
public class CreditCardView extends FrameLayout {
    @Bind(R.id.view_credit_card_nickname_text_view)
    TextView nicknameTextView;
    @Bind(R.id.view_credit_card_representation_card_text_view)
    TextView representationCardTextView;
    @Bind(R.id.view_credit_card_setting_image_view)
    ImageView settingImageView;
    @Bind(R.id.view_credit_card_setting_layout)
    RelativeLayout settingLayout;
    @Bind(R.id.view_credit_card_set_representation_check_box)
    CheckBox representationCheckBox;
    @Bind(R.id.view_credit_card_delete_image_view)
    ImageView deleteImageView;
    @Bind(R.id.view_credit_card_setting_complete_button)
    Button completeButton;

    private OnCreditCardViewClickListener onCreditCardViewClickListener;

    public CreditCardView(Context context) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.view_credit_card, null);

        ButterKnife.bind(this, view);
        
        this.addView(view);
    }

    public void setView(final CreditCardItem item) {
        nicknameTextView.setText(item.getCardName() + "/" + item.getNickname());
        if (item.isRepresentation())
            representationCardTextView.setVisibility(View.VISIBLE);

        settingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingImageView.setVisibility(View.GONE);
                settingLayout.setVisibility(View.VISIBLE);
            }
        });

        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreditCardViewClickListener.onDeleteClick(v);
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreditCardViewClickListener.onCompleteClick(representationCheckBox);
                item.setRepresentation(representationCheckBox.isChecked());
                settingImageView.setVisibility(View.VISIBLE);
                settingLayout.setVisibility(View.GONE);
            }
        });
    }

    public void setOnCreditCardViewClickListener(OnCreditCardViewClickListener onCreditCardViewClickListener) {
        this.onCreditCardViewClickListener = onCreditCardViewClickListener;
    }
}
