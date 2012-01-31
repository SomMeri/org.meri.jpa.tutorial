package org.meri.jpa.cascading;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import org.meri.jpa.cascading.entities.CascadeFirst;
import org.meri.jpa.cascading.entities.CascadeFourth;
import org.meri.jpa.cascading.entities.CascadeOneToOneInverse;
import org.meri.jpa.cascading.entities.CascadeOneToOneOwner;
import org.meri.jpa.cascading.entities.CascadePersistDemo;
import org.meri.jpa.cascading.entities.CascadeSecond;
import org.meri.jpa.cascading.entities.CascadeThird;
import org.meri.jpa.cascading.entities.OneToOneInverse;
import org.meri.jpa.cascading.entities.OneToOneOwner;

public class CascadingTestCase extends AbstractTestCase {

  protected static final BigDecimal SIMON_SLASH_ID = CascadingConstants.SIMON_SLASH_ID;
  protected static final String PERSISTENCE_UNIT = CascadingConstants.PERSISTENCE_UNIT;
  protected static final String CHANGELOG_LOCATION = CascadingConstants.CHANGELOG_LOCATION;

  protected static EntityManagerFactory factory;

  @Test
  public void wrongOrderNoCascade() {
    // create a relationship between entities
    OneToOneOwner owner = new OneToOneOwner(8);
    OneToOneInverse inverse = new OneToOneInverse(8);
    owner.setInverse(inverse);
    inverse.setOwner(owner);

    // persist the owner first
    EntityManager em = getFactory().createEntityManager();
    em.getTransaction().begin();

    em.persist(owner);
    em.persist(inverse);

    try {
      em.getTransaction().commit();
    } catch (PersistenceException ex) {
      // we tried to save the owner first
      // that is wrong
      em.close();
      return;
    }

    fail("It was supposed to throw an exception.");
  }

  @Test
  public void wrongOrderCascade() {
    // create a relationship between entities
    CascadeOneToOneOwner owner = new CascadeOneToOneOwner(7);
    CascadeOneToOneInverse inverse = new CascadeOneToOneInverse(7);
    owner.setInverse(inverse);
    inverse.setOwner(owner);

    EntityManager em = getFactory().createEntityManager();
    em.getTransaction().begin();

    // persist the owner first
    em.persist(owner);
    em.persist(inverse);

    // wrong order - commit throws an exception
    try {
      em.getTransaction().commit();
    } catch (PersistenceException ex) {
      em.close();
      return;
    }

    fail("It was supposed to throw an exception.");
  }

  @Test
  public void basicNoCascadeInverse() {
    // create a relationship between entities
    OneToOneOwner owner = new OneToOneOwner(9);
    OneToOneInverse inverse = new OneToOneInverse(9);
    owner.setInverse(inverse);
    inverse.setOwner(owner);

    // persist only the owner
    EntityManager em = getFactory().createEntityManager();
    em.getTransaction().begin();

    em.persist(inverse);

    try {
      em.getTransaction().commit();
    } catch (IllegalStateException ex) {
      em.close();
      return;
    }

    fail("It was supposed to throw an exception.");
  }

  @Test
  public void impossibleMergeNoCascade() {
    // create a relationship between entities
    OneToOneOwner owner = new OneToOneOwner(11);
    OneToOneInverse inverse = new OneToOneInverse(11);
    owner.setInverse(inverse);
    inverse.setOwner(owner);

    // persist only the owner
    EntityManager em = getFactory().createEntityManager();
    em.getTransaction().begin();

    try {
      em.merge(owner);
    } catch (RuntimeException ex) {
      em.getTransaction().rollback();
    }

    // check whether previous catch closed the transaction
    assertFalse(em.getTransaction().isActive());

    em.getTransaction().begin();

    try {
      em.merge(inverse);
    } catch (RuntimeException ex) {
      em.getTransaction().rollback();
      em.close();
      return;
    }

    fail("It was supposed to throw an exception (twice).");
  }

  //FIXME: update this test in the post
  @Test
  public void mergeInverse() {
    // create a relationship between entities
    CascadeOneToOneOwner owner = new CascadeOneToOneOwner(10);
    CascadeOneToOneInverse inverse = new CascadeOneToOneInverse(10);
    owner.setInverse(inverse);
    inverse.setOwner(owner);

    // persist only the inverse
    EntityManager em = getFactory().createEntityManager();
    em.getTransaction().begin();
    em.merge(inverse);
    em.getTransaction().commit();
    em.close();

    // the merge operation was cascaded to the owner too
    // the owner was saved to the database
    assertEntityExists(CascadeOneToOneOwner.class, 10);
  }

  @Test
  public void mergeOwner() {
    // create a relationship between entities
    CascadeOneToOneOwner owner = new CascadeOneToOneOwner(12);
    CascadeOneToOneInverse inverse = new CascadeOneToOneInverse(12);
    owner.setInverse(inverse);
    inverse.setOwner(owner);

    // persist only the inverse
    EntityManager em = getFactory().createEntityManager();
    em.getTransaction().begin();
    try {
      em.merge(owner);
      em.getTransaction().commit();
    } catch (PersistenceException ex) {
      // Cascading itself does not solve everything.
      // The owner was merged first, then the merge
      // cascaded to the inverse.

      // Therefore, JPA will try to insert the owner
      // first which will result in foreign key violation.
      em.close();
      return;
    }

    fail("JPA was supposed to throw an exception.");
  }

  @Test
  public void ressurectDeletedEntity() {
    EntityManager em = getFactory().createEntityManager();
    em.getTransaction().begin();
    // load two related entities
    CascadeOneToOneOwner owner = em.find(CascadeOneToOneOwner.class, 2);
    CascadeOneToOneInverse inverse = owner.getInverse();
    // delete one of them
    em.remove(owner);
    // new entity demo references second one
    CascadePersistDemo demo = new CascadePersistDemo(2);
    demo.setInverse(inverse);
    // persist the demo
    em.persist(demo);
    em.getTransaction().commit();
    em.close();

    // the persist operation cascaded through the 'inverse'
    // to the removed 'owner'. The owner was not deleted.
    assertEntityExists(CascadeOneToOneOwner.class, 2);
  }

  @Test
  public void cascadeDeleteThroughUnloaded() {
    // check the data: first, second and third entities are 
    // chained and have the same id
    checkChain(1);

    // remove first entity
    EntityManager em = getFactory().createEntityManager();
    em.getTransaction().begin();
    CascadeFirst first = em.find(CascadeFirst.class, 1);
    em.remove(first);
    em.getTransaction().commit();
    em.close();

    // both second and third entities have been removed
    // Note: neither one was loaded by the original entity manager
    assertEntityNOTExists(CascadeSecond.class, 1);
    assertEntityNOTExists(CascadeThird.class, 1);
  }

  @Test
  public void cascadeMergeThroughUnloaded() {
    // check the data: first, second and third entities are 
    // chained and have the same id
    checkChain(3);

    // load first and third elements of chain
    EntityManager em = getFactory().createEntityManager();
    CascadeFirst first = em.find(CascadeFirst.class, 3);
    CascadeThird third = em.find(CascadeThird.class, 3);
    assertEquals("clean", first.getSomeValue());
    assertEquals("clean", third.getSomeValue());
    em.close();

    // modify both first and third element of chain
    first.setSomeValue("dirty");
    third.setSomeValue("dirty");

    // merge and commit first element of chain
    EntityManager em2 = getFactory().createEntityManager();
    em2.getTransaction().begin();
    em2.merge(first);
    em2.getTransaction().commit();
    em2.close();

    // merge did not passed through unloaded elements
    EntityManager em1 = getFactory().createEntityManager();
    CascadeFirst firstCheck = em1.find(CascadeFirst.class, 3);
    CascadeThird thirdCheck = em1.find(CascadeThird.class, 3);
    assertEquals("dirty", firstCheck.getSomeValue());
    assertEquals("clean", thirdCheck.getSomeValue());
    em1.close();
  }

  @Test
  public void cascadePersistThroughUnloaded() {
    // check the data: first, second and third entities are 
    // chained and have the same id
    checkChain(2);

    // Add new elements to the beginning and end of the chain. Middle of the
    // chain
    // is never loaded.
    EntityManager em = getFactory().createEntityManager();
    em.getTransaction().begin();
    // add fourth element to the chain
    CascadeThird third = em.find(CascadeThird.class, 2);
    CascadeFourth fourth = new CascadeFourth(2);
    fourth.getThird().add(third);
    third.getFourth().add(fourth);

    // add new element to the beginning
    CascadeFirst first = em.find(CascadeFirst.class, 2);
    CascadeSecond beginning = new CascadeSecond(12);
    first.getSecond().add(beginning);
    beginning.getFirst().add(first);

    // persist only beginning
    em.persist(beginning);
    em.getTransaction().commit();
    em.close();

    // cascade persist passed through unloaded elements
    // fourth element was saved into the database
    assertEntityExists(CascadeFourth.class, 2);
  }

  @Test
  public void cascadeRefreshThroughUnloaded() {
    // check the data: first, second and third entities are 
    // chained and have the same id
    checkChain(2);

    EntityManager em = getFactory().createEntityManager();
    // modify both first and third element of chain
    CascadeFirst first = em.find(CascadeFirst.class, 2);
    CascadeThird third = em.find(CascadeThird.class, 2);

    assertEquals("clean", first.getSomeValue());
    assertEquals("clean", third.getSomeValue());

    first.setSomeValue("dirty");
    third.setSomeValue("dirty");
    // refresh first element of chain
    em.refresh(first);

    // refresh does not pass through unloaded values
    assertEquals("clean", first.getSomeValue());
    assertEquals("dirty", third.getSomeValue());

    em.close();
  }

  @Test
  public void cascadeDetachThroughUnloaded() {
    // check the data: first, second and third entities are 
    // chained and have the same id
    checkChain(2);

    EntityManager em = getFactory().createEntityManager();
    // load both first and third element of chain
    CascadeFirst first = em.find(CascadeFirst.class, 2);
    CascadeThird third = em.find(CascadeThird.class, 2);
    // detach first element
    em.detach(first);
    // only first element was detached
    // detach operation does not cascade through unloaded elements
    assertFalse(em.contains(first));
    assertTrue(em.contains(third));
    em.close();
  }

  private void checkChain(long id) {
    EntityManager emInit = getFactory().createEntityManager();
    CascadeFirst initFirst = emInit.find(CascadeFirst.class, id);
    
    CascadeSecond initSecond = initFirst.getSecond().get(0);
    assertEquals(id, initSecond.getId());
    
    CascadeThird initThird = initSecond.getThird().get(0);
    assertEquals(id, initThird.getId());
    emInit.close();
  }

  @Override
  protected String getInitialChangeLog() {
    return CHANGELOG_LOCATION;
  }

  @Override
  protected EntityManagerFactory getFactory() {
    return factory;
  }

  @BeforeClass
  public static void createFactory() {
    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
  }

  @AfterClass
  public static void closeFactory() {
    factory.close();
  }

}
