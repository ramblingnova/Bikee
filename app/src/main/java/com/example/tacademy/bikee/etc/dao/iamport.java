package com.example.tacademy.bikee.etc.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by User on 2016-04-27.
 */
public class iamport {
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Object vbank_num;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Object vbank_name;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Object vbank_holder;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int vbank_date;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String user_agent;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String status;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String receipt_url;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String pg_tid;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String pg_provider;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String pay_method;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int paid_at;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String name;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String merchant_uid;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String imp_uid;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int failed_at;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Object fail_reason;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Object custom_data;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int card_quota;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String card_name;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int cancelled_at;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Object cancel_reason;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int cancel_amount;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Object buyer_tel;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Object buyer_postcode;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String buyer_name;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String buyer_email;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    Object buyer_addr;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    String apply_num;
    @Getter
    @Setter(AccessLevel.PUBLIC)
    int amount;
}
