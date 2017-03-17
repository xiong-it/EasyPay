package io.github.xiong_it.easypay.Network;

import java.io.IOException;
import java.io.InputStream;
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
 * Blog:{@see <a href="http://blog.csdn.net/xiong_it">http://blog.csdn.net/xiong_it</a>} | {@see <a href="https://xiong-it.github.io">https://xiong-it.github.io</a>}
 * github:{@see <a href="https://github.com/xiong-it">https://github.com/xiong-it</a>}
 * <p>
 * Description: here is the description for this file.
 */

public class HttpUrlConnetClient implements NetworkClientInterf {

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
                    url = new URL(apiUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(20 * 1000);
                    connection.setReadTimeout(10 * 1000);

                    connection.addRequestProperty("payway", payParams.getPayWay().toString());
                    connection.addRequestProperty("price", String.valueOf(payParams.getGoodsPrice()));
                    connection.addRequestProperty("goods_name", payParams.getGoodsTitle());
                    connection.addRequestProperty("goods_introduction", payParams.getGoodsIntroduction());
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
                try {
                    url = new URL(apiUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(20 * 1000);
                    connection.setReadTimeout(10 * 1000);

                    connection.addRequestProperty("payway", payParams.getPayWay().toString());
                    connection.addRequestProperty("price", String.valueOf(payParams.getGoodsPrice()));
                    connection.addRequestProperty("goods_name", payParams.getGoodsTitle());
                    connection.addRequestProperty("goods_introduction", payParams.getGoodsIntroduction());
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
}
