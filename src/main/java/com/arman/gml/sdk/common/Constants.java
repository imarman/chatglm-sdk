package com.arman.gml.sdk.common;

/**
 * @author Arman
 * @date 2023/10/16
 */
public interface Constants {

    /**
     * 请求地址
     * model:模型
     */
    String V3_COMPLETIONS_SSE = "api/paas/v3/model-api/{model}/sse-invoke";

    String BEARER_HEAD_PREFIX = "Bearer ";

    String SSE_CONTENT_TYPE = "text/event-stream";
    String APPLICATION_JSON = "application/json";
    String JSON_CONTENT_TYPE = APPLICATION_JSON + "; charset=utf-8";


}
