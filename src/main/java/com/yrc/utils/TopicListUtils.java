package com.yrc.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Proxy;

public class TopicListUtils {
    private static final String TOPIC_LIST_URL = "https://sstm.moe/forum/72-%E5%90%8C%E7%9B%9F%E7%AD%BE%E5%88%B0%E5%8C%BA/";

    private static final String TOPIC_CSS_PATH = ".ipsDataItem";

    private static final String TOPIC_URL_PATH = ".ipsDataItem_title>.ipsType_break>a";

    private static final Logger log = LoggerFactory.getLogger(TopicListUtils.class);

    private TopicListUtils() {
    }

    public static String getTodayTopicUrl() {
        return getTodayTopicUrl(Proxy.NO_PROXY);
    }

    public static String getTodayTopicUrl(Proxy proxy) {
        Document topicListPage;
        try {
            topicListPage = Jsoup.connect(TOPIC_LIST_URL)
                .proxy(proxy)
                .timeout(CommonUtils.TIMEOUT)
                .get();
        } catch (IOException e) {
            log.error("无法获取签到帖列表，尝试试用代理");
            return null;
        }
        Elements topicList = topicListPage.select(TOPIC_CSS_PATH);
        Element todayTopic = topicList.get(0);
        Elements topicUrl = todayTopic.select(TOPIC_URL_PATH);
        String link = topicUrl.attr(CommonUtils.HREF);
        log.info("获取今日签到帖链接：{}", link);
        return link;
    }
}
