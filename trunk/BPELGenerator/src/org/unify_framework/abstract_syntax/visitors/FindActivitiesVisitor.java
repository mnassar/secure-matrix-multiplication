package org.unify_framework.abstract_syntax.visitors;

import org.unify_framework.abstract_syntax.Activity;
import org.unify_framework.abstract_syntax.AndJoin;
import org.unify_framework.abstract_syntax.AndSplit;
import org.unify_framework.abstract_syntax.AtomicActivity;
import org.unify_framework.abstract_syntax.CompositeActivity;
import org.unify_framework.abstract_syntax.ControlInputPort;
import org.unify_framework.abstract_syntax.ControlOutputPort;
import org.unify_framework.abstract_syntax.EndEvent;
import org.unify_framework.abstract_syntax.StartEvent;
import org.unify_framework.abstract_syntax.Transition;
import org.unify_framework.abstract_syntax.XorJoin;
import org.unify_framework.abstract_syntax.XorSplit;
import org.unify_framework.abstract_syntax.connector_mechanism.ActivityPointcut;
import org.unify_framework.abstract_syntax.connector_mechanism.AtomicActivityPointcut;
import org.unify_framework.abstract_syntax.connector_mechanism.CompositeActivityPointcut;
import org.unify_framework.abstract_syntax.connector_mechanism.Composition;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FindActivitiesVisitor implements ElementVisitor {
	
	private class PatternMatcher {
		
		private Pattern pattern;
		private boolean matchCompositeActivities;
		private boolean matchAtomicActivities;
		
		PatternMatcher(String regex, boolean matchCompositeActivities, boolean matchAtomicActivities) {
			
			this.pattern = Pattern.compile(regex);
			this.matchCompositeActivities = matchCompositeActivities;
			this.matchAtomicActivities = matchAtomicActivities;
		}
		
		boolean match(Activity activity) {
			
			if (activity instanceof AtomicActivity) {
				return this.matchAtomicActivities && this.pattern.matcher(activity.getQualifiedName()).matches();
			} else /* if (activity instanceof CompositeActivity) */ {
				return this.matchCompositeActivities && this.pattern.matcher(activity.getQualifiedName()).matches();
			}
		}
	}
	
	private static final Log log = LogFactory.getLog(FindActivitiesVisitor.class);

	private String regex;
	private PatternMatcher patternMatcher;
	private Set<Activity> result;
	
	public FindActivitiesVisitor(ActivityPointcut activityPointcut) {
		
		this.regex = activityPointcut.getExpression();
		boolean matchCompositeActivities = true;
		boolean matchAtomicActivities = true;
		if (activityPointcut instanceof CompositeActivityPointcut) {
			matchAtomicActivities = false;
		} else if (activityPointcut instanceof AtomicActivityPointcut) {
			matchCompositeActivities = false;
		}
		this.patternMatcher = new PatternMatcher(this.regex, matchCompositeActivities, matchAtomicActivities);
		this.result = new HashSet<Activity>();
	}
	
	public Set<Activity> getResult() {
		
		return this.result;
	}

	@Override
	public void visit(AndJoin join) {
		
		// Do nothing
	}

	@Override
	public void visit(AndSplit split) {
		
		// Do nothing
	}

	@Override
	public void visit(AtomicActivity activity) {
		
		String activityQualifiedName = activity.getQualifiedName();
		log.debug("Checking whether atomic activity '" + activityQualifiedName + "' matches regex '" + this.regex + "'");
		if (this.patternMatcher.match(activity)) {
			log.debug("Atomic activity '" + activityQualifiedName + "' matches regex '" + this.regex + "'");
			this.result.add(activity);
		}
	}

	@Override
	public void visit(Transition transition) {
		
		// Do nothing
	}

	@Override
	public void visit(CompositeActivity activity) {
		
		String activityQualifiedName = activity.getQualifiedName();
		log.debug("Checking whether composite activity '" + activityQualifiedName + "' matches regex '" + this.regex + "'");
		if (this.patternMatcher.match(activity)) {
			log.debug("Composite activity '" + activityQualifiedName + "' matches regex '" + this.regex + "'");
			this.result.add(activity);
		}
		for (Activity childActivity : activity.getActivities()) {
			childActivity.accept(this);
		}
	}

	@Override
	public void visit(Composition composition) {
		
		for (CompositeActivity concern : composition.getConcerns()) {
			concern.accept(this);
		}
	}

	@Override
	public void visit(ControlInputPort controlPort) {
		
		// Do nothing
	}

	@Override
	public void visit(ControlOutputPort controlPort) {
		
		// Do nothing
	}

	@Override
	public void visit(EndEvent event) {
		
		// Do nothing
	}

	@Override
	public void visit(StartEvent event) {
		
		// Do nothing
	}

	@Override
	public void visit(XorJoin join) {
		
		// Do nothing
	}

	@Override
	public void visit(XorSplit split) {
		
		// Do nothing
	}
	
	public void visitComposition(Composition composition) {
		
		composition.accept(this);
	}
	
	public void visitWorkflow(CompositeActivity workflow) {
		
		workflow.accept(this);
	}
}
