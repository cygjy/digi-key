package com.jd.resource.shopify;

import com.jd.resource.utils.ContentUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * shopify变体类
 * @author chenyang
 */
@Component
public class ShopifyVariants {

    private static String apiShop;

    private static String apiToken;

    private static String apiSecret;

    private static String apiKey;


    @Value("${shopify.apiShop}")
    private String shopName;

    @Value("${shopify.apiToken}")
    private String shopToken;

    @Value("${shopify.apiSecretKey}")
    private String shopSecretKey;

    @Value("${shopify.apiKey}")
    private String shopKey;



    @PostConstruct
    public void init() {
        apiKey = this.shopKey;
        apiShop = this.shopName;
        apiToken = this.shopToken;
        apiSecret = this.shopSecretKey;
    }

    public static String createVariants(String payload,String productId){
        System.out.println("pay - product----------->"+payload);
        CloseableHttpClient client = HttpClients.createDefault();
        String url = "https://"+apiShop+"/admin/api/2023-01/products/"+productId+"/variants.json";
        StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
        HttpPost post = new  HttpPost(url);
        post.setEntity(entity);
        post.setHeader("Accept","application/json");
        post.setHeader("Content-Type", "application/json");
        post.setHeader("X-Shopify-Access-Token",apiToken);

        try {
            CloseableHttpResponse execute = client.execute(post);
            int statusCode = execute.getStatusLine().getStatusCode();
            System.out.println("statusCode------------------->"+statusCode);
            System.out.println("responseBody------------------->"+execute);
            String content = ContentUtils.getContent(execute);
            System.out.println("content----------------------->"+content);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
