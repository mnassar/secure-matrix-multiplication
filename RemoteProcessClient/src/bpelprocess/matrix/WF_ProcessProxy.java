package bpelprocess.matrix;

public class WF_ProcessProxy implements bpelprocess.matrix.WF_Process {
  private String _endpoint = null;
  private bpelprocess.matrix.WF_Process wF_Process = null;
  
  public WF_ProcessProxy() {
    _initWF_ProcessProxy();
  }
  
  public WF_ProcessProxy(String endpoint) {
    _endpoint = endpoint;
    _initWF_ProcessProxy();
  }
  
  private void _initWF_ProcessProxy() {
    try {
      wF_Process = (new bpelprocess.matrix.WF_ProcessServiceLocator()).getWF_ProcessPort();
      if (wF_Process != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wF_Process)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wF_Process)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wF_Process != null)
      ((javax.xml.rpc.Stub)wF_Process)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public bpelprocess.matrix.WF_Process getWF_Process() {
    if (wF_Process == null)
      _initWF_ProcessProxy();
    return wF_Process;
  }
  
  public void process(int input, java.lang.String matA, java.lang.String matB, javax.xml.rpc.holders.StringHolder result, javax.xml.rpc.holders.StringHolder instanceID) throws java.rmi.RemoteException{
    if (wF_Process == null)
      _initWF_ProcessProxy();
    wF_Process.process(input, matA, matB, result, instanceID);
  }
  
  public void multResult1(java.lang.String in, int op_id, int job_id) throws java.rmi.RemoteException{
    if (wF_Process == null)
      _initWF_ProcessProxy();
    wF_Process.multResult1(in, op_id, job_id);
  }
  
  public void multResult2(java.lang.String in, int op_id, int job_id) throws java.rmi.RemoteException{
    if (wF_Process == null)
      _initWF_ProcessProxy();
    wF_Process.multResult2(in, op_id, job_id);
  }
  
  public void addResult(java.lang.String in, int op_id, int job_id) throws java.rmi.RemoteException{
    if (wF_Process == null)
      _initWF_ProcessProxy();
    wF_Process.addResult(in, op_id, job_id);
  }
  
  public void multResult3(java.lang.String in, int job_id, int op_id) throws java.rmi.RemoteException{
    if (wF_Process == null)
      _initWF_ProcessProxy();
    wF_Process.multResult3(in, job_id, op_id);
  }
  
  public void multResult4(java.lang.String in, int job_id, int op_id) throws java.rmi.RemoteException{
    if (wF_Process == null)
      _initWF_ProcessProxy();
    wF_Process.multResult4(in, job_id, op_id);
  }
  
  public void cpCallback1(java.lang.String in, int op_id, int job_id) throws java.rmi.RemoteException{
    if (wF_Process == null)
      _initWF_ProcessProxy();
    wF_Process.cpCallback1(in, op_id, job_id);
  }
  
  public void cpCallback2(java.lang.String in, int op_id, int job_id) throws java.rmi.RemoteException{
    if (wF_Process == null)
      _initWF_ProcessProxy();
    wF_Process.cpCallback2(in, op_id, job_id);
  }
  
  public void cpCallback3(java.lang.String in, int op_id, int job_id) throws java.rmi.RemoteException{
    if (wF_Process == null)
      _initWF_ProcessProxy();
    wF_Process.cpCallback3(in, op_id, job_id);
  }
  
  public void cpCallback4(java.lang.String in, int op_id, int job_id) throws java.rmi.RemoteException{
    if (wF_Process == null)
      _initWF_ProcessProxy();
    wF_Process.cpCallback4(in, op_id, job_id);
  }
  
  
}