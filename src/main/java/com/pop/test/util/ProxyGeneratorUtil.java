package com.pop.test.util;

import java.io.FileOutputStream;
import java.io.IOException;

import sun.misc.ProxyGenerator;

public class ProxyGeneratorUtil {
    
    private static String DEFAULT_CLASS_NAME = "$Proxy";
    
    public static byte [] saveGenerateProxyClass(String path,Class<?> [] interfaces) {  
   
        byte[] classFile = ProxyGenerator.generateProxyClass(DEFAULT_CLASS_NAME, interfaces);
          
        FileOutputStream out = null;
          
        try {  
            String filePath = path + "/" + DEFAULT_CLASS_NAME + ".class";
            out = new FileOutputStream(filePath);  
            out.write(classFile);  
            out.flush();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if(out != null) out.close();  
            } catch (IOException e) {
                //ignore 
            }  
        }  
        return classFile;
    }

    public static void main(String[] args) {
//        Class<?> interfaces [] = {SayComeImpl.class};
//        //运行时，确保目录存在
//        saveGenerateProxyClass("/Users/pengmaokui/proxyClass", interfaces);
    }
}