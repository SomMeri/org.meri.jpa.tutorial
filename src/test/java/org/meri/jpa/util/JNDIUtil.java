package org.meri.jpa.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.spi.NamingManager;

import org.meri.jpa.util.naming.ImMemoryDefaultContextFactoryBuilder;
import org.osjava.sj.jndi.AbstractContext;

public class JNDIUtil {
	
	private final static String JNDI_BIND_DATA_SOURCE_ERROR = "Could not bind data to JNDI. Most likely, JNDI was not initialized yet (see JNDIUtil.initializeJNDI()). Original exception: ";

	/**
	 * Initializes standalone in-memory JNDI. 
	 * 
	 * First run installs standalone in-memory JNDI. Any subsequent 
	 * run deletes all data stored in JNDI.
	 *
	 * @throws ConfigurationException runtime wrapper over NamingException 
	 */
	public void initializeJNDI() {
		if (jndiInitialized()) {
			cleanAllInMemoryData();
		} else {
			installDefaultContextFactoryBuilder();
		}
	}

	/**
	 * Convenience method - deletes the property from the JNDI.
	 * 
	 * @param propertyName name of the property to be deleted
	 * @throws ConfigurationException runtime wrapper over NamingException 
	 */
	public void cleanJNDI(String propertyName) {
		try {
			Context ctx = new InitialContext();
			Object previousValue = ctx.lookup(propertyName);
			if (previousValue!=null)
				ctx.unbind(propertyName);
		} catch (NamingException e) {
			// We can not solve the problem. We will let it go up without
			// having to declare the exception every time.
			throw new ConfigurationException(e);
		}

	}

	/**
	 * Convenience method - finds property value in the JNDI.
	 * 
	 * @param propertyName name of the property to be found
	 * @throws ConfigurationException runtime wrapper over NamingException 
	 */
	public Object lookup(String propertyName) {
		try {
			Context ctx = new InitialContext();
			Object value = ctx.lookup(propertyName);
			
			return value;
		} catch (NamingException e) {
			// We can not solve the problem. We will let it go up without
			// having to declare the exception every time.
			throw new ConfigurationException(JNDI_BIND_DATA_SOURCE_ERROR, e);
		}
	}

	/**
	 * Convenience method - stores the property in the JNDI. 
	 * 
	 * This method either binds or rebinds the property value. 
	 * 
	 * @param propertyName name of the property to be stored
	 * @throws ConfigurationException runtime wrapper over NamingException 
	 */
	public void bindToJNDI(String propertyName, Object value) {
		try {
			Context ctx = new InitialContext();
			Object previousValue = ctx.lookup(propertyName);
			if (previousValue==null)
				ctx.bind(propertyName, value);
			else 
				ctx.rebind(propertyName, value);
		} catch (NamingException e) {
			// We can not solve the problem. We will let it go up without
			// having to declare the exception every time.
			throw new ConfigurationException(JNDI_BIND_DATA_SOURCE_ERROR, e);
		}
	}

	private boolean jndiInitialized() {
		return NamingManager.hasInitialContextFactoryBuilder();
	}

	private void cleanAllInMemoryData() {
		CleanerContext cleaner = new CleanerContext();
		try {
			cleaner.close();
		} catch (NamingException e) {
			throw new RuntimeException("Memory context cleaning failed:", e);
		}
	}

	private void installDefaultContextFactoryBuilder() {
		try {
			NamingManager.setInitialContextFactoryBuilder(new ImMemoryDefaultContextFactoryBuilder());
		} catch (NamingException e) {
			//We can not solve the problem. We will let it go up without
			//having to declare the exception every time.
			throw new ConfigurationException(e);
		}
	}

}

@SuppressWarnings({ "rawtypes", "unchecked" })
class CleanerContext extends AbstractContext {
	
	private static Hashtable environnement = new Hashtable();
	static {
		environnement.put("org.osjava.sj.jndi.shared", "true");
	}

	public CleanerContext() {
		super(environnement);
	}

	@Override
	public Context createSubcontext(Name name) throws NamingException {
		return null;
	}
	
}