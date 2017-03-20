package io.github.xiong_it.sample.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.github.xiong_it.easypay.EasyPay;

import static io.github.xiong_it.easypay.pay.paystrategy.WeChatPayStrategy.WECHAT_PAY_RESULT_ACTION;
import static io.github.xiong_it.easypay.pay.paystrategy.WeChatPayStrategy.WECHAT_PAY_RESULT_EXTRA;

/**
 * Created by michaelx on 17-3-15.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI mWxApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWxApi = WXAPIFactory.createWXAPI(this, EasyPay.getInstance().getWeChatAppID());
        mWxApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWxApi.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {
        Log.d(TAG, "请求发出的回调");
    }

    @Override
    public void onResp(BaseResp baseResp) {
        int errCode = baseResp.errCode;
        sendPayResultBroadcast(errCode);
    }

    /**
     * 发送微信支付结果的本地广播
     * 本地广播比全局广播效率高，更安全
     * <p>
     * 接收者请参考：
     * http://www.cnblogs.com/trinea/archive/2012/11/09/2763182.html
     *
     * @param resultCode
     */
    private void sendPayResultBroadcast(int resultCode) {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        Intent payResult = new Intent();
        payResult.setAction(WECHAT_PAY_RESULT_ACTION);
        payResult.putExtra(WECHAT_PAY_RESULT_EXTRA, resultCode);
        broadcastManager.sendBroadcast(payResult);
        finish();
    }
}
