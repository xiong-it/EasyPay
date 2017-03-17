package io.github.xiong_it.easypay.Network;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import io.github.xiong_it.easypay.PayParams;

/**
 * Author: michaelx
 * Create: 17-3-17.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:{@see <a href="http://blog.csdn.net/xiong_it">http://blog.csdn.net/xiong_it</a>} | {@see <a href="https://xiong-it.github.io">https://xiong-it.github.io</a>}
 * github:{@see <a href="https://github.com/xiong-it">https://github.com/xiong-it</a>}
 * <p>
 * Description: volley网络请求封装.
 */

public class VolleyClient implements NetworkClientInterf {
    @Override
    public void get(PayParams payParams, final CallBack c) {
        RequestQueue queue = Volley.newRequestQueue(payParams.getActivity());
        String baseUrl = payParams.getApiUrl();
        StringBuffer sburl = new StringBuffer();
        sburl.append(baseUrl)
                .append("?")
                .append("pay_way=").append(payParams.getPayWay())
                .append("&")
                .append("price=").append(payParams.getGoodsPrice())
                .append("&")
                .append("goods_name").append(payParams.getGoodsTitle())
                .append("&")
                .append("goods_introduction").append(payParams.getGoodsIntroduction());
        StringRequest request = new StringRequest(Request.Method.GET, sburl.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        c.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                c.onFailure();
            }
        });
        queue.add(request);
    }

    @Override
    public void post(final PayParams payParams, final CallBack c) {
        RequestQueue queue = Volley.newRequestQueue(payParams.getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, payParams.getApiUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        c.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                c.onFailure();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pay_way", payParams.getPayWay().toString());
                params.put("price", String.valueOf(payParams.getGoodsPrice()));
                params.put("goods_name", payParams.getGoodsTitle());
                params.put("goods_introduction", payParams.getGoodsIntroduction());

                return params;
            }
        };

        queue.add(request);
    }
}
