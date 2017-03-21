package io.github.xiong_it.easypay.pay.paystrategy;

/**
 * Author: michaelx
 * Create: 17-3-13.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:http://blog.csdn.net/xiong_it | https://xiong-it.github.io
 * github:https://github.com/xiong-it
 * <p>
 * Description: 支付策略上下文角色.
 */

public class PayContext {
    private PayStrategyInterf mStrategy;

    public PayContext(PayStrategyInterf strategy) {
        mStrategy = strategy;
    }

    public void pay() {
        if (mStrategy != null) {
            mStrategy.doPay();
        }
    }
}
