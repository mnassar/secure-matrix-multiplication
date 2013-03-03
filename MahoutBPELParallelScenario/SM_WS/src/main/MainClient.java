package main;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import bpelprocess.matrix.*;
import bpelprocess.matrix.WF_ProcessServiceStub.WF_ProcessRequest;
import bpelprocess.matrix.WF_ProcessServiceStub.WF_ProcessResponse;
public class MainClient {

	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws AxisFault, RemoteException, InterruptedException {
		// TODO Auto-generated method stub
		WF_ProcessServiceStub wf = new WF_ProcessServiceStub();
		 long soTimeout = 5 * 60 * 1000; // Two minutes
		   
		wf._getServiceClient().getOptions().setTimeOutInMilliSeconds(soTimeout);
		WF_ProcessRequest req = new WF_ProcessRequest();
		req.setInput(0);
		WF_ProcessResponse res = wf.process(req);
		Thread.sleep(100000);
		System.out.println("RESULT OF BPEL PROCESS: " + res.getResult());
		
	}

}
