package io.github.xiong_it.easypay.paystrategy;

import android.app.Activity;

import io.github.xiong_it.easypay.callback.OnPayResultListener;

/**
 * Author: michaelx
 * Create: 17-3-13.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:{@see <a href="http://blog.csdn.net/xiong_it">http://blog.csdn.net/xiong_it</a>} | {@see <a href="https://xiong-it.github.io">https://xiong-it.github.io</a>}
 * github:{@see <a href="https://github.com/xiong-it">https://github.com/xiong-it</a>}
 * <p>
 * Description:支付策略类抽象类.
 */

public abstract class BasePayStrategy implements PayStrategyInterf{
    protected Activity mActivity;
    protected String mPrePayInfo;
    protected OnPayResultListener mOnPayResultListener;

    public BasePayStrategy(Activity aty, String prePayInfo, OnPayResultListener resultListener) {
        mActivity = aty;
        mPrePayInfo = prePayInfo;
        mOnPayResultListener = resultListener;
    }

    public abstract void toPay();
}