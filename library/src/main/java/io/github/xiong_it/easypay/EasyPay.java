package io.github.xiong_it.easypay;

import android.support.annotation.NonNull;

import io.github.xiong_it.easypay.network.NetworkClientFactory;
import io.github.xiong_it.easypay.network.NetworkClientInterf;
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

    private static EasyPay sInstance;

    public static final int NETWORK_NOT_AVAILABLE_ERR = -1;
    public static final int REQUEST_TIME_OUT_ERR = -2;

    private EasyPay() {
    }

    public static EasyPay getInstance() {
        synchronized (EasyPay.class) {
            if (sInstance == null) {
                sInstance = new EasyPay();
            }
        }
        return sInstance;
    }

    public String getWeChatAppID() {
        return mPayParams.getWeChatAppID();
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
                pc = new PayContext(new WeChatPayStrategy(mPayParams.getActivity(), prePayInfo, mOnPayResultListener));
                break;

            case ALiPay:
                pc = new PayContext(new ALiPayStrategy(mPayParams.getActivity(), prePayInfo, mOnPayResultListener));
                break;

            default:
                pc = new PayContext(new WeChatPayStrategy(mPayParams.getActivity(), prePayInfo, mOnPayResultListener));
                break;
        }
        pc.pay();
    }

    public EasyPay requestPayInfo(@NonNull PayParams params, OnPayInfoRequestListener onPayInfoRequestListener) {
        mOnPayInfoRequestListener = onPayInfoRequestListener;
        mOnPayInfoRequestListener.onPayInfoRequetStart();

        mPayParams = params;

        HttpType type = params.getHttpType();
        NetworkClientInterf client = NetworkClientFactory.newClient(params.getNetworkClientType());
        NetworkClientInterf.CallBack callBack = new NetworkClientInterf.CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                mOnPayInfoRequestListener.onPayInfoRequstSuccess();
                doPay(result);
            }

            @Override
            public void onFailure() {
                mOnPayInfoRequestListener.onPayInfoRequestFailure();
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
