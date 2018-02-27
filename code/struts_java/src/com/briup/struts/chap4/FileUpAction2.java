package com.briup.struts.chap4;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;


public class FileUpAction2 extends ActionSupport implements RequestAware{
	private File[] file;
	private String[] fileFileName;
	private Map<String, Object> request;
	
	public File[] getFile() {
		return file;
	}
	public void setFile(File[] file) {
		this.file = file;
	}
	public String[] getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}
	public Map<String, Object> getRequest() {
		return request;
	}
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	@Override
	public String execute() throws Exception {
		String root = 	ServletActionContext.getServletContext().getRealPath("/file2");
		File f = new File(root);
		if(!f.exists()){ //如果目录不存在
			f.mkdir();
		}
		for(int i = 0;i < file.length;i++){
			//在父目录中构建子文件
			File file2 = new File(root,fileFileName[i]);
			//第一个参数需要上传的文件
			//第二个参数上传文件最后存放在服务器的位置
			FileUtils.copyFile(file[i],file2);
		}
		return SUCCESS;
	}
}
