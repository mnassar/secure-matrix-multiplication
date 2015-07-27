# Introduction #

Cloud Computing has the advantage of on-demand network access to a shared pool of configurable computing resources that requires minimal management overhead. So the computational power of cloud customers is not limited by their resource-constraint devices. They can outsource their computations into the cloud with unlimited computing resources in a pay-per-use manner.

However the cloud computing paradigm is subject to many threats due to the sharing of physical infrastructure and the use of virtualization. Examples of these threats include but not limited to malicious code installation, impersonation, data leaks, denial of service, repudiation of virtual machine launches, exploits of virtual machine resets and interference with virtual machines or data belonging to other customers ..etc. That's why **secure** outsourcing of data and computation is challenging in this emerging computing model in terms of input privacy and cheating detection. Other security properties that should be preserved when outsourcing computations to public cloud providers:
  1. Confidentiality to avoid the risk of data leakage. The cloud provider should not be able to learn anything from the input data nor the result data.
  1. Integrity to ensure no tampering with the data during the transmission and on storage.
  1. Verifiability to ensure that the outsourced computation is done correctly to protect against cloud provider cheating.
  1. Efficiency to minimize response time and lower latency.

Some approaches tend to encrypt sensitive data before outsourcing (1) so as to
provide end-to-end data confidentiality assurance in the cloud and beyond. However, ordinary data encryption techniques in essence prevent cloud from performing
any meaningful operation of the underlying plaintext data (2), making the computation over encrypted data a very hard problem. Beside it is not efficient enough for practical applications nor secure tamper-proof hardware that are expensive and not scalable.

In this project, a prototype for secure outsourcing of scientific computations using multiple clouds and without using cryptography is implemented. We address the issues of a real deployment in terms of distributed, reliable and secure storage, secure data transfer and computation. In our approach, the user communicates with a trusted broker which manages secure parallel computations on untrusted commodity clouds. The broker performs data distribution and coordination of secure computations, while the commodity clouds perform expensive parallel computations. We start with matrix multiplication which is an expensive operation (e.g. takes 𝑂(𝑛3) using the brute force approach), and needs to be outsourced. It is also included in various and more complex computations across different domains.


# System Design #


## Use Case Diagram ##

The client can upload the matrices files to the broker which will then -depending on the protocol used- copy it encrypted or splits of it to the commodity clouds as will be discussed later. The client then requests the compute service on the broker with the operation or expression to be done over these matrices and the broker responds with a link that will be used to download the results. In secure outsourcing of computations, it is assumed that the client machine is of limited resources; limited disk space to store all input and output files at the same time; limited RAM and CPU power to  process this data and that the computations are long-running jobs. The client can then monitor the job status and get information about it like the total runtime. At last, the client can download the results from the broker. Figure 1 shows the use case for the secure outsourcing of computations as a service.

![http://secure-matrix-multiplication.googlecode.com/files/usecase.png](http://secure-matrix-multiplication.googlecode.com/files/usecase.png)

**Figure 1: Use case Diagram**

---

## Block Diagram ##

The broker has web-services exposed that are used to start the client services. The computation service calls protocol setup to choose and set parameters of the secure protocol/mechanism to use (e.g. additive splitting, homomorphic encryption, secret sharing and randomization ….). Depending on the protocol the way the data or matrices are uploaded to the cloud providers differ whether encrypted or split. The number of clouds to be used also differs. The Data Placement Controller is responsible to save metadata about which matrix have been uploaded to which server. Depending also on the protocol setup, the services to call differ; so a work flow is generated that coordinates which services to invoke and how. We use ODE work flow engine that executes work flows in BPEL (Business Process Execution Language). The actual steps of computations used by the protocol are done by services offered by the clouds and by services existing on the broker and they are invoked by the workflow as shown in Figure 2.

![http://secure-matrix-multiplication.googlecode.com/files/blockdiagram.png](http://secure-matrix-multiplication.googlecode.com/files/blockdiagram.png)

**Figure2: Block Diagram**

---

## Broker ##

### Web services ###
Details to be added soon

### Date Placement Controller ###
Details to be added ...

### Workflow Generation ###

Workflow generation module generates BPEL files dynamically based on different protocol scenarios. The source code can be checked out [here](http://code.google.com/p/secure-matrix-multiplication/source/browse/#svn%2Ftrunk%2FBPELGenerator). In this task we made use of unify framework developed by Vrije University in Brussels (http://soft.vub.ac.be/soft/).  But this framework is not complete for all BPEL specification and structures, it doesn't provide means for correlation sets definition and binding, and some others features. So we have updated it with the new features we need.

### Workflow Execution ###
Generated WS-BPEL workflow is then deployed using Apache ODE (Orchestration Director Engine) http://ode.apache.org/index.html on our broker Apache Tomcat web server. It coordinates the invocation of cloud and broker web services and their callbacks.




---


## Cloud Services ##
Details to be added soon


---

# Reference #

  1. Cloud Security Alliance, “Security guidance for critical areas of focus in cloud computing,” 2009, online at http://www.cloudsecurityalliance.org
  1. C. Gentry, “Computing arbitrary functions of encrypted data,” Commun. ACM, vol. 53, no. 3, pp. 97–105, 2010