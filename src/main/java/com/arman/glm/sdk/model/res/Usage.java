package com.arman.glm.sdk.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Arman
 * @date 2023/10/18
 */
@Data
public class Usage {

    @JsonProperty("completion_tokens")
    private int completionTokens;

    @JsonProperty("prompt_tokens")
    private int promptTokens;

    @JsonProperty("total_tokens")
    private int totalTokens;

    private int usage;
}
