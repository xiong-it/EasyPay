package io.github.xiong_it.easypay;

import android.app.Activity;
import android.support.annotation.NonNull;

import io.github.xiong_it.easypay.callback.OnPayInfoRequestListener;
import io.github.xiong_it.easypay.callback.OnPayResultListener;

/**
 * Created by michaelx on 2017/3/11.
 */

public final class EasyPay {
    private OnPayInfoRequestListener mOnPayInfoRequestListener;
    private OnPayResultListener mOnPayResultListener;
    private Activity mContext;
    private String mWechatAppID;

    private EasyPay(Activity activity) {
        mContext = activity;
    }

    private void setWechatAppID(String id) {
        mWechatAppID = id;
    }

    public void toPay(@NonNull OnPayResultListener onPayResultListener) {
        mOnPayResultListener = onPayResultListener;
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

        public Builder requestType() {
            return this;
        }

        public Builder requestServerUrl(String url) {
            apiUrl = url;
            return this;
        }

        public EasyPay build() {
            EasyPay payClient = new EasyPay(mContext);
            payClient.setWechatAppID(wechatAppId);


            // TODO
            return payClient;
        }

    }

}
