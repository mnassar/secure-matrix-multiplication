package org.workflow.translate.expression;

import org.unify_framework.instances.bpel.BpelProcess;

public class Workflow {

	
	private BpelProcess process ;
	private	String queryLanguage="urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0";
	
	public Workflow(String process_name, String process_namespace)
	{
	
		process = new BpelProcess(process_name, process_namespace);
	
		//TODO: Add to the process namespace declarations for the additive splitting and other protocols
		//and add namespace for the cloud and broker services
		/*
		 *  process.addNamespaceDeclaration("ns", "http://www.example.org/MatServ24/");
			process.addNamespaceDeclaration("ns1", "http://www.example.org/MatServ25/");
			process.addNamespaceDeclaration("ns2", "http://www.example.org/MatServ26/");
			process.addNamespaceDeclaration("ns3", "http://www.example.org/MatServ33/");
			process.addNamespaceDeclaration("ns4", "http://www.example.org/MatServ27/");
		    process.addNamespaceDeclaration("tns", "http://matrix.bpelprocess");
		    */
		process.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
		process.addNamespaceDeclaration("ode", "http://www.apache.org/ode/type/extension");
		process.setExitOnStandardFault("yes");
		process.setSuppressJoinFailure("yes");
	}
}
