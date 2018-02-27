package com.briup.ch071;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class ComparatorTest {
	public static void main(String[] args){
		TreeSet set = new TreeSet(new Mycomparator());	
		set.add(new Teacher(7));
		set.add(new Teacher(3));
		set.add(new Teacher(2));
		set.add(new Teacher(5));
		set.add(new Teacher(6));
		set.add(new Teacher(4));
		set.add(new Teacher(1));
		Iterator i = set.iterator();
		while(i.hasNext()){
			Teacher t = (Teacher)i.next();
			System.out.println(t);
		}
	}
}

class Mycomparator implements Comparator{
	public int compare(Object o1,Object o2){
		System.out.println(o1+","+o2);
		Teacher t1 = (Teacher)o1;
		Teacher t2 = (Teacher)o2;
		return t2.id-t1.id;
	}
}

class Teacher implements Comparable{
	public int id;
	public Teacher(){}
	public Teacher(int id){
		this.id = id;
	}
	@Override
	public int compareTo(Object o) {
		Teacher t = (Teacher)o;
		System.out.println(id+"compareto"+t.id);
		return t.id-id;
	}
	//濡傛灉鏄�锛堟鏁帮級鐨勮瘽锛屾搴忔帓鍒楁妸鏂版坊鍔犵殑鍏冪礌鏀惧湪鏃х殑鍏冪礌鍚庨潰
	//0鐨勮瘽璁や负鍓嶅悗鍏冪礌鐩稿悓锛屼笉娣诲姞
	//濡傛灉鏄互涓礋鏁扮殑璇濓紝鎸夌収鍊掑彊鎺掑垪
	public String toString(){
		return id+"";
	}
	
	
}