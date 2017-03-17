package io.github.xiong_it.easypay.Network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author: michaelx
 * Create: 17-3-17.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:{@see <a href="http://blog.csdn.net/xiong_it">http://blog.csdn.net/xiong_it</a>} | {@see <a href="https://xiong-it.github.io">https://xiong-it.github.io</a>}
 * github:{@see <a href="https://github.com/xiong-it">https://github.com/xiong-it</a>}
 * <p>
 * Description: retrofit请求示例类
 */

public interface PrePayInfoService {
    @GET("?")  // TODO 添加路径
    Call<ResponseBody> getPrePayInfo(@Query("pay_way")String payWay, @Query("price") String GoddsPrice, @Query("goods_name") String goodsName, @Query(("goods_introduction")) String goodsIntroduce);

    @POST("?") // TODO 添加路径
    Call<ResponseBody> postPrePayInfo(@Query("pay_way")String payWay, @Query("price") String GoddsPrice, @Query("goods_name") String goodsName, @Query(("goods_introduction")) String goodsIntroduce);
}