package com.phung.clothshop.model.product;

public enum EProductStatus {
    AVAIABLE,
    SOLDOUT;

    public String getValue() {
        return this.name();
    }
}
