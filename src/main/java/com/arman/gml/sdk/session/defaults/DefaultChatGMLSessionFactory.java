package com.arman.gml.sdk.session.defaults;


import com.arman.gml.sdk.interceptor.HttpAuthInterceptor;
import com.arman.gml.sdk.session.ChatGmlSession;
import com.arman.gml.sdk.session.ChatGmlSessionFactory;
import com.arman.gml.sdk.session.GmlConfiguration;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

/**
 * 会话工厂
 *
 * @author Arman
 */
@AllArgsConstructor
public class DefaultChatGMLSessionFactory implements ChatGmlSessionFactory {

    private final GmlConfiguration configuration;


    @Override
    public ChatGmlSession openSession() {
        // 1. 日志配置
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(configuration.getLogLevel());

        // 2. 开启 Http 客户端
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new HttpAuthInterceptor(configuration))
                .connectTimeout(configuration.getHttpOption().connectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(configuration.getHttpOption().writeTimeout(), TimeUnit.SECONDS)
                .readTimeout(configuration.getHttpOption().readTimeout(), TimeUnit.SECONDS)
                .build();

        configuration.setOkHttpClient(okHttpClient);

        return new DefaultChatGMLSession(configuration);
    }

}
