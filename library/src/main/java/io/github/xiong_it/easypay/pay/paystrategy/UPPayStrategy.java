package io.github.xiong_it.easypay.pay.paystrategy;

import com.unionpay.UPPayAssistEx;

import io.github.xiong_it.easypay.EasyPay;
import io.github.xiong_it.easypay.PayParams;

/**
 * Created by michaelx on 17-7-12.
 */

public class UPPayStrategy extends BasePayStrategy {
    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 ;"01" - 连接银联测试环境
     *****************************************************************/
    private final String OFFICIAL_MODE = "00";
    private final String DEV_MODE = "01";
    private final String sMODE = OFFICIAL_MODE;
    // TODO 请开发同学根据实际情况修改上述环境mode

    private static final int PLUGIN_VALID = 0;// 有效的响应码
    private static final int PLUGIN_NOT_INSTALLED = -1;// 银联支付插件未安装
    private static final int PLUGIN_NEED_UPGRADE = 2;// 银联支付环境需要升级

    public UPPayStrategy(PayParams params, String prePayInfo, EasyPay.PayCallBack resultListener) {
        super(params, prePayInfo, resultListener);
    }

    @Override
    public void doPay() {
        String tn = getTn(mPrePayInfo);
        int ret = UPPayAssistEx.startPay(mPayParams.getActivity(), null, null, tn, sMODE);
        if (ret == PLUGIN_VALID) {
            mOnPayResultListener.onPayCallBack(EasyPay.COMMON_PAY_OK);
        } else if (ret == PLUGIN_NOT_INSTALLED) {
            mOnPayResultListener.onPayCallBack(EasyPay.UPPAY_PLUGIN_NOT_INSTALLED);
        } else if (ret == PLUGIN_NEED_UPGRADE) {
            mOnPayResultListener.onPayCallBack(EasyPay.UPPAy_PLUGIN_NEED_UPGRADE);
        } else {
            mOnPayResultListener.onPayCallBack(EasyPay.COMMON_PAY_ERR);
        }
    }

    private String getTn(String payinfo) {
        // TODO 请根据自身需求解析mPrePayinfo得到预支付订单号tn
        return "";
    }

}
