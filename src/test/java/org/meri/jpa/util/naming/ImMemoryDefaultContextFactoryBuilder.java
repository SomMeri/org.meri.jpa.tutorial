package org.meri.jpa.util.naming;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ImMemoryDefaultContextFactoryBuilder implements InitialContextFactoryBuilder {

	public ImMemoryDefaultContextFactoryBuilder() {
		super();
	}

	public InitialContextFactory createInitialContextFactory(Hashtable env) throws NamingException {
		String requestedFactory = null;
		if (env!=null) {
			requestedFactory = (String) env.get(Context.INITIAL_CONTEXT_FACTORY);
		}

		if (requestedFactory != null) {
			return simulateBuilderlessNamingManager(requestedFactory);
		}
		
		return new CloseSafeMemoryContextFactory();
	}

	private InitialContextFactory simulateBuilderlessNamingManager(String requestedFactory) throws NoInitialContextException {
		try {
			InitialContextFactory result = (InitialContextFactory) loadClass(requestedFactory).newInstance();
			return result;
		} catch (Exception e) {
			NoInitialContextException ne = new NoInitialContextException("Cannot instantiate class: " + requestedFactory);
			ne.setRootCause(e);
			throw ne;
		}
	}

	private Class loadClass(String className) throws ClassNotFoundException {
		ClassLoader cl = getContextClassLoader();
		return Class.forName(className, true, cl);
	}

	private ClassLoader getContextClassLoader() {
		return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				return Thread.currentThread().getContextClassLoader();
			}
		});
	}

}