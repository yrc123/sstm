package com.yrc.sstm.core.utils;

import com.yrc.sstm.core.SstmCookies;
import com.yrc.sstm.core.pojo.FormData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.Proxy;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CommentUtils {
    public static String getDefaultMessage() {
        return "签到，%d";
    }
    public static String formatCheckInMessage(String message) {
        if (StringUtils.isBlank(message)) {
            message = getDefaultMessage();
        }
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MM.dd"));
        String replacedMessage = message.replace("%d", date);
        return String.format("<p>%s</p>", replacedMessage);
    }

    private static final String FORM_PATH = "div[data-role=replyArea]>form";

    private static final String ATTR_VALUE = "value";

    private static final String ATTR_NAME = "name";

    private static final String INPUT_PATH = "input";

    private static final String CSRF_KEY_PATH = "input[name=csrfKey]";

    private static final String TOPIC_COMMENT_PATH = "[data-role=contentEditor]";

    private static final String TOPIC_COMMENT_ATTR_NAME = "name";

    private static final String PLUPLOAD_PATH = "input[name=plupload]";

    private static final String PLUPLOAD_VALUE = "value";

    private static final String COMMENT_FORM_SUBMITTED_REGEX = "commentform_\\d*_submitted";


    private CommentUtils() {
    }

    public static void sendComment(String topicUrl, SstmCookies cookies) {
        sendComment(topicUrl, getDefaultMessage(), Proxy.NO_PROXY, cookies);
    }

    public static void sendComment(String topicUrl, String message, SstmCookies cookies) {
        sendComment(topicUrl, message, Proxy.NO_PROXY, cookies);
    }

    public static void sendComment(String topicUrl, Proxy proxy, SstmCookies cookies) {
        sendComment(topicUrl, getDefaultMessage(), proxy, cookies);
    }

    public static void sendComment(String topicUrl, String message, Proxy proxy, SstmCookies cookies) {
        FormData formData = new FormData();
        //获取form表单
        Document topicPage;
        Elements form;
        try {
            topicPage = Jsoup.connect(topicUrl).cookies(cookies.toMap())
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
        formData.setTopicComment(formatCheckInMessage(message));
        //设置plupload
        String plupload = form.select(PLUPLOAD_PATH).get(0).attr(PLUPLOAD_VALUE);
        formData.setPlupload(plupload);
        //获取topicComment
        String topicCommentName = topicPage.select(TOPIC_COMMENT_PATH)
            .get(0).attr(TOPIC_COMMENT_ATTR_NAME);
        formData.setTopicCommentName(topicCommentName);
        //获取commentFormSubmitted
        for (Element element : form.select(INPUT_PATH)) {
            if (element.attr(ATTR_NAME).matches(COMMENT_FORM_SUBMITTED_REGEX)) {
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
            uploadPage = Jsoup.connect(topicUrl).cookies(cookies.toMap())
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
            Jsoup.connect(topicUrl).cookies(cookies.toMap())
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
