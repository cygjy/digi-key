package com.jd.resource.utils;

import com.jd.common.exception.GlobalException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ContentUtils {

    private static final String EMPTY = "";


    private static final Logger log = LoggerFactory.getLogger(ContentUtils.class);

    /**
     * 根据响应获取响应实体
     *
     * @param response
     * @return
     */
    public static String getContent(CloseableHttpResponse response) {
        HttpEntity entity = response.getEntity();// 获取响应实体
        String content = EMPTY;
        try {
            content = EntityUtils.toString(entity, StandardCharsets.UTF_8);// 用string接收响应实体
            EntityUtils.consume(entity);// 消耗响应实体，并关闭相关资源占用
            if (response != null) {
                response.close();
            }
        } catch (IOException e1) {
            throw new GlobalException("解析响应实体时java IO 异常！");
        }
        return content;
    }

}
