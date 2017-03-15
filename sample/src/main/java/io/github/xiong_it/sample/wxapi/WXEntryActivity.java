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

import static io.github.xiong_it.easypay.paystrategy.WeChatPayStrategy.WECHAT_PAY_RESULT_ACTION;
import static io.github.xiong_it.easypay.paystrategy.WeChatPayStrategy.WECHAT_PAY_RESULT_EXTRA;

/**
 * Created by michaelx on 17-3-15.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";

    private IWXAPI wxapi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IWXAPI wxapi = WXAPIFactory.createWXAPI(this, "");
        wxapi.registerApp("");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxapi.handleIntent(intent, this);
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

    private void sendPayResultBroadcast(int resultCode) {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        Intent payResult = new Intent();
        payResult.setAction(WECHAT_PAY_RESULT_ACTION);
        payResult.putExtra(WECHAT_PAY_RESULT_EXTRA, resultCode);
        broadcastManager.sendBroadcast(payResult);
    }
}
