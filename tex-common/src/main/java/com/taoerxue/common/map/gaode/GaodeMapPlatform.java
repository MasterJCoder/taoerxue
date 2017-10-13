package com.taoerxue.common.map.gaode;


import com.taoerxue.common.map.MapPlatform;
import com.taoerxue.util.HttpClientUtil;
import com.taoerxue.util.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 高德地图
 */
public class GaodeMapPlatform implements MapPlatform {

    private String geoUrl = "http://restapi.amap.com/v3/geocode/geo";
    private String key = "5571b0fc531c3502861902436b8d0a2c";


    @Override
    public AddressDetailInfo addressToLngLat(String address) {
        Map<String, String> map = new HashMap<>();
        map.put("key", key);
        map.put("address", address);
        String s = HttpClientUtil.doPost(geoUrl, map);
        System.out.println("1:" + s);
        GeoResult geoResult = JsonUtils.jsonToPojo(s, GeoResult.class);
        if (geoResult == null)
            return null;
        List<GeoResult.GeocodesBean> geoCodes = geoResult.getGeoCodes();
        if (geoCodes == null || geoCodes.size() == 0)
            return null;
        GeoResult.GeocodesBean geocodesBean = geoCodes.get(0);
        String areaId = geocodesBean.getAdCode();
        String cityId = areaId.substring(0, 4) + "00";
        String provinceId = cityId.substring(0, 2) + "0000";
        String lngLat = geocodesBean.getLocation();
        String lng = lngLat.split(",")[0];
        String lat = lngLat.split(",")[1];
        AddressDetailInfo addrToLngLatResult = new AddressDetailInfo();
        addrToLngLatResult.setAreaId(Integer.parseInt(areaId));
        addrToLngLatResult.setCityId(Integer.parseInt(cityId));
        addrToLngLatResult.setProvinceId(Integer.parseInt(provinceId));
        addrToLngLatResult.setProvinceName(geocodesBean.getProvince());
        addrToLngLatResult.setCityName(geocodesBean.getCity());
        addrToLngLatResult.setAreaName(geocodesBean.getDistrict());
        addrToLngLatResult.setLng(Double.parseDouble(lng));
        addrToLngLatResult.setLat(Double.parseDouble(lat));
        return addrToLngLatResult;
    }

    public static void main(String[] args) {
        GaodeMapPlatform gaode = new GaodeMapPlatform();
        gaode.addressToLngLat("方恒国际中心A座");
    }
}
