package com.arman.gml.sdk.session;

import com.arman.gml.sdk.model.req.ChatCompletionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

/**
 * 会话服务接口
 *
 * @author Arman
 */
public interface ChatGmlSession {

    EventSource completions(ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener);

}
