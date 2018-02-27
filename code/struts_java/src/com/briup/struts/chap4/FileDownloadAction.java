package com.briup.struts.chap4;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class FileDownloadAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String inputPath; //文件路径
	public String fileName; 
	
	
	public String getInputPath() {
		return inputPath;
	}



	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}



	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public InputStream getDownloadFile() throws IOException{
		/*System.out.println(inputPath);
		InputStream in =  ServletActionContext.getServletContext().getResourceAsStream(inputPath);
		System.out.println(in);
		return in;*/
		//获取绝对路径
		String path = ServletActionContext.getServletContext().getRealPath(fileName);
		System.out.println(path);
		File file = new File(path);
		return FileUtils.openInputStream(file);
	}
	
	public String getDownloadFileName(){
		//return "aaa.jpg";
		//中文
		String downloadFileName;
		downloadFileName = URLEncoder.encode("文件下载.jpg");
		return downloadFileName;
	}
}
