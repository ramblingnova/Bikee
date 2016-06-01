package com.bikee.www.renter.sidemenu.creditcard;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CreditCardItem {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String _id;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String cardName;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String nickname;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    boolean representation;

    public CreditCardItem(
            String _id,
            String cardName,
            String nickname) {
        this._id = _id;
        this.cardName = cardName;
        this.nickname = nickname;
        this.representation = false;
    }

    public CreditCardItem(
            String _id,
            String cardName,
            String nickname,
            boolean representation) {
        this._id = _id;
        this.cardName = cardName;
        this.nickname = nickname;
        this.representation = representation;
    }
}
