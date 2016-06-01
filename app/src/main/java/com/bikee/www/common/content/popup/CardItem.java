package com.bikee.www.common.content.popup;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-04-27.
 */
public class CardItem {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String id;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String name;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String nickname;

    public CardItem(String id, String name, String nickname) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
    }
}
