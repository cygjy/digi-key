package com.jd.resource.utils;

import com.jd.common.core.domain.AjaxResult;
import com.jd.common.exception.GlobalException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * shopify 产品添加操作
 * @author chenyang
 */
@Component
public class ProductUtils {

    private static final Logger log = LoggerFactory.getLogger(ProductUtils.class);

    public AjaxResult createShopifyProduct(String payload,String shop,String accessToken){

        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        String url = "https://"+shop+"/admin/api/2022-04/products.json";
        HttpPost post = new HttpPost(url);
        StringEntity requestBody = new StringEntity(payload, ContentType.APPLICATION_JSON);
        post.setEntity(requestBody);
        post.setHeader("Accept","application/json");
        post.setHeader("Content-Type","application/json");
        post.setHeader("X-Shopify-Access-Token",accessToken);

        try {
            CloseableHttpResponse execute  = closeableHttpClient.execute(post);
            int statusCode = execute.getStatusLine().getStatusCode();
            log.info("code{}",statusCode);
            HttpEntity entity = execute.getEntity();
            log.info("entity{}",entity);
            return AjaxResult.success(execute);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new GlobalException("操作异常");
    }
}
