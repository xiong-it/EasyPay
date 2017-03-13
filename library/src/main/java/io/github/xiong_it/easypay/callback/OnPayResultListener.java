package io.github.xiong_it.easypay.callback;

import io.github.xiong_it.easypay.PayWay;

/**
 * Created by michaelx on 2017/3/11.
 */

public interface OnPayResultListener {
    void onPayCancel(PayWay payWay);

    void onPaySuccess(PayWay payWay);

    void onPayFailure(PayWay payWay, int errCode);
}
