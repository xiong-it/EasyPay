package io.github.xiong_it.easypay.Network;

import io.github.xiong_it.easypay.enums.NetworkClientType;

/**
 * Author: michaelx
 * Create: 17-3-17.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:{@see <a href="http://blog.csdn.net/xiong_it">http://blog.csdn.net/xiong_it</a>} | {@see <a href="https://xiong-it.github.io">https://xiong-it.github.io</a>}
 * github:{@see <a href="https://github.com/xiong-it">https://github.com/xiong-it</a>}
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
