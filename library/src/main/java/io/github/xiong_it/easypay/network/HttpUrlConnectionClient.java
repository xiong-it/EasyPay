package io.github.xiong_it.easypay.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.github.xiong_it.easypay.PayParams;
import io.github.xiong_it.easypay.util.ThreadManager;

/**
 * Author: michaelx
 * Create: 17-3-17.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:http://blog.csdn.net/xiong_it | https://xiong-it.github.io
 * github:https://github.com/xiong-it
 * <p>
 * Description: here is the description for this file.
 */

public class HttpUrlConnectionClient implements NetworkClientInterf {

    public void get(final PayParams payParams, final CallBack callBack) {
        Runnable command = new Thread() {
            @Override
            public void run() {
                super.run();
                String apiUrl = payParams.getApiUrl();
                URL url = null;
                HttpURLConnection connection = null;
                InputStream inputStream = null;
                try {
                    StringBuffer sburl = new StringBuffer();
                    // TODO 需要和服务器开发人员协商接口形式需要为：微信，支付宝，银联等 预支付信息走一个接口，通过pay_way或者其他字段进行区分。
                    // 以下信息出商品详情介绍(goods_introduction)外，均为必须上传字段，key值由开发者和服务器人员协商自行定义。
                    sburl.append(apiUrl)
                            .append("?")
                            .append("pay_way=").append(payParams.getPayWay())
                            .append("&")
                            .append("price=").append(payParams.getGoodsPrice())
                            .append("&")
                            .append("goods_name=").append(payParams.getGoodsName())
                            .append("&")
                            .append("goods_introduction=").append(payParams.getGoodsIntroduction());
                    url = new URL(sburl.toString());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(20 * 1000);
                    connection.setReadTimeout(10 * 1000);

                    connection.connect();

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        inputStream = connection.getInputStream();
                        byte[] data = new byte[512];
                        int len = 0;
                        StringBuffer sb = new StringBuffer();
                        while ((len = inputStream.read(data)) > 0) {
                            sb.append(new String(data, 0, len));
                        }
                        callBack.onSuccess(sb.toString());
                    } else {
                        callBack.onFailure();
                    }
                } catch (Exception e) {
                    callBack.onFailure();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        ThreadManager.execute(command);
    }

    public void post(final PayParams payParams, final CallBack callBack) {
        Runnable command = new Thread() {
            @Override
            public void run() {
                super.run();
                String apiUrl = payParams.getApiUrl();
                URL url = null;
                HttpURLConnection connection = null;
                InputStream inputStream = null;
                OutputStream outputStream = null;
                // TODO 需要和服务器开发人员协商接口形式需要为：微信，支付宝，银联等 预支付信息走一个接口，通过pay_way或者其他字段进行区分。
                // 以下信息出商品详情介绍(goods_introduction)外，均为必须上传字段，key值由开发者和服务器人员协商自行定义。
                try {
                    url = new URL(apiUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(20 * 1000);
                    connection.setReadTimeout(10 * 1000);
                    connection.setDoOutput(true);

                    outputStream = connection.getOutputStream();
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("pay_way=").append(payParams.getPayWay())
                            .append("&")
                            .append("price=").append(payParams.getGoodsPrice())
                            .append("&")
                            .append("goods_name=").append(payParams.getGoodsName())
                            .append("&")
                            .append("goods_introduction=").append(payParams.getGoodsIntroduction());
                    String params = stringBuffer.toString();
                    outputStream.write(params.getBytes());
                    outputStream.flush();

                    connection.connect();

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        inputStream = connection.getInputStream();
                        byte[] data = new byte[512];
                        int len = 0;
                        StringBuffer sb = new StringBuffer();
                        while ((len = inputStream.read(data)) > 0) {
                            sb.append(new String(data, 0, len));
                        }
                        callBack.onSuccess(sb.toString());
                    } else {
                        callBack.onFailure();
                    }
                } catch (Exception e) {
                    callBack.onFailure();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        ThreadManager.execute(command);
    }
}
