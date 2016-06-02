package com.bigtion.bikee.etc.dao;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tacademy on 2015-11-12.
 */
public class Image {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String cdnUri = null;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    List<String> files = null;
}
