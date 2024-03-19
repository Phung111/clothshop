package com.phung.clothshop.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class EnumToString {
    public static List<String> convert(Enum<?>[] enumValues) {
        List<String> stringList = new ArrayList<>();

        for (Enum<?> enumValue : enumValues) {
            stringList.add(enumValue.name()); // Hoặc enumValue.toString()
        }

        return stringList;
    }
}
