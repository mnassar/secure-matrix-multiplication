package org.client.cloudservices.matserv;

import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.axis2.AxisFault;
import org.client.cloudservices.matserv.MatServStub.Multiply;

public class CloudTest {

	/**
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
	
	 	MatServStub ms= new MatServStub();
	 
		Multiply multipl = new Multiply();
		multipl.setJob_id("1");
		multipl.setOp_id("2");
		multipl.setMatA_ID("1458_10_10_1");
		multipl.setMatB_ID("282_10_10_2");
		multipl.setCallback("http://localhost:8080/ode/processes/AdditiveSplitting");
	
	
		ms.multiply(multipl);
	/*
		
		String splitA = new String("24343_10_10_1");
		int start = splitA.indexOf("_")+1;
			Pattern patt = Pattern.compile("\\D|_");
			Matcher match = patt.matcher(splitA.substring(start));
			boolean found = match.find();
			int end = found == false ? splitA.length() : match.end();        	                	            	  
			String mat_size = splitA.substring(start,start+end-1);
			System.out.println(mat_size);
		*/	
	}

}
