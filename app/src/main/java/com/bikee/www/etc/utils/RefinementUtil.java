package com.bikee.www.etc.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.bikee.www.etc.dao.Comment;
import com.bikee.www.etc.dao.GetChannelResInfoReceiveObject;
import com.bikee.www.etc.dao.Reserve;
import com.bikee.www.etc.dao.Result;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by User on 2016-03-09.
 */
public class RefinementUtil {
    public static String getBicycleImageURLStringFromResult(Result result) {
        if ((null == result.getImage())
                || (null == result.getImage().getCdnUri())
                || (null == result.getImage().getFiles())
                || (0 >= result.getImage().getFiles().size())) {
            return "";
        } else {
            return result.getImage().getCdnUri() + result.getImage().getFiles().get(0);
        }
    }

    public static String getBicycleImageURLStringFromSendBirdResult(GetChannelResInfoReceiveObject.SendBirdResult result) {
        if ((null == result.getBike().getImage())
                || (null == result.getBike().getImage().getCdnUri())
                || (null == result.getBike().getImage().getFiles())
                || (0 >= result.getBike().getImage().getFiles().size())) {
            return "";
        } else {
            return result.getBike().getImage().getCdnUri()
                    + result.getBike().getImage().getFiles().get(0);
        }
    }

    public static List<String> getBicycleImageURLListFromResult(Result result) {
        List<String> list = new ArrayList<>();
        if ((null == result.getImage())
                || (null == result.getImage().getCdnUri())
                || (null == result.getImage().getFiles())
                || (0 >= result.getImage().getFiles().size())) {
            return list;
        } else {
            for (String s : result.getImage().getFiles()) {
                list.add(result.getImage().getCdnUri() + s);
            }
            return list;
        }
    }

    public static String getUserImageURLStringFromResult(Result result) {
        if ((null == result.getUser().getImage())
                || (null == result.getUser().getImage().getCdnUri())
                || (null == result.getUser().getImage().getFiles())
                || (0 >= result.getUser().getImage().getFiles().size())) {
            return "";
        } else {
            return result.getUser().getImage().getCdnUri() + result.getUser().getImage().getFiles().get(0);
        }
    }

    public static String getUserImageURLStringFromReserve(Reserve reserve) {
        String renterImageURL;
        if ((null == reserve.getRenter().getImage())
                || (null == reserve.getRenter().getImage().getCdnUri())
                || (null == reserve.getRenter().getImage().getFiles())) {
            renterImageURL = "";
        } else {
            renterImageURL = "https://" + reserve.getRenter().getImage().getCdnUri() + reserve.getRenter().getImage().getFiles().get(0);
        }
        return renterImageURL;
    }

    public static String getUserImageURLStringFromComment(Comment comment) {
        if ((null == comment.getWriter().getImage())
                || (null == comment.getWriter().getImage().getCdnUri())
                || (null == comment.getWriter().getImage().getFiles())
                || (0 >= comment.getWriter().getImage().getFiles().size())) {
            return "";
        } else {
            return comment.getWriter().getImage().getCdnUri() + comment.getWriter().getImage().getFiles().get(0);
        }
    }

    public static List<String> getComponentListFromResult(Result result) {
        if ((null == result.getComponents())
                || (0 >= result.getComponents().size())) {
            return null;
        } else {
            return result.getComponents();
        }
    }

    public static String getBicycleTypeStringFromBicycleType(String type) {
        String retVal;
        switch (type) {
            case "A":
                retVal = "보급형";
                break;
            case "B":
                retVal = "산악용";
                break;
            case "C":
                retVal = "하이브리드";
                break;
            case "D":
                retVal = "픽시";
                break;
            case "E":
                retVal = "폴딩";
                break;
            case "F":
                retVal = "미니벨로";
                break;
            case "G":
                retVal = "전기자전거";
                break;
            default:
                retVal = "";
                break;
        }
        return retVal;
    }

    public static String getBicycleHeightStringFromBicycleHeight(String height) {
        String retVal;
        switch (height) {
            case "01":
                retVal = "~ 145cm";
                break;
            case "02":
                retVal = "145cm ~ 155cm";
                break;
            case "03":
                retVal = "155cm ~ 165cm";
                break;
            case "04":
                retVal = "165cm ~ 175cm";
                break;
            case "05":
                retVal = "175cm ~ 185cm";
                break;
            case "06":
                retVal = "185cm ~";
                break;
            default:
                retVal = "";
                break;
        }
        return retVal;
    }

    public static String getBicycleComponentStringFromBicycleComponent(String component) {
        String retVal;
        switch (component) {
            case "A":
                retVal = "자물쇠";
                break;
            case "B":
                retVal = "라이트";
                break;
            case "C":
                retVal = "반사판";
                break;
            case "D":
                retVal = "장갑";
                break;
            case "E":
                retVal = "바구니";
                break;
            case "F":
                retVal = "트레일러";
                break;
            case "G":
                retVal = "백미러";
                break;
            case "H":
                retVal = "헬멧";
                break;
            default:
                retVal = "";
                break;
        }
        return retVal;
    }

    public List<String> getBicycleComponentListFromResult(Result result) {
        List<String> list = new ArrayList<>();
        for (String component : result.getComponents()) {
            list.add(component);
        }
        return list;
    }

    public static String findAddress(Context context, double lat, double lng) {
        StringBuffer bf = new StringBuffer();
        Geocoder geocoder = new Geocoder(context, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                // 세번째 인수는 최대결과값인데 하나만 리턴받도록 설정했다
                address = geocoder.getFromLocation(lat, lng, 1);
                // 설정한 데이터로 주소가 리턴된 데이터가 있으면
                if (address != null && address.size() > 0) {
                    bf.append(address.get(0).getAdminArea())
                            .append(" " + address.get(0).getLocality())
                            .append(" " + address.get(0).getThoroughfare());
                }
            }
        } catch (IOException e) {
            Toast.makeText(context, "주소취득 실패", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return bf.toString();
    }

    public static LatLng findGeoPoint(Context context, String address) {
        Geocoder geocoder = new Geocoder(context, Locale.KOREA);
        Address addr;
        LatLng latLng = null;
        try {
            List<Address> listAddress = geocoder.getFromLocationName(address, 1);
            if (listAddress.size() > 0) { // 주소값이 존재 하면
                addr = listAddress.get(0); // Address형태로
                int lat = (int) (addr.getLatitude() * 1E6);
                int lng = (int) (addr.getLongitude() * 1E6);
                latLng = new LatLng(lat, lng);
            }
        } catch (IOException e) {
            Toast.makeText(context, "좌표변환 실패", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return latLng;
    }

    public static String calculateRentPeriod(Date startDate,
                                             Date endDate) {
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);

        long diffMillis = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();

        int hour = (int) (diffMillis / (60 * 60 * 1000));
        int hourExtraTime = hour % 24;
        int dayExtraTime = (hour / 24) % 30;
        int monthExtraTime = hour / (24 * 30);

        StringBuilder builder = new StringBuilder();
        boolean chain = false;

        if (monthExtraTime > 0) {
            builder.append(monthExtraTime).append("개월");
            chain = true;
        }
        if (dayExtraTime > 0) {
            if (chain)
                builder.append(" ");
            builder.append(dayExtraTime).append("일");
            chain = true;
        }
        if (hourExtraTime > 0) {
            if (chain)
                builder.append(" ");
            builder.append(hourExtraTime).append("시간");
        }

        return builder.toString();
    }

    public static int calculatePrice(Date startDate,
                                     Date endDate,
                                     int pricePerMonth,
                                     int pricePerDay,
                                     int pricePerHour) {
        int retVal = 0;
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);

        long diffMillis = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();

        int hour = (int) (diffMillis / (60 * 60 * 1000));
        int hourExtraTime = hour % 24;
        int dayExtraTime = (hour / 24) % 30;
        int monthExtraTime = hour / (24 * 30);

        if (monthExtraTime > 0)
            retVal += pricePerMonth * monthExtraTime;
        if (dayExtraTime > 0)
            retVal += pricePerDay * dayExtraTime;
        if (hourExtraTime > 0)
            retVal += pricePerHour * hourExtraTime;

        return retVal;
    }
}
