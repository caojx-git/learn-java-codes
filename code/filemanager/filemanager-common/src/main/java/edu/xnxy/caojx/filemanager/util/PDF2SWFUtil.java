package edu.xnxy.caojx.filemanager.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Description:pdf转swf文件工具类，使用该工具类首先需要安装swftools
 * <p>
 * 1.下载swftools 地址
 * http://www.swftools.org/download.html
 * 2.安装依赖
 * yum install giflib-devel
 * yum-y install giflib-devellibjpeg-devel freetype-devel t1lib-devel zlib
 * yum install gcc-c++
 * yum install gcc-c++ libstdc++-devel
 * <p>
 * 3.解压到/usr/local/src
 * sudo cp swftools-2013-04-09-1007.tar.gz /usr/local/src
 * sudo cd /usr/local/src swftools-2013-04-09-1007.tar.gz
 * sudo tar -zxvf swftools-2013-04-09-1007.tar.gz
 * cd /usr/local/src/swftools-2013-04-09-1007/
 * sudo ./configure --prefix=/usr/local/swftools
 * sudo make
 * sudo make install
 * <p>
 * 提示：不过不安装以来，安装后可能会缺少某些命令工具，如缺少pdf2swf
 *
 * @author caojx
 *         Created by caojx on 2017年04月24 上午9:50:50
 */
public class PDF2SWFUtil {

    private static final Logger log = Logger.getLogger(PDF2SWFUtil.class);

    private static String OFFICE_CONFIG = "officeutil.properties";

    /**
     * Description：pdf文件格式转swf文件格式方法，
     *
     * @param pdfPath pdf文件路径
     */
    public static void pdf2SWF(String pdfPath) {
        log.info("开始将pdf转呈swf文件...");
        Runtime r = Runtime.getRuntime();
        log.info("pdfPath:" + pdfPath);
        String swfPath = pdfPath.substring(0, pdfPath.lastIndexOf('.'));
        log.info("/usr/local/swftools/bin/pdf2swf -t " + pdfPath + " -s flashversion=9 -o " + swfPath + ".swf");
        FileInputStream fileInputStream = null;
        try {
            Properties properties = new Properties();
            properties.load(PDF2SWFUtil.class.getClassLoader().getResourceAsStream("officeutil.properties"));
            String swftoolsPath = properties.getProperty("swftools.path");
            Process process = r.exec(swftoolsPath + "bin/pdf2swf -t " + pdfPath + " -s flashversion=9 -o " + swfPath + ".swf");
            process.waitFor();
            process.exitValue();
            process.destroy();
            log.info("转换结束...");
        } catch (IOException e) {
            log.error("转换出错", e);
        } catch (InterruptedException e) {
            log.error("转换出过程出现中断", e);
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                log.error("swftools配置读取失败，请检查swftools配置", e);
            }
        }

    }
}
