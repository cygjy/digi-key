package com.jd.resource.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * hmac验证 shopify的真实性
 * @author chenyang
 */
public class HMACValidator {
    public static String sha256HMAC(String key, String data) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, DecoderException {
        Mac hmac = Mac.getInstance("HmacSHA256");
        System.out.println("data "+data);
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        hmac.init(secret_key);
        return Hex.encodeHexString(hmac.doFinal(data.getBytes("UTF-8")));
    }

    public static boolean validateShopifyAskForPermission(String key, String hmac, String shop, String timestamp) throws Exception {
        return (sha256HMAC(key, "shop="+shop+"&timestamp="+timestamp).compareTo(hmac) > 0);
    }

    public static boolean validateShopifyAskForPermission(String key, String hmac,String code, String shop,String state ,String timestamp) throws Exception {
        return (sha256HMAC(key, "code="+code+"&shop="+shop+"&state="+state+"&timestamp="+timestamp).compareTo(hmac) == 0);
    }
}
