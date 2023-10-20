package com.arman.glm.sdk.model.res;

import lombok.Data;

/**
 * 返回结果
 *
 * @author 小傅哥，微信：fustack
 */
@Data
public class ChatCompletionSseResponse {

    private String data;

    private String meta;

    @Data
    public static class Meta {

        private String task_status;

        private Usage usage;

        private String task_id;

        private String request_id;

    }

}
