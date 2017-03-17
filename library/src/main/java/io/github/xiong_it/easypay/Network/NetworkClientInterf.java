package io.github.xiong_it.easypay.Network;

import io.github.xiong_it.easypay.PayParams;

/**
 * Author: michaelx
 * Create: 17-3-17.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:{@see <a href="http://blog.csdn.net/xiong_it">http://blog.csdn.net/xiong_it</a>} | {@see <a href="https://xiong-it.github.io">https://xiong-it.github.io</a>}
 * github:{@see <a href="https://github.com/xiong-it">https://github.com/xiong-it</a>}
 * <p>
 * Description: 网络访问接口.
 */

public interface NetworkClientInterf {
    interface CallBack {
        void onSuccess(String result);
        void onFailure();
    }

    void get(PayParams payParams, CallBack c);

    void post(PayParams payParams, CallBack c);
}
