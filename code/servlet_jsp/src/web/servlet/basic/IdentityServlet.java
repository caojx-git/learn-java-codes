package web.servlet.basic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.*;
/*
 * 使用Servlet输出图片的验证码。原理是，服务器生成一个包含随机字符串的图片发给客户端
 * 客户端提交数据是需要填写字符串作为验证，由于字符串保存在图片中，因此机器很难识别，从而
 * 达到防止有些人使用计算机恶意程序发送信息的目的
 * */
public class IdentityServlet extends HttpServlet{
	//随机字典
	private static final char[] CHARS = {'2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'M', 'N', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'a', 'b', 'c', 'd', 'e' , 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
			};//不包括0、o、l、l等难辨字符
	
	private static Random random = new Random(); //随机数
	/*
	 * 获取6位随机数
	 * */
	public static String getRandomString() { //获取6位随机数
		StringBuffer buffer = new StringBuffer(); //字符缓冲
		for(int i = 0; i < 6; i++) {
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		}
		return buffer.toString();
	}
	
	/*
	 * 随机获取颜色
	 * */
	public static Color getRandomColor() {
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}
	
	/*
	 * 返回某颜色的反色
	 * */
	
	public static Color getReverseColor(Color c) {
		return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("image/jpeg");   //设置输出类型必须的
		String randomString = getRandomString(); //随机字符串
		req.getSession(true).setAttribute("randomString", "randomString");//方到session中
		int width = 100;		//图片的宽度
		int height = 30;		//图片的高度
		
		Color color = getRandomColor();	//随机获取颜色
		Color reverse = getReverseColor(color); //反色用于前景色
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR); //创建一个彩色图片
		Graphics2D g = bi.createGraphics();		//获取图片对象
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16)); //设置字体
		g.setColor(color);	//设置颜色
		g.fillRect(0, 0, width, height); //设置背景
		g.setColor(reverse);	//设置颜色
		g.drawString(randomString, 18, 20);  //绘制随机字符
		/*
		 * 绘制噪点最多100个
		 * */
		for(int i = 0 , n = random.nextInt(100); i < n; i++) {
			g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);
		}
		
		ServletOutputStream out = resp.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.flush();
	}
	
	
}


