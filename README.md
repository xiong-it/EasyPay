 EasyPay是什么
==========
EasyPay旨在帮助Android开发者快速集成接入移动支付SDK，其中包括主流的**微信APP支付**，**支付宝APP支付**，银联支付（开发中）。  欢迎右上角star/fork，感谢!  


----------


为什么要用EasyPay
============

EasyPay和微信支付等移动支付SDK的区别
------------------------
EasyPay是一个开源的聚合支付可定制化框架，目前已集成微信APP支付，支付宝APP支付SDK。银联支付（开发中）。  

Android开发者只需要简单调用EasyPay的几行代码，即可调起支付客户端，完成支付流程，得到支付结果。  

EasyPay宗旨：简单，易用，可扩展。  

EasyPay和其他第三方聚合支付的区别
--------------------

第三方聚合支付，如知名的Ping++，需要同时接入其Server端SDK和Client端SDK，使接入企业面临风险：
> 1、信息泄露风险  
2、支付集成服务商提供服务跟不上商户业务发展需要的风险  
3、支付集成服务商系统稳定性、安全性的风险  
4、资金安全风险  
---知乎：[《使用第三方支付集成有何风险，例如 Beecloud 或者 Ping++？》][4]
  
此外，天下没有免费的午餐，第三方聚合支付平台一般需要收取**5%~15%**左右的手续费等各种服务费用，使得接入企业收益受损。  

而使用开源的[EasyPay][5]，代码透明，与Server端无关，Android开发者只需要根据自己需求对EasyPay进行个性化定制，即可打造一个支付平台齐全的无风险支付框架。但是客观的讲，这同时也是EasyPay的短板，它只简化了APP端开发者的调用工作，Server端工作人员仍需要按照移动支付第三方平台的SDK文档进行开发。  

如果觉得[EasyPay][6]对你有帮助，你付出的仅仅是一个点赞，或者一个star或者fork，如果不满意，请帮忙提issue指出，而不是5%-15%左右的手续费等各种服务费用。  

通过阅读EasyPay源码，你可以知道移动支付的流程是怎样的：
    
    APP->APP服务器->支付平台后台服务器->APP服务器->APP->支付客户端->APP
  
通过扩展EasyPay，你可以较快的搭建一个私有的功能完善的支付框架。


怎么用EasyPay
==========
用户场景：
> APP用户选择一个价格为666元的商品："皮皮虾"，商品描述："此商品属性过于强大，难以调教，一般人切勿轻易购买，吼吼！"，然后用户进入收款台，选择了微信支付。  
  
好勒，皮皮虾，咱们走！此处省略：皮皮虾，咱们走.jpg 
```
 PayParams params = new PayParams.Builder(this)
                .wechatAppID("your_wechat_appid")// 仅当支付方式选择微信支付时需要此参数
                .payWay(PayWay.WechatPay)
                .goodsPrice(66600)// 单位为：分
                .goodsName("皮皮虾")
                .goodsIntroduction("此商品属性过于强大，难以调教，一般人切勿轻易购买，吼吼！")
                .httpType(HttpType.Get)// 使用get请求
                .httpClientType(NetworkClientType.Retrofit)
                .requestBaseUrl("https://api.github.com/")// 此处替换为为你的app服务器host主机地址
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
                // / TODO 可以将loading状态去掉了。获取预支付信息失败，会同时得到一个支付失败的回调。
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
```
开发者调用步骤：
 1. 通过建造者模式创建支付参数PayParams实例并传入EasyPay的创建方法中
 2. 传入支付结果回调接口实例得到支付结果回调
 
假如你的app中每个商品有id,请求服务器时可以用商品id代替价格，让服务器自己去查询价格，防止客户端中的商品价格被恶意修改。


----------


开发者需要做什么
=========
上一节是开发者在Activity/Fragment之类的View层调用代码，除此之外，开发者还需要做一些少量的额外的工作。  

**需要导入EasyPay/library源码依赖并修改app客户端相关文件**

 1. 下载EasyPay源码到本地    
 2. 在Android Studio中打开你的app项目  
 3. Android Studio左上角File->New->Import Module->... 选择library目录导入，app会自动依赖library这个module  
 4. 复制`EasyPay/sample`下`wxapi`包到你的包路径下，假如你的包名:`com.app.payclient`，那么wxapi包应该放在payclient包下面  
 5. 按照`EasyPay/sample`的`AndroidMenifest.xml`文件修改你的清单文件  
 6. 按照`EasyPay/sample`的`proguard-rules.pro`修改你的混淆文件   
   
**需要修改服务器请求路径和请求字段和返回的json解析**

由于笔者并不知道你的服务器地址和请求路径及字段和返回json格式，所以你只需要动动小手**修改`EasyPay/library`中网络请求和解析**部分代码即可.   
  
假如你的支付api接口文档如下:  
host：http://api.yourhost.com/  
路径：pay/   
请求方式为：Http，get  
请求需要的参数字段:  
  
字段|类型|意义
-|-|-
pay_way|int|支付方式：0-微信，1-支付宝，2-银联 等
price|int|商品价格，单位：人民币-分
goods_name|String(128)|商品名称，将在支付客户端展示
goods_introduction|String(512)|商品描述，微信支付可填，支付宝必填  
  
假设你想使用的网络框架是**Retrofit2**。那么`network/NetwrokClientInterf`的实现类RetrofitClient需要做如下修改：
由于Retrofit请求一般需要借助一个xxService类，那么实际修改的是xxService类，以EasyPay源码中的`network/PrePayInfoService`为例，它需要修改成如下：
```java
public interface PrePayInfoService {
    @GET("pay/")
    Call<ResponseBody> getPrePayInfo(@Query("pay_way")String payWay, @Query("price") String GoddsPrice, @Query("goods_name") String goodsName, @Query(("goods_introduction")) String goodsIntroduce);

   /*@POST("pay/")
    Call<ResponseBody> postPrePayInfo(@Query("pay_way")String payWay, @Query("price") String GoddsPrice, @Query("goods_name") String goodsName, @Query(("goods_introduction")) String goodsIntroduce);*/
}
// （如需更多字段请自行添加参数）
```
**当网络连接使用其他框架时，需要在NetworkClientInterf对应的实现类中修改路径及请求参数字段。**

假如当前用户使用了**微信支付**，当服务器返回的数据格式如下时:  

字段|类型|意义
-|-|-
errCode|int|错误码，0表示数据正确返回
errString|String|错误提示
data|String|返给客户端的json数据

假如json的格式如下:  

字段|类型|意义
-|-|-
appid|String|微信appid
partnerid|String|商户号
prepayid|String|预支付交易id
package|String|固定值：Sign=WXPay
noncestr|String|随机字符串
timestamp|String|时间戳
sign|String|签名
  
  
以你们server端人员给出的实际json字段来修改`pay/PrePayInfo`。  

**当为其他支付方式时，也需要在对应的PayStragetyInterf支付实现策略类中修改解析。**
  
library源码中需要修改的地方都打上了`TODO`标签，导入Android Studio后，如下图方式查看：
![TODO](http://img.blog.csdn.net/20170322143832260?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvWGlvbmdfSVQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


----------


开发者能做什么
=======
EasyPay目前实现了微信，支付宝app支付，如果你觉得支付逻辑代码不OK？完全可以通过实现PayStragetyInterf来完全重写一个自己的微信，支付宝支付策略。  
  
EasyPay支持的平台（微信，支付宝，银联）不在你的需求范围内？可以通过实现PayStragetyInterf来扩展一种支付方式。  
  
EasyPay支持的网络框架（HttpUrlConnection，OkHttp3（前两者严格意义上不属于框架）,Volley,Retrofit2）用的不顺手？那就自己撸一个NetworkClientInterf接口的实现类来实现自己的网络请求客户端。  
  
其他，还是不够满足你的需求，欢迎提出issue，或者加入一起开发，完善该repo，打造一个更加优秀的EasyPay。  


----------


附录
==

移动支付开发博文
---------
微信支付：[Android App支付系列（一）：微信支付接入详细指南][7]  
支付宝：[Android App支付系列（二）：支付宝SDK接入详细指南][8]  


移动支付的流程
--------

 1. APP将商品信息post给APP服务器
 2. APP服务器携带商品信息和一些其他信息请求支付平台服务器，获取预支付订单信息
 3. APP服务器得到预支付订单信息并返给APP
 4. APP解析预支付订单信息
 5. APP利用解析后的预支付信息调起支付客户端（微信，支付宝，等）
 6. 支付客户端将支付结果返给APP
 7. APP向用户展示支付结果


EasyPay的回调errCode错误码列表
----------------------

**通用errCode**|**意义**
-|-
1|当前网络无连接（尚未进入支付阶段）
2|请求APP服务器超时（尚未进入支付阶段）
-1|支付失败-原因未知，需要开发者手动排查
**微信errCode**|一般不会碰到
-3|微信接收支付请求失败
-4|微信支付认证失败，拒绝支付交易
-5|微信版本低，不支持交易
-6|微信拒绝了支付交易
-7|未安装微信客户端，交易失败
**支付宝errCode**|一般不会碰到
8000|支付结果待确认,生成了交易订单，但是未支付。
6002|网络差导致支付失败
6004|支付结果未知
6005|支付失败，原因未知

  [1]: https://github.com/xiong-it/EasyPay
  [2]: http://blog.csdn.net/xiong_it
  [3]: https://github.com/xiong-it/EasyPay
  [4]: https://www.zhihu.com/question/31237376
  [5]: https://github.com/xiong-it/EasyPay
  [6]: https://github.com/xiong-it/EasyPay
  [7]: http://blog.csdn.net/xiong_it/article/details/51685033
  [8]: http://blog.csdn.net/xiong_it/article/details/51819559




