package com.example.tacademy.bikee.renter.sidemenu.card;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CardItem {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int image;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String _id;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String cardName;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String nickname;

    public CardItem(int image, String _id, String cardName, String nickname) {
        this.image = image;
        this._id = _id;
        this.cardName = cardName;
        this.nickname = nickname;
    }
}
