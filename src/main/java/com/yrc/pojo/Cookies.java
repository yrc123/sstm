package com.yrc.pojo;

import com.yrc.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Builder
@Data
public class Cookies {
    private String ips4IPSSessionFront;

    private String ips4SsId;

    private String ips4LoginKey;

    private String ips4MemberId;

    private String ips4DeviceKey;

    public Map<String, String> toMap() {
        Map<String, String> cookies = new HashMap<>();
        if (ips4IPSSessionFront != null) {
            cookies.put(CookieField.IPS4_IPSSESSIONFRONT_NAME.toString(),
                ips4IPSSessionFront);
        }
        if (ips4DeviceKey != null) {
            cookies.put(CookieField.IPS4_DEVICE_KEY_NAME.toString(),
                ips4DeviceKey);
        }
        if (ips4SsId != null) {
            cookies.put(CookieField.IPS4_SS_ID_NAME.toString(),
                ips4SsId);
        }
        if (ips4LoginKey != null) {
            cookies.put(CookieField.IPS4_LOGIN_KEY_NAME.toString(),
                ips4LoginKey);
        }
        if (ips4MemberId != null) {
            cookies.put(CookieField.IPS4_MEMBER_ID_NAME.toString(),
                ips4MemberId);
        }
        return cookies;
    }

    @Override
    public String toString() {
        return toMap().toString();
    }

    public void login(Proxy proxy) {
        ips4IPSSessionFront = CommonUtils.getSessionFront(proxy, this);
    }

    public void login() {
        login(null);
    }
}
