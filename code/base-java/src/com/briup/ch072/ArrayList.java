package com.briup.ch072;
import java.util.Iterator;
import java.util.List;

public class ArrayList {
	//���������ݵ�����
	private Object[] array;
	//��ʾ��������ЧԪ�صĸ���
	private int count;
	//��ʾ�������ĳ���
	private int max_length;

	public ArrayList(){
		max_length = 5;
		array = new Object[max_length];
	}
	private void grow(){
		Object[] temp = new Object[max_length+10];
		//�Ƚ�ԭ������������ݸ��Ƶ���������
		System.arraycopy(array,0,temp,0,count);
		array = temp;
		max_length += 10;
	}

	public boolean add(Object o){
		if(count>=max_length){
			grow();
		}
		array[count] = o;
		count++;
		return true;
	}
	public void add(int index,Object o){
		if(index>count||index<0){
			return;
		}
		if(count>=max_length){
			grow();
		}
		System.arraycopy(array,index,array,
		index+1,count-index);
		array[index] = o;
		count++;
	}
	public void clear(){
		for(int i=0;i<count;i++){
			array[i] = null;
		}
		count = 0;
	}
	public Object get(int index){
		if(index<0||index>=count){
			return null;
		}
		return array[index];
	}
	public boolean contains(Object o){
		for(int i=0;i<count;i++){
			if(array[i].equals(o)){
				return true;			
			}
		}
		return false;
	}
	public int indexOf(Object o){
		for(int i=0;i<count;i++){
			if(array[i].equals(o)){
				return i;
			}
		}
		return -1;
	}
	public boolean isEmpty(){
		return count==0;
	}
	public Object remove(int index){
		if(index<0||index>=count){
			return null;
		}
		Object temp = array[index];
		System.arraycopy(array,index+1,array,
			index,count-index-1);
		count--;
		return temp;
	}
	public boolean remove(Object o){
		int i = indexOf(o);
		if(i!=-1){
			remove(i);
			return true;
		}else{
			return false;
		}
	}
	public Object set(int index,Object o){
		Object temp = array[index];
		array[index] = o;
		return temp;
	}
	public int size(){
		return count;
	}
	public Object[] toArray(){
		Object[] a = new Object[count];
		System.arraycopy(array,0,a,0,count);
		return a;
	}
	private class MyIterator implements Iterator{
		//�α��ʼ��
		private int cursor = 0;
		
		@Override
		public boolean hasNext() {
			return cursor<count;
		}

		@Override
		public Object next() {
			Object temp = array[cursor];
			cursor++;
			return temp;
		}

		@Override
		public void remove() {
			ArrayList.this.remove(cursor-1);
		}
	}
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return new MyIterator();
	}
}
