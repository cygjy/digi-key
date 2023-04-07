package com.jd.resource.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import javax.crypto.Cipher;
import java.io.Reader;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class RSAUtils {

    /**
     * PKCS1私钥解密
     *
     * @param str        str
     * @return String
     * @throws Exception Exception
     */
    public static String decryptRSA(String str, String privateKey) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        rsa.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
        byte[] utf8 = rsa.doFinal(Base64.decodeBase64(str));
        String result = new String(utf8, "UTF-8");
        return result;
    }

    private static PrivateKey getPrivateKey(String privateKey) throws Exception {
        Reader privateKeyReader = new StringReader(privateKey);
        PEMParser privatePemParser = new PEMParser(privateKeyReader);
        Object privateObject = privatePemParser.readObject();
        if (privateObject instanceof PEMKeyPair) {
            PEMKeyPair pemKeyPair = (PEMKeyPair) privateObject;
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            PrivateKey privKey = converter.getPrivateKey(pemKeyPair.getPrivateKeyInfo());
            return privKey;
        }
        return null;
    }

    /**
     * PKCS8公钥加密
     *
     * @param str       加密字符串
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String publicKeyEncrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").
                generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        //当长度过长的时候，需要分割后加密 117个字节
        byte[] resultBytes = getMaxResultEncrypt(str, cipher);

        String outStr = Base64.encodeBase64String(resultBytes);
        return outStr;
    }

    private static byte[] getMaxResultEncrypt(String str, Cipher cipher) throws Exception {
        byte[] inputArray = str.getBytes();
        int inputLength = inputArray.length;
        // 最大加密字节数，超出最大字节数需要分组加密
        int MAX_ENCRYPT_BLOCK = 117;
        // 标识
        int offSet = 0;
        byte[] resultBytes = {};
        byte[] cache = {};
        while (inputLength - offSet > 0) {
            if (inputLength - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(inputArray, offSet, MAX_ENCRYPT_BLOCK);
                offSet += MAX_ENCRYPT_BLOCK;
            } else {
                cache = cipher.doFinal(inputArray, offSet, inputLength - offSet);
                offSet = inputLength;
            }
            resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
            System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
        }
        return resultBytes;
    }

}
