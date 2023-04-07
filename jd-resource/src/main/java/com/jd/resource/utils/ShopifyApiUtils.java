package com.jd.resource.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * 获取第三方数据api
 * @author chenyang
 */
public class ShopifyApiUtils {



    public static JSONObject getShopId(String shop,String apiV,String accessToken){
        String url = "https://"+shop+"/admin/api/"+apiV+"/shop.json";
        String doGet = HttpUtils.doGet(url, apiV,accessToken);
        JSONObject jsonObject = JSONObject.parseObject(doGet);
        return jsonObject;
    }

    /**
     * 获取产品详情
     * @param shop
     * @param apiV
     * @param productId
     * @param accessToken
     * @return
     */
    public static JSONObject getProduct(String shop,String apiV,String productId,String accessToken){
       // https://your-development-store.myshopify.com/admin/api/2022-04/products/632910392.json
        String url = "https://"+shop+"/admin/api/"+apiV+"/products/"+productId+".json";
        String doGet = HttpUtils.doGet(url, null,accessToken);
        JSONObject jsonObject = JSONObject.parseObject(doGet);
        return jsonObject;
    }


    /**
     * 获取产品列表
     * @param shop
     * @param apiV
     * @param accessToken
     * @return
     */
    public static JSONObject getProductList(String shop,String apiV,String accessToken){
//        https://your-development-store.myshopify.com/admin/api/2022-04/products.json
        String url = "https://"+shop+"/admin/api/"+apiV+"/products.json";
        String doGet = HttpUtils.doGet(url, apiV,accessToken);
        JSONObject jsonObject = JSONObject.parseObject(doGet);
        return jsonObject;
    }
}
