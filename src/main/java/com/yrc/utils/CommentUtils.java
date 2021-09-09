package com.yrc.utils;

import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yrc.pojo.Cookies;
import com.yrc.pojo.FormData;

public class CommentUtils {
    public static final String DEFAULT_MESSAGE = "<p>签到</p>";

    private static final String FORM_PATH = "div[data-role=replyArea]>form";

    private static final String ATTR_VALUE = "value";

    private static final String ATTR_NAME = "name";

    private static final String INPUT_PATH = "input";

    private static final String CSRF_KEY_PATH = "input[name=csrfKey]";

    private static final String TOPIC_COMMENT_PATH = ".ipsComposeArea_editor>div";

    private static final String TOPIC_COMMENT_ATTR_NAME = "data-ipseditor-name";

    private static final String PLUPLOAD_PATH = "input[name=plupload]";

    private static final String PLUPLOAD_VALUE = "value";

    private static final String COMMENT_FORM_SUBMMITED_REGEX = "commentform_\\d*_submitted";

    private static final Logger log = LoggerFactory.getLogger(CommentUtils.class);

    private CommentUtils() {
    }

    public static void sendComment(String topicUrl) {
        sendComment(topicUrl, DEFAULT_MESSAGE, Proxy.NO_PROXY);
    }

    public static void sendComment(String topicUrl, String message) {
        sendComment(topicUrl, message, Proxy.NO_PROXY);
    }

    public static void sendComment(String topicUrl, Proxy proxy) {
        sendComment(topicUrl, DEFAULT_MESSAGE, proxy);
    }

    public static void sendComment(String topicUrl, String message, Proxy proxy) {
        FormData formData = new FormData();
        //获取form表单
        Document topicPage;
        Elements form;
        try {
            topicPage = Jsoup.connect(topicUrl).cookies(Cookies.getInstance().toMap())
                    .proxy(proxy)
                    .timeout(CommonUtils.TIMEOUT)
                    .get();
            form = topicPage.select(FORM_PATH);
        } catch (IOException e) {
            log.error("无法获取签到帖列表，尝试试用代理");
            return;
        }
        //获取csrfKey
        String csrfKey = form.select(CSRF_KEY_PATH).get(0).attr(ATTR_VALUE);
        formData.setCsrfKey(csrfKey);
        //设置签到消息
        formData.setTopicComment(message);
        //设置plupload
        String plupload = form.select(PLUPLOAD_PATH).get(0).attr(PLUPLOAD_VALUE);
        formData.setPlupload(plupload);
        //获取topicComment
        String topicCommentName = topicPage.select(TOPIC_COMMENT_PATH)
                .get(0).attr(TOPIC_COMMENT_ATTR_NAME);
        formData.setTopicCommentName(topicCommentName);
        //获取commentFormSubmitted
        for (Element element : form.select(INPUT_PATH)) {
            if (element.attr(ATTR_NAME).matches(COMMENT_FORM_SUBMMITED_REGEX)) {
                formData.setCommentFormSubmittedName(element.attr(ATTR_NAME));
                break;
            }
        }
        //获取topicCommentUpload
        Map<String, String> query = new HashMap<>();
        query.put("csrfKey", csrfKey);
        query.put("getUploader", topicCommentName);
        Document uploadPage;
        try {
            uploadPage = Jsoup.connect(topicUrl).cookies(Cookies.getInstance().toMap())
                    .data(query)
                    .proxy(proxy)
                    .timeout(CommonUtils.TIMEOUT)
                    .get();
        } catch (IOException e) {
            log.error("无法获取签到帖列表，尝试试用代理");
            return;
        }
        String topicCommentUploadName = uploadPage.select(INPUT_PATH).get(0).attr(ATTR_NAME);
        formData.setTopicCommentUploadName(topicCommentUploadName);
        String topicCommentUpload = uploadPage.select(INPUT_PATH).get(0).attr(ATTR_VALUE);
        formData.setTopicCommentUpload(topicCommentUpload);
        log.info("成功获取表单信息：{}", formData);
        try {
            Jsoup.connect(topicUrl).cookies(Cookies.getInstance().toMap())
                    .proxy(proxy)
                    .data(formData.toMap())
                    .timeout(CommonUtils.TIMEOUT)
                    .post();
        } catch (IOException e) {
            log.error("发帖失败，尝试使用代理");
        }
        log.info("发帖完成");
    }
}
