package com.phung.clothshop.domain.entity.ship;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import org.json.*;

public enum EPronvince {
    HA_NOI("Ha Noi"),
    HO_CHI_MINH("Ho Chi Minh"),
    HAIPHONG("Hai Phong"),
    QUANG_NINH("Quang Ninh"),
    NINH_BINH("Ninh Binh"),
    THAI_BINH("Thai Binh"),
    VINH_PHUC("Vinh Phuc"),
    HAI_DUONG("Hai Duong"),
    HUNG_YEN("Hung Yen"),
    THANH_HOA("Thanh Hoa"),
    NGHE_AN("Nghe An"),
    HA_TINH("Ha Tinh"),
    QUANG_BINH("Quang Binh"),
    QUANG_TRI("Quang Tri"),
    THUA_THIEN_HUE("Thua Thien Hue"),
    DA_NANG("Da Nang"),
    QUANG_NAM("Quang Nam"),
    QUANG_NGAI("Quang Ngai"),
    BINH_DINH("Binh Dinh"),
    PHU_YEN("Phu Yen"),
    KHANH_HOA("Khanh Hoa"),
    NINH_THUAN("Ninh Thuan"),
    BINH_THUAN("Binh Thuan"),
    KON_TUM("Kon Tum"),
    GIA_LAI("Gia Lai"),
    DAK_LAK("Dak Lak"),
    DAK_NONG("Dak Nong"),
    LAM_DONG("Lam Dong"),
    BINH_PHUOC("Binh Phuoc"),
    TAY_NINH("Tay Ninh"),
    BINH_DUONG("Binh Duong"),
    DONG_NAI("Dong Nai"),
    BA_RIA_VUNG_TAU("Ba Ria-Vung Tau"),
    LONG_AN("Long An"),
    TIEN_GIANG("Tien Giang"),
    BEN_TRE("Ben Tre"),
    TRA_VINH("Tra Vinh"),
    VINH_LONG("Vinh Long"),
    DONG_THAP("Dong Thap"),
    AN_GIANG("An Giang"),
    KIEN_GIANG("Kien Giang"),
    CAN_THO("Can Tho"),
    HAU_GIANG("Hau Giang"),
    SOC_TRANG("Soc Trang"),
    BAC_LIEU("Bac Lieu"),
    CA_MAU("Ca Mau"),
    DIEN_BIEN("Dien Bien"),
    LAI_CHAU("Lai Chau"),
    LAO_CAI("Lao Cai"),
    YEN_BAI("Yen Bai"),
    SON_LA("Son La"),
    HOA_BINH("Hoa Binh"),
    THAI_NGUYEN("Thai Nguyen"),
    LANG_SON("Lang Son"),
    BAC_KAN("Bac Kan"),
    CAO_BANG("Cao Bang"),
    BAC_GIANG("Bac Giang"),
    PHU_THO("Phu Tho"),
    TUYEN_QUANG("Tuyen Quang"),
    HA_GIANG("Ha Giang"),
    VIET_TRI("Viet Tri");

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
