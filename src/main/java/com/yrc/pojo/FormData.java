package com.yrc.pojo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FormData {
    private String csrfKey;

    private String topicComment;

    private String topicCommentName;

    private String topicCommentUpload;

    private String topicCommentUploadName;

    private String commentFormSubmittedName;

    private String plupload;

    public FormData() {
    }

    public FormData(String csrfKey,
            String topicComment,
            String topicCommentName,
            String topicCommentUpload,
            String topicCommentUploadName,
            String commentFormSubmittedName,
            String plupload) {
        this.csrfKey = csrfKey;
        this.topicComment = topicComment;
        this.topicCommentName = topicCommentName;
        this.topicCommentUpload = topicCommentUpload;
        this.topicCommentUploadName = topicCommentUploadName;
        this.commentFormSubmittedName = commentFormSubmittedName;
        this.plupload = plupload;
    }

    public String getCsrfKey() {
        return csrfKey;
    }

    public void setCsrfKey(String csrfKey) {
        this.csrfKey = csrfKey;
    }

    public String getTopicComment() {
        return topicComment;
    }

    public void setTopicComment(String topicComment) {
        this.topicComment = topicComment;
    }

    public String getTopicCommentName() {
        return topicCommentName;
    }

    public void setTopicCommentName(String topicCommentName) {
        this.topicCommentName = topicCommentName;
    }

    public String getTopicCommentUpload() {
        return topicCommentUpload;
    }

    public void setTopicCommentUpload(String topicCommentUpload) {
        this.topicCommentUpload = topicCommentUpload;
    }

    public String getTopicCommentUploadName() {
        return topicCommentUploadName;
    }

    public void setTopicCommentUploadName(String topicCommentUploadName) {
        this.topicCommentUploadName = topicCommentUploadName;
    }

    public String getCommentFormSubmittedName() {
        return commentFormSubmittedName;
    }

    public void setCommentFormSubmittedName(String commentFormSubmittedName) {
        this.commentFormSubmittedName = commentFormSubmittedName;
    }

    public String getPlupload() {
        return plupload;
    }

    public void setPlupload(String plupload) {
        this.plupload = plupload;
    }

    public Map<String, String> toMap() {
        Map<String, String> data = new HashMap<>();
        if (Objects.nonNull(topicCommentName) && Objects.nonNull(topicComment)) {
            data.put(topicCommentName, topicComment);
        }
        if (Objects.nonNull(csrfKey)) {
            data.put(FormDataField.CSRF_KEY_NAME.toString(), csrfKey);
        }
        if (Objects.nonNull(topicCommentUploadName) && Objects.nonNull(topicCommentUpload)) {
            data.put(topicCommentUploadName, topicCommentUpload);
        }
        if (Objects.nonNull(commentFormSubmittedName)) {
            data.put(commentFormSubmittedName, FormDataField.COMMENT_FORM_SUBMITTED_VALUE.toString());
        }
        data.put(FormDataField.CONTENT_REPLY_NAME.toString(),
                FormDataField.CONTENT_REPLY_VALUE.toString());
        data.put(FormDataField.TOPIC_AUTO_FOLLOW_NAME.toString(),
                FormDataField.TOPIC_AUTO_FOLLOW_VALUE.toString());
        data.put(FormDataField.MAX_FILE_SIZE_NAME.toString(),
                FormDataField.MAX_FILE_SIZE_VALUE.toString());
        if (Objects.nonNull(plupload)) {
            data.put(FormDataField.PLUPLOAD_NAME.toString(), plupload);
        }
        return data;
    }

    @Override
    public String toString() {
        return toMap().toString();
    }
}
