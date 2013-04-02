package main;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.example.www.matserv.Compute;
import org.matserv.MatServCallbackHandler;
import org.matserv.MatServStub;

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
		/*WF_ProcessServiceStub wf = new WF_ProcessServiceStub();
		 long soTimeout = 5 * 60 * 1000; // Two minutes
		   
		wf._getServiceClient().getOptions().setTimeOutInMilliSeconds(soTimeout);
		WF_ProcessRequest req = new WF_ProcessRequest();
		req.setInput(0);
		WF_ProcessResponse res = wf.process(req);
		Thread.sleep(100000);
		System.out.println("RESULT OF BPEL PROCESS: " + res.getResult());
		
		*/
		/*
		MatServStub ms= new MatServStub();
		Compute compute0= new Compute();
		MatServCallbackHandler callback = new MatServCallbackHandler(){} ;
		
		compute0.setJob_id(1000);
		compute0.setMatA_ID("A_100_1B_100_1");
		compute0.setMatB_ID("C_1_A_100_1B_100_1");
		compute0.setOp_id(1);
		compute0.setOperation("copy");
		compute0.setCallback("multiply1");
	
		//ComputeResponse result= ms.compute(compute0);
		//callback.receiveResultcompute(result);
		ms.compute(compute0);
	//	callback.wait();
	    Thread.sleep(100000);
	*/	
	//callback.wait();

		 String runtime_comm2 = "scp -r '/home/farida/Documents/B1' hadoop@10.160.2.27:Documents" ;
		 Process rm_scp_proc;
		try {
			rm_scp_proc = Runtime.getRuntime().exec(runtime_comm2);
			 Reader stdOut = new InputStreamReader(rm_scp_proc.getInputStream (), "US-ASCII");
			 OutputStream stdIn = rm_scp_proc.getOutputStream ();
/*
			    char[] passRequest = new char[1000000];//Choose it big enough for scp password request and all that goes before it
			    int len = 0;
			    while (stdOut.read (passRequest, len, passRequest.length - len)!=-1)
			    {
			        len += stdOut.read (passRequest, len, passRequest.length - len);
			        System.out.println("Length is " + len);
			        System.out.println("String: " + passRequest.toString());
			        if (new String (passRequest, 0, len).contains ("password:")) break;
			    }
*/
			 Thread.sleep(100000);
			    System.out.println ("Password requested");
			    stdIn.write ("broker_1212\n".getBytes ("US-ASCII"));
			    stdIn.flush ();
			    //stdIn.close();
			    int exit_value = rm_scp_proc.waitFor();
			    if(exit_value == 0 )
			    	System.out.println("File Copied!! ");
			 
			    rm_scp_proc.destroy();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				System.out.println("CALLBACK");
		
	}

}
