package org.meri.jpa.simplest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.meri.jpa.AbstractTestCase;
import org.meri.jpa.simplest.entities.Person;

public class PersistEntityTest extends AbstractTestCase {

  private static final BigDecimal SIMON_SLASH_ID = SimplestConstants.SIMON_SLASH_ID;
  private static final String PERSISTENCE_UNIT = SimplestConstants.PERSISTENCE_UNIT;
  private static final String CHANGELOG_LOCATION = SimplestConstants.CHANGELOG_LOCATION;

  private static EntityManagerFactory factory;

  @Test 
  public void updateEntity() {
    EntityManager em1 = factory.createEntityManager();
    //open transaction
    em1.getTransaction().begin();
    Person person1 = em1.find(Person.class, SIMON_SLASH_ID);
    //change the entity
    person1.setFirstName("nobody");
    //commit data
    em1.getTransaction().commit();
    em1.close();

    //load another entity
    EntityManager em2 = factory.createEntityManager();
    Person person2 = em2.find(Person.class, SIMON_SLASH_ID);
    em2.close();

    // entities are different
    assertNotSame(person1, person2);
    // second entity contains all commited changes
    assertEquals("nobody", person2.getFirstName());
  }

  @Test 
  public void updateEntity_note() {
    EntityManager em1 = factory.createEntityManager();
    Person person1 = em1.find(Person.class, SIMON_SLASH_ID);
    //change the entity
    person1.setFirstName("hello");
    //open transaction and commit data
    em1.getTransaction().begin();
    em1.getTransaction().commit();
    em1.close();

    //load another entity
    EntityManager em2 = factory.createEntityManager();
    Person person2 = em2.find(Person.class, SIMON_SLASH_ID);
    em2.close();

    // entities are different
    assertNotSame(person1, person2);
    // entities contains all commited changes
    assertEquals("hello", person2.getFirstName());
  }

  @Test 
  public void detachedEntity() {
    EntityManager em1 = factory.createEntityManager();
    //open transaction
    em1.getTransaction().begin();
    Person person1 = em1.find(Person.class, SIMON_SLASH_ID);
    
    //change and detach the entity
    person1.setFirstName("detacheEntity");
    em1.detach(person1);
    
    //commit data
    em1.getTransaction().commit();
    em1.close();

    //load another entity
    EntityManager em2 = factory.createEntityManager();
    Person person2 = em2.find(Person.class, SIMON_SLASH_ID);
    em2.close();

    // entities are different
    assertNotSame(person1, person2);
    // the change was not saved
    assertNotSame("detacheEntity", person2.getFirstName());
  }

  @Test 
  public void insertEntityMerge() {
    Person newPerson = new Person(2, "MM", "Martin", "Martinez");

    EntityManager em1 = factory.createEntityManager();
    em1.getTransaction().begin();
    //merge entity properties into persistence context 
    em1.merge(newPerson);
    em1.getTransaction().commit();
    em1.close();

    //new entity was saved and is available to different entity manager
    EntityManager em2 = factory.createEntityManager();
    Person person2 = em2.find(Person.class, 2);
    em2.close();

    // check loaded entity
    assertEquals("MM", person2.getUserName());
  }

  @Test 
  public void insertEntityMerge_note() {
    //FIXME: this was supposed to do something ...
    Person newPerson = new Person(2, "AA", "Andrea", "Antarez");

    EntityManager em1 = factory.createEntityManager();
    em1.getTransaction().begin();
    //merge entity properties into persistence context 
    em1.merge(newPerson);
    em1.getTransaction().commit();
    em1.close();

    //new entity was saved and is available to different entity manager
    EntityManager em2 = factory.createEntityManager();
    Person person2 = em2.find(Person.class, 2);
    em2.close();

    // check loaded entity
    assertEquals("AA", person2.getUserName());
  }

  @Test 
  public void insertEntityPersist() {
    Person newPerson = new Person(3, "BB", "Bob", "Brandert");

    EntityManager em1 = factory.createEntityManager();
    em1.getTransaction().begin();
    //persist the entity 
    em1.persist(newPerson);
    em1.getTransaction().commit();
    //the entity was attached 
    assertTrue(em1.contains(newPerson));
    em1.close();
    
    //new entity was saved and is available to different entity manager
    EntityManager em2 = factory.createEntityManager();
    Person person2 = em2.find(Person.class, 3);
    em2.close();

    // check loaded entity
    assertEquals("BB", person2.getUserName());
  }

  @Test 
  public void insertEntityPersist_Note() {
    Person newPerson = new Person(4, "CC", "Cyntia", "Crowd");

    EntityManager em1 = factory.createEntityManager();
    em1.getTransaction().begin();
    //persist the entity 
    em1.persist(newPerson);
    em1.getTransaction().commit();
    em1.close();

    Person otherPerson = new Person(4, "CC", "Cyntia", "Crowd");
    EntityManager em2 = factory.createEntityManager();
    em2.getTransaction().begin();
    //persist the entity 
    try {
      em2.persist(otherPerson);
      em2.getTransaction().commit();
    } catch (PersistenceException ex) {
      //from the API: The EntityExistsException may be thrown when the persist operation 
      //is invoked, or the EntityExistsException or another PersistenceException may be 
      //thrown at flush or commit time.
      em2.close();
      return ;
    }
    
    fail("Either persist or commit should throw an exception.");
  }

  @Override
  protected String getInitialChangeLog() {
    return CHANGELOG_LOCATION;
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
