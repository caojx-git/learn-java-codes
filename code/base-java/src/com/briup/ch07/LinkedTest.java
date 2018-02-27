package com.briup.ch07;

public class LinkedTest {
	private class Node{//节点
		public Node pre;
		public Object element;
		public Node next;
		
		public Node(Node pre,Object element,Node next){
			this.pre = pre;
			this.element = element;
			this.next = next;
		}
	}
	private Node first;
	private int size;
	private Node last;
	public static void main(String[] args){
		
	}
}
