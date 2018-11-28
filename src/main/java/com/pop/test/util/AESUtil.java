//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.pop.test.util;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    public static final String AES_ALGORITHM = "AES";
    public static final String CHAR_ENCODING = "utf-8";

    public AESUtil() {
    }

    public static String encrypt(String data, String key) {
        if (key.length() < 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        } else {
            if (key.length() > 16) {
                key = key.substring(0, 16);
            }

            try {
                Cipher cipher = Cipher.getInstance("AES");
                byte[] byteContent = data.getBytes("utf-8");
                cipher.init(1, genKey(key));
                byte[] result = cipher.doFinal(byteContent);
                return parseByte2HexStr(result);
            } catch (Throwable var5) {
                throw new RuntimeException(var5);
            }
        }
    }

    public static String decrypt(String data, String key) {
        if (key.length() < 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        } else {
            if (key.length() > 16) {
                key = key.substring(0, 16);
            }

            try {
                byte[] decryptFrom = parseHexStr2Byte(data);
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(2, genKey(key));
                byte[] result = cipher.doFinal(decryptFrom);
                return new String(result, "utf-8");
            } catch (Throwable var5) {
                throw new RuntimeException(var5);
            }
        }
    }

    private static SecretKeySpec genKey(String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("utf-8"), "AES");
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
            return seckey;
        } catch (UnsupportedEncodingException var4) {
            throw new RuntimeException("genKey fail!", var4);
        }
    }

    private static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < buf.length; ++i) {
            String hex = Integer.toHexString(buf[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }

    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hexStr.length() / 2];

            for(int i = 0; i < hexStr.length() / 2; ++i) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte)(high * 16 + low);
            }

            return result;
        }
    }

    public static void main(String[] args) {
        String string = "12345678";
        String key = "3bf5d340dd4e464f";
        String encrypt = encrypt(string, key);
        System.out.println(encrypt);
    }
}
