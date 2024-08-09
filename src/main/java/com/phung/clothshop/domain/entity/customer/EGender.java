package com.phung.clothshop.domain.entity.customer;

public enum EGender {

    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    public String getValue() {
        return this.name();
    }

    private final String name;

    EGender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static EGender fromName(String name) {
        for (EGender gender  : EGender.values()) {
            if (gender .getName().equalsIgnoreCase(name)) {
                return gender ;
            }
        }
        throw new IllegalArgumentException("Invalid EGender name: " + name);
    }
}
