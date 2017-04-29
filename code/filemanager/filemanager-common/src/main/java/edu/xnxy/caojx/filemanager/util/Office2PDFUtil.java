package edu.xnxy.caojx.filemanager.util;

import org.apache.log4j.Logger;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

/**
 * Description:文件转pdf工具类支持.txt,.docx,dox,ppt,.pptx,xls,.xlsx,.png,.jpg等文件转成pdf
 *
 * 1.使用该工具类，需要先安libreoffice
 *      yum install -y libreoffice.x86_64
 * 2.添加相关jar
 *      jodconverter-2.2.1.jar
 *      jodconverter-core-3.0-beta-4.jar
 *      commons-cli-1.2.jar
 *      commons-io-1.4.jar
 *
 * @author caojx
 *         Created by caojx on 2017年04月24 上午9:50:50
 */
public class Office2PDFUtil {

    private static final Logger log = Logger.getLogger(Office2PDFUtil.class);

    /**
     * Description: 将Office文档转换为PDF. 运行该函数需要用到libreOffice
     *
     * 安装libreoffice:yum install -y libreoffice.x86_64
     *
     * @param sourceFile 源文件, 绝对路径. 可以是Office2003-2007全部格式的文档, Office2010的没测试. 包括.doc,
     *                   .docx, .xls, .xlsx, .ppt, .pptx等. 示例: /home/caojx/temp/source.doc
     * @param destFile   目标文件. 绝对路径. 示例: /home/caojx/temp/dest.pdf
     * @return 操作成功与否的提示信息. 如果返回 -1, 表示找不到源文件, 或officeutil.properties配置错误,或转换失败
     * 如果返回 0,则表示操作成功;
     */
    public static int office2PDF(String sourceFile, String destFile) {
        try {
            log.info("libreOffice开始转换..............................");
            //判断pdf文件是否已经存在存在，存在直接返回
            File outputFile = new File(destFile);
            if(outputFile.exists()){
                log.info(outputFile.getName()+"已经存在，不用转换");
                return 0;
            }else {
                Long startTime = new Date().getTime();
                File inputFile = new File(sourceFile);
                if (!inputFile.exists()) {
                    return -1; //返回-1表示源文件不存在
                }
                // 如果目标路径不存在, 则新建该路径
                if (!outputFile.getParentFile().exists()) {
                    outputFile.getParentFile().mkdirs();
                }

                //读取officeutil.properties配置文件中的libreoffice的安装路径
                Properties properties = new Properties();

                properties.load(Office2PDFUtil.class.getClassLoader().getResourceAsStream("officeutil.properties"));

                // 此类在jodconverter中3版本中存在，在2.2.2版本中不存在
                DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
                // libreOffice的安装目录
                String libreOfficePath = properties.getProperty("libreOffice.path");
                configuration.setOfficeHome(new File(libreOfficePath));
                // 端口号
                String librePort = properties.getProperty("libreOffice.port");
                configuration.setPortNumber(Integer.valueOf(librePort));

                OfficeManager officeManager = configuration.buildOfficeManager();
                officeManager.start();

                //开始转换
                log.info("...start.....");
                OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
                File outputFile2 = new File(destFile);
                converter.convert(inputFile, outputFile2);

                //转换结束
                log.info("转换结束。。。。。");
                String pdfPath = outputFile.getPath();
                Long endTime = new Date().getTime();
                Long time = endTime - startTime;
                log.info("libreOffice转换所用时间为：" + time);

                //停止officeManager
                officeManager.stop();
                return 0;
            }
        } catch (FileNotFoundException e) {
            log.error("找不到配置文件officeutil.properties", e);
            return -1;
        } catch (IOException e) {
            log.error("读取配置文件出错officeutil.properties", e);
            return -1;
        }
    }
}
