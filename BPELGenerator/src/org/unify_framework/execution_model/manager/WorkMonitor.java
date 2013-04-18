package org.unify_framework.execution_model.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

public class WorkMonitor implements Observer, Runnable {
	
	static final Log log = LogFactory.getLog(WorkMonitor.class);
	
	private Display display;
	private Shell shell;
	List list;
	Map<String, WorkItem> workItems;
	private Thread thread;
	
	public WorkMonitor() {
		
//		log.debug("Creating work monitor...");
		this.workItems = new HashMap<String, WorkItem>();
	}
	
	public void addWorkItem(WorkItem workItem) {
		
		// Add the work item to the work items collection: 
		final String workItemName = workItem.getName();
		log.debug("Adding work item '" + workItemName + "' to work monitor...");
		this.workItems.put(workItemName, workItem);
		
		// Wait for display creation:
		while (this.display == null) {
			try {
				log.debug("Waiting for display creation...");
				Thread.sleep(100);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		// Add the work item to the shell:
		this.display.asyncExec(new Runnable() {

			@Override
			public void run() {
				
				log.debug("Adding work item to shell...");
				WorkMonitor.this.list.add(workItemName);
			}
		});
	}
	
	public boolean isAlive() {
		
		return this.thread.isAlive();
	}
	
	public void removeWorkItem(WorkItem workItem) {
		
		// Remove the work item from the work items collection:
		final String workItemName = workItem.getName();
		log.debug("Removing work item '" + workItemName + "' from work monitor...");
		this.workItems.remove(workItem);
		
		// Remove the work item from the shell:
		this.display.asyncExec(new Runnable() {
			
			@Override
			public void run() {
				
				log.debug("Removing work item from shell...");
				WorkMonitor.this.list.remove(workItemName);
			}
		});
	}

	@Override
	public void run() {
		
		log.debug("Running work monitor...");
		runEventLoop();
	}
	
	public void runEventLoop() {
		
		// Create the shell:
		this.display = new Display();
		log.debug("Created the display");
		this.shell = new Shell(this.display);
		this.shell.setSize(315, 400);
		this.shell.setText("Work Monitor");
		Label label = new Label(this.shell, SWT.NONE);
		label.setBounds(20, 15, 260, 15);
		label.setText("Active work items:");
		this.list = new List(this.shell, SWT.BORDER | SWT.V_SCROLL);
		this.list.setBounds(20, 45, 260, 200);
		
		this.list.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
//				log.debug("The user has double-clicked the list");
				int index = WorkMonitor.this.list.getSelectionIndex();
				if (index > -1) {
					int x = e.x;
					int y = e.y;
//					log.debug("The user has double-clicked the list at location {" + x + ", " + y + "}");
					Rectangle itemBounds = new Rectangle(0, index * WorkMonitor.this.list.getItemHeight(), WorkMonitor.this.list.getBounds().width, WorkMonitor.this.list.getItemHeight());
//					log.debug("The bounds of the selected item are " + itemBounds);
					if (itemBounds.contains(x, y)) {
						String item = WorkMonitor.this.list.getItem(index);
						log.debug("The user has double-clicked on item '" + item + "'");
						WorkMonitor.this.workItems.get(item).finish();
					} else {
						// Do nothing
					}
				}
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				
				// Do nothing
			}
			
			@Override
			public void mouseUp(MouseEvent e) {
				
				// Do nothing
			}
		});
		
		// Position the shell at the center of the primary monitor:
		Monitor primary = this.display.getPrimaryMonitor();
		Rectangle primaryBounds = primary.getBounds();
		Rectangle shellBounds = this.shell.getBounds();
		int x = primaryBounds.x + (primaryBounds.width - shellBounds.width) / 2;
		int y = primaryBounds.y + (primaryBounds.height - shellBounds.height) / 2;
		this.shell.setLocation(x, y);
		
		// Run the event loop:
		this.shell.open();
		while (!this.shell.isDisposed()) {
			if (!this.display.readAndDispatch()) {
				this.display.sleep();
			}
		}
		this.display.dispose();
	}
	
	public void start() {
		
		this.thread = new Thread(this);
		this.thread.start();
	}

	@Override
	public void update(Observable o, Object arg) {
		
		WorkItem workItem = (WorkItem) arg;
		if (workItem.getExecutionManager() == null) {
			removeWorkItem(workItem);
		} else {
			addWorkItem(workItem);
		}
	}
}
