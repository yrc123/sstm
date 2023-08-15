package com.yrc.sstm.core;

import com.yrc.sstm.core.utils.CommentUtils;
import com.yrc.sstm.core.utils.CommonUtils;
import com.yrc.sstm.core.utils.TopicListUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Slf4j
public class SstmHomePage {
    private final SstmCookies cookies;
    private final Proxy proxy;
    private final String checkInMessage;
    public SstmHomePage(SstmConfig sstmConfig) {
        this.cookies = sstmConfig.getCookie();
        this.proxy = getProxy(sstmConfig.getProxy());
        this.checkInMessage = sstmConfig.getMessage();
    }

    public void login() {
        log.debug("开始登录");
        log.debug("当前cookie信息：{}", cookies);
        cookies.setIps4IPSSessionFront(CommonUtils.getSessionFront(proxy, cookies));
        log.info("获取Cookie信息：{}", cookies);

    }
    public boolean isLogin() {
        return StringUtils.isNotBlank(cookies.getIps4IPSSessionFront());
    }
    public void checkIn() {
        if (!isLogin()) {
            log.error("未登录");
            return;
        }
        String todayTopicUrl = TopicListUtils.getTodayTopicUrl(proxy);
        CommentUtils.sendComment(todayTopicUrl, checkInMessage, proxy, cookies);
    }

    private Proxy getProxy(SstmProxy proxyConfig) {
        Proxy proxy;
        if (proxyConfig == null) {
            return Proxy.NO_PROXY;
        }

        if (StringUtils.isNoneBlank(proxyConfig.getHostname())
            && proxyConfig.getPort() != null) {
            proxy = new Proxy(Proxy.Type.HTTP,
                new InetSocketAddress(
                    proxyConfig.getHostname(),
                    proxyConfig.getPort()
                )
            );

        } else {
            proxy = Proxy.NO_PROXY;
        }
        return proxy;
    }
}
