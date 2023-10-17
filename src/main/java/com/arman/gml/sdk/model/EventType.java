package com.arman.gml.sdk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息类型 <a href="https://open.bigmodel.cn/dev/api#chatglm_lite">chatglm_lite</a>
 *
 * @author Arman
 */
@Getter
@AllArgsConstructor
public enum EventType {

    ADD("add", "增量"),
    FINISH("finish", "结束"),
    ERROR("error", "错误"),
    INTERRUPTED("interrupted", "中断"),

    ;
    private final String code;
    private final String info;

}
