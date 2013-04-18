package org.unify_framework.concrete_syntax.parser;

import java.io.File;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.unify_framework.concrete_syntax.CsAndJoin;
import org.unify_framework.concrete_syntax.CsAndSplit;
import org.unify_framework.concrete_syntax.CsAtomicActivity;
import org.unify_framework.concrete_syntax.CsBasicTransition;
import org.unify_framework.concrete_syntax.CsCompositeActivity;
import org.unify_framework.concrete_syntax.CsEndEvent;
import org.unify_framework.concrete_syntax.CsStartEvent;

public class ActivityDigester {
	
	private static final Log log = LogFactory.getLog(ActivityDigester.class);
	
	public static CsCompositeActivity parse(File file) {
		
		try {
			
			log.info("Parsing '" + file.getCanonicalPath() + "'...");
			Digester digester = new Digester();
			digester.setValidating(false);
			
			digester.addObjectCreate("*/CompositeActivity", CsCompositeActivity.class);
			digester.addSetProperties("*/CompositeActivity", "name", "name");
			digester.addRule("*/CompositeActivity", new CustomSetNextRule("addChild"));
			
			digester.addObjectCreate("*/CompositeActivity/StartEvent", CsStartEvent.class);
			digester.addSetProperties("*/CompositeActivity/StartEvent", "name", "name");
			digester.addSetNext("*/CompositeActivity/StartEvent", "setStartEvent" );
			
			digester.addObjectCreate("*/CompositeActivity/AtomicActivity", CsAtomicActivity.class);
			digester.addSetProperties("*/CompositeActivity/AtomicActivity", "name", "name");
			digester.addSetNext("*/CompositeActivity/AtomicActivity", "addChild");
			
			digester.addObjectCreate("*/CompositeActivity/EndEvent", CsEndEvent.class);
			digester.addSetProperties("*/CompositeActivity/EndEvent", "name", "name");
			digester.addSetNext("*/CompositeActivity/EndEvent", "setEndEvent");
			
			digester.addObjectCreate("*/CompositeActivity/Transition", CsBasicTransition.class);
			digester.addSetProperties("*/CompositeActivity/Transition",
					new String[] { "source", "destination" },
					new String[] { "source", "destination" });
			digester.addSetNext("*/CompositeActivity/Transition", "addTransition");
			
			digester.addObjectCreate("*/CompositeActivity/AndSplit", CsAndSplit.class);
			digester.addSetProperties("*/CompositeActivity/AndSplit", "source", "source");
			digester.addObjectCreate("*/CompositeActivity/AndSplit/Destination", Destination.class);
			digester.addSetProperties("*/CompositeActivity/AndSplit/Destination", "destination", "destination");
			digester.addSetNext("*/CompositeActivity/AndSplit/Destination", "addDestination");
			digester.addSetNext("*/CompositeActivity/AndSplit", "addTransition");
			
			digester.addObjectCreate("*/CompositeActivity/AndJoin", CsAndJoin.class);
			digester.addSetProperties("*/CompositeActivity/AndJoin", "destination", "destination");
			digester.addObjectCreate("*/CompositeActivity/AndJoin/Source", Source.class);
			digester.addSetProperties("*/CompositeActivity/AndJoin/Source", "source", "source");
			digester.addSetNext("*/CompositeActivity/AndJoin/Source", "addSource");
			digester.addSetNext("*/CompositeActivity/AndJoin", "addTransition");
			
			CsCompositeActivity ca = (CsCompositeActivity) digester.parse(file);
			log.info("Parsed '" + file.getCanonicalPath() + "'");
			return ca;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}
}
