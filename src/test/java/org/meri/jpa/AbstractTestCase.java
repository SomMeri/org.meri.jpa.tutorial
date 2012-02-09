package org.meri.jpa;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.meri.jpa.util.InMemoryDBUtil;
import org.meri.jpa.util.JNDIUtil;

public abstract class AbstractTestCase {

  private JNDIUtil jndiUtil = new JNDIUtil();
  private InMemoryDBUtil databaseUtil = new InMemoryDBUtil();

  @BeforeClass
  public static void beforeClass() {
    (new JNDIUtil()).initializeJNDI();
    (new InMemoryDBUtil()).removeDatabase();
  }

  @Before
  public void beforeEachTest() {
    jndiUtil.initializeJNDI();
    databaseUtil.initializeDatabase(getInitialChangeLog());
  }

  protected abstract String getInitialChangeLog();

  protected JNDIUtil getJndiUtil() {
    return jndiUtil;
  }

  protected InMemoryDBUtil getDatabaseUtil() {
    return databaseUtil;
  }

  protected Connection getConnection() {
    try {
      DataSource dataSource = databaseUtil.getDataSource();
      return dataSource.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException("Failed to create db connection.", e);
    }
  }

  protected void removeDatabase() {
    databaseUtil.removeDatabase();
  }

  /* JPA Utils */
  protected void insert(Object object) {
    EntityManager em = getFactory().createEntityManager();
    em.getTransaction().begin();

    em.persist(object);

    em.getTransaction().commit();
    em.close();
  }

  protected void mergeAndCommit(Object object) {
    EntityManager em = getFactory().createEntityManager();
    em.getTransaction().begin();

    em.merge(object);

    em.getTransaction().commit();
    em.close();
  }

  protected <T> void assertEntityExists(Class<T> entityClass, Object primaryKey) {
    EntityManager em1 = getFactory().createEntityManager();
    T thing = em1.find(entityClass, primaryKey);
    assertNotNull(thing);
    em1.close();
  }

  protected <T> void assertEntityNOTExists(Class<T> entityClass, Object primaryKey) {
    EntityManager em1 = getFactory().createEntityManager();
    T thing = em1.find(entityClass, primaryKey);
    assertNull(thing);
    em1.close();
  }

  protected abstract EntityManagerFactory getFactory();

}
