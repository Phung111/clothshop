package com.phung.clothshop.domain.entity.ship;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import org.json.*;

public enum EPronvince {
    AN_GIANG("An Giang"),
    BAC_GIANG("Bac Giang"),
    BAC_KAN("Bac Kan"),
    BAC_LIEU("Bac Lieu"),
    BAC_NINH("Bac Ninh"),
    BEN_TRE("Ben Tre"),
    BINH_DINH("Binh Dinh"),
    BINH_DUONG("Binh Duong"),
    BINH_PHUOC("Binh Phuoc"),
    BINH_THUAN("Binh Thuan"),
    CA_MAU("Ca Mau"),
    CAO_BANG("Cao Bang"),
    DA_NANG("Da Nang"),
    DAK_LAK("Dak Lak"),
    DAK_NONG("Dak Nong"),
    DIEN_BIEN("Dien Bien"),
    DONG_NAI("Dong Nai"),
    DONG_THAP("Dong Thap"),
    GIA_LAI("Gia Lai"),
    HA_GIANG("Ha Giang"),
    HA_NOI("Ha Noi"),
    HAI_PHONG("Hai Phong"),
    HAI_DUONG("Hai Duong"),
    HAU_GIANG("Hau Giang"),
    HOA_BINH("Hoa Binh"),
    HUNG_YEN("Hung Yen"),
    KHANH_HOA("Khanh Hoa"),
    KIEN_GIANG("Kien Giang"),
    KON_TUM("Kon Tum"),
    LAI_CHAU("Lai Chau"),
    LANG_SON("Lang Son"),
    LAO_CAI("Lao Cai"),
    LONG_AN("Long An"),
    NINH_BINH("Ninh Binh"),
    NINH_THUAN("Ninh Thuan"),
    PHU_THO("Phu Tho"),
    PHU_YEN("Phu Yen"),
    QUANG_BINH("Quang Binh"),
    QUANG_NAM("Quang Nam"),
    QUANG_NGAI("Quang Ngai"),
    QUANG_NINH("Quang Ninh"),
    QUANG_TRI("Quang Tri"),
    SOC_TRANG("Soc Trang"),
    SON_LA("Son La"),
    TAY_NINH("Tay Ninh"),
    THAI_BINH("Thai Binh"),
    THAI_NGUYEN("Thai Nguyen"),
    THANH_HOA("Thanh Hoa"),
    THUA_THIEN_HUE("Thua Thien Hue"),
    TIEN_GIANG("Tien Giang"),
    TRA_VINH("Tra Vinh"),
    TUYEN_QUANG("Tuyen Quang"),
    VIET_TRI("Viet Tri"),
    VINH_LONG("Vinh Long"),
    VINH_PHUC("Vinh Phuc"),
    YEN_BAI("Yen Bai");

    private final String name;

    EPronvince(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double[] getCoordinates() {
        try {
            String encodedLocation = URLEncoder.encode(this.getName(), "UTF-8");
            String urlString = "https://nominatim.openstreetmap.org/search?q=" + encodedLocation
                    + "&format=json&limit=1";

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONArray jsonArray = new JSONArray(response.toString());
            if (jsonArray.length() > 0) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                double lat = jsonObject.getDouble("lat");
                double lon = jsonObject.getDouble("lon");
                return new double[] { lat, lon };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static EPronvince fromName(String name) {
        for (EPronvince province : EPronvince.values()) {
            if (province.getName().equalsIgnoreCase(name)) {
                return province;
            }
        }
        throw new IllegalArgumentException("Invalid EPronvince name: " + name);
    }

}
