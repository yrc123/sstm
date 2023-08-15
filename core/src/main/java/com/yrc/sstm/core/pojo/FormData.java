package com.yrc.sstm.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
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
