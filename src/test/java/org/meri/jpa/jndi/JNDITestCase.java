package org.meri.jpa.jndi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Test;
import org.meri.jpa.util.JNDIUtil;

public class JNDITestCase {

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void testDummyContext() throws NamingException {
		Hashtable environnement = new Hashtable();
		environnement.put(Context.INITIAL_CONTEXT_FACTORY, "org.meri.jpa.jndi.MyContextFactory");

		Context ctx = new InitialContext(environnement);
		Object value = ctx.lookup("jndiName");
		ctx.close();

		assertEquals("stored value", value);
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void testExplicitFactory() throws NamingException {
		JNDIUtil jndiUtil = new JNDIUtil();
		jndiUtil.initializeJNDI();

		Hashtable environnement = new Hashtable();
		environnement.put(Context.INITIAL_CONTEXT_FACTORY, "org.meri.jpa.jndi.MyContextFactory");

		Context ctx = new InitialContext(environnement);
		Object value = ctx.lookup("jndiName");
		ctx.close();

		assertEquals("stored value", value);
	}

	@Test
	public void testJNDI() throws NamingException {
		JNDIUtil jndiUtil = new JNDIUtil();
		jndiUtil.initializeJNDI();
		
		Context c = new InitialContext();
		c.bind("propertyValue", "Hello");
		c.close();

		Context c2 = new InitialContext();
		Object lookup = c2.lookup("propertyValue");
		assertEquals("Hello", lookup);
		c2.close();
	}

	@Test
	public void testJNDIReset() throws NamingException {
		JNDIUtil jndiUtil = new JNDIUtil();
		jndiUtil.initializeJNDI();

		Context c = new InitialContext();
		c.bind("propertyValue", "Hello");
		c.close();

		jndiUtil.initializeJNDI();

		Context c2 = new InitialContext();
		Object lookup = c2.lookup("propertyValue");
		assertNull(lookup);
		c2.close();
	}

}


