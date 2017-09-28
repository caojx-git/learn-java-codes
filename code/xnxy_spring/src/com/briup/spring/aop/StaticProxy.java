package com.briup.spring.aop;


/*
 * 静态代理
 * */
public class StaticProxy {
	public static void main(String[] args) {
		Product product = new Company(); //委托公司
		Product product2 = new ComPro(product);//代理公司
		product2.create();
		
	}
}


interface Product{
	public abstract void create();
}

//委托类
class Company implements Product{

	@Override
	public void create() {
		System.out.println("生产鞋");
		
	}
}

//代理类
class ComPro implements Product{
	
	private Product product;

	public ComPro(){}
	
	public ComPro(Product product){
		this.product = product;
	}
	
	@Override
	public void create() {
		System.out.println("阿迪达斯");
		product.create();
	}
	
}