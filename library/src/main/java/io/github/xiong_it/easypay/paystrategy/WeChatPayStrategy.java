package io.github.xiong_it.easypay.paystrategy;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Author: michaelx
 * Create: 17-3-13.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:{@see <a href="http://blog.csdn.net/xiong_it">http://blog.csdn.net/xiong_it</a>} | {@see <a href="https://xiong-it.github.io">https://xiong-it.github.io</a>}
 * github:{@see <a href="https://github.com/xiong-it">https://github.com/xiong-it</a>}
 * <p>
 * Description:微信支付策略.
 */

public class WeChatPayStrategy extends BasePayStrategy {
    private LocalBroadcastManager mBroadcastManager;

    public static final String WECHAT_PAY_RESULT_ACTION = "com.tencent.mm.opensdk.WECHAT_PAY_RESULT_ACTION";
    public static final String WECHAT_PAY_RESULT_EXTRA = "com.tencent.mm.opensdk.WECHAT_PAY_RESULT_EXTRA";

    public WeChatPayStrategy(Activity aty) {
        super(aty);
    }

    @Override
    public void toPay() {

    }

    private void registPayResultBroadcast() {
        mBroadcastManager = LocalBroadcastManager.getInstance(super.mActivity);
        IntentFilter filter = new IntentFilter(WECHAT_PAY_RESULT_ACTION);
        mBroadcastManager.registerReceiver(mReceiver, filter);
    }

    private void unRegistPayResultBroadcast() {
        if (mBroadcastManager != null && mReceiver != null) {
            mBroadcastManager.unregisterReceiver(mReceiver);
        }
    }

    private static BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int result = intent.getIntExtra(WECHAT_PAY_RESULT_EXTRA, -7);
            switch (result) {
                case 0:
                    break;

                case -1:
                    break;

                case -2:
                    break;

                case -3:
                    break;

                case -4:
                    break;

                case -5:
                    break;

                case -6:
                    break;

                default:
                    break;
            }
        }
    };
}
