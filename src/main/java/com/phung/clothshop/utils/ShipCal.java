package com.phung.clothshop.utils;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.phung.clothshop.domain.entity.customer.Address;
import com.phung.clothshop.domain.entity.ship.EPronvince;
import com.phung.clothshop.exceptions.CustomErrorException;

@Component
public class ShipCal {

    public static Long calculate(Address address) {

        String ePronvinceFrom = "Thua Thien Hue";
        String ePronvinceTo = address.getEPronvince().getName();

        Long shippingCost = calculateAndPrintDistance(ePronvinceFrom, ePronvinceTo);

        return shippingCost;
    }

    public static Long calculateShippingCost(Long distance) {
        if (distance < 500) {
            return 17000L;
        } else if (distance < 1000) {
            return 34000L;
        } else {
            return 50000L;
        }
    }

    private static double calculateDistanceBetweenProvinces(EPronvince province1, EPronvince province2) {
        double[] coords1 = province1.getCoordinates();
        double[] coords2 = province2.getCoordinates();

        if (coords1 != null && coords2 != null) {
            return calculateDistance(coords1[0], coords1[1], coords2[0], coords2[1]);
        } else {
            return -1;
        }
    }

    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        int R = 6371; // Bán kính trái đất (đơn vị: km)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public static Long calculateAndPrintDistance(String provinceName1, String provinceName2) {
        EPronvince enumProvince1 = null;
        EPronvince enumProvince2 = null;

        // Kiểm tra xem chuỗi đầu vào có tồn tại trong enum không
        for (EPronvince province : EPronvince.values()) {
            if (province.getName().equalsIgnoreCase(provinceName1)) {
                enumProvince1 = province;
            }
            if (province.getName().equalsIgnoreCase(provinceName2)) {
                enumProvince2 = province;
            }
        }

        if (enumProvince1 != null && enumProvince2 != null) {
            double distance = calculateDistanceBetweenProvinces(enumProvince1, enumProvince2);
            System.out.println(distance);
            if (distance != -1) {
                Long roundedDistance = Math.round(distance);
                Long shippingCost = calculateShippingCost(roundedDistance);
                return shippingCost;
            } else {
                throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to calculate distance.");
            }
        } else {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "At least one province not found in enum list.");
        }
    }
}
