package com.example.tacademy.bikee.renter.sidemenu.cardmanagement;

import java.io.Serializable;

/**
 * Created by Tacademy on 2015-11-03.
 */
public class CardItem implements Serializable {

    public CardItem(String card_type, String card_number, String expiration_date, String birth_date, String password) {
        this.card_type = card_type;
        this.card_number = card_number;
        this.expiration_date = expiration_date;
        this.birth_date = birth_date;
        this.password = password;
    }

    String img_url;
    String card_type;
    String card_number;
    String expiration_date;
    String birth_date;
    String password;
}
