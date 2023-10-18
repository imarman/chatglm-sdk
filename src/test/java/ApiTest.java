import com.arman.gml.sdk.model.EventType;
import com.arman.gml.sdk.model.Model;
import com.arman.gml.sdk.model.Prompt;
import com.arman.gml.sdk.model.Role;
import com.arman.gml.sdk.model.req.ChatCompletionRequest;
import com.arman.gml.sdk.model.res.ChatCompletionResponse;
import com.arman.gml.sdk.model.res.ChatCompletionSseResponse;
import com.arman.gml.sdk.model.res.R;
import com.arman.gml.sdk.session.ChatGmlSession;
import com.arman.gml.sdk.session.ChatGmlSessionFactory;
import com.arman.gml.sdk.session.GmlConfiguration;
import com.arman.gml.sdk.session.defaults.DefaultChatGMLSessionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author Arman
 * @date 2023/10/17
 */
@Slf4j
public class ApiTest {

    private ChatGmlSession chatGmlSession;

    @Before
    public void test_OpenAiSessionFactory() {
        // 1. 配置文件
        GmlConfiguration gmlConfiguration = new GmlConfiguration();
        gmlConfiguration.setApiHost("https://open.bigmodel.cn/");
        gmlConfiguration.setApiSecretKey("xxxxx.yyyyyyy");
        // 2. 会话工厂
        ChatGmlSessionFactory factory = new DefaultChatGMLSessionFactory(gmlConfiguration);
        // 3. 开启会话
        this.chatGmlSession = factory.openSession();
    }

    /**
     * 流式对话
     */
    @Test
    public void test_completions() throws InterruptedException {
        // 入参；模型、请求信息
        ChatCompletionRequest request = new ChatCompletionRequest();
        request.setModel(Model.CHAT_GLM_LITE); // chatGLM_6b_SSE、chatglm_lite、chatglm_lite_32k、chatglm_std、chatglm_pro

        Prompt prompt = new Prompt(Role.USER.getCode(), "用java写一个冒泡排序");

        request.setPrompt(Lists.newArrayList(prompt));

        CountDownLatch countDownLatch = new CountDownLatch(1);

        // 请求
        chatGmlSession.completions(request, new EventSourceListener() {
            @SneakyThrows
            @Override
            public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
                // ChatCompletionResponse response = JSON.parseObject(data, ChatCompletionResponse.class);
                // log.info("测试结果 id：{}", id);
                ObjectMapper objectMapper = new ObjectMapper();
                ChatCompletionSseResponse response = objectMapper.readValue(data, ChatCompletionSseResponse.class);
                // log.info("测试结果 onEvent：{}", response.getData());
                System.out.print(response.getData());
                // type 消息类型，add 增量，finish 结束，error 错误，interrupted 中断
                if (EventType.FINISH.getCode().equals(type)) {
                    ChatCompletionSseResponse.Meta meta = objectMapper.readValue(response.getMeta(), ChatCompletionSseResponse.Meta.class);
                    // ChatCompletionResponse.Meta meta = response.getMeta();
                    log.info("[输出结束] Tokens {}", objectMapper.writeValueAsString(meta));
                }
            }

            @Override
            public void onClosed(@NotNull EventSource eventSource) {
                log.info("对话完成");
                countDownLatch.countDown();
            }

        });

        // 等待
        countDownLatch.await();
    }

    /**
     * 同步请求测试
     */
    @Test
    public void test_completions2() {
        ChatCompletionRequest request = new ChatCompletionRequest();
        request.setModel(Model.CHAT_GLM_PRO);

        Prompt prompt = new Prompt(Role.USER.getCode(), "用java写一个冒泡排序");

        request.setPrompt(Lists.newArrayList(prompt));

        // 请求
        R<ChatCompletionResponse> response = chatGmlSession.completions(request);

        response.getData().getChoices().stream().map(Prompt::content).forEach(System.out::println);
    }

}

