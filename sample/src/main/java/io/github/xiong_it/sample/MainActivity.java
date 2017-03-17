package io.github.xiong_it.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.github.xiong_it.easypay.EasyPay;
import io.github.xiong_it.easypay.HttpClientType;
import io.github.xiong_it.easypay.HttpType;
import io.github.xiong_it.easypay.PayWay;
import io.github.xiong_it.easypay.callback.OnPayInfoRequestListener;
import io.github.xiong_it.easypay.callback.OnPayResultListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EasyPay pay = new EasyPay.Builder(this)
                .wechatAppID("")
                .payWay(PayWay.WechatPay)
                .goodsPrice(1000)
                .goodsTitle("")
                .goodsIntroduction("")
                .httpType(HttpType.Post)
                .httpClientType(HttpClientType.Retrofit)
                .requestBaseUrl("")
                .build();

        pay.requestPayInfo(new OnPayInfoRequestListener() {
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
