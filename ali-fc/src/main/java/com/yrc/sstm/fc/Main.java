package com.yrc.sstm.fc;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.StreamRequestHandler;
import com.yrc.sstm.core.SstmConfig;
import com.yrc.sstm.core.SstmCookies;
import com.yrc.sstm.core.SstmHomePage;
import com.yrc.sstm.core.SstmProxy;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

@Slf4j
public class Main implements StreamRequestHandler {
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) {
        SstmConfig sstmConfig = buildSstmConfig();
        SstmHomePage homePage = new SstmHomePage(sstmConfig);
        homePage.login();
        homePage.checkIn();
    }

    private SstmConfig buildSstmConfig() {
        SstmProxy proxy = buildSstmProxy();
        SstmCookies cookies = buildSstmCookies();
        String message = buildMessage();
        return SstmConfig.builder()
            .proxy(proxy)
            .cookie(cookies)
            .message(message)
            .build();
    }

    private String buildMessage() {
        return ParamUtils.getParamValue(ParamUtils.MESSAGE);
    }

    private SstmProxy buildSstmProxy() {
        Integer port = Optional.ofNullable(ParamUtils.getParamValue(ParamUtils.PORT))
            .map(Integer::parseInt)
            .orElse(null);
        return SstmProxy.builder()
            .hostname(ParamUtils.getParamValue(ParamUtils.HOSTNAME))
            .port(port)
            .build();
    }

    private SstmCookies buildSstmCookies() {
        return SstmCookies.builder()
            .ips4LoginKey(ParamUtils.getParamValue(ParamUtils.IPS4_LOGIN_KEY))
            .ips4MemberId(ParamUtils.getParamValue(ParamUtils.IPS4_MEMBER_ID))
            .ips4DeviceKey(ParamUtils.getParamValue(ParamUtils.IPS4_DEVICE_KEY))
            .ips4SsId(ParamUtils.getParamValue(ParamUtils.IPS4_SS_ID))
            .build();
    }
}
