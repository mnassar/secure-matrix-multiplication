/**
 * WF_ProcessServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bpelprocess.matrix;

public class WF_ProcessServiceLocator extends org.apache.axis.client.Service implements bpelprocess.matrix.WF_ProcessService {

    public WF_ProcessServiceLocator() {
    }


    public WF_ProcessServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WF_ProcessServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WF_ProcessPort
    private java.lang.String WF_ProcessPort_address = "http://10.160.2.27:8080/ode/processes/WF_Process";

    public java.lang.String getWF_ProcessPortAddress() {
        return WF_ProcessPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WF_ProcessPortWSDDServiceName = "WF_ProcessPort";

    public java.lang.String getWF_ProcessPortWSDDServiceName() {
        return WF_ProcessPortWSDDServiceName;
    }

    public void setWF_ProcessPortWSDDServiceName(java.lang.String name) {
        WF_ProcessPortWSDDServiceName = name;
    }

    public bpelprocess.matrix.WF_Process getWF_ProcessPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WF_ProcessPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWF_ProcessPort(endpoint);
    }

    public bpelprocess.matrix.WF_Process getWF_ProcessPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            bpelprocess.matrix.WF_ProcessBindingStub _stub = new bpelprocess.matrix.WF_ProcessBindingStub(portAddress, this);
            _stub.setPortName(getWF_ProcessPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWF_ProcessPortEndpointAddress(java.lang.String address) {
        WF_ProcessPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (bpelprocess.matrix.WF_Process.class.isAssignableFrom(serviceEndpointInterface)) {
                bpelprocess.matrix.WF_ProcessBindingStub _stub = new bpelprocess.matrix.WF_ProcessBindingStub(new java.net.URL(WF_ProcessPort_address), this);
                _stub.setPortName(getWF_ProcessPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WF_ProcessPort".equals(inputPortName)) {
            return getWF_ProcessPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://matrix.bpelprocess", "WF_ProcessService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://matrix.bpelprocess", "WF_ProcessPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WF_ProcessPort".equals(portName)) {
            setWF_ProcessPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
