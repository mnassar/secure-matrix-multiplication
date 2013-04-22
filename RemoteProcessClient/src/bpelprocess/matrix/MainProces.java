package bpelprocess.matrix;

import java.rmi.RemoteException;

import javax.xml.rpc.holders.StringHolder;

import org.apache.axis.AxisFault;

import bpelprocess.matrix.WF_ProcessServiceStub.WF_ProcessRequest;
import bpelprocess.matrix.WF_ProcessServiceStub.WF_ProcessResponse;

public class MainProces {

	/**
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
		WF_ProcessServiceStub  ms = new WF_ProcessServiceStub();
		
		WF_ProcessRequest req= new WF_ProcessRequest();
		req.setInput(116);
		req.setMatA("A_10000");
		req.setMatB("B_10000");
		
		WF_ProcessResponse res = ms.process(req);
		
		System.out.println("Result is "+ res.getResult()+" for instance ID = "+res.getInstanceID());
		
	}

}
