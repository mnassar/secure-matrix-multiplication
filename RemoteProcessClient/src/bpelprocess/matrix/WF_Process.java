/**
 * WF_Process.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bpelprocess.matrix;

public interface WF_Process extends java.rmi.Remote {
    public void process(int input, java.lang.String matA, java.lang.String matB, javax.xml.rpc.holders.StringHolder result, javax.xml.rpc.holders.StringHolder instanceID) throws java.rmi.RemoteException;
    public void multResult1(java.lang.String in, int op_id, int job_id) throws java.rmi.RemoteException;
    public void multResult2(java.lang.String in, int op_id, int job_id) throws java.rmi.RemoteException;
    public void addResult(java.lang.String in, int op_id, int job_id) throws java.rmi.RemoteException;
    public void multResult3(java.lang.String in, int job_id, int op_id) throws java.rmi.RemoteException;
    public void multResult4(java.lang.String in, int job_id, int op_id) throws java.rmi.RemoteException;
    public void cpCallback1(java.lang.String in, int op_id, int job_id) throws java.rmi.RemoteException;
    public void cpCallback2(java.lang.String in, int op_id, int job_id) throws java.rmi.RemoteException;
    public void cpCallback3(java.lang.String in, int op_id, int job_id) throws java.rmi.RemoteException;
    public void cpCallback4(java.lang.String in, int op_id, int job_id) throws java.rmi.RemoteException;
}
