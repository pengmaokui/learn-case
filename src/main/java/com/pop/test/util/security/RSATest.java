package com.pop.test.util.security;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class RSATest {
	public static void main(String[] args) throws IOException {
        try {  
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(2048);  
            KeyPair pair = gen.generateKeyPair();
            //rsa生成一对公私钥  
            PublicKey publicKey  = pair.getPublic();
            PrivateKey privateKey  = pair.getPrivate();
            //SHA1withRSA算法进行签名  
            Signature sign = Signature.getInstance("SHA1withRSA");
            sign.initSign(privateKey);  
            byte[] data = "sss".getBytes();  
            //更新用于签名的数据  
            sign.update(data);  
            byte[] signature = sign.sign();  
            Signature verifySign = Signature.getInstance("SHA1withRSA");  
            verifySign.initVerify(publicKey);  
            //用于验签的数据  
            verifySign.update("ssss".getBytes());
            boolean flag = verifySign.verify(signature);  
            System.out.println(flag);  
              
        } catch (Exception e) {  
            e.printStackTrace();
        }  
          
    }  
  
}  