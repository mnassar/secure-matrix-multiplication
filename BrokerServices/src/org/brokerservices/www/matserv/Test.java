package org.brokerservices.www.matserv;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.brokerservices.www.matserv.MatServStub.Add;
import org.brokerservices.www.matserv.MatServStub.AddOnBroker;
import org.brokerservices.www.matserv.MatServStub.Add_list_type0;
import org.brokerservices.www.matserv.MatServStub.Resplit;

public class Test {

	/**
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub

		MatServStub ms = new MatServStub("http://localhost:8080/BrokerServices/services/MatServ/");
		
		
		Resplit resplit = new Resplit();
		resplit.setJob_id("1158");
		resplit.setMatA_ID("7434");
		resplit.setMatB_ID("9385");
		resplit.setOp_id("1234");
		resplit.setCallback("http://localhost:8080/ode/processes/AdditiveSplitting");
		
		ms.resplit(resplit);
		
		/*
		Add add = new Add();
		add.setJob_id("111");
		add.setMatA_ID("8836");
		add.setMatB_ID("506");
		add.setOp_id("123");
		add.setCallback("http://localhost:8080/ode/processes/AdditiveSplitting");
		Add_list_type0 addlist = new Add_list_type0();
		addlist.setCloudURL("127.0.0.1");
		addlist.setSplitName("506_10_10_1");
		add.addAdd_list(addlist);
		
		Add_list_type0 addlist1 = new Add_list_type0();
		addlist1.setCloudURL("127.0.0.1");
		addlist1.setSplitName("506_10_10_2");
		add.addAdd_list(addlist1);
		
		Add_list_type0 addlist2 = new Add_list_type0();
		addlist2.setCloudURL("127.0.0.1");
		addlist2.setSplitName("8836_10_10_1");
		add.addAdd_list(addlist2);
		
		Add_list_type0 addlist3 = new Add_list_type0();
		addlist3.setCloudURL("127.0.0.1");
		addlist3.setSplitName("8836_10_10_2");
		add.addAdd_list(addlist3);
		
		ms.add(add);
		*/
		/*
		Add add = new Add();
		add.setJob_id("111");
		add.setMatA_ID("A");
		add.setMatB_ID("B");
		add.setOp_id("123");
		add.setCallback("http://localhost:8080/ode/processes/AdditiveSplitting");
		Add_list_type0 addlist = new Add_list_type0();
		addlist.setCloudURL("127.0.0.1");
		//addlist.setSplitName("A_B_1");
		addlist.setSplitName("A_10_10_1");
		add.addAdd_list(addlist);
		
		Add_list_type0 addlist1 = new Add_list_type0();
		addlist1.setCloudURL("127.0.0.1");
		//addlist1.setSplitName("A_B_2");
		addlist1.setSplitName("A_10_10_2");
		add.addAdd_list(addlist1);
		
		Add_list_type0 addlist2 = new Add_list_type0();
		addlist2.setCloudURL("127.0.0.1");
		//addlist2.setSplitName("A_B_3");
		addlist2.setSplitName("B_10_10_1");
		add.addAdd_list(addlist2);
		
		Add_list_type0 addlist3 = new Add_list_type0();
		addlist3.setCloudURL("127.0.0.1");
		//addlist3.setSplitName("A_B_4");
		addlist3.setSplitName("B_10_10_2");
		add.addAdd_list(addlist3);
		
		ms.add(add);
		*/
	/*	
		Add add = new Add();
		add.setJob_id("111");
		add.setMatA_ID("506");
		add.setMatB_ID("8836");
		add.setOp_id("123");
		add.setCallback("http://localhost:8080/ode/processes/AdditiveSplitting");
		Add_list_type0 addlist = new Add_list_type0();
		addlist.setCloudURL("127.0.0.1");
		//addlist.setSplitName("A_B_1");
		addlist.setSplitName("506");
		add.addAdd_list(addlist);
		
		Add_list_type0 addlist1 = new Add_list_type0();
		addlist1.setCloudURL("127.0.0.1");
		//addlist1.setSplitName("A_B_2");
		addlist1.setSplitName("8836");
		add.addAdd_list(addlist1);
		ms.add(add);
		
		*/
		
	}

}
