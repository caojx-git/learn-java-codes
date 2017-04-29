package edu.xnxy.caojx.filemanager.web;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by caojx on 17-4-25.
 */
public class Office2PDFUtil {

    public String doDocToFdpLibre() {

// File inputFile = new File("d:/1.txt");
// File inputFile = new File("d:/ppt.ppt");
// File inputFile = new File("d:/pptx.pptx");
// File inputFile = new File("d:/doc.doc");
//File inputFile = new File("/home/caojx/图片/曹建祥毕业总结.docx");
   // File inputFile = new File("/home/caojx/图片/工作日程.xlsx");
    //File inputFile = new File("/home/caojx/图片/a.png");
// File inputFile = new File("d:/gif.gif");
        File inputFile = new File("/home/caojx/图片/java使用pdf2swf_百度搜索.html");
        System.out.println("libreOffice开始转换..............................");
        Long startTime = new Date().getTime();
//
// txt:使用libreOffice来转换pdf，转换成功，但是中文有乱码!!!!!
// doc:这是office中的doc文档，可以转换成功，并且中文没有乱码
//docx:这是office中的docx文档，可以转换成功，并且中文没有乱码
//ppt和pptx：转换成功。
//xls:转换成功，没有中文乱码
//xlsx：转换成功
//jpg和png:成功
        String libreOfficePath = "/usr/lib64/libreoffice";
// 此类在jodconverter中3版本中存在，在2.2.2版本中不存在
        DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
// libreOffice的安装目录
        configuration.setOfficeHome(new File(libreOfficePath));
// 端口号
        configuration.setPortNumber(8100);
// configuration.setTaskExecutionTimeout(1000 * 60 * 5L);//
// 设置任务执行超时为5分钟
// configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);//
// 设置任务队列超时为24小时
        OfficeManager officeManager = configuration.buildOfficeManager();
        officeManager.start();

        System.out.println("...start.....");
        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
        File outputFile = new File("/home/caojx/图片/java使用pdf2swf_百度搜索.pdf");
        converter.convert(inputFile, outputFile);
// converter.convert(inputFile, stw, outputFile, pdf);
// 转换结束
        System.out.println("转换结束。。。。。");
        String pdfPath = outputFile.getPath();
        long endTime = new Date().getTime();
        long time = endTime - startTime;
        System.out.println("libreOffice转换所用时间为："+time);
        officeManager.stop();
        return pdfPath;
    }


    public void doPdfToSwf(String pdfPath){
        Runtime r = Runtime.getRuntime();
        System.out.println("pdfPath:"+pdfPath);
        String swfPath = pdfPath.substring(0,pdfPath.lastIndexOf('.'));
        System.out.println("/usr/local/swftools/bin/pdf2swf -t "+pdfPath+" -s flashversion=9 -o "+swfPath+".swf");
        try {
            Process process = r.exec("/usr/local/swftools/bin/pdf2swf -t "+pdfPath+" -s flashversion=9 -o "+swfPath+".swf");
            // 关闭OpenOffice服务的进程
            process.waitFor();
            process.exitValue();
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Office2PDFUtil office2PDFUtil =  new Office2PDFUtil();
        String pdfPath = office2PDFUtil.doDocToFdpLibre();
        office2PDFUtil.doPdfToSwf(pdfPath);
    }
}
