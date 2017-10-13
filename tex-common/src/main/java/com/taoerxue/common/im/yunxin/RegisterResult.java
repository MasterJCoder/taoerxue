package com.taoerxue.common.im.yunxin;

/**
 * Created by lizhihui on 2017-04-14 10:14.
 */
public class RegisterResult {

    /**
     * code : 200
     * info : {"token":"dwfsdj","accid":"111","name":"sb"}
     */

    private int code;
    private InfoBean info;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * token : dwfsdj
         * accid : 111
         * name : sb
         */

        private String token;
        private String accid;
        private String name;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAccid() {
            return accid;
        }

        public void setAccid(String accid) {
            this.accid = accid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
