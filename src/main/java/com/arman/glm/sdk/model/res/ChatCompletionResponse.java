package com.arman.glm.sdk.model.res;

import com.arman.glm.sdk.model.Prompt;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 返回结果
 *
 * @author 小傅哥，微信：fustack
 */
@Data
public class ChatCompletionResponse {

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("task_id")
    private String taskId;

    @JsonProperty("task_status")
    private String taskStatus;

    private Usage usage;

    private List<Prompt> choices;

}
