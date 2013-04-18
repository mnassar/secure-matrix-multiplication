package org.unify_framework.abstract_syntax.connector_mechanism;

/**
 * @author <a href="mailto:niels@unify-framework.org">Niels Joncheere</a>
 * @version $Id: DataMapping.java 13215 2010-09-01 13:27:15Z njonchee $
 */
public class DataMapping {
	
	public static abstract class Variable {

		public abstract String getVariable();
		public abstract String getPart();
		public abstract String getQuery();
		public abstract String getString();
	}
	
	public static class MessageTypeVariable extends Variable {
		
		private String variable;
		private String part;
		private String query;
		private String queryNamespacePrefix;
		private String queryNamespace;
		
		public MessageTypeVariable(QualifiedName variable, QualifiedName part, String query, String queryNamespacePrefix, String queryNamespace) {
			
			this.variable = variable.toString();
			this.part = part.toString();
			this.query = query;
			this.queryNamespacePrefix = queryNamespacePrefix;
			this.queryNamespace = queryNamespace;
		}
		
		@Override
		public String getVariable() {
			
			return this.variable;
		}
		
		@Override
		public String getPart() {
			
			return this.part;
		}
		
		@Override
		public String getQuery() {
			
			return this.query;
		}
		
		@Override
		public String getString() {
			
			return null;
		}
		
		public String getQueryNamespacePrefix() {
			
			return this.queryNamespacePrefix;
		}
		
		public String getQueryNamespace() {
			
			return this.queryNamespace;
		}
	}
	
	public static class StringConstant extends Variable {
		
		private String string;
		
		public StringConstant(String string) {
			
			this.string = string;
		}
		
		@Override
		public String getVariable() {
			
			return null;
		}
		
		@Override
		public String getPart() {
			
			return null;
		}
		
		@Override
		public String getQuery() {
			
			return null;
		}
		
		@Override
		public String getString() {
			
			return this.string;
		}
	}
	
	public static class TypeVariable extends Variable {
		
		private String variable;
		private String query;
		private String queryNamespacePrefix;
		private String queryNamespace;
		
		public TypeVariable(QualifiedName variable, String query, String queryNamespacePrefix, String queryNamespace) {
			
			this.variable = variable.toString();
			this.query = query;
			this.queryNamespacePrefix = queryNamespacePrefix;
			this.queryNamespace = queryNamespace;
		}
		
		@Override
		public String getVariable() {
			
			return this.variable;
		}
		
		@Override
		public String getPart() {
			
			return null;
		}
		
		@Override
		public String getQuery() {
			
			return this.query;
		}
		
		@Override
		public String getString() {
			
			return null;
		}
		
		public String getQueryNamespacePrefix() {
			
			return this.queryNamespacePrefix;
		}
		
		public String getQueryNamespace() {
			
			return this.queryNamespace;
		}
	}
	
	private Variable lhs;
	private Variable rhs;
	
	public DataMapping(Variable lhs, String string) {
		
		this.setLhs(lhs);
		this.setRhs(new StringConstant(string));
	}
	
	public DataMapping(Variable lhs, Variable rhs) {
		
		this.setLhs(lhs);
		this.setRhs(rhs);
	}
	
	public Variable getLhs() {
		
		return this.lhs;
	}
	
	public Variable getRhs() {
		
		return this.rhs;
	}
	
	public void setLhs(Variable lhs) {
		
		this.lhs = lhs;
	}
	
	public void setRhs(Variable rhs) {
		
		this.rhs = rhs;
	}
}
