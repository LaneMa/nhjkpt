package org.framework.core.udp;

import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {

	
	public static void main(String args[]) {
		try {
			
			//byte[] message="testinfo分水利电力111我1111".getBytes();
			
			byte[] message="9;1;1;2;0;0;690;20;8;0;0;0".getBytes();
			
			// 初始化一个UDP包。
			// DatagramPacket的构造方法中必须使用InetAddress，而不能是IP地址或者域名
			InetAddress address = InetAddress.getByName("127.0.0.1");
			int port= 7777;
			
			
			// 注意：如果在构造DatagramPacket时，不提供IP地址和端口号，
			// 则需要调用DatagramSocket的connect方法，否则无法发送UDP包
			DatagramPacket packet = new DatagramPacket(message, message.length);
			DatagramSocket dsocket = new DatagramSocket();
			dsocket.connect(address, port);
			dsocket.send(packet);
			System.out.println("Send: " + new String(message,"UTF-8"));
			dsocket.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
