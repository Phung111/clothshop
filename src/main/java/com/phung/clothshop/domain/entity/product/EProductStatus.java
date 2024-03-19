package com.phung.clothshop.domain.entity.product;

public enum EProductStatus {
    AVAIABLE,
    SOLDOUT;

    public String getValue() {
        return this.name();
    }
}
