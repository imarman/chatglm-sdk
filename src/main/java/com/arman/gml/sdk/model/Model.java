package com.arman.gml.sdk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会话模型
 *
 * @author Arman
 */
@Getter
@AllArgsConstructor
public enum Model {

    CHAT_GLM_6B_SSE("chatGLM_6b_SSE", "ChatGLM-6B 测试模型"),
    CHAT_GLM_LITE("chatglm_lite", "轻量版模型，适用对推理速度和成本敏感的场景"),
    CHAT_GLM_LITE_32K("chatglm_lite_32k", "标准版模型，适用兼顾效果和成本的场景"),
    CHAT_GLM_STD("chatglm_std", "适用于对知识量、推理能力、创造力要求较高的场景"),
    CHAT_GLM_PRO("chatglm_pro", "适用于对知识量、推理能力、创造力要求较高的场景"),

    ;
    private final String code;
    private final String info;
}
