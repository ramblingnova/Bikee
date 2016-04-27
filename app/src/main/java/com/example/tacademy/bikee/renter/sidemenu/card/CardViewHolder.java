package com.example.tacademy.bikee.renter.sidemenu.card;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.bikee.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 2016-04-26.
 */
public class CardViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.view_holder_card_card_image_view)
    ImageView cardImageView;
    @Bind(R.id.view_holder_card_nickname_text_view)
    TextView nicknameTextView;

    private OnCardViewClickListener onCardViewClickListener;

    public CardViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }

    public void setView(CardItem item) {
        cardImageView.setImageResource(item.getImage());
        nicknameTextView.setText(item.getCardName() + "/" + item.getNickname());
    }

    @OnClick(R.id.view_holder_card_card_image_view)
    void onImageClick(View view) {
        onCardViewClickListener.onCardImageClick(view);
    }

    @OnClick(R.id.view_holder_card_delete_card_image_view)
    void onDeleteClick(View view) {
        onCardViewClickListener.onDeleteCardClick(view);
    }

    public void setOnCardViewClickListener(OnCardViewClickListener onCardViewClickListener) {
        this.onCardViewClickListener = onCardViewClickListener;
    }
}
