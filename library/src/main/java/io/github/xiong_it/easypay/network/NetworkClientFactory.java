package io.github.xiong_it.easypay.network;

import io.github.xiong_it.easypay.enums.NetworkClientType;

/**
 * Author: michaelx
 * Create: 17-3-17.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:http://blog.csdn.net/xiong_it | https://xiong-it.github.io
 * github:https://github.com/xiong-it
 * <p>
 * Description: 网络访问接口简单工厂.
 */

public class NetworkClientFactory {

    public static NetworkClientInterf newClient(NetworkClientType networkClientType) {
        switch (networkClientType) {
            case HttpUrlConnetion:
                return new HttpUrlConnectionClient();

            case Volley:
                return new VolleyClient();

            case Retrofit:
                return new RetrofitClient();

            case OkHttp:
                return new OkHttpClientImpl();

            default:
                return new HttpUrlConnectionClient();
        }
    }
}
