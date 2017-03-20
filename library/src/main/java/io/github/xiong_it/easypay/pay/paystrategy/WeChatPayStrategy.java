package io.github.xiong_it.easypay.pay.paystrategy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.github.xiong_it.easypay.EasyPay;
import io.github.xiong_it.easypay.PayParams;
import io.github.xiong_it.easypay.pay.PrePayInfo;

/**
 * Author: michaelx
 * Create: 17-3-13.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:http://blog.csdn.net/xiong_it | https://xiong-it.github.io
 * github:https://github.com/xiong-it
 * <p>
 * Description:微信支付策略.
 */

public class WeChatPayStrategy extends BasePayStrategy {
    private LocalBroadcastManager mBroadcastManager;
    private Context mContext;

    public static final String WECHAT_PAY_RESULT_ACTION = "com.tencent.mm.opensdk.WECHAT_PAY_RESULT_ACTION";
    public static final String WECHAT_PAY_RESULT_EXTRA = "com.tencent.mm.opensdk.WECHAT_PAY_RESULT_EXTRA";

    public WeChatPayStrategy(PayParams params, String prePayInfo, EasyPay.PayCallBack resultListener) {
        super(params, prePayInfo, resultListener);
        mContext = params.getActivity();
    }

    @Override
    public void toPay() {
        IWXAPI wxapi = WXAPIFactory.createWXAPI(mContext.getApplicationContext(), mPayParams.getWeChatAppID(), true);
        if (!wxapi.isWXAppInstalled()) {
            super.mOnPayResultListener.onPayCallBack(EasyPay.WECHAT_NOT_INSTALLED_ERR);
            return;
        }

        if (!wxapi.isWXAppSupportAPI()) {
            super.mOnPayResultListener.onPayCallBack(EasyPay.WECHAT_UNSUPPORT_ERR);
            return;
        }
        wxapi.registerApp(mPayParams.getWeChatAppID());
        registPayResultBroadcast();

        // TODO 需要做正式解析，修改PrePayInfo.java类
        Gson gson = new Gson();
        PrePayInfo payInfo = gson.fromJson(mPrePayInfo, PrePayInfo.class);
        PayReq req = new PayReq();
        req.appId = payInfo.appid;
        req.partnerId = payInfo.partnerid;
        req.prepayId = payInfo.prepayid;
        req.packageValue = payInfo.packageValue;
        req.nonceStr = payInfo.noncestr;
        req.timeStamp = payInfo.timestamp;
        req.sign = payInfo.sign;

        // 发送支付请求：跳转到微信客户端
        wxapi.sendReq(req);
    }

    private void registPayResultBroadcast() {
        mBroadcastManager = LocalBroadcastManager.getInstance(mContext.getApplicationContext());
        IntentFilter filter = new IntentFilter(WECHAT_PAY_RESULT_ACTION);
        mBroadcastManager.registerReceiver(mReceiver, filter);
    }

    private void unRegistPayResultBroadcast() {
        if (mBroadcastManager != null && mReceiver != null) {
            mBroadcastManager.unregisterReceiver(mReceiver);
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int result = intent.getIntExtra(WECHAT_PAY_RESULT_EXTRA, -100);
            mOnPayResultListener.onPayCallBack(result);

            unRegistPayResultBroadcast();
        }
    };
}
