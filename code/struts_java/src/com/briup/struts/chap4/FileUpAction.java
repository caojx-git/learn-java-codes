package com.briup.struts.chap4;

import java.awt.Checkbox;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FileUpAction extends ActionSupport implements RequestAware{
	private File file;
	private String fileFileName;
	private Map<String, Object> request;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
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
		//ServletActionContext.getServletContext() 获取工程路经
		String root = ServletActionContext.getServletContext().getRealPath("/file");
		System.out.println(root);
		File f = new File(root);
		if(!f.exists()){//构建目录
			f.mkdir();
		}
		//判断是否是图片
		/*boolean flag = Check.isPicture(fileFileName,"");
		if(true){
			request.put("msg", "只能上传图片");
			return ERROR;
		}*/
		//f2上传文件存放的位置
		File f2 = new File(f, fileFileName);
		//输入流，读取本地文件的内容
		InputStream is = new FileInputStream(file);
		byte[] b = new byte[1024];
		int num = 0;
		//输出流，将读取到的数据写入到心构建的f2文件中
		OutputStream os = new FileOutputStream(f2);
		while((num = is.read(b)) != -1){
			os.write(b);
		}
		os.flush();
		request.put("path", file);
		os.close();
		return SUCCESS;
	}
}
