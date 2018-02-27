package com.briup.run.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.briup.run.common.util.RandomChar;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class AuthImg extends HttpServlet {

	private static final long serialVersionUID = -8281845659081613239L;

	private Font mFont = new Font("Times New Roman", Font.PLAIN, 18);

	public void init() throws ServletException {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();

		int width = 60, height = 20;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();
		Random random = new Random();

		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		g.setFont(mFont);

		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		String rand = RandomChar.getChars(3, 4);
		char c;
		for (int i = 0; i < 4; i++) {
			c = rand.charAt(i);
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(String.valueOf(c), 13 * i + 6, 16);
		}
		HttpSession seesion = request.getSession();
		seesion.setAttribute("authCode", rand);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image);
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
