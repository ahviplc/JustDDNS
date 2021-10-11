package com.lc.ddns.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * 开机启动工具
 */
public class AutoStartUtil {
    private static Logger logger = Logger.getLogger(AutoStartUtil.class);

    // 写入快捷方式 是否自启动，快捷方式的名称，注意后缀是lnk
    public static boolean setAutoStart(boolean isAutoStart, String lnkPath) {
        String startFolder = "";
        lnkPath=lnkPath.replaceAll("/","\\\\");
        String osName = System.getProperty("os.name");
        if (osName.equals("Windows 7") || osName.equals("Windows 8") || osName.equals("Windows 10")
                || osName.equals("Windows Server 2012 R2") || osName.equals("Windows Server 2014 R2")
                || osName.equals("Windows Server 2016")) {
            startFolder = System.getProperty("user.home")
                    + "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
        }
        if (osName.endsWith("Windows XP")) {
            startFolder = System.getProperty("user.home") + "\\「开始」菜单\\程序\\启动";
        }

        return setRunBySys(isAutoStart, lnkPath, startFolder);
    }

    // 设置是否随系统启动
    private static boolean setRunBySys(boolean b, String lnkPath, String startPath) {
        String startFilePath=startPath + "\\" + lnkPath.substring(lnkPath.lastIndexOf("\\")+1);
        File startFile = new File(startFilePath);
        Runtime run = Runtime.getRuntime();
        File lnkFile = new File(lnkPath);
        // 复制
        try {
            if (b) {
                // 写入
                // 判断是否隐藏，注意用系统copy布置为何隐藏文件不生效
                if (lnkFile.isHidden()) {
                    // 取消隐藏
                    try {
                        Runtime.getRuntime().exec("attrib -H \"" + lnkPath + "\"");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (lnkFile.exists()) {
                    run.exec("cmd /c copy " + formatPath(lnkPath) + " " + formatPath(startPath));
                    run.exec("cmd /c del " + formatPath(lnkPath));
                    System.out.println("cmd /c del " + formatPath(lnkPath));
                }
                // 延迟0.5秒防止复制需要时间
                Thread.sleep(500);
            } else {
                // 删除
                if (startFile.exists()) {
                    if (startFile.isHidden()) {
                        // 取消隐藏
                        try {
                            Runtime.getRuntime().exec("attrib -H \"" + startFilePath + "\"");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Thread.sleep(500);
                    }
                    run.exec("cmd /c del " + formatPath(startFile.getAbsolutePath()));
                    run.exec("cmd /c del " + formatPath(lnkPath));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 解决路径中空格问题
    private static String formatPath(String path) {
        if (path == null) {
            return "";
        }
        return path.replaceAll(" ", "\" \"");
    }

    public static void main(String[] args) {
        setAutoStart(true, "D:\\IdeaProjects\\AliYunDdns\\target\\StartAliYunDdns.lnk");
        setAutoStart(false, "D:/IdeaProjects/AliYunDdns/target/StartAliYunDdns.lnk");
    }
}
