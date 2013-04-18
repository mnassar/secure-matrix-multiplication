package org.unify_framework.abstract_syntax;

import java.io.OutputStream;
import java.util.List;

/**
 * Represents a composite unit of work.
 * 
 * <p>A composite activity consists of a number of children, which are
 * {@link Node} objects:</p>
 * 
 * <ul>
 * <li>One {@link StartEvent} object, which represents the point where the
 * activity's control flow starts</li>
 * <li>One {@link EndEvent} object, which represents the point where the
 * activity's control flow ends</li>
 * <li>Zero or more {@link Activity} objects, which represent the activity's
 * units of work</li>
 * <li>Zero or more {@link ControlNode} objects, which allow routing the
 * activity's control flow</li>
 * </ul>
 * 
 * <p>Each of these {@link Node} objects has a number of
 * {@link ControlInputPort} objects and {@link ControlOutputPort} objects.  The
 * activity's control flow is specified by connecting these
 * {@link ControlOutputPort} objects to {@link ControlInputPort} objects using
 * {@link Transition} objects.</p>
 * 
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: CompositeActivity.java 18692 2011-11-30 14:59:35Z njonchee $
 * 
 * @model
 */
public interface CompositeActivity extends Activity {
	
	/**
	 * Adds a new child activity to the activity.
	 * 
	 * @param activity The child activity to be added
	 */
	public void addActivity(Activity activity);
	
	/**
	 * Adds a new child to the activity.
	 * 
	 * @param child The child to be added
	 */
	public void addChild(Node child);
	
	/**
	 * Adds a new control node to the activity.
	 * 
	 * @param controlNode The control node to be added
	 */
	public void addControlNode(ControlNode controlNode);
	
	/**
	 * Connects two of the activity's children.
	 * 
	 * <p>This method will create a new {@link Transition} object from the
	 * <code>source</code> to the <code>destination</code>.</p>
	 * 
	 * @param source
	 * @param destination
	 */
	public Transition connect(ControlOutputPort source, ControlInputPort destination);
	
	/**
	 * Connects two of the activity's children.
	 * 
	 * <p>This method will create a new {@link Transition} object from the
	 * <code>sourceNode</code>'s {@link ControlOutputPort} object to the
	 * <code>destinationNode</code>'s {@link ControlInputPort} object.</p>
	 * 
	 * @param sourceNode
	 * @param destinationNode
	 */
	public Transition connect(Node sourceNode, Node destinationNode);
	
	public void generateGraphviz(OutputStream os);
	
	/**
	 * @return The activity's child activities
	 */
	public List<Activity> getActivities();
	
	/**
	 * @return The activity's children
	 * 
	 * @model opposite="parent" containment="true"
	 */
	public List<Node> getChildren();
	
	/**
	 * @return The activity's control nodes
	 */
	public List<ControlNode> getControlNodes();
	
	/**
	 * @return The activity's end event
	 */
	public EndEvent getEndEvent();
	
	/**
	 * @return The activity's start event
	 */
	public StartEvent getStartEvent();
	
	/**
	 * @return The activity's transitions
	 * 
	 * @model
	 */
	public List<Transition> getTransitions();
	
	/**
	 * Removes a child activity from the activity.
	 * 
	 * @param activity The child activity to be removed
	 */
	public void removeActivity(Activity activity);
	
	/**
	 * Removes a transition from the activity.
	 * 
	 * @param transition The transition to be removed
	 */
	public void removeTransition(Transition transition);
	
	/**
	 * Replaces an existing child activity with a new child activity
	 * 
	 * @param existingActivity
	 * @param newActivity
	 */
	public void replaceActivity(Activity existingActivity, Activity newActivity);
}
