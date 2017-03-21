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
                .wechatAppID("your_wechat_appid")// 仅当支付方式选择微信支付时需要此参数
                .payWay(PayWay.WechatPay)
                .goodsPrice(66600)// 单位为：分
                .goodsName("皮皮虾")
                .goodsIntroduction("此商品属性过于强大，难以调教，一般人切勿轻易购买，吼吼！")
                .httpType(HttpType.Get)
                .httpClientType(NetworkClientType.Retrofit)
                .requestBaseUrl("http://blog.csdn.net/")// 此处替换为为你的app服务器host主机地址
                .build();

        EasyPay.newInstance(params).requestPayInfo(new OnPayInfoRequestListener() {
            @Override
            public void onPayInfoRequetStart() {
                // TODO 在此处做一些loading操作,progressbar.show();
            }

            @Override
            public void onPayInfoRequstSuccess() {
                // TODO 可以将loading状态去掉了。请求预支付信息成功，开始跳转到客户端支付。
            }

            @Override
            public void onPayInfoRequestFailure() {
                // / TODO 可以将loading状态去掉了。获取预支付信息失败，会同时得到一个支付失败的回调。可以将loading状态去掉了。
            }
        }).toPay(new OnPayResultListener() {
            @Override
            public void onPaySuccess(PayWay payWay) {
                // 支付成功
            }

            @Override
            public void onPayCancel(PayWay payWay) {
                // 支付流程被用户中途取消
            }

            @Override
            public void onPayFailure(PayWay payWay, int errCode) {
                // 支付失败，errCode码详见来源博客或者github项目主页的README文档
            }
        });
    }
}
