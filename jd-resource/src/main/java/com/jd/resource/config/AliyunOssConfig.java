package com.jd.resource.config;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenyang
 * @Description: 阿里云 OSS 基本配置
 */
@Configuration
@Data
@Accessors(chain = true)
public class AliyunOssConfig {
    /**
     * O地域节点
     */
    @Value("${aliyun.endPoint}")
    private String endPoint;
    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;
    /**
     * OSS的Bucket名称
     */
    @Value("${aliyun.bucketName}")
    private String bucketName;
    /**
     * Bucket 域名
     */
    @Value("${aliyun.urlPrefix}")
    private String urlPrefix;


    /**
     * 将OSS 客户端交给Spring容器托管
     * @return OSSClient
     */
    @Bean
    public OSS OssClient() {
        return new OSSClient(endPoint, accessKeyId, accessKeySecret);
    }
}
