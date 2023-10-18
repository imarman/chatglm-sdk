package com.arman.gml.sdk.session;

import com.arman.gml.sdk.model.req.ChatCompletionRequest;
import com.arman.gml.sdk.model.res.ChatCompletionResponse;
import com.arman.gml.sdk.model.res.R;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

/**
 * 会话服务接口
 *
 * @author Arman
 */
public interface ChatGmlSession {

    EventSource completions(ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener);

    R<ChatCompletionResponse> completions(ChatCompletionRequest chatCompletionRequest);

}
