package org.meri.jpa.relationships;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.meri.jpa.relationships.entities.Person;
import org.meri.jpa.relationships.entities.TwitterAccount;

//FIXME: upratat changelog
//FIXME: persistence testy su zle, netestuju ci sa savol aj relationship
public class LoadRelationshipTestCase extends AbstractRelationshipTestCase {

  public LoadRelationshipTestCase() {
  }

  /**
   * Lazy loading explanation. This is not a real test. 
   */
  @Test
  public void lazyLoading() {
    EntityManager em = factory.createEntityManager();
    
    // load the person, twitter accounts are not loaded yet
    Person simon = em.find(Person.class, SIMON_SLASH_ID);
    
    // load twitter accounts from the database
    Collection<TwitterAccount> twitterAccounts = simon.getTwitterAccounts();
    assertEquals(2, twitterAccounts.size());

    em.close();
  }

  /**
   * Closed entity manager is not able to lazy load data.
   */
  @Test
  public void lazyLoadingCloseWarning() {
    EntityManager em = factory.createEntityManager();
    Person simon = em.find(Person.class, SIMON_SLASH_ID);
    em.close();
    
    assertNull(simon.getTwitterAccounts());
  }

  /**
   * Detached entity is not able to lazy load data.
   */
  @Test
  public void lazyLoadingDetachWarning() {
    EntityManager em = factory.createEntityManager();

    Person simon = em.find(Person.class, SIMON_SLASH_ID);
    em.detach(simon);
    assertNull(simon.getTwitterAccounts());
    
    em.close();
  }

  /**
   * Many to one relationship is NOT lazy loaded by default. However, it 
   * is possible to configure it to be lazy loaded.
   */
  @Test
  public void noDefaultLazyLoadingManyToOne() {
    EntityManager em = factory.createEntityManager();
    TwitterAccount account = em.find(TwitterAccount.class, SIMON_SLASH_ID);
    em.close();
    
    assertNotNull(account.getOwner());
  }

  /**
   * One to one relationship is always eagerly loaded. It is NOT possible 
   * to configure lazy loading on it.
   */
  @Test
  public void noLazyLoadingOneToOne() {
    EntityManager em = factory.createEntityManager();
    Person simon = em.find(Person.class, SIMON_SLASH_ID);
    em.close();
    
    assertNotNull(simon.getFacebookAccount());
  }

}
