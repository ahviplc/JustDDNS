package com.lc.ddns.util;

import net.jimmc.jshortcut.JShellLink;

/**
 * 快捷方式工具类
 *
 * @author ice(古雨QQ : 494174519, 13728989948 ( 你的代码改进, 是对我们共同的鼓励))
 */
public class ShortCut {

    // 需要先下载 jshortcut.jar直接在百度搜一下
    public static void main(String args[]) {
        String fileFolderPath = "D:/IdeaProjects/AliYunDdns/target/StartAliYunDdns.bat";
        String writeFolderPath = "D:/IdeaProjects/AliYunDdns/target/StartAliYunDdns";
        createShortCut(fileFolderPath, writeFolderPath);
    }

    /**
     * 创建一个快捷方式
     *
     * @param fileOrFolderPath  源文件夹路径
     * @param writeShortCutPath 目标文件路径(快捷方式型)
     */
    public static void createShortCut(String fileOrFolderPath, String writeShortCutPath) {
        JShellLink link = new JShellLink();
        String writeShortCutPath1 = writeShortCutPath.replaceAll("\\\\", "/");
        String folder = writeShortCutPath1.substring(0, writeShortCutPath1.lastIndexOf("/"));
        String name = writeShortCutPath1.substring(writeShortCutPath1.lastIndexOf("/") + 1, writeShortCutPath1.length());
        link.setName(name);// 目的快捷方式文件夹名称
        link.setFolder(folder);// 目的快捷方式文件路径片段
        link.setPath(fileOrFolderPath);
        link.save();
    }

    /**
     * 获取一个快捷方式真实地址
     *
     * @param fileFolderPath 源文件夹路径
     */
    public static String getShortCutRealPath(String fileFolderPath) {
        // 根据快捷方式的路径和文件夹名,获取源文件夹地址
        fileFolderPath = fileFolderPath.replaceAll("/", "\\");
        String folder = fileFolderPath.substring(0, fileFolderPath.lastIndexOf("\\"));
        String name = fileFolderPath.substring(fileFolderPath.lastIndexOf("\\") + 1, fileFolderPath.length());
        JShellLink link = new JShellLink(folder, name);
        link.load();
        return link.getPath();
    }
}
