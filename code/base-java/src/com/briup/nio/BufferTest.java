package com.briup.nio;

import java.nio.CharBuffer;

/**
 * 本案例参考 疯狂Java讲义.李刚.pdf
 * nio Buffer操作
 */
public class BufferTest {

    public static void main(String[] args) {
        //创建Buffer
        CharBuffer buff = CharBuffer.allocate(8);
        System.out.println("capacity:"+buff.capacity());
        System.out.println("limit:"+buff.limit());
        System.out.println("position:"+buff.position());

        //放入元素
        buff.put("a");
        buff.put("b");
        buff.put("c");

        System.out.println("加入三个元素后,position="+buff.position());

        //调用flip()
         buff.flip();
        System.out.println("执行flip后，position="+buff.position());
        System.out.println("执行flip后，limit="+buff.limit());

        //去除第一个元素
        System.out.println("第一个元素（position=0）:"+buff.get());
        System.out.println("去除一个元素后，position="+buff.position());

        //调用clear方法
        buff.clear();

        System.out.println("执行clear()后，limit="+buff.limit());
        System.out.println("执行clear()后，position="+buff.position());
        System.out.println("执行clear()后，缓冲区的内容并没有被清除:"+buff.get(2));
        System.out.println("执行绝对读取后，position="+buff.position());
    }
}
