package com.jd.resource.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * 根据IP地址获取详细的地域信息
 * 淘宝API : http://ip.taobao.com/service/getIpInfo.php?ip=218.192.3.42
 * 新浪API : http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=218.192.3.42
 * @File AddressUtils.java
 * @Package org.gditc.weicommunity.util
 * @Description TODO
 * @Copyright Copyright © 2014
 * @Site https://github.com/Cryhelyxx
 * @Blog http://blog.csdn.net/Cryhelyxx
 * @Email cryhelyxx@gmail.com
 * @Company GDITC
 * @Date 2014年11月6日 下午1:46:37
 * @author Cryhelyxx
 * @version 1.0
 */
@Slf4j
public class AddressUtils {

    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    private static final String URL_PREFIX = "http://cpdc.chinapost.com.cn/web/index.php?m=postsearch&c=index&a=ajax_addr&searchkey=";


    public static void main(String[] args) throws Exception{

        Map<String, Object> province = AddressUtils.getProvince("66.28.255.255");
        System.out.println("province："+province);
        String city = AddressUtils.getCity("219.147.223.51");
        System.out.println("city："+city);
        String country = AddressUtils.getCountry("116.230.91.153");
        System.out.println("country："+country);
    }

    public static String getCity(String ip) throws IOException, GeoIp2Exception {
        // 创建 GeoLite2 数据库
        //这里是下载的GeoLite2 City库的路径
        File database = new File("D:\\developmentTool\\ip\\ipCity.mmdb");
        // 读取数据库内容
        DatabaseReader reader = new DatabaseReader.Builder(database).build();
        InetAddress ipAddress = InetAddress.getByName(ip);
        // 获取查询结果
        CityResponse response = reader.city(ipAddress);
        //获取City
        City city = response.getCity();
        return city.getNames().get("zh-CN");
    }

    public static Map<String,Object> getProvince(String ip) throws IOException, GeoIp2Exception {
        Map<String,Object> map = new HashMap<>(8);
        // 创建 GeoLite2 数据库
        File database = new File("D:\\developmentTool\\ip\\ipCity.mmdb");
        // 读取数据库内容
        DatabaseReader reader = new DatabaseReader.Builder(database).build();
        InetAddress ipAddress = InetAddress.getByName(ip);
        // 获取查询结果
        CityResponse response = reader.city(ipAddress);
        //获取Province
        Subdivision subdivision = response.getMostSpecificSubdivision();
        String names = subdivision.getNames().get("zh-CN");
        String isoCode = subdivision.getIsoCode();
        Country country = response.getCountry();
        Postal postal = response.getPostal();
        Location location = response.getLocation();
        // 经度
        Double longitude = location.getLongitude();
        // 维度
        Double latitude = location.getLatitude();
        country.getNames();
        country.getIsoCode();
        map.put("names",names);
        map.put("isoCode",isoCode);
        map.put("postal",postal.getCode());
        map.put("longitude",longitude);
        map.put("latitude",latitude);
        return map;
    }


    /**
     * 通过地址获取邮编信息
     * @param addr -> 地址
     * @return postcode -> 邮编
     */
    public static String getPostCodeByAddr(String addr) {

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(URL_PREFIX + addr, String.class);
        log.info("response = {}", StringEscapeUtils.unescapeJava(response));

        JSONObject jsonResp = JSON.parseObject(response);

        return Optional.ofNullable(jsonResp)
                .map(jsonObject -> jsonObject.getJSONArray("rs"))
                .filter(jsonArray -> jsonArray.size() > 0)
                // 地址不精确导致找到多个默认取第一个
                .map(jsonArray -> jsonArray.getJSONObject(0))
                .map(jsonObject -> jsonObject.getString("POSTCODE"))
                .orElse(StringUtils.EMPTY);
    }

    public static String getCountry(String ip) throws IOException, GeoIp2Exception {
        // 创建 GeoLite2 数据库
        File database = new File("D:\\developmentTool\\ip\\ipCity.mmdb");
        // 读取数据库内容
        DatabaseReader reader = new DatabaseReader.Builder(database).build();
        InetAddress ipAddress = InetAddress.getByName(ip);
        // 获取查询结果
        CityResponse response = reader.city(ipAddress);
        //获取country
        Country country = response.getCountry();
        return country.getNames().get("zh-CN");
    }

    /**
     * 获取真实IP
     */
    public static String getIpAddr(HttpServletRequest request) throws IOException, GeoIp2Exception {
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                assert inet != null;
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }


    /**
     * @return java.lang.String
     * @author chen
     * 获取公网ip
     */
    public static String getIpv4IP() {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            URL realUrl = new URL("https://www.taobao.com/help/getip.php");
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error("获取ipv4公网地址异常");
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        String str = result.toString().replace("ipCallback({ip:", "");
        String ipStr = str.replace("})", "");
        String replace = ipStr.replace('"', ' ');
        System.out.println("replace:"+replace);
        return replace;
    }


    /**
     * 获取本机地址
     */
    public static String getLocalIp() {
        String ip = null;
        try {
            Enumeration<?> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
                for (InterfaceAddress add : InterfaceAddress) {
                    InetAddress Ip = add.getAddress();
                    if (Ip != null && Ip instanceof Inet4Address) {
                        ip = Ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            log.warn(e.getMessage(), e);
        }
        return ip;
    }

    /**
     * 格式化Mac地址
     *
     * @param addr 源地址
     */
    public static String formatMacAddr(String addr) {
        if (StringUtils.isBlank(addr)) {
            return addr;
        }
        String mac = addr.replaceAll("(.{2})", "$1:").toUpperCase();
        return mac.substring(0, mac.length() - 1);
    }
}
