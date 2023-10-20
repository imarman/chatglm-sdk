package com.arman.glm.sdk.session;

import com.arman.glm.sdk.model.req.ChatCompletionRequest;
import com.arman.glm.sdk.model.res.ChatCompletionResponse;
import com.arman.glm.sdk.model.res.GlmResponse;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

/**
 * 会话服务接口
 *
 * @author Arman
 */
public interface ChatGlmSession {

    EventSource completions(ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener);

    GlmResponse<ChatCompletionResponse> completions(ChatCompletionRequest chatCompletionRequest);

}
