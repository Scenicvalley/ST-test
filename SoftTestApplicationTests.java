package com.example.softtest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.softtest.Test.Junit5Test;
import io.micrometer.common.util.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URLEncoder;
import java.net.http.HttpClient;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;


@SpringBootTest
class SoftTestApplicationTests {

    @Autowired
    Junit5Test junit5Test;

    @BeforeAll
    static void init() {
        System.out.println("init");
    }

    //所有的测试方法前都会执行一遍
//    @BeforeEach
//    void each() {
//        System.out.println("each");
//    }

    @Test
    void contextLoads() {
        Assertions.assertEquals(3, junit5Test.add(1, 2));
    }


    //编写一个百度定位API的测试样例通过输入地址获取经纬度
    //百度定位API的请求地址为：http://api.map.baidu.com/geocoding/v3/?address=地址&output=json&ak=你的ak
    @Test
    void testBaiduApi() {

    }

    /**
     * http://lbsyun.baidu.com/apiconsole/key
     * <百度开发者>用户申请注册的key，自v2开始参数修改为“ak”，之前版本参数为“key” 申请ak
     */
    final static String AK = "Tw8W1DP6LQvWTWo1bkaZzuR3kpshTlw6";


    /**
     * 地理编码 URL
     */
    final static String ADDRESS_TO_LONGITUDEA_URL = "http://api.map.baidu.com/geocoding/v3/?output=json&location=showLocation";

    /**
     * 地理编码
     *         详细的位置信息
     * @return
     */
    @Test
    public void AddressTolongitudea() {
        String AK = "Tw8W1DP6LQvWTWo1bkaZzuR3kpshTlw6";
        String ADDRESS_TO_LONGITUDEA_URL = "http://api.map.baidu.com/geocoding/v3/?output=json&location=showLocation";
        String address = "江苏省南京市江宁区南京航空航天大学将军路校区";
        String url =ADDRESS_TO_LONGITUDEA_URL + "&ak=" + AK + "&address=" + address;
        CloseableHttpClient client = HttpClients.createDefault(); // 创建默认http连接
        HttpPost post = new HttpPost(url);// 创建一个post请求
        try {
            HttpResponse response = client.execute(post);// 用http连接去执行get请求并且获得http响应
            HttpEntity entity = response.getEntity();// 从response中取到响实体
            String location = EntityUtils.toString(entity);// 把响应实体转成文本
            //解析json
            if (StringUtils.isNotBlank(location)) {
                //将json字符串转换为json对象
                JSONObject jsonObject = JSONObject.parseObject(location);
                //获取json对象中的result属性
                JSONObject result = jsonObject.getJSONObject("result");
                //获取result属性中的location属性
                JSONObject location1 = result.getJSONObject("location");
                //获取location属性中的lat属性
                String lat = location1.getString("lat");
                //获取location属性中的lng属性
                String lng = location1.getString("lng");
                System.out.println("经度：" + lng + "，纬度：" + lat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}