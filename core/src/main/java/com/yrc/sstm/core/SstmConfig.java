package com.yrc.sstm.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SstmConfig {
    private SstmProxy proxy;
    private SstmCookies cookie;
    /**
     * 你签到贴的内容，如未设置，则内容为"签到+日期"
     * %d 占位符将被替换为日期 如"签到%d"会被解析为"签到08.16"
     */
    private String message;
}
