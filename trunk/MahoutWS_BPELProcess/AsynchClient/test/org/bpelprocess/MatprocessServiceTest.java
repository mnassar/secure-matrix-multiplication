

/**
 * MatprocessServiceTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package org.bpelprocess;

    /*
     *  MatprocessServiceTest Junit test case
    */

    public class MatprocessServiceTest extends junit.framework.TestCase{

     
        /**
         * Auto generated test method
         */
        public  void testprocess() throws java.lang.Exception{

        org.bpelprocess.MatprocessServiceStub stub =
                    new org.bpelprocess.MatprocessServiceStub();//the default implementation should point to the right endpoint

           org.bpelprocess.MatprocessServiceStub.MatprocessRequest matprocessRequest6=
                                                        (org.bpelprocess.MatprocessServiceStub.MatprocessRequest)getTestObject(org.bpelprocess.MatprocessServiceStub.MatprocessRequest.class);
                    // TODO : Fill in the matprocessRequest6 here
                matprocessRequest6.setInput("callback");
                        assertNotNull(stub.process(
                        matprocessRequest6));
                  



        }
        
         /**
         * Auto generated test method
         */
        public  void testStartprocess() throws java.lang.Exception{
            org.bpelprocess.MatprocessServiceStub stub = new org.bpelprocess.MatprocessServiceStub();
             org.bpelprocess.MatprocessServiceStub.MatprocessRequest matprocessRequest6=
                                                        (org.bpelprocess.MatprocessServiceStub.MatprocessRequest)getTestObject(org.bpelprocess.MatprocessServiceStub.MatprocessRequest.class);
                    // TODO : Fill in the matprocessRequest6 here
                matprocessRequest6.setInput("Input");

                stub.startprocess(
                         matprocessRequest6,
                    new tempCallbackN65548()
                );
              


        }

        private class tempCallbackN65548  extends org.bpelprocess.MatprocessServiceCallbackHandler{
            public tempCallbackN65548(){ super(null);}

            public void receiveResultprocess(
                         org.bpelprocess.MatprocessServiceStub.MatprocessResponse result
                            ) {
                System.out.println(result.getResult());
            }

            public void receiveErrorprocess(java.lang.Exception e) {
                fail();
            }

        }
      
          /**
          * Auto generated test method
          */
          public  void testonCallback() throws java.lang.Exception{

          org.bpelprocess.MatprocessServiceStub stub =
          new org.bpelprocess.MatprocessServiceStub();//the default implementation should point to the right endpoint
          org.bpelprocess.MatprocessServiceStub.OnCallback onCallback8=
                  (org.bpelprocess.MatprocessServiceStub.OnCallback)getTestObject(org.bpelprocess.MatprocessServiceStub.OnCallback.class);
                      // TODO : Fill in the onCallback8 here
                  onCallback8.setInput("callback");

                  //There is no output to be tested!
                  stub.onCallback(
                  onCallback8);

              
          }
      
        //Create an ADBBean and provide it as the test object
        public org.apache.axis2.databinding.ADBBean getTestObject(java.lang.Class type) throws java.lang.Exception{
           return (org.apache.axis2.databinding.ADBBean) type.newInstance();
        }

        
        

    }
    