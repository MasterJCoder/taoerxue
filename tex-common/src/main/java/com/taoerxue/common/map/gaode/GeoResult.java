package com.taoerxue.common.map.gaode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoResult {


    /**
     * status : 1
     * info : OK
     * infocode : 10000
     * count : 1
     * geocodes : [{"formatted_address":"北京市朝阳区方恒国际中心|A座","province":"北京市","citycode":"010","city":"北京市","district":"朝阳区","township":[],"neighborhood":{"name":[],"type":[]},"building":{"name":[],"type":[]},"adcode":"110105","street":[],"number":[],"location":"116.480724,39.989584","level":"门牌号"}]
     */

    private String status;
    private String info;
    @JsonProperty("infocode")
    private String infoCode;
    private String count;
    @JsonProperty("geocodes")
    private List<GeocodesBean> geoCodes;

    public String getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(String infoCode) {
        this.infoCode = infoCode;
    }

    public List<GeocodesBean> getGeoCodes() {
        return geoCodes;
    }

    public void setGeoCodes(List<GeocodesBean> geoCodes) {
        this.geoCodes = geoCodes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }



    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }



    public static class GeocodesBean {
        /**
         * formatted_address : 北京市朝阳区方恒国际中心|A座
         * province : 北京市
         * citycode : 010
         * city : 北京市
         * district : 朝阳区
         * township : []
         * neighborhood : {"name":[],"type":[]}
         * building : {"name":[],"type":[]}
         * adcode : 110105
         * street : []
         * number : []
         * location : 116.480724,39.989584
         * level : 门牌号
         */

        private String formatted_address;
        private String province;
        @JsonProperty("citycode")
        private String cityCode;
        private String city;
        private String district;
        private NeighborhoodBean neighborhood;
        private BuildingBean building;
        @JsonProperty("adcode")
        private String adCode;
        private String location;
        private String level;
        private List<?> township;
        private List<?> street;
        private List<?> number;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getAdCode() {
            return adCode;
        }

        public void setAdCode(String adCode) {
            this.adCode = adCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public NeighborhoodBean getNeighborhood() {
            return neighborhood;
        }

        public void setNeighborhood(NeighborhoodBean neighborhood) {
            this.neighborhood = neighborhood;
        }

        public BuildingBean getBuilding() {
            return building;
        }

        public void setBuilding(BuildingBean building) {
            this.building = building;
        }



        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public List<?> getTownship() {
            return township;
        }

        public void setTownship(List<?> township) {
            this.township = township;
        }

        public List<?> getStreet() {
            return street;
        }

        public void setStreet(List<?> street) {
            this.street = street;
        }

        public List<?> getNumber() {
            return number;
        }

        public void setNumber(List<?> number) {
            this.number = number;
        }

        public static class NeighborhoodBean {
            private List<?> name;
            private List<?> type;

            public List<?> getName() {
                return name;
            }

            public void setName(List<?> name) {
                this.name = name;
            }

            public List<?> getType() {
                return type;
            }

            public void setType(List<?> type) {
                this.type = type;
            }
        }

        public static class BuildingBean {
            private List<?> name;
            private List<?> type;

            public List<?> getName() {
                return name;
            }

            public void setName(List<?> name) {
                this.name = name;
            }

            public List<?> getType() {
                return type;
            }

            public void setType(List<?> type) {
                this.type = type;
            }
        }
    }
}
