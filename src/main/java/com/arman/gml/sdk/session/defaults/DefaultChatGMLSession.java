package com.arman.gml.sdk.session.defaults;

import com.arman.gml.sdk.common.Constants;
import com.arman.gml.sdk.model.req.ChatCompletionRequest;
import com.arman.gml.sdk.session.ChatGmlSession;
import com.arman.gml.sdk.session.GmlConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.internal.sse.RealEventSource;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

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

}
