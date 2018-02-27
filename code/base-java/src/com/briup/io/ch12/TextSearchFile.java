package com.briup.io.ch12;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
//操作查找文件的类
//引用类
public class TextSearchFile {
    static int countFiles = 0;// 声明统计文件个数的变量
    static int countFolders = 0;// 声明统计文件夹的变量
    public static File[] searchFile(File folder, final String keyWord) {// 递归查找包含关键字的文件
         
        File[] subFolders = folder.listFiles(new FileFilter() {// 运用内部匿名类获得文件
                    public boolean accept(File pathname) {// 实现FileFilter类的accept方法
                        if (pathname.isFile())// 如果是文件
                            countFiles++;
                        else
                            // 如果是目录
                            countFolders++;
                        if (pathname.isDirectory()
                        		|| (pathname.isFile() && pathname.getName()
                                        .toLowerCase()
                                        .contains(keyWord.toLowerCase())))// 目录或文件包含关键字
                            return true;
                        return false;
                    }
                });
         
        List result = new ArrayList();// 声明一个集合
        for (int i = 0; i < subFolders.length; i++) {// 循环显示文件夹或文件
            if (subFolders[i].isFile()) {// 如果是文件则将文件添加到结果列表中
                result.add(subFolders[i]);
            } else {// 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中
                File[] foldResult = searchFile(subFolders[i], keyWord);
                for (int j = 0; j < foldResult.length; j++) {// 循环显示文件
                    result.add(foldResult[j]);// 文件保存到集合中
                }
            }
        }
         
        File files[] = new File[result.size()];// 声明文件数组，长度为集合的长度
        result.toArray(files);// 集合数组化
        return files;
    }
    public static void main(String[] args) {// java程序的主入口处
        File folder = new File("E:/c program");// 默认目录
        String keyword = "x";
        if (!folder.exists()) {// 如果文件夹不存在
            System.out.println("目录不存在：" + folder.getAbsolutePath());
            return;
        }
        File[] result = searchFile(folder, keyword);// 调用方法获得文件数组
        System.out.println("在 " + folder + " 以及所有子文件时查找对象" + keyword);
        System.out.println("查找了" + countFiles + " 个文件，" + countFolders
                + " 个文件夹，共找到 " + result.length + " 个符合条件的文件：");
        for (int i = 0; i < result.length; i++) {// 循环显示文件
            File file = result[i];
            System.out.println(file.getAbsolutePath() + " ");// 显示文件绝对路径
        }
    }
}