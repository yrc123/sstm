package com.yrc.sstm.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class SstmProxy {
    /**
     * 如果要设置代理，代理的地址
     */
    private String hostname;

    /**
     * 如果要设置代理，代理的端口
     */
    private Integer port;
}
