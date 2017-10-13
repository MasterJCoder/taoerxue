package com.taoerxue.common.map;


import com.taoerxue.common.map.gaode.AddressDetailInfo;

public interface MapPlatform {

     AddressDetailInfo addressToLngLat(String address);
}
