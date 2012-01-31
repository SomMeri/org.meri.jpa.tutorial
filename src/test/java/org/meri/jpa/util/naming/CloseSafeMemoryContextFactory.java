package org.meri.jpa.util.naming;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public class CloseSafeMemoryContextFactory implements InitialContextFactory {

	private static final String SHARE_DATA_PROPERTY = "org.osjava.sj.jndi.shared";

	public CloseSafeMemoryContextFactory() {
		super();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Context getInitialContext(Hashtable environment) throws NamingException {
		Hashtable sharingEnv = (Hashtable) environment.clone();

		// all instances will share data
		if (!sharingEnv.containsKey(SHARE_DATA_PROPERTY)) {
			sharingEnv.put(SHARE_DATA_PROPERTY, "true");
		}
		
		CloseSafeMemoryContext context = new CloseSafeMemoryContext(sharingEnv);
		return context;
	}

}
