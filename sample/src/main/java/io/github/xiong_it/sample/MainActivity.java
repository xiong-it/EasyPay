package io.github.xiong_it.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.xiong_it.easypay.EasyPay;
import io.github.xiong_it.easypay.PayParams;
import io.github.xiong_it.easypay.callback.OnPayInfoRequestListener;
import io.github.xiong_it.easypay.callback.OnPayResultListener;
import io.github.xiong_it.easypay.enums.NetworkClientType;
import io.github.xiong_it.easypay.enums.HttpType;
import io.github.xiong_it.easypay.enums.PayWay;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        PayParams params = new PayParams.Builder(this)
                .wechatAppID("")
                .payWay(PayWay.WechatPay)
                .goodsPrice(1000)
                .goodsTitle("")
                .goodsIntroduction("")
                .httpType(HttpType.Post)
                .httpClientType(NetworkClientType.Retrofit)
                .requestBaseUrl("")
                .build();

        EasyPay.getInstance().requestPayInfo(params, new OnPayInfoRequestListener() {
            @Override
            public void onPayInfoRequetStart() {

            }

            @Override
            public void onPayInfoRequesting() {

            }

            @Override
            public void onPayInfoRequstSuccess() {

            }

            @Override
            public void onPayInfoRequestFailure() {

            }
        }).toPay(new OnPayResultListener() {
            @Override
            public void onPayCancel(PayWay payWay) {

            }

            @Override
            public void onPaySuccess(PayWay payWay) {

            }

            @Override
            public void onPayFailure(PayWay payWay, int errCode) {

            }
        });
    }
}
