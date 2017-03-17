package io.github.xiong_it.easypay;

import android.app.Activity;

import io.github.xiong_it.easypay.enums.NetworkClientType;
import io.github.xiong_it.easypay.enums.HttpType;
import io.github.xiong_it.easypay.enums.PayWay;

/**
 * Author: michaelx
 * Create: 17-3-17.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:{@see <a href="http://blog.csdn.net/xiong_it">http://blog.csdn.net/xiong_it</a>} | {@see <a href="https://xiong-it.github.io">https://xiong-it.github.io</a>}
 * github:{@see <a href="https://github.com/xiong-it">https://github.com/xiong-it</a>}
 * <p>
 * Description: 支付相关参数.
 */

public class PayParams {
    private Activity mActivity;
    private String mWechatAppID;
    private PayWay mPayWay;
    private int mGoodsPrice;
    private String mGoodsTitle;
    private String mGoodsIntroduction;
    private HttpType mHttpType;
    private NetworkClientType mNetworkClientType = NetworkClientType.HttpUrlConnetion;
    private String mApiUrl;

    public Activity getActivity() {
        return mActivity;
    }

    private void setActivity(Activity activity) {
        mActivity = activity;
    }

    public String getWeChatAppID() {
        return mWechatAppID;
    }

    private void setWechatAppID(String id) {
        mWechatAppID = id;
    }

    public PayWay getPayWay() {
        return mPayWay;
    }

    private void setPayWay(PayWay mPayWay) {
        this.mPayWay = mPayWay;
    }

    public int getGoodsPrice() {
        return mGoodsPrice;
    }

    private void setGoodsPrice(int mGoodsPrice) {
        this.mGoodsPrice = mGoodsPrice;
    }

    public String getGoodsTitle() {
        return mGoodsTitle;
    }

    private void setGoodsTitle(String mGoodsTitle) {
        this.mGoodsTitle = mGoodsTitle;
    }

    public String getGoodsIntroduction() {
        return mGoodsIntroduction;
    }

    private void setGoodsIntroduction(String mGoodsIntroduction) {
        this.mGoodsIntroduction = mGoodsIntroduction;
    }

    public HttpType getHttpType() {
        return mHttpType;
    }

    private void setHttpType(HttpType mHttpType) {
        this.mHttpType = mHttpType;
    }

    public NetworkClientType getNetworkClientType() {
        return mNetworkClientType;
    }

    private void setNetworkClientType(NetworkClientType mNetworkClientType) {
        this.mNetworkClientType = mNetworkClientType;
    }

    public String getApiUrl() {
        return mApiUrl;
    }

    private void setApiUrl(String mApiUrl) {
        this.mApiUrl = mApiUrl;
    }

    public static class Builder {
        Activity mActivity;
        String wechatAppId;
        PayWay payWay;
        int goodsPrice;
        String goodsTitle;
        String goodsIntroduction;
        HttpType httpType;
        NetworkClientType mNetworkClientType;
        String apiUrl;

        public Builder(Activity activity) {
            mActivity = activity;
        }

        public PayParams.Builder wechatAppID(String appid) {
            wechatAppId = appid;
            return this;
        }

        public PayParams.Builder payWay(PayWay way) {
            payWay = way;
            return this;
        }

        public PayParams.Builder goodsPrice(int price) {
            goodsPrice = price;
            return this;
        }

        public PayParams.Builder goodsTitle(String title) {
            goodsTitle = title;
            return this;
        }

        public PayParams.Builder goodsIntroduction(String introduction) {
            goodsIntroduction = introduction;
            return this;
        }

        public PayParams.Builder httpType(HttpType type) {
            httpType = type;
            return this;
        }

        public PayParams.Builder httpClientType(NetworkClientType clientType) {
            mNetworkClientType = clientType;
            return this;
        }

        public PayParams.Builder requestBaseUrl(String url) {
            apiUrl = url;
            return this;
        }

        public PayParams build() {
            PayParams params = new PayParams();

            params.setActivity(mActivity);
            params.setWechatAppID(wechatAppId);
            params.setPayWay(payWay);
            params.setGoodsPrice(goodsPrice);
            params.setGoodsTitle(goodsTitle);
            params.setGoodsIntroduction(goodsIntroduction);
            params.setHttpType(httpType);
            params.setNetworkClientType(mNetworkClientType);
            params.setApiUrl(apiUrl);

            return params;
        }

    }

}
