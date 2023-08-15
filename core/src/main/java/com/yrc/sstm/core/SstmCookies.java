package com.yrc.sstm.core;

import com.yrc.sstm.core.pojo.CookieField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Builder
@Data
public class SstmCookies {

    /**
     * 在登录之后获取
     */
    private String ips4IPSSessionFront;

    /**
     * cookie中的ips4_ss_id
     */
    private String ips4SsId;

    /**
     * cookie中的ips4_login_key
     */
    private String ips4LoginKey;

    /**
     * cookie中的ips4_member_id
     */
    private String ips4MemberId;

    /**
     * cookie中的ips4_device_key
     */
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
}
