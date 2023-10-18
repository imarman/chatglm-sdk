package com.arman.gml.sdk.session.defaults;

import com.arman.gml.sdk.common.Constants;
import com.arman.gml.sdk.model.req.ChatCompletionRequest;
import com.arman.gml.sdk.model.res.ChatCompletionResponse;
import com.arman.gml.sdk.model.res.R;
import com.arman.gml.sdk.session.ChatGmlSession;
import com.arman.gml.sdk.session.GmlConfiguration;
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
public class DefaultChatGMLSession implements ChatGmlSession {

    /**
     * OpenAi 接口
     */
    private final GmlConfiguration gmlConfiguration;

    @SneakyThrows
    @Override
    public EventSource completions(ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener) {
        ObjectMapper objectMapper = new ObjectMapper();

        // 请求地址
        String url = gmlConfiguration.getApiHost()
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
        EventSource.Factory factory = gmlConfiguration.createEventRequestFactory();
        return factory.newEventSource(request, eventSourceListener);
    }

    @SneakyThrows
    @Override
    public R<ChatCompletionResponse> completions(ChatCompletionRequest chatCompletionRequest) {
        ObjectMapper objectMapper = new ObjectMapper();

        // 请求地址
        String url = gmlConfiguration.getApiHost()
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

        try (Response response = gmlConfiguration.getOkHttpClient().newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new RuntimeException("fetch response error : " + response);
            }

            String bodyStr = response.body().string();
            return objectMapper.readValue(bodyStr, new TypeReference<R<ChatCompletionResponse>>() {
            });
        }

    }

}
