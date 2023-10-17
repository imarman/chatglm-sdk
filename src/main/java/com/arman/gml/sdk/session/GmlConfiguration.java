package com.arman.gml.sdk.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSources;

/**
 * 配置文件
 *
 * @author Arman
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GmlConfiguration {

    private String apiHost = "https://open.bigmodel.cn/api/paas/";

    private String apiKey;

    private String apiSecret;

    // 智普Ai https://open.bigmodel.cn/usercenter/apikeys - apiSecretKey = {apiKey}.{apiSecret}
    private String apiSecretKey;

    private OkHttpClient okHttpClient;

    private HttpLoggingInterceptor.Level logLevel = HttpLoggingInterceptor.Level.HEADERS;

    private HttpOption httpOption = new HttpOption(450, 450, 450);

    public void setApiSecretKey(String apiSecretKey) {
        this.apiSecretKey = apiSecretKey;
        String[] arrStr = apiSecretKey.split("\\.");
        if (arrStr.length != 2) {
            throw new RuntimeException("invalid apiSecretKey");
        }
        this.apiKey = arrStr[0];
        this.apiSecret = arrStr[1];
    }

    public EventSource.Factory createEventRequestFactory() {
        return EventSources.createFactory(okHttpClient);
    }

    public record HttpOption(long connectTimeout, long writeTimeout, long readTimeout) {
        public HttpOption {
            if (connectTimeout < 0 || writeTimeout < 0 || readTimeout < 0) {
                throw new IllegalArgumentException("invalid http option");
            }
        }
    }
}
