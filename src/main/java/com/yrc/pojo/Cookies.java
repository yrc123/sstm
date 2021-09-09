package com.yrc.pojo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cookies {
    private String ips4IPSSessionFront;

    private String ips4SsId;

    private String ips4LoginKey;

    private String ips4MemberId;

    private String ips4DeviceKey;


    private static final Cookies instance = new Cookies();

    private Cookies() {
    }

    public static Cookies getInstance() {
        return instance;
    }

    public String getIps4IPSSessionFront() {
        return ips4IPSSessionFront;
    }

    public Cookies setIps4IPSSessionFront(String ips4IPSSessionFront) {
        this.ips4IPSSessionFront = ips4IPSSessionFront;
        return this;
    }

    public String getIps4SsId() {
        return ips4SsId;
    }

    public Cookies setIps4SsId(String ips4SsId) {
        this.ips4SsId = ips4SsId;
        return this;
    }

    public String getIps4LoginKey() {
        return ips4LoginKey;
    }

    public Cookies setIps4LoginKey(String ips4LoginKey) {
        this.ips4LoginKey = ips4LoginKey;
        return this;
    }

    public String getIps4MemberId() {
        return ips4MemberId;
    }

    public Cookies setIps4MemberId(String ips4MemberId) {
        this.ips4MemberId = ips4MemberId;
        return this;
    }

    public String getIps4DeviceKey() {
        return ips4DeviceKey;
    }

    public Cookies setIps4DeviceKey(String ips4DeviceKey) {
        this.ips4DeviceKey = ips4DeviceKey;
        return this;
    }

    public Map<String, String> toMap() {
        Map<String, String> cookies = new HashMap<>();
        if (Objects.nonNull(ips4IPSSessionFront)) {
            cookies.put(CookieField.IPS4_IPSSESSIONFRONT_NAME.toString(),
                    ips4IPSSessionFront);
        }
        if (Objects.nonNull(ips4DeviceKey)) {
            cookies.put(CookieField.IPS4_DEVICE_KEY_NAME.toString(),
                    ips4DeviceKey);
        }
        if (Objects.nonNull(ips4SsId)) {
            cookies.put(CookieField.IPS4_SS_ID_NAME.toString(),
                    ips4SsId);
        }
        if (Objects.nonNull(ips4LoginKey)) {
            cookies.put(CookieField.IPS4_LOGIN_KEY_NAME.toString(),
                    ips4LoginKey);
        }
        if (Objects.nonNull(ips4MemberId)) {
            cookies.put(CookieField.IPS4_MEMBER_ID_NAME.toString(),
                    ips4MemberId);
        }
        return cookies;
    }

    @Override
    public String toString() {
        return toMap().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cookies cookies = (Cookies) o;
        return Objects.equals(ips4IPSSessionFront, cookies.ips4IPSSessionFront) && Objects.equals(ips4SsId, cookies.ips4SsId) && Objects.equals(ips4LoginKey,
                cookies.ips4LoginKey) && Objects.equals(ips4MemberId, cookies.ips4MemberId) && Objects.equals(ips4DeviceKey, cookies.ips4DeviceKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ips4IPSSessionFront, ips4SsId, ips4LoginKey, ips4MemberId, ips4DeviceKey);
    }
}
