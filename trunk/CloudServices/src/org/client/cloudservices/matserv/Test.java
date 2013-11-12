package org.client.cloudservices.matserv;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String server_ip =null;
		InetAddress ip =null;
			Enumeration<NetworkInterface> en;
			try {
				en = NetworkInterface.getNetworkInterfaces();
				while(en.hasMoreElements()){
				    NetworkInterface ni=(NetworkInterface) en.nextElement();
				    Enumeration<InetAddress> ee = ni.getInetAddresses();
				    while(ee.hasMoreElements()) {
				        InetAddress ia= (InetAddress) ee.nextElement();
				        server_ip = ia.getHostAddress();
				        ip = ia;
				        System.out.println(ia.getHostAddress());
				       System.out.println(ia.getHostName());
				    }
				}
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}

}
