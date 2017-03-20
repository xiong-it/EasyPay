package io.github.xiong_it.easypay.pay.paystrategy;

import io.github.xiong_it.easypay.EasyPay;
import io.github.xiong_it.easypay.PayParams;

/**
 * Author: michaelx
 * Create: 17-3-13.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:{@see <a href="http://blog.csdn.net/xiong_it">http://blog.csdn.net/xiong_it</a>} | {@see <a href="https://xiong-it.github.io">https://xiong-it.github.io</a>}
 * github:{@see <a href="https://github.com/xiong-it">https://github.com/xiong-it</a>}
 * <p>
 * Description: 支付宝策略.
 */

public class ALiPayStrategy extends BasePayStrategy {
    public ALiPayStrategy(PayParams params, String prePayInfo, EasyPay.PayCallBack resultListener) {
        super(params, prePayInfo, resultListener);
    }

    @Override
    public void toPay() {

    }
}
