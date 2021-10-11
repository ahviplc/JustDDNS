package com.lc.ddns.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取公网ip
 *
 * @author ienai
 */
public class IPUtil {
    private static Logger logger = Logger.getLogger(IPUtil.class);

    /**
     * 获取公网IP地址
     *
     * @return String
     */
    public static String getIP() {
        String[] cmds = {"curl", "-L", "tool.lu/ip"};
        String str = execCurl(cmds);
        Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
        Matcher matcher = p.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public static void main(String args[]) {
        IPUtil ip = new IPUtil();
        System.out.println(ip.getIP());
    }

    public static String execCurl(String[] cmds) {
        ProcessBuilder process = new ProcessBuilder(cmds);
        Process p;
        try {
            p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();
        } catch (IOException e) {
            logger.error("获取IP地址失败");
            e.printStackTrace();
        }
        return null;
    }
}
