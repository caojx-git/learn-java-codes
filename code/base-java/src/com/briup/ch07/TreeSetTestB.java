package com.briup.ch07;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class TreeSetTestB {
	public static void main(String[] args){
		Set set = new TreeSet(new MyComparator());
		for(int i=1;i<=10;i++){
			set.add(i);
		}
		Iterator i = set.iterator();
		while(i.hasNext()){
			int t = (int)i.next();
			System.out.println(t);
		}
	}
}

class MyComparator implements Comparator{
	@Override
	public int compare(Object o1, Object o2) {
		return -1;
		/*int n1 = (int)o1;
		int n2 = (int)o2;
		if(n1%2==1&&n2%2==0)
		{
			return -1;
		}else if(n1%2==0&&n2%2==1){
			return 1;
		}else{
			if(n1%2==0){
				return n2-n1;
			}else
				return n1-n2;
		}*/
	}
}