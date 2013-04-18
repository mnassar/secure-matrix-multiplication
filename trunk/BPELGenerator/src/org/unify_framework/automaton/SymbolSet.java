package org.unify_framework.automaton;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class SymbolSet {
	protected final boolean sign;
	protected final Set<String> symbols;

	public SymbolSet(boolean sign, Collection<String> symbols) {
		this.sign = sign;
		for(String s: symbols)
			if(s == null)
				throw new IllegalArgumentException("Null symbol");
		this.symbols = Collections.unmodifiableSet(new LinkedHashSet<String>(symbols));
	}
	
	public SymbolSet(boolean sign, String... symbols) {
		this(sign,Arrays.asList(symbols));
	}
	
	public boolean match(String s) {
		boolean contains = this.symbols.contains(s);
		return this.sign ? contains : !contains;
	}
	
	public boolean isNone() {
		return this.sign && this.symbols.isEmpty();
	}
	
	public boolean isAll() {
		return !this.sign && this.symbols.isEmpty();
	}
	
	public SymbolSet negate() {
		return new SymbolSet(!this.sign,this.symbols);
	}
	
	private static <E> Set<E> intersection(Set<E> a, Set<E> b) {
		Set<E> res = new LinkedHashSet<E>(a);
		res.retainAll(b);
		return res;
	}
	
	private static <E> Set<E> union(Set<E> a, Set<E> b) {
		Set<E> res = new LinkedHashSet<E>(a);
		res.addAll(b);
		return res;		
	}
	
	private static <E> Set<E> difference(Set<E> a, Set<E> b) {
		Set<E> res = new LinkedHashSet<E>(a);
		res.removeAll(b);
		return res;		
	}
	
	public SymbolSet intersect(SymbolSet o) {
		if(this.sign) {
			if(o.sign)
				return new SymbolSet(true,intersection(this.symbols,o.symbols));
			else
				return new SymbolSet(true,difference(this.symbols,o.symbols));
		} else {
			if(o.sign)
				return new SymbolSet(true,difference(o.symbols,this.symbols));
			else
				return new SymbolSet(false,union(this.symbols,o.symbols));
		}		
	}
	
	public SymbolSet unify(SymbolSet o) {
		if(this.sign) {
			if(o.sign)
				return new SymbolSet(true,union(this.symbols,o.symbols));
			else
				return new SymbolSet(false,difference(o.symbols,this.symbols));
		} else {
			if(o.sign)
				return new SymbolSet(false,difference(this.symbols,o.symbols));
			else
				return new SymbolSet(false,intersection(this.symbols,o.symbols));
		}
	}
	
	public SymbolSet substract(SymbolSet o) {
		return intersect(o.negate());
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this)
			return true;
		else if(o instanceof SymbolSet) {
			SymbolSet so = (SymbolSet)o;
			return this.sign == so.sign && this.symbols.equals(so.symbols);
		} else
			return false;
	}
	
	@Override
	public int hashCode() {
		int c = this.symbols.hashCode();
		return this.sign ? c : -c;
	}
	
	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		if(!this.sign)
			res.append('\u00ac'); // NOT SIGN
		if(this.symbols.isEmpty())
			res.append('\u2205'); // EMPTY SET
		else if(this.symbols.size() == 1)
			res.append(this.symbols.iterator().next());
		else {
			res.append('{');
			boolean first = true;
			for(String s: this.symbols) {
				if(first)
					first = false;
				else
					res.append(',');
				res.append(s);
			}
			res.append('}');
		}
		return res.toString();
	}

}
