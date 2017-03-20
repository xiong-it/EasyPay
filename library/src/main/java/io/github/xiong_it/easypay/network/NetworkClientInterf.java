package io.github.xiong_it.easypay.network;

import io.github.xiong_it.easypay.PayParams;

/**
 * Author: michaelx
 * Create: 17-3-17.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:http://blog.csdn.net/xiong_it | https://xiong-it.github.io
 * github:https://github.com/xiong-it
 * <p>
 * Description: 网络访问接口.
 */

public interface NetworkClientInterf {
    interface CallBack<R> {
        void onSuccess(R result);
        void onFailure();
    }

    void get(PayParams payParams, CallBack c);

    void post(PayParams payParams, CallBack c);
}
