package com.jd.resource.controller;

import com.jd.common.core.domain.AjaxResult;
import com.jd.common.core.redis.RedisCache;
import com.jd.framework.config.RedisConfig;
import com.jd.resource.utils.AddressUtils;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author chenyang
 */
@RestController
@RequestMapping(value = "/web/Ip")
public class IpController {


    @Autowired
    private RedisCache redisCache;

    /**
     * 根据ip获取地址
     *
     * @return
     */
    @PostMapping(value = "/address")
    public AjaxResult createProduct(HttpServletRequest request) {
        try {
            Map<String, Object> map = new LinkedHashMap<>(8);
            String ipv4Ip = AddressUtils.getIpv4IP();
            String ipKey = "ip:" + ipv4Ip;
            String ip = ipv4Ip.replaceAll(" ", "");
            Object ipCache = redisCache.getCacheObject(ipKey);
            map.put("ip", ip);
            // 如果 ipKey存在 返回true 说明不是第一次访问  如果ipKey为空 返回false 说明是第一次访问
            if (ipCache == null) {
                redisCache.setCacheObject(ipKey, ipv4Ip);
                // 设置过期时间为60秒
                redisCache.expire(ipKey, 60);
                Boolean persist = redisCache.redisTemplate.persist(ipKey);
                if (persist) {
                    map.put("isIpFlag", false);
                }
            }else {
                map.put("isIpFlag", true);
            }
            System.out.println("ip:" + ipv4Ip);
            System.out.println("ips-->" + ip);
            Map<String, Object> province = AddressUtils.getProvince(ip);
            map.put("province", province);
            return AjaxResult.success(map);
        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error();
    }
}
