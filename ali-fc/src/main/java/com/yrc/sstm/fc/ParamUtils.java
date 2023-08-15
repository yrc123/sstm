package com.yrc.sstm.fc;

public final class ParamUtils {
    //change this class to enum
    private ParamUtils() {}

    public static final String IPS4_LOGIN_KEY = "IPS4_LOGIN_KEY";
    public static final String IPS4_MEMBER_ID = "IPS4_MEMBER_ID";
    public static final String IPS4_DEVICE_KEY = "IPS4_DEVICE_KEY";
    public static final String IPS4_SS_ID = "IPS4_SS_ID";
    public static final String MESSAGE = "MESSAGE";
    public static final String HOSTNAME = "HOSTNAME";
    public static final String PORT = "PORT";

    public static String getParamValue(String key) {
        return System.getenv(key);
    }

}
