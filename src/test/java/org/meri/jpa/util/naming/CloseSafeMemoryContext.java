package org.meri.jpa.util.naming;

import java.util.Hashtable;

import javax.naming.NamingException;

import org.osjava.sj.memory.MemoryContext;

@SuppressWarnings({"rawtypes"}) 
public class CloseSafeMemoryContext extends MemoryContext {

	public CloseSafeMemoryContext(Hashtable env) {
		super(env);
	}

	@Override
	public void close() throws NamingException {
		// Original context lost all data on close();
		// That made it unusable for my tests. 
	}

}
