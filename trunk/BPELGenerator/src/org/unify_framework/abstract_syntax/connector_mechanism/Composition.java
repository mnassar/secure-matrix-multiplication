package org.unify_framework.abstract_syntax.connector_mechanism;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlPort;
import org.unify_framework.abstract_syntax.visitors.Element;
import org.unify_framework.abstract_syntax.visitors.ElementVisitor;
import org.unify_framework.abstract_syntax.visitors.FindActivitiesVisitor;
import org.unify_framework.abstract_syntax.visitors.FindActivityVisitor;
import org.unify_framework.abstract_syntax.visitors.FindControlPortVisitor;
import org.unify_framework.instances.bpel.BpelProcess;
import org.unify_framework.instances.bpel.serializer.BpelSerializer;
import org.unify_framework.source_code_weaver.SourceCodeWeaver;

public class Composition implements Element {
	
	private static final Log log = LogFactory.getLog(Composition.class);
	
	private String name;
	private CompositeActivity baseConcern;
	private Set<CompositeActivity> otherConcerns;
	private List<Connector> connectors;
	private SourceCodeWeaver scw;
	
	public Composition() {
		
		this.otherConcerns = new HashSet<CompositeActivity>();
		this.connectors = new ArrayList<Connector>();
	}
	
	public Composition(String name, CompositeActivity baseConcern) {
		
		this();
		log.debug("Creating composition '" + name + "' with base concern '" + baseConcern.getName() + "'");
		setName(name);
		setBaseConcern(baseConcern);
	}

	@Override
	public void accept(ElementVisitor visitor) {
		
		visitor.visit(this);
	}
	
	public void addConcern(CompositeActivity concern) {
		
		this.otherConcerns.add(concern);
	}
	
	public void addConnector(Connector connector) {
		
		this.connectors.add(connector);
	}
	
	public void addConnector(String connector) {
		
		this.connectors.add(ConnectorEnum.parseConnector(connector, this));
	}
	
	public void addConnectorFromFile(File file) {
		
		try {
			addConnector(FileUtils.readFileToString(file));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addConnectorFromFile(String path) {
		
		try {
			addConnector(FileUtils.readFileToString(new File(path)));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Set<Activity> findActivities(ActivityPointcut activityPointcut) {
		
		FindActivitiesVisitor visitor = new FindActivitiesVisitor(activityPointcut);
		visitor.visitComposition(this); // TODO Change in other methods as well?
//		visitor.visitWorkflow(this.baseConcern);
		return visitor.getResult();
	}
	
	public Activity findActivity(QualifiedName qualifiedName) {
		
		FindActivityVisitor visitor = new FindActivityVisitor(qualifiedName);
		visitor.visitComposition(this);
		return visitor.getResult();
	}
	
	public ControlPort findControlPort(ControlPortPointcut controlPortPointcut) {
		
		FindControlPortVisitor visitor = new FindControlPortVisitor(controlPortPointcut);
		visitor.visitComposition(this);
		return visitor.getResult();
	}
	
	public Set<CompositeActivity> getConcerns() {
		
		Set<CompositeActivity> concerns = new HashSet<CompositeActivity>();
		concerns.add(this.baseConcern);
		for (CompositeActivity concern : this.otherConcerns) {
			concerns.add(concern);
		}
		return concerns;
	}
	
	public List<Connector> getConnectors() {
		
		return this.connectors;
	}
	
	public CompositeActivity getBaseConcern() {
		
		return this.baseConcern;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public String getQualifiedName() {
		
		return getName();
	}

	public void serialize(String path) { // TODO Make this method BPEL-independent
		
		BpelSerializer serializer = new BpelSerializer();
		serializer.serialize((BpelProcess) this.getBaseConcern(), path);
	}
	
	public void setBaseConcern(CompositeActivity baseConcern) {
		
		this.baseConcern = baseConcern;
	}
	
	public void setName(String name) {
		
		this.name = name;
	}
	
	public SourceCodeWeaver getWeaver() {
		
		return this.scw;
	}
	
	public void setWeaver(SourceCodeWeaver weaver) {
		
		this.scw = weaver;
	}
	
	public void weave() {
		
		if (this.scw == null) {
			throw new RuntimeException("The composition has no associated source code weaver!");
		} else {
			this.scw.weave();
		}
	}
}
