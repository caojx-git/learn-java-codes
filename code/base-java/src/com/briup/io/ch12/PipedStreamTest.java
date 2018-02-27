package com.briup.io.ch12;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedStreamTest {
	public static void main(String[] args) throws IOException{
		PipedOutputStream pos = null;
		PipedInputStream pis = null;
		pos = new PipedOutputStream();
		pis = new PipedInputStream(pos);
		
		pos.write(255);
		System.out.println(pis.read());

		pos.close();
		pis.close();
		
	}
}
