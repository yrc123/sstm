package com.yrc.sstm.core.pojo;

public enum FormDataField {
    CSRF_KEY_NAME("csrfKey"),
    CONTENT_REPLY_NAME("_contentReply"),
    TOPIC_AUTO_FOLLOW_NAME("topic_auto_follow"),
    MAX_FILE_SIZE_NAME("MAX_FILE_SIZE"),
    PLUPLOAD_NAME("plupload"),
    COMMENT_FORM_SUBMITTED_VALUE("1"),
    CONTENT_REPLY_VALUE("1"),
    TOPIC_AUTO_FOLLOW_VALUE("0"),
    MAX_FILE_SIZE_VALUE("2097152");

    private final String name;

    FormDataField(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
