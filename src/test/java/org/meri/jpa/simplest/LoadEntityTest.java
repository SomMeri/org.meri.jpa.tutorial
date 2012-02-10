package org.meri.jpa.simplest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.meri.jpa.AbstractTestCase;
import org.meri.jpa.simplest.entities.Person;

//FIXME: upratat changelog
public class LoadEntityTest extends AbstractTestCase {

  private static final BigDecimal SIMON_SLASH_ID = SimplestConstants.SIMON_SLASH_ID;
  private static final String PERSISTENCE_UNIT = SimplestConstants.PERSISTENCE_UNIT;
  private static final String CHANGELOG_LOCATION = SimplestConstants.CHANGELOG_LOCATION;

  private static EntityManagerFactory factory;

  public LoadEntityTest() {
  }

  @Override
  protected String getInitialChangeLog() {
    return CHANGELOG_LOCATION;
  }

  /**
   * These whether loading with find method works correctly.
   * Load the same entity twice with the same manager. Two 
   * found objects are equal. 
   */
  @Test
  public void entityEquality_same_EntityManager() {
    EntityManager em = factory.createEntityManager();
    Person person1 = em.find(Person.class, SIMON_SLASH_ID);
    Person person2 = em.find(Person.class, SIMON_SLASH_ID);
    em.close();

    // entities are equal
    assertEquals(person1, person2);
    // changes in one entity are visible in second entity
    person1.setFirstName("nobody");
    assertEquals("nobody", person2.getFirstName());
  }

  /**
   * Test whether entities loaded by different entity managers
   * are different.
   */
  @Test
  public void entityEquality_different_EntityManager() {
    EntityManager em1 = factory.createEntityManager();
    Person person1 = em1.find(Person.class, SIMON_SLASH_ID);
    em1.close();

    EntityManager em2 = factory.createEntityManager();
    Person person2 = em2.find(Person.class, SIMON_SLASH_ID);
    em2.close();

    // entities are different
    assertNotSame(person1, person2);
    // entities are independent
    person1.setFirstName("nobody");
    assertNotSame("nobody", person2.getFirstName());
  }

  /**
   * The merge method should copy all entity data into 
   * entity manager. Entity itself in not attached nor
   * persisted.
   */
  @Test
  public void entityEquality_mergeInto_EntityManager() {
    // load and detach entity
    EntityManager em1 = factory.createEntityManager();
    Person person1 = em1.find(Person.class, SIMON_SLASH_ID);
    em1.close();

    // change its property
    person1.setFirstName("New Name");

    // merge it into some entity manager
    EntityManager em2 = factory.createEntityManager();
    em2.merge(person1);

    //this change will be ignored
    person1.setFirstName("Ignored Change");
    
    Person person2 = em2.find(Person.class, SIMON_SLASH_ID);
    em2.close();

    // entity itself was not attached
    assertNotSame(person1, person2);
    // however, its changed data was
    assertEquals("New Name", person2.getFirstName());

    // changed data are NOT available in different entity manager
    EntityManager em3 = factory.createEntityManager();
    Person person3 = em3.find(Person.class, SIMON_SLASH_ID);
    em3.close();

    assertNotSame("New Name", person3.getFirstName());
  }

  /**
   * Refresh method test loads an entity with two different
   * entity managers. One updates the entity and another
   * refreshes it. The test checks whether refreshed entity 
   * contains changes.
   * 
   */
  @Test
  public void entityEquality_refresh_EntityManager() {
    // load an entity
    EntityManager emRefresh = factory.createEntityManager();
    Person person1 = emRefresh.find(Person.class, SIMON_SLASH_ID);

    // load the same entity by different entity manager
    EntityManager emChange = factory.createEntityManager();
    Person person2 = emChange.find(Person.class, SIMON_SLASH_ID);

    // change the first entity - second entity remains the same
    emRefresh.getTransaction().begin();
    person1.setFirstName("refreshDemo");
    assertNotSame("refreshDemo", person2.getFirstName());

    // commit the transaction - second entity still remains the same
    emRefresh.getTransaction().commit();
    assertNotSame("refreshDemo", person2.getFirstName());

    // refresh second entity - it changes
    emChange.refresh(person2);
    assertEquals("refreshDemo", person2.getFirstName());

    emRefresh.close();
    emChange.close();
  }

  /**
   * Refresh on detached entity throws an IllegalArgumentException.
   */
  @Test
  public void refreshDetachedEntity() {
    EntityManager em = factory.createEntityManager();
    Person person1 = em.find(Person.class, SIMON_SLASH_ID);

    em.detach(person1);
    try {
      em.refresh(person1);
    } catch (IllegalArgumentException ex) {
      em.close();
      return ;
    }

    fail("An exception was expected.");
  }

  @Test
  public void entityEquality_same_Transaction() {
    EntityManager em = factory.createEntityManager();
    em.getTransaction().begin();

    Person person1 = em.find(Person.class, SIMON_SLASH_ID);
    Person person2 = em.find(Person.class, SIMON_SLASH_ID);

    // end transaction
    em.getTransaction().commit();
    em.close();

    assertEquals(person1, person2);
  }

  /**
   * Test the side effect of the rollback method. All 
   * loaded entities are detached.
   */
  @Test
  public void entityEquality_rollbacked_transaction() {
    EntityManager em = factory.createEntityManager();
    // load entity and rollback the transaction
    em.getTransaction().begin();
    Person person1 = em.find(Person.class, SIMON_SLASH_ID);
    em.getTransaction().rollback();

    // load the same entity again
    em.getTransaction().begin();
    Person person2 = em.find(Person.class, SIMON_SLASH_ID);
    em.getTransaction().commit();

    em.close();
    // second entity is different from the first one
    assertNotSame(person1, person2);
  }

  @Test
  public void entityEquality_commited_transaction() {
    EntityManager em = factory.createEntityManager();

    em.getTransaction().begin();
    Person person1 = em.find(Person.class, SIMON_SLASH_ID);
    em.getTransaction().commit();

    em.getTransaction().begin();
    Person person2 = em.find(Person.class, SIMON_SLASH_ID);
    em.getTransaction().rollback();

    em.close();
    assertEquals(person1, person2);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testJPQL() {
    EntityManager em = factory.createEntityManager();

    Query query = em.createQuery("SELECT x FROM Person x");
    List<Person> allUsers = query.getResultList();
    Person person = em.find(Person.class, SIMON_SLASH_ID);
    em.close();

    assertEquals(allUsers.get(0), person);
  }

  /**
   * Test the clear method of entity manager. Load an entity, 
   * clear the entity manager and load the same entity again. 
   * Loaded entities are different.
   */
  @Test
  public void entityEquality_cleared_EntityManager() {
    EntityManager em = factory.createEntityManager();

    Person person1 = em.find(Person.class, SIMON_SLASH_ID);
    em.clear();
    Person person2 = em.find(Person.class, SIMON_SLASH_ID);
    em.close();

    assertNotSame(person1, person2);
  }

  /**
   * Test the detach method of entity manager. Load an entity, 
   * detach it and load the same entity again. 
   * Loaded entities are different.
   */  
  @Test
  public void entityEquality_detach() {
    EntityManager em = factory.createEntityManager();

    Person person1 = em.find(Person.class, SIMON_SLASH_ID);
    em.detach(person1);
    Person person2 = em.find(Person.class, SIMON_SLASH_ID);
    em.close();

    assertNotSame(person1, person2);
  }

  @BeforeClass
  public static void createFactory() {
    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
  }

  @AfterClass
  public static void closeFactory() {
    factory.close();
  }

  @Override
  protected EntityManagerFactory getFactory() {
    return factory;
  }

}
