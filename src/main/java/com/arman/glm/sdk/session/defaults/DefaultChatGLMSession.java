package com.arman.glm.sdk.session.defaults;

import com.arman.glm.sdk.model.req.ChatCompletionRequest;
import com.arman.glm.sdk.model.res.ChatCompletionResponse;
import com.arman.glm.sdk.model.res.R;
import com.arman.glm.sdk.session.ChatGlmSession;
import com.arman.glm.sdk.session.GlmConfiguration;
import com.arman.glm.sdk.common.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

/**
 * @author Arman
 * @date 2023/10/17
 */
@AllArgsConstructor
@Slf4j
public class DefaultChatGLMSession implements ChatGlmSession {

    /**
     * OpenAi 接口
     */
    private final GlmConfiguration glmConfiguration;

    @SneakyThrows
    @Override
    public EventSource completions(ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener) {
        ObjectMapper objectMapper = new ObjectMapper();

        // 请求地址
        String url = glmConfiguration.getApiHost()
                .concat(Constants.V3_COMPLETIONS_SSE)
                .replace("{model}", chatCompletionRequest.getModel().getCode());

        // 请求体
        RequestBody requestBody = RequestBody
                .create(objectMapper.writeValueAsBytes(chatCompletionRequest), MediaType.parse(Constants.APPLICATION_JSON));

        // 构建请求信息
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        // 返回事件结果
        EventSource.Factory factory = glmConfiguration.createEventRequestFactory();
        return factory.newEventSource(request, eventSourceListener);
    }

    @SneakyThrows
    @Override
    public R<ChatCompletionResponse> completions(ChatCompletionRequest chatCompletionRequest) {
        ObjectMapper objectMapper = new ObjectMapper();

        // 请求地址
        String url = glmConfiguration.getApiHost()
                .concat(Constants.V3_COMPLETIONS_INVOKE)
                .replace("{model}", chatCompletionRequest.getModel().getCode());

        // 请求体
        RequestBody requestBody = RequestBody
                .create(objectMapper.writeValueAsBytes(chatCompletionRequest), MediaType.parse(Constants.APPLICATION_JSON));

        // 构建请求信息
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try (Response response = glmConfiguration.getOkHttpClient().newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new RuntimeException("fetch response error : " + response);
            }

            String bodyStr = response.body().string();
            return objectMapper.readValue(bodyStr, new TypeReference<R<ChatCompletionResponse>>() {
            });
        }

    }

}
