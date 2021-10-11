package com.lc.ddns.util;


import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * properties 文件读取
 *
 * @author xiang
 */
public class PropertiesUtil {
    private static Logger logger = Logger.getLogger(PropertiesUtil.class);
    //指定配置文件地址resource目录下:
    public static Properties prop = new Properties();
    //判断是否外部prop文件
    private static boolean isOut = false;

    static {
        try {
            logger.info("读取配置文件，从和jar包相同目录下找ddns.properties");
            //尝试从外部读取ddns.properties文件
            FileInputStream inputStream = new FileInputStream("ddns.properties");
            prop.load(inputStream);
            isOut = true;
        } catch (IOException e) {
            logger.error("读取配置文件失败，请把ddns.properties放在和jar包相同目录下");
            logger.info("再次读取配置文件，从src/main/resources下的ddns.properties");
            InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream("ddns.properties");
            try {
                prop.load(is);
            } catch (IOException e1) {
                System.out.println("读取内部测试配置文件失败");
            }
        }
    }

    public static void reload() {
        if (isOut) {
            try {
                logger.info("reload isOut为true 读取配置文件，从和jar包相同目录下找ddns.properties");
                //尝试从外部读取ddns.properties文件
                FileInputStream inputStream = new FileInputStream("ddns.properties");
                prop.load(inputStream);
                inputStream.close();
            } catch (Exception e) {
                logger.error("读取配置文件失败，请把ddns.properties放在和jar包相同目录下");
            }
        } else {
            try {
                logger.info("reload isOut为false 读取配置文件，从src/main/resources下的ddns.properties");
                InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("ddns.properties");
                prop.load(inputStream);
                inputStream.close();
            } catch (Exception e1) {
                logger.error("读取内部测试配置文件失败");
            }
        }
    }

    public static String getProperty(String key) {
        reload();
        return prop.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println(PropertiesUtil.getProperty("AccessKeyID"));
        System.out.println(PropertiesUtil.getProperty("AccessKeySecret"));
        System.out.println(PropertiesUtil.getProperty("DomainName"));
        System.out.println(PropertiesUtil.getProperty("ChiledDomainNames"));
        System.out.println(PropertiesUtil.getProperty("CheckTime"));
        System.out.println(PropertiesUtil.getProperty("AutoStart"));
    }
}
