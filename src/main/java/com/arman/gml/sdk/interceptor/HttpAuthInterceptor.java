package com.arman.gml.sdk.interceptor;

import com.arman.gml.sdk.common.Constants;
import com.arman.gml.sdk.session.GmlConfiguration;
import com.arman.gml.sdk.utils.BearerTokenUtils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * 接口拦截器
 *
 * @author Arman
 */
public class HttpAuthInterceptor implements Interceptor {

    /**
     * 智普Ai，配置类
     */
    private final GmlConfiguration gmlConfiguration;

    public HttpAuthInterceptor(GmlConfiguration gmlConfiguration) {
        this.gmlConfiguration = gmlConfiguration;
    }

    @Override
    public @NotNull Response intercept(Chain chain) throws IOException {

        // 增强请求
        String token = Constants.BEARER_HEAD_PREFIX + BearerTokenUtils.getToken(gmlConfiguration.getApiKey(), gmlConfiguration.getApiSecret());

        Request request = chain.request()
                .newBuilder()
                // 增加请求头
                .header("Authorization", token)
                .build();

        return chain.proceed(request);
    }

}
