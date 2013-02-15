package org.example.www.matserv;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.bpelprocess.MatprocessServiceCallbackHandler;
import org.bpelprocess.MatprocessServiceStub;
import org.bpelprocess.MatprocessServiceStub.MatprocessRequest;
import org.bpelprocess.MatprocessServiceStub.MatprocessResponse;
import org.bpelprocess.MatprocessServiceStub.OnCallback;
import org.example.www.matserv.MatServStub.Compute;

public class AsynchClient {

	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws RemoteException, InterruptedException {
		// TODO Auto-generated method stub
		MatprocessServiceStub ms= new MatprocessServiceStub();
		MatprocessRequest compute0= new MatprocessRequest();
		//MatprocessServiceCallbackHandler callback = new MatprocessServiceCallbackHandler(){} ;
		
		compute0.setInput("callback");
			
	
		//ComputeResponse result= ms.compute(compute0);
		//callback.receiveResultcompute(result);
		MatprocessResponse res=  ms.process(compute0);
	//	callback.wait();
	    Thread.sleep(1000);
		OnCallback onCallback2= new OnCallback();
		onCallback2.setInput("callback");
	    ms.onCallback(onCallback2);
	//callback.wait();
		System.out.println("CALLBACK" + res.getResult());
	}

}
