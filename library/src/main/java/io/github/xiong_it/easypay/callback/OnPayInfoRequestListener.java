package io.github.xiong_it.easypay.callback;

/**
 * Created by michaelx on 2017/3/11.
 */

public interface OnPayInfoRequestListener {
    void onPayInfoRequetStart();

    void onPayInfoRequesting();

    void onPayInfoRequstSuccess();

    void onPayInfoRequestFailure();
}
