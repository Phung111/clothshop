package com.phung.clothshop.domain.entity.product;

public enum ECategory {
    SHIRT,
    PANT,
    SHOE,
    SANDAL,
    JACKET,
    SKIRT,
    DRESS,
    SUIT,
    HOODIE,
    SWEATER,
    UNDERWEAR,
    SOCKS,
    SCARF,
    HAT,
    GLOVES,
    BELT,
    CAP,
    BAG,
    SPORT,
    GLASS,
    EYEWEAR,
    TIE,
    SKINCARE,
    MAKEUP,
    HAIRCARE,
    BODYCAIR,
    MOBILE,
    GADGET,
    DESKTOP,
    MONITOR,
    MOUSE,
    SPEAKER,
    HEADPHONE,
    KEYBOARD,
    CAMERA,
    WATCH,
    ;


    public String getValue() {
        return this.name();
    }
}
