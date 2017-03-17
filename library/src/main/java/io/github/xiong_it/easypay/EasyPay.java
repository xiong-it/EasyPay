package io.github.xiong_it.easypay;

import android.app.Activity;
import android.support.annotation.NonNull;

import io.github.xiong_it.easypay.Network.NetworkClientFactory;
import io.github.xiong_it.easypay.Network.NetworkClientInterf;
import io.github.xiong_it.easypay.callback.OnPayInfoRequestListener;
import io.github.xiong_it.easypay.callback.OnPayResultListener;
import io.github.xiong_it.easypay.enums.HttpType;
import io.github.xiong_it.easypay.enums.PayWay;
import io.github.xiong_it.easypay.paystrategy.ALiPayStrategy;
import io.github.xiong_it.easypay.paystrategy.PayContext;
import io.github.xiong_it.easypay.paystrategy.WeChatPayStrategy;

/**
 * Created by michaelx on 2017/3/11.
 */

public final class EasyPay {
    private OnPayInfoRequestListener mOnPayInfoRequestListener;
    private OnPayResultListener mOnPayResultListener;
    private PayParams mPayParams;
    private Activity mActivity;

    private static EasyPay sInstance;

    public static final int NETWORK_NOT_AVAILABLE_ERR = -1;
    public static final int REQUEST_TIME_OUT_ERR = -2;

    private EasyPay(Activity activity) {
        mActivity = activity;
    }

    public static EasyPay getInstance(Activity activity) {
        synchronized (sInstance) {
            if (sInstance == null) {
                sInstance = new EasyPay(activity);
            }
        }
        return sInstance;
    }

    public void toPay(@NonNull OnPayResultListener onPayResultListener) {
        mOnPayResultListener = onPayResultListener;
    }

    private void doPay(String prePayInfo) {
        PayWay way = mPayParams.getPayWay();
        if (mPayParams.getPayWay() == null) {
            throw new NullPointerException("请设置支付方式");
        }
        PayContext pc = null;
        switch (way) {
            case WechatPay:
                pc = new PayContext(new WeChatPayStrategy(mActivity, prePayInfo, mOnPayResultListener));
                break;

            case ALiPay:
                pc = new PayContext(new ALiPayStrategy(mActivity, prePayInfo, mOnPayResultListener));
                break;

            default:
                pc = new PayContext(new WeChatPayStrategy(mActivity, prePayInfo, mOnPayResultListener));
                break;
        }
        pc.pay();
    }

    public EasyPay requestPayInfo(@NonNull PayParams params, OnPayInfoRequestListener onPayInfoRequestListener) {
        mOnPayInfoRequestListener = onPayInfoRequestListener;

        mPayParams = params;

        HttpType type = params.getHttpType();
        NetworkClientInterf client = NetworkClientFactory.newClient(params.getNetworkClientType());
        NetworkClientInterf.CallBack callBack = new NetworkClientInterf.CallBack() {
            @Override
            public void onSuccess(String result) {
                doPay(result);
            }

            @Override
            public void onFailure() {
                sendPayResult(REQUEST_TIME_OUT_ERR);
            }
        };

        switch (type) {
            case Get:
                client.get(mPayParams, callBack);
                break;

            case Post:
            default:
                client.post(mPayParams, callBack);
                break;
        }
        return this;
    }

    private void sendPayResult(int code) {
        switch (code) {
            case 1:
                mOnPayResultListener.onPaySuccess(mPayParams.getPayWay());
                break;

            case 0:
                mOnPayResultListener.onPayCancel(mPayParams.getPayWay());
                break;

            default:
                mOnPayResultListener.onPayFailure(mPayParams.getPayWay(), code);
                break;
        }
    }

}
