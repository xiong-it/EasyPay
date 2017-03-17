package io.github.xiong_it.easypay;

import android.app.Activity;
import android.support.annotation.NonNull;

import io.github.xiong_it.easypay.callback.OnPayInfoRequestListener;
import io.github.xiong_it.easypay.callback.OnPayResultListener;
import io.github.xiong_it.easypay.paystrategy.ALiPayStrategy;
import io.github.xiong_it.easypay.paystrategy.PayContext;
import io.github.xiong_it.easypay.paystrategy.WeChatPayStrategy;

/**
 * Created by michaelx on 2017/3/11.
 */

public final class EasyPay {
    private OnPayInfoRequestListener mOnPayInfoRequestListener;
    private OnPayResultListener mOnPayResultListener;
    private Activity mContext;
    private static String mWechatAppID;
    private PayWay mPayWay;
    private int mGoodsPrice;
    private String mGoodsTitle;
    private String mGoodsIntroduction;
    private HttpType mHttpType;
    private HttpClientType mHttpClientType = HttpClientType.Retrofit;
    private String mApiUrl;

    private EasyPay(Activity activity) {
        mContext = activity;
    }

    public static String getWeChatAppID() {
        return mWechatAppID;
    }

    private void setWechatAppID(String id) {
        mWechatAppID = id;
    }

    private void setPayWay(PayWay mPayWay) {
        this.mPayWay = mPayWay;
    }

    private void setGoodsPrice(int mGoodsPrice) {
        this.mGoodsPrice = mGoodsPrice;
    }

    private void setGoodsTitle(String mGoodsTitle) {
        this.mGoodsTitle = mGoodsTitle;
    }

    private void setGoodsIntroduction(String mGoodsIntroduction) {
        this.mGoodsIntroduction = mGoodsIntroduction;
    }

    private void setHttpType(HttpType mHttpType) {
        this.mHttpType = mHttpType;
    }

    private void setHttpClientType(HttpClientType mHttpClientType) {
        this.mHttpClientType = mHttpClientType;
    }

    private void setApiUrl(String mApiUrl) {
        this.mApiUrl = mApiUrl;
    }

    public void toPay(@NonNull OnPayResultListener onPayResultListener) {
        mOnPayResultListener = onPayResultListener;

        if (mPayWay == null) {
            throw new NullPointerException("请设置支付方式");
        }
        PayContext pc = null;
        switch (mPayWay) {
            case WechatPay:
                pc = new PayContext(new WeChatPayStrategy(mContext));
                break;

            case ALiPay:
                pc = new PayContext(new ALiPayStrategy(mContext));
                break;

            default:
                pc = new PayContext(new WeChatPayStrategy(mContext));
                break;
        }

        pc.pay();
    }

    public EasyPay requestPayInfo(OnPayInfoRequestListener onPayInfoRequestListener) {
        mOnPayInfoRequestListener = onPayInfoRequestListener;

        return this;
    }

    public static class Builder {
        Activity mContext;
        String wechatAppId;
        PayWay payWay;
        int goodsPrice;
        String goodsTitle;
        String goodsIntroduction;
        HttpType httpType;
        HttpClientType httpClientType;
        String apiUrl;

        public Builder(Activity aty) {
            mContext = aty;
        }

        public Builder wechatAppID(String appid) {
            wechatAppId = appid;
            return this;
        }

        public Builder payWay(PayWay way) {
            payWay = way;
            return this;
        }

        public Builder goodsPrice(int price) {
            goodsPrice = price;
            return this;
        }

        public Builder goodsTitle(String title) {
            goodsTitle = title;
            return this;
        }

        public Builder goodsIntroduction(String introduction) {
            goodsIntroduction = introduction;
            return this;
        }

        public Builder httpType(HttpType type) {
            httpType = type;
            return this;
        }

        public Builder httpClientType(HttpClientType clientType) {
            httpClientType = clientType;
            return this;
        }

        public Builder requestBaseUrl(String url) {
            apiUrl = url;
            return this;
        }

        public EasyPay build() {
            EasyPay payClient = new EasyPay(mContext);

            payClient.setWechatAppID(wechatAppId);
            payClient.setPayWay(payWay);
            payClient.setGoodsPrice(goodsPrice);
            payClient.setGoodsTitle(goodsTitle);
            payClient.setGoodsIntroduction(goodsIntroduction);
            payClient.setHttpType(httpType);
            payClient.setHttpClientType(httpClientType);
            payClient.setApiUrl(apiUrl);

            return payClient;
        }

    }

}
