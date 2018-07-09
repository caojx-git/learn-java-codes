package com.briup.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * 本案例参考 疯狂Java讲义.李刚.pdf
 * nio Channel操作
 */
public class FileChannelTest {

    public static void main(String[] args) {
        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            File f = new File("/Users/caojx/code/learn/code/base-java/src/com/briup/nio/FileChannelTest.java");
            //创建FileInputStream, 以该文件输入流创建FileChannel
            inChannel = new FileInputStream(f).getChannel();

            //将FileChannel里的全部数据映射成ByteBuffer
            MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, f.length());

            //使用GBK的字符集来创建解码器
            Charset charset = Charset.forName("UTF-8");
            //以文件输出流创建FileBuffer 用以控制输出
            outChannel = new FileOutputStream("/Users/caojx/code/learn/code/base-java/src/com/briup/nio/a.txt").getChannel();
            //直接将buffer的数据全部输出
            outChannel.write(buffer);

            //再次调用buffer的clear方法，复原limit,position的位置
            buffer.clear();

            //创建解码器对象
            CharsetDecoder decoder = charset.newDecoder();
            //使用解码器将ByteBuffer转换成CharBuffer
            CharBuffer charBuffer = decoder.decode(buffer);

            //CharBuffer的toString方法可以获取对应的字符串
            System.out.println(charBuffer);


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (inChannel == null) {
                    inChannel.close();
                }
                if (outChannel == null) {
                    outChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
