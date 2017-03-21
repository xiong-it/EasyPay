package io.github.xiong_it.easypay.network;

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
 * Blog:http://blog.csdn.net/xiong_it | https://xiong-it.github.io
 * github:https://github.com/xiong-it
 * <p>
 * Description: retrofit请求示例类
 */

public interface PrePayInfoService {
    // TODO 需要和服务器开发人员协商接口形式需要为：微信，支付宝，银联等 预支付信息走一个接口，通过pay_way或者其他字段进行区分。
    // 以下信息出商品详情介绍(goods_introduction)外，均为必须上传字段，key值由开发者和服务器人员协商自行定义。
    @GET("?")  // TODO 添加实际接口路径
    Call<ResponseBody> getPrePayInfo(@Query("pay_way")String payWay, @Query("price") String GoddsPrice, @Query("goods_name") String goodsName, @Query(("goods_introduction")) String goodsIntroduce);

    @POST("?") // TODO 添加实际接口路径
    Call<ResponseBody> postPrePayInfo(@Query("pay_way")String payWay, @Query("price") String GoddsPrice, @Query("goods_name") String goodsName, @Query(("goods_introduction")) String goodsIntroduce);
}