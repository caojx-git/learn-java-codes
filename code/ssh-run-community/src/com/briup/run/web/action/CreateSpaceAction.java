package com.briup.run.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.briup.run.common.bean.Graderecord;
import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.bean.Memberspace;
import com.briup.run.common.bean.Pointaction;
import com.briup.run.common.bean.Pointrecord;
import com.briup.run.common.util.BeanFactory;
import com.briup.run.service.IMemberService;
import com.opensymphony.xwork2.ActionSupport;
public class CreateSpaceAction extends ActionSupport{
	private Memberspace memberspace;
	private File icon;
	private String iconFileName;
	
	private IMemberService iMemberService;
	
	public IMemberService getiMemberService() {
		return iMemberService;
	}

	public void setiMemberService(IMemberService iMemberService) {
		this.iMemberService = iMemberService;
	}

	public Memberspace getMemberspace() {
		return memberspace;
	}

	public void setMemberspace(Memberspace memberspace) {
		this.memberspace = memberspace;
	}

	public File getIcon() {
		return icon;
	}

	public void setIcon(File icon) {
		this.icon = icon;
	}

	public String getIconFileName() {
		return iconFileName;
	}

	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
	}

	@Override
	public String execute() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			Memberinfo memberinfo = (Memberinfo) session.getAttribute("memberinfo");
			System.out.println(memberinfo.getNickname()+"--"+memberinfo.getId());
			Boolean isSpace = iMemberService.isMemberspace(memberinfo.getId());//判断是否有个人空间
			System.out.println("-----"+isSpace);
			
			
			if (!isSpace) {//如果没有个人空间
				//设置头像
				String path = fileUp();
				//设置积分
				Pointaction pointaction = iMemberService.findPointactionByPointAction("CREATEPERSONALSPACE");
				long spoint = memberinfo.getPoint()+pointaction.getPoint();
				memberinfo.setPoint(spoint);
				//设置积分记录
				Date date = new Date();
				Pointrecord pointrecord = new Pointrecord(pointaction, memberinfo.getNickname(), date);
				iMemberService.save(pointrecord);
				//设置积分等级
				Graderecord graderecord = iMemberService.findMemberinfoLevel(spoint);
				memberinfo.setGraderecord(graderecord);
				
				memberspace.setIcon(path);
				memberspace.setMemberinfo(memberinfo);
				memberinfo.setMemberSpace(memberspace);
				iMemberService.saveOrUpdate(memberinfo);
			}else {
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String fileUp(){//返回头像在服务器中的路径
		//1.获取工程的根路径
		String path = "";
		String root = ServletActionContext.getServletContext().getRealPath("/iconImgs");
		File f = new File(root);
		if (!f.exists()) {
			f.mkdir();
		}
		try {
			//创建输入流 读取上传的文件对象
			InputStream in = new FileInputStream(icon);
			//创建输出流
			byte[] b = new byte[1024];
			File file = new File(f, iconFileName);
			OutputStream out = new FileOutputStream(file);
			while(in.read(b) != -1){
				out.write(b);
			}
			out.flush();
			in.close();
			out.close();
			path = "/SSHRunCommunity/iconImgs/"+iconFileName;
			System.out.println(path+"-----------------");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
