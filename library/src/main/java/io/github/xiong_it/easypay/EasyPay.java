package io.github.xiong_it.easypay;

import android.app.Activity;
import android.support.annotation.NonNull;

import io.github.xiong_it.easypay.callback.OnPayInfoRequestListener;
import io.github.xiong_it.easypay.callback.OnPayResultListener;
import io.github.xiong_it.easypay.enums.HttpType;
import io.github.xiong_it.easypay.enums.PayWay;
import io.github.xiong_it.easypay.network.NetworkClientFactory;
import io.github.xiong_it.easypay.network.NetworkClientInterf;
import io.github.xiong_it.easypay.pay.paystrategy.ALiPayStrategy;
import io.github.xiong_it.easypay.pay.paystrategy.PayContext;
import io.github.xiong_it.easypay.pay.paystrategy.WeChatPayStrategy;
import io.github.xiong_it.easypay.util.NetworkUtils;

/**
 * Author: michaelx
 * Create: 2017/3/11.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:http://blog.csdn.net/xiong_it | https://xiong-it.github.io
 * github:https://github.com/xiong-it
 * <p>
 * Description: 支付SDK封装工具类.
 */

public final class EasyPay {
    private OnPayInfoRequestListener mOnPayInfoRequestListener;
    private OnPayResultListener mOnPayResultListener;
    private PayParams mPayParams;

    private static EasyPay sInstance;

    // 通用结果码
    public static final int COMMON_PAY_OK = 0;
    public static final int COMMON_PAY_ERR = -1;
    public static final int COMMON_USER_CACELED_ERR = -2;
    public static final int COMMON_NETWORK_NOT_AVAILABLE_ERR = 1;
    public static final int COMMON_REQUEST_TIME_OUT_ERR = 2;

    // 微信结果码
    public static final int WECHAT_SENT_FAILED_ERR = -3;
    public static final int WECHAT_AUTH_DENIED_ERR = -4;
    public static final int WECHAT_UNSUPPORT_ERR = -5;
    public static final int WECHAT_BAN_ERR = -6;
    public static final int WECHAT_NOT_INSTALLED_ERR = -7;

    // 支付宝结果码
    public static final int ALI_PAY_WAIT_CONFIRM_ERR = 8000;
    public static final int ALI_PAY_NET_ERR = 6002;
    public static final int ALI_PAY_UNKNOW_ERR = 6004;
    public static final int ALI_PAY_OTHER_ERR = 6005;

    private EasyPay(PayParams params) {
        mPayParams = params;
    }

    public static EasyPay newInstance(PayParams params) {
        if (params != null) {
            sInstance = new EasyPay(params);
            return sInstance;
        }
        return sInstance;
    }

    public String getWeChatAppID() {
        return mPayParams.getWeChatAppID();
    }

    public void toPay(@NonNull OnPayResultListener onPayResultListener) {
        mOnPayResultListener = onPayResultListener;
        if (!NetworkUtils.isNetworkAvailable(mPayParams.getActivity().getApplicationContext())) {
            sendPayResult(COMMON_NETWORK_NOT_AVAILABLE_ERR);
        }
    }

    /**
     * 进行支付策略分发
     * @param prePayInfo
     */
    private void doPay(String prePayInfo) {
        PayContext pc = null;
        PayWay way = mPayParams.getPayWay();
        EasyPay.PayCallBack callBack = new PayCallBack() {
            @Override
            public void onPayCallBack(int code) {
                sendPayResult(code);
            }
        };

        switch (way) {
            case WechatPay:
                pc = new PayContext(new WeChatPayStrategy(mPayParams, prePayInfo, callBack));
                break;

            case ALiPay:
                pc = new PayContext(new ALiPayStrategy(mPayParams, prePayInfo, callBack));
                break;

            default:
                break;
        }
        pc.pay();
    }

    /**
     * 请求APP服务器获取预支付信息：微信，支付宝，银联都需要此步骤
     *
     * @param onPayInfoRequestListener
     * @return
     */
    public EasyPay requestPayInfo(OnPayInfoRequestListener onPayInfoRequestListener) {
        if (mPayParams.getPayWay() == null) {
            throw new NullPointerException("请设置支付方式");
        }

        mOnPayInfoRequestListener = onPayInfoRequestListener;
        mOnPayInfoRequestListener.onPayInfoRequetStart();

        NetworkClientInterf client = NetworkClientFactory.newClient(mPayParams.getNetworkClientType());
        NetworkClientInterf.CallBack callBack = new NetworkClientInterf.CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                mOnPayInfoRequestListener.onPayInfoRequstSuccess();
                doPay(result);
            }

            @Override
            public void onFailure() {
                mOnPayInfoRequestListener.onPayInfoRequestFailure();
                sendPayResult(COMMON_REQUEST_TIME_OUT_ERR);
            }
        };

        HttpType type = mPayParams.getHttpType();
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

    /**
     * 回调支付结果到请求界面
     *
     * @param code
     */
    private void sendPayResult(int code) {
        switch (code) {
            case COMMON_PAY_OK:
                mOnPayResultListener.onPaySuccess(mPayParams.getPayWay());
                break;

            case COMMON_USER_CACELED_ERR:
                mOnPayResultListener.onPayCancel(mPayParams.getPayWay());
                break;

            default:
                mOnPayResultListener.onPayFailure(mPayParams.getPayWay(), code);
                break;
        }
        releaseMomery();
    }

    private void releaseMomery() {
        Activity activity = mPayParams.getActivity();
        activity = null;
        mPayParams = null;
        sInstance = null;
    }

    public interface PayCallBack {
        void onPayCallBack(int code);
    }

}
