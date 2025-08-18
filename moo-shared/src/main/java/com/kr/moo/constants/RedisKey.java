package com.kr.moo.constants;

public class RedisKey {

    public static String getSeatKey(Long storeId) {
        return "seat:status:" + storeId;
    }

    public static String getStoreKey(Long storeId) {
        return "moo:store:" + storeId;
    }

}
