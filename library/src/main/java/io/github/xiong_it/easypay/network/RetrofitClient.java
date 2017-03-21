package io.github.xiong_it.easypay.network;

import io.github.xiong_it.easypay.PayParams;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Author: michaelx
 * Create: 17-3-17.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:http://blog.csdn.net/xiong_it | https://xiong-it.github.io
 * github:https://github.com/xiong-it
 * <p>
 * Description: retrofit网络请求简单封装.
 */

public class RetrofitClient implements NetworkClientInterf {
    @Override
    public void get(PayParams payParams, final CallBack c) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(payParams.getApiUrl())
                .build();
        PrePayInfoService service = retrofit.create(PrePayInfoService.class);
        Call<ResponseBody> call = service.getPrePayInfo(payParams.getPayWay().toString(), String.valueOf(payParams.getGoodsPrice()), payParams.getGoodsName(), payParams.getGoodsIntroduction());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    c.onSuccess(response.body().toString());
                } else {
                    c.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                c.onFailure();
            }
        });
    }

    @Override
    public void post(PayParams payParams, final CallBack c) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(payParams.getApiUrl())
                .build();
        PrePayInfoService service = retrofit.create(PrePayInfoService.class);
        Call<ResponseBody> call = service.postPrePayInfo(payParams.getPayWay().toString(), String.valueOf(payParams.getGoodsPrice()), payParams.getGoodsName(), payParams.getGoodsIntroduction());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    c.onSuccess(response.body().toString());
                } else {
                    c.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                c.onFailure();
            }
        });
    }
}
