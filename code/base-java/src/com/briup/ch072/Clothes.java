package com.briup.ch072;

/*
 * 下边的每个枚举实例 都会生成一个.class文件
 * */
public enum Clothes implements Interface{
	//public static Car AUDI = new Car("audi","German");5.0之前
	CHINEL(1000,"France"){ //该花括是类体的一部分
		public void method(){		
			
		}
		public void test(){}  //实现接口中的方法，表明不同枚举实例有不同的行为
	},
	
	GUCCI{
		public void method(){
			
		}
		public void test(){}
	},
	
	ONLY{
		public void method(){
			
		}
		public void test(){}
	};//枚锟劫碉拷实锟斤拷
	private double price;
	private String country;
	
	private Clothes(){
		//枚锟斤拷锟叫的癸拷锟斤拷锟斤拷锟斤拷之锟斤拷锟斤拷锟斤拷锟斤拷为private锟斤拷为锟剿凤拷止锟斤拷锟斤拷锟斤拷锟斤拷锟�
	}
	private Clothes(double price,String country){
		this.price=price;
		this.country=country;
	}
	public double getPrice(){
		return price;
	}
	public String getCountry(){
		return country;
	}

}

interface Interface{
	public abstract void test();
}
