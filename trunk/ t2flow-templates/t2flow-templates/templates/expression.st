expression(first_workflow_name,
sec_workflow_name,
first_workflow_output_port,
sec_workflow_input_port,
first_dataflow_ref,
sec_dataflow_ref,
dataflow1,
dataflow2) ::= << 
<workflow xmlns="http://taverna.sf.net/2008/xml/t2flow" version="1" producedBy="taverna-2.4.0">
<dataflow id="0c649ec7-b682-4bd0-9f58-86fe39c75e7d" role="top"><name>Workflow4</name><inputPorts /><outputPorts /><processors><processor><name>$first_workflow_name$</name><inputPorts /><outputPorts><port><name>$first_workflow_output_port$</name><depth>0</depth><granularDepth>0</granularDepth></port></outputPorts><annotations /><activities><activity><raven><group>net.sf.taverna.t2.activities</group><artifact>dataflow-activity</artifact><version>1.4</version></raven><class>net.sf.taverna.t2.activities.dataflow.DataflowActivity</class><inputMap /><outputMap><map from="$first_workflow_output_port$" to="$first_workflow_output_port$" /></outputMap><configBean encoding="dataflow"><dataflow ref="$first_dataflow_ref$" /></configBean><annotations /></activity></activities><dispatchStack><dispatchLayer><raven><group>net.sf.taverna.t2.core</group><artifact>workflowmodel-impl</artifact><version>1.4</version></raven><class>net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.Parallelize</class><configBean encoding="xstream"><net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.ParallelizeConfig xmlns="">
  <maxJobs>1</maxJobs>
</net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.ParallelizeConfig></configBean></dispatchLayer><dispatchLayer><raven><group>net.sf.taverna.t2.core</group><artifact>workflowmodel-impl</artifact><version>1.4</version></raven><class>net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.ErrorBounce</class><configBean encoding="xstream"><null xmlns="" /></configBean></dispatchLayer><dispatchLayer><raven><group>net.sf.taverna.t2.core</group><artifact>workflowmodel-impl</artifact><version>1.4</version></raven><class>net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.Failover</class><configBean encoding="xstream"><null xmlns="" /></configBean></dispatchLayer><dispatchLayer><raven><group>net.sf.taverna.t2.core</group><artifact>workflowmodel-impl</artifact><version>1.4</version></raven><class>net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.Retry</class><configBean encoding="xstream"><net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.RetryConfig xmlns="">
  <backoffFactor>1.0</backoffFactor>
  <initialDelay>1000</initialDelay>
  <maxDelay>5000</maxDelay>
  <maxRetries>0</maxRetries>
</net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.RetryConfig></configBean></dispatchLayer><dispatchLayer><raven><group>net.sf.taverna.t2.core</group><artifact>workflowmodel-impl</artifact><version>1.4</version></raven><class>net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.Invoke</class><configBean encoding="xstream"><null xmlns="" /></configBean></dispatchLayer></dispatchStack><iterationStrategyStack><iteration><strategy /></iteration></iterationStrategyStack></processor><processor><name>$sec_workflow_name$</name><inputPorts><port><name>$sec_workflow_input_port$</name><depth>0</depth></port></inputPorts><outputPorts /><annotations /><activities><activity><raven><group>net.sf.taverna.t2.activities</group><artifact>dataflow-activity</artifact><version>1.4</version></raven><class>net.sf.taverna.t2.activities.dataflow.DataflowActivity</class><inputMap><map from="$sec_workflow_input_port$" to="$sec_workflow_input_port$" /></inputMap><outputMap /><configBean encoding="dataflow"><dataflow ref="$sec_dataflow_ref$" /></configBean><annotations /></activity></activities><dispatchStack><dispatchLayer><raven><group>net.sf.taverna.t2.core</group><artifact>workflowmodel-impl</artifact><version>1.4</version></raven><class>net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.Parallelize</class><configBean encoding="xstream"><net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.ParallelizeConfig xmlns="">
  <maxJobs>1</maxJobs>
</net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.ParallelizeConfig></configBean></dispatchLayer><dispatchLayer><raven><group>net.sf.taverna.t2.core</group><artifact>workflowmodel-impl</artifact><version>1.4</version></raven><class>net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.ErrorBounce</class><configBean encoding="xstream"><null xmlns="" /></configBean></dispatchLayer><dispatchLayer><raven><group>net.sf.taverna.t2.core</group><artifact>workflowmodel-impl</artifact><version>1.4</version></raven><class>net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.Failover</class><configBean encoding="xstream"><null xmlns="" /></configBean></dispatchLayer><dispatchLayer><raven><group>net.sf.taverna.t2.core</group><artifact>workflowmodel-impl</artifact><version>1.4</version></raven><class>net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.Retry</class><configBean encoding="xstream"><net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.RetryConfig xmlns="">
  <backoffFactor>1.0</backoffFactor>
  <initialDelay>1000</initialDelay>
  <maxDelay>5000</maxDelay>
  <maxRetries>0</maxRetries>
</net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.RetryConfig></configBean></dispatchLayer><dispatchLayer><raven><group>net.sf.taverna.t2.core</group><artifact>workflowmodel-impl</artifact><version>1.4</version></raven><class>net.sf.taverna.t2.workflowmodel.processor.dispatch.layers.Invoke</class><configBean encoding="xstream"><null xmlns="" /></configBean></dispatchLayer></dispatchStack><iterationStrategyStack><iteration><strategy><cross><port name="$sec_workflow_input_port$" depth="0" /></cross></strategy></iteration></iterationStrategyStack></processor></processors><conditions /><datalinks><datalink><sink type="processor"><processor>$sec_workflow_name$</processor><port>$sec_workflow_input_port$</port></sink><source type="processor"><processor>$first_workflow_name$</processor><port>$first_workflow_output_port$</port></source></datalink></datalinks><annotations><annotation_chain_2_2 encoding="xstream"><net.sf.taverna.t2.annotation.AnnotationChainImpl xmlns="">
  <annotationAssertions>
    <net.sf.taverna.t2.annotation.AnnotationAssertionImpl>
      <annotationBean class="net.sf.taverna.t2.annotation.annotationbeans.IdentificationAssertion">
        <identification>0c649ec7-b682-4bd0-9f58-86fe39c75e7d</identification>
      </annotationBean>
      <date>2012-12-03 10:46:03.249 UTC</date>
      <creators />
      <curationEventList />
    </net.sf.taverna.t2.annotation.AnnotationAssertionImpl>
  </annotationAssertions>
</net.sf.taverna.t2.annotation.AnnotationChainImpl></annotation_chain_2_2><annotation_chain_2_2 encoding="xstream"><net.sf.taverna.t2.annotation.AnnotationChainImpl xmlns="">
  <annotationAssertions>
    <net.sf.taverna.t2.annotation.AnnotationAssertionImpl>
      <annotationBean class="net.sf.taverna.t2.annotation.annotationbeans.IdentificationAssertion">
        <identification>2c720de3-0338-4823-85c7-460ad115fe6a</identification>
      </annotationBean>
      <date>2012-12-03 09:38:33.549 UTC</date>
      <creators />
      <curationEventList />
    </net.sf.taverna.t2.annotation.AnnotationAssertionImpl>
  </annotationAssertions>
</net.sf.taverna.t2.annotation.AnnotationChainImpl></annotation_chain_2_2></annotations></dataflow>
$dataflow2$
$dataflow1$
</workflow>
>>