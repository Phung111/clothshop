package com.phung.clothshop.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.phung.clothshop.domain.entity.ship.EPronvince;
import java.lang.reflect.Method;

@Component
public class EnumToString {
    public static List<String> convert(Enum<?>[] enumValues) {
        List<String> stringList = new ArrayList<>();

        for (Enum<?> enumValue : enumValues) {
            stringList.add(enumValue.name()); // Hoặc enumValue.toString()
        }

        return stringList;
    }

    public List<String> convertToName(Enum<?>[] enumValues) {
        List<String> stringList = new ArrayList<>();

        for (Enum<?> enumValue : enumValues) {
            try {
                Method getNameMethod = enumValue.getClass().getMethod("getName");
                String name = (String) getNameMethod.invoke(enumValue);
                stringList.add(name);
            } catch (Exception e) {
                stringList.add(enumValue.name());
            }
        }

        return stringList;
    }
}
