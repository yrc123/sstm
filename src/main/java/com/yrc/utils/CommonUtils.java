package com.yrc.utils;

import java.io.IOException;
import java.net.Proxy;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yrc.pojo.CookieField;
import com.yrc.pojo.Cookies;

public class CommonUtils {
    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

    private static final String USERNAME_PATH = "#elUserLink";

    private static final String SESSIONFRONT_URL = "https://sstm.moe/";

    public static final String HREF = "href";

    public static final Integer TIMEOUT = 15000;

    private CommonUtils() {
    }

    public static String getSessionFront() {
        return getSessionFront(Proxy.NO_PROXY);
    }

    public static String getSessionFront(Proxy proxy) {
        try {
            Connection.Response response = Jsoup.connect(SESSIONFRONT_URL)
                    .cookies(Cookies.getInstance().toMap())
                    .proxy(proxy)
                    .execute();
            String username = response.parse().select(USERNAME_PATH).get(0).text();
            log.info("发现用户：{}", username);
            return response.cookies().get(CookieField.IPS4_IPSSESSIONFRONT_NAME.toString());
        } catch (IOException e) {
            log.error("无法获取session，尝试试用代理");
            return null;
        }
    }
}
