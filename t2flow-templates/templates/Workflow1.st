Workflow1(name,url) ::= << 
<workflow xmlns="http://taverna.sf.net/2008/xml/t2flow" version="1" producedBy="taverna-2.4.0">
<dataflow id="dd5c6bc9-cd32-4609-836a-1fd2a9489463" role="top">
<name>$name$</name>
	<inputPorts>
		<port>
			<name>input</name>
			<depth>0</depth>
			<granularDepth>0</granularDepth>
			<annotations />
		</port>
	</inputPorts>
	<outputPorts>
		<port>
			<name>output</name>
			<annotations />
		</port>
	</outputPorts>
	<processors>
	<processor>
		<name>REST_Service</name>
		<inputPorts>
			<port>
				<name>id</name>
				<depth>0</depth>
			</port>
		</inputPorts>
		<outputPorts>
			<port>
				<name>responseBody</name>
				<depth>0</depth>
				<granularDepth>0</granularDepth>
			</port>
		</outputPorts>
		<annotations />
		<activities>
			<activity>
				<raven>
					<group>net.sf.taverna.t2.activities</group>
					<artifact>rest-activity</artifact>
					<version>1.4</version>
				</raven>
				<class>net.sf.taverna.t2.activities.rest.RESTActivity</class>
				<inputMap>
					<map from="id" to="id" />
				</inputMap>
				<outputMap>
					<map from="responseBody" to="responseBody" />
				</outputMap>
				<configBean encoding="xstream">
					<net.sf.taverna.t2.activities.rest.RESTActivityConfigurationBean xmlns="">
						<httpMethod>GET</httpMethod>
  						<urlSignature>$url$</urlSignature>
  						<acceptsHeaderValue>text/plain</acceptsHeaderValue>
  						<contentTypeForUpdates>application/xml</contentTypeForUpdates>
  						<outgoingDataFormat>String</outgoingDataFormat>
  						<sendHTTPExpectRequestHeader>false</sendHTTPExpectRequestHeader>
  						<showRedirectionOutputPort>false</showRedirectionOutputPort>
  						<showActualUrlPort>false</showActualUrlPort>
  						<showResponseHeadersPort>false</showResponseHeadersPort>
  						<escapeParameters>true</escapeParameters>
  						<otherHTTPHeaders />
  				<activityInputs>
    				<entry>
      					<string>id</string>
     					<java-class>java.lang.String</java-class>
    				</entry>
  				</activityInputs>
				</net.sf.taverna.t2.activities.rest.RESTActivityConfigurationBean>
					</configBean>
						<annotations />
			</activity>
		</activities>
		<dispatchStack>
			<dispatchLayer>
				<raven>
					<group>net.sf.taverna.t2.core</group>
					<artifact>workflowmodel-impl</artifact>
					<version>1.4</version>
				</raven>
				<class>net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.Parallelize</class>
				<configBean encoding="xstream">
					<net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.ParallelizeConfig xmlns="">
		  				<maxJobs>1</maxJobs>
					</net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.ParallelizeConfig>
				</configBean>
			</dispatchLayer>
			<dispatchLayer>
				<raven>
					<group>net.sf.taverna.t2.core</group>
					<artifact>workflowmodel-impl</artifact>
					<version>1.4</version>
				</raven>
				<class>net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.ErrorBounce</class><configBean encoding="xstream"><null xmlns="" /></configBean></dispatchLayer><dispatchLayer><raven><group>net.sf.taverna.t2.core</group><artifact>workflowmodel-impl</artifact><version>1.4</version></raven><class>net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.Failover</class><configBean encoding="xstream"><null xmlns="" /></configBean></dispatchLayer><dispatchLayer><raven><group>net.sf.taverna.t2.core</group><artifact>workflowmodel-impl</artifact><version>1.4</version></raven><class>net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.Retry</class>
				<configBean encoding="xstream">
					<net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.RetryConfig xmlns="">
  						<backoffFactor>1.0</backoffFactor>
  						<initialDelay>1000</initialDelay>
  						<maxDelay>5000</maxDelay>
  						<maxRetries>0</maxRetries>
					</net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.RetryConfig>
				</configBean>
			</dispatchLayer>
			<dispatchLayer>
				<raven>
					<group>net.sf.taverna.t2.core</group>
					<artifact>workflowmodel-impl</artifact>
					<version>1.4</version>
				</raven>
				<class>net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.Invoke</class>
				<configBean encoding="xstream">
					<null xmlns="" />
				</configBean>
			</dispatchLayer>
		</dispatchStack>
		<iterationStrategyStack>
			<iteration>
				<strategy>
					<cross>
						<port name="id" depth="0" />
					</cross>
				</strategy>
			</iteration>
		</iterationStrategyStack>
	</processor>
	</processors>
	<conditions />
	<datalinks>
		<datalink>
			<sink type="processor">
				<processor>REST_Service</processor>
					<port>id</port>
			</sink>
			<source type="dataflow">
				<port>input</port>
			</source>
		</datalink>
		<datalink>
			<sink type="dataflow">
				<port>output</port>
			</sink>
			<source type="processor">
				<processor>REST_Service</processor>
					<port>responseBody</port></source>
		</datalink>
	</datalinks>
	<annotations>
		<annotation_chain_2_2 encoding="xstream">
			<net.sf.taverna.t2.annotation.AnnotationChainImpl xmlns="">
  				<annotationAssertions>
    				<net.sf.taverna.t2.annotation.AnnotationAssertionImpl>
      					<annotationBean class="net.sf.taverna.t2.annotation.annotationbeans.IdentificationAssertion">
        					<identification>f0f582c3-d902-4ccb-a9ec-78d6f8ff1025</identification>
      					</annotationBean>
      					<date>2012-11-21 20:48:59.224 UTC</date>
      					<creators />
      					<curationEventList />
    				</net.sf.taverna.t2.annotation.AnnotationAssertionImpl>
			  	</annotationAssertions>
			</net.sf.taverna.t2.annotation.AnnotationChainImpl>
		</annotation_chain_2_2><annotation_chain_2_2 encoding="xstream">
			<net.sf.taverna.t2.annotation.AnnotationChainImpl xmlns="">
  				<annotationAssertions>
    				<net.sf.taverna.t2.annotation.AnnotationAssertionImpl>
      					<annotationBean class="net.sf.taverna.t2.annotation.annotationbeans.IdentificationAssertion">
        					<identification>dd5c6bc9-cd32-4609-836a-1fd2a9489463</identification>
      					</annotationBean>
      					<date>2012-11-21 21:14:31.590 UTC</date>
      					<creators />
      					<curationEventList />
    				</net.sf.taverna.t2.annotation.AnnotationAssertionImpl>
  				</annotationAssertions>
			</net.sf.taverna.t2.annotation.AnnotationChainImpl>
			</annotation_chain_2_2><annotation_chain_2_2 encoding="xstream">
				<net.sf.taverna.t2.annotation.AnnotationChainImpl xmlns="">
  					<annotationAssertions>
    					<net.sf.taverna.t2.annotation.AnnotationAssertionImpl>
      						<annotationBean class="net.sf.taverna.t2.annotation.annotationbeans.IdentificationAssertion">
        						<identification>47f4550e-f127-4a0f-bbe7-849ba2f6f7c9</identification>
      						</annotationBean>
      						<date>2012-11-21 20:42:56.733 UTC</date>
      						<creators />
      						<curationEventList />
    					</net.sf.taverna.t2.annotation.AnnotationAssertionImpl>
  					</annotationAssertions>
				</net.sf.taverna.t2.annotation.AnnotationChainImpl></annotation_chain_2_2><annotation_chain_2_2 encoding="xstream">
				<net.sf.taverna.t2.annotation.AnnotationChainImpl xmlns="">
  					<annotationAssertions>
    					<net.sf.taverna.t2.annotation.AnnotationAssertionImpl>
      						<annotationBean class="net.sf.taverna.t2.annotation.annotationbeans.IdentificationAssertion">
        						<identification>e2e86158-d318-4bb8-85b1-eb3b8901539b</identification>
      						</annotationBean>
      						<date>2012-11-21 21:11:14.153 UTC</date>
      						<creators />
      						<curationEventList />
    					</net.sf.taverna.t2.annotation.AnnotationAssertionImpl>
  					</annotationAssertions>
				</net.sf.taverna.t2.annotation.AnnotationChainImpl>
		</annotation_chain_2_2>
	</annotations>
 </dataflow>
</workflow> >> 