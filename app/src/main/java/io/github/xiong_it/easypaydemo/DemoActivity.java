package io.github.xiong_it.easypaydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.github.xiong_it.easypay.EasyPay;
import io.github.xiong_it.easypay.PayWay;
import io.github.xiong_it.easypay.callback.OnPayInfoRequestListener;
import io.github.xiong_it.easypay.callback.OnPayResultListener;

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        new EasyPay.Builder(this).build().requestPayInfo(new OnPayInfoRequestListener() {
            @Override
            public void onPayInfoRequetStart() {

            }

            @Override
            public void onPayInfoRequesting() {

            }

            @Override
            public void onPayInfoRequstSuccess() {

            }

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
