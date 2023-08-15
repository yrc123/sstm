package com.yrc.sstm.core.pojo;

public enum CookieField {
    IPS4_IPSSESSIONFRONT_NAME("ips4_IPSSessionFront"),
    IPS4_SS_ID_NAME("ips4_ss_id"),
    IPS4_LOGIN_KEY_NAME("ips4_login_key"),
    IPS4_MEMBER_ID_NAME("ips4_member_id"),
    IPS4_DEVICE_KEY_NAME("ips4_device_key");

    private final String name;

    CookieField(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
