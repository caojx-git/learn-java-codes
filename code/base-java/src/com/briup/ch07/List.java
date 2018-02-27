package com.briup.ch07;

import java.util.Iterator;

public interface List{
	//在集合的末尾添加新的元素
	public abstract boolean add(Object o);
	//在集合的指定位置插入一个新的元素
	public abstract void add(int index,Object o);
	//清空当前集合
	public abstract void clear();
	//判断集合中是否包含某个对象
	public abstract boolean contains(Object o);
	//返回指定坐标中存放的元素
	public abstract Object get(int index);
	//返回指定元素在集合中第一次出现的坐标,
	//如果元素不存在，返回-1
	public abstract int indexOf(Object o);
	//判断集合是否为空
	public abstract boolean isEmpty();
	//删除指定坐标中存放的元素,并将删除的元素
	//返回
	public abstract Object remove(int index);
	//删除集合中第一次出现的指定元素
	public abstract boolean remove(Object o);
	//用指定元素替换集合中指定位置的元素
	//并且返回被替换的元素
	public abstract Object set(int i,Object o);
	//返回集合中的元素个数
	public abstract int size();
	//返回一个包含当前集合中所有元素的数组
	public abstract Object[] toArray();
	
	public abstract Iterator iterator();
}
