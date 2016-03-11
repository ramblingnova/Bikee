package com.example.tacademy.bikee.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Data {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Boolean is_silhouette = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String url = null;
}
