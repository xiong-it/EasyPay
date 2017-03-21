package io.github.xiong_it.easypay.network;

import java.io.IOException;

import io.github.xiong_it.easypay.PayParams;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author: michaelx
 * Create: 17-3-17.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:http://blog.csdn.net/xiong_it | https://xiong-it.github.io
 * github:https://github.com/xiong-it
 * <p>
 * Description: okhttp3网络请求简单封装.
 */

public class OkHttpClientImpl implements NetworkClientInterf {

    @Override
    public void get(PayParams payParams, final CallBack c) {
        String baseUrl = payParams.getApiUrl();
        StringBuffer sburl = new StringBuffer();
        // TODO 需要和服务器开发人员协商接口形式需要为：微信，支付宝，银联等 预支付信息走一个接口，通过pay_way或者其他字段进行区分。
        // 以下信息出商品详情介绍(goods_introduction)外，均为必须上传字段，key值由开发者和服务器人员协商自行定义。
        sburl.append(baseUrl)
                .append("?")
                .append("pay_way=").append(payParams.getPayWay())
                .append("&")
                .append("price=").append(payParams.getGoodsPrice())
                .append("&")
                .append("goods_name=").append(payParams.getGoodsName())
                .append("&")
                .append("goods_introduction=").append(payParams.getGoodsIntroduction());

        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(sburl.toString()).build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                c.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    c.onSuccess(response.body().string());
                } else {
                    c.onFailure();
                }
            }
        });
    }

    @Override
    public void post(PayParams payParams, final CallBack c) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        // TODO 需要和服务器开发人员协商接口形式需要为：微信，支付宝，银联等 预支付信息走一个接口，通过pay_way或者其他字段进行区分。
        // 以下信息出商品详情介绍(goods_introduction)外，均为必须上传字段，key值由开发者和服务器人员协商自行定义。
        RequestBody body = new FormBody.Builder()
                .add("pay_way", payParams.getPayWay().toString())
                .add("price", String.valueOf(payParams.getGoodsPrice()))
                .add("goods_name", payParams.getGoodsName())
                .add("goods_introduction", payParams.getGoodsIntroduction())
                .build();

        final Request request = new Request.Builder().url(payParams.getApiUrl()).post(body).build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                c.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    c.onSuccess(response.body().string());
                } else {
                    c.onFailure();
                }
            }
        });
    }
}
