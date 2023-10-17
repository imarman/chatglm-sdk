package com.arman.gml.sdk.model.req;

import com.arman.gml.sdk.model.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 请求参数
 *
 * @author Arman
 */
@Data
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionRequest {

    /**
     * 模型
     */
    private Model model = Model.CHAT_GLM_6B_SSE;

    /**
     * 请求ID
     */
    @JsonProperty("request_id")
    private String requestId = String.format("%d", System.currentTimeMillis());
    /**
     * 控制温度【随机性】
     */
    private float temperature = 0.9f;
    /**
     * 多样性控制；
     */
    @JsonProperty("top_p")
    private float topP = 0.7f;
    /**
     * 输入给模型的会话信息
     * 用户输入的内容；role=user
     * 挟带历史的内容；role=assistant
     */
    private List<Prompt> prompt;

    /**
     * 智普AI sse 固定参数 incremental = true 【增量返回】
     */
    private boolean incremental = true;

    /**
     * sseformat, 用于兼容解决sse增量模式okhttpsse截取data:后面空格问题, [data: hello]。只在增量模式下使用sseFormat。
     */
    private String sseFormat = "data";

    public record Prompt(String role, String content) { }

}
