package com.briup.run.common.util.ip;

public class TestIPSeeker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IPSeeker seeker = IPSeeker.getInstance();
		String ip = "218.9.252.75";
		String country = seeker.getCountry(ip);
		String area = seeker.getArea(ip);
		System.out.println( country+"*******" + area);
	}
}
