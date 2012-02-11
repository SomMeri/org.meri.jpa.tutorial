package org.meri.jpa.simplest;

import static org.junit.Assert.fail;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.meri.jpa.AbstractTestCase;
import org.meri.jpa.simplest.entities.Person;

//FIXME: upratat changelog
//FIXME: persistence testy su zle, netestuju ci sa savol aj relationship
public class DeleteEntityTest extends AbstractTestCase {

  private static final BigDecimal SIMON_SLASH_ID = SimplestConstants.SIMON_SLASH_ID;
  private static final String PERSISTENCE_UNIT = SimplestConstants.PERSISTENCE_UNIT;
  private static final String CHANGELOG_LOCATION = SimplestConstants.CHANGELOG_LOCATION;

  private static EntityManagerFactory factory;

  @Before
  public void beforeEachTest() {
    // as these tests deletes data, we have to 
    // clean the database after each test
    getDatabaseUtil().removeDatabase();
    super.beforeEachTest();
  }
  
  @Test
  public void deleteEntity() {
    EntityManager em1 = factory.createEntityManager();
    
    //delete an entity
    em1.getTransaction().begin();
    Person person1 = em1.find(Person.class, SIMON_SLASH_ID);
    em1.remove(person1);
    em1.getTransaction().commit();
    em1.close();

    //try to load deleted entity - it does not exists 
    //anymore
    assertEntityNOTExists(Person.class, SIMON_SLASH_ID);
  }

  @Test
  public void refreshDeletedEntity() {
    EntityManager em1 = factory.createEntityManager();
    
    //load the entity
    EntityManager em2 = factory.createEntityManager();
    Person person2 = em2.find(Person.class, SIMON_SLASH_ID);

    //delete it
    em1.getTransaction().begin();
    Person person1 = em1.find(Person.class, SIMON_SLASH_ID);
    em1.remove(person1);
    em1.getTransaction().commit();
    em1.close();

    //refresh loaded entity
    try {
      em2.refresh(person2);
    } catch(EntityNotFoundException ex) {
      return ;
    } finally {
      em2.close();
    }
    
    fail("An exception was expected.");
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
