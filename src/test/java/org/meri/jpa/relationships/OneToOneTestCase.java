package org.meri.jpa.relationships;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.JoinTable;

import org.junit.Test;
import org.meri.jpa.relationships.entities.onetoone.ColumnOneToOneOwner;
import org.meri.jpa.relationships.entities.onetoone.LazyOneToOneInverse;
import org.meri.jpa.relationships.entities.onetoone.LazyOneToOneOwner;
import org.meri.jpa.relationships.entities.onetoone.OneToOneInverse;
import org.meri.jpa.relationships.entities.onetoone.OneToOneOwner;
import org.meri.jpa.relationships.entities.onetoone.PrimaryOneToOneInverse;
import org.meri.jpa.relationships.entities.onetoone.PrimaryOneToOneOwner;
import org.meri.jpa.relationships.entities.onetoone.TableOneToOneInverse;
import org.meri.jpa.relationships.entities.onetoone.TableOneToOneOwner;

//FIXME: upratat changelog
public class OneToOneTestCase extends AbstractRelationshipTestCase {

  /**
   * One-to-one relationship is eager by default.
   */
  @Test
  public void eagerDefault() {
    EntityManager em = factory.createEntityManager();
    
    OneToOneOwner owner = em.find(OneToOneOwner.class, 1);
    em.close();
    
    //One to one relationship is eager by default, I CAN do this:
    OneToOneInverse inverse = owner.getInverse();
    assertEquals(5, inverse.getId());
  }

  /**
   * Owner in one-to-one relationship can be
   * set to lazy.
   */
  @Test
  public void lazyOneToOne_owner() {
    EntityManager em = factory.createEntityManager();
    
    LazyOneToOneOwner owner = em.find(LazyOneToOneOwner.class, 1);
    em.close();
    
    //The relation ship is lazily loaded and the manager is already closed
    assertNull(owner.getInverse());
  }

  /**
   * Inverse in one-to-one relationship can NOT be
   * set to lazy.
   */
  @Test
  public void lazyOneToOne_inverse() {
    EntityManager em = factory.createEntityManager();
    
    OneToOneInverse owner = em.find(OneToOneInverse.class, 5);
    em.close();
    
    //lazy loading is possible only on the owner side
    assertEquals(1, owner.getOwner().getId());
  }

  /**
   * Mandatory one-to-one relationship demo.
   */
  @Test
  public void mandatoryOneToOne_owner() {
    EntityManager em = factory.createEntityManager();
    em.getTransaction().begin();
    
    LazyOneToOneOwner owner = new LazyOneToOneOwner(10);
    try {
      em.persist(owner);
      em.getTransaction().commit();
    } catch (RuntimeException ex) {
      em.close();
      return ;
    }
    
    fail("The transaction was supposed to fail.");
  }

  /**
   * Mandatory one-to-one relationship demo.
   */
  @Test
  public void mandatoryOneToOne_inverse() {
    EntityManager em = factory.createEntityManager();
    em.getTransaction().begin();
    
    LazyOneToOneInverse inverse = new LazyOneToOneInverse(10);
    try {
      em.persist(inverse);
      em.getTransaction().commit();
    } catch (RuntimeException ex) {
      em.close();
      return ;
    }
    
    fail("The transaction was supposed to fail.");
  }
  
  /**
   * Test whether persisting of one-to-one
   * relationship with custom joining column 
   * works correctly.
   */
  @Test
  public void persistColumnOneToOne() {
    EntityManager em = factory.createEntityManager();
    em.getTransaction().begin();
    
    ColumnOneToOneOwner owner = new ColumnOneToOneOwner(10);
    em.persist(owner);
    
    em.getTransaction().commit();
    em.close();
    
    assertEntityExists(ColumnOneToOneOwner.class, 10);
  }

  /**
   * Test whether load of one-to-one
   * relationship with custom joining column 
   * works correctly.
   */
  @Test
  public void loadColumnOneToOne() {
    EntityManager em = factory.createEntityManager();
    ColumnOneToOneOwner owner = em.find(ColumnOneToOneOwner.class, 1);
    em.close();
    
    assertEquals(5, owner.getInverse().getId());
  }

  /**
   * The {@link JoinTable} annotation is not compatible 
   * with one-to-one relationship.
   */
  @Test
  public void insertUpdateLoadTableOneToOne() {
    //WARNING: this test may behave unpredictably
    TableOneToOneOwner owner = new TableOneToOneOwner(10);
    insert(owner);

    TableOneToOneInverse inverse = new TableOneToOneInverse(9);
    insert(inverse);

    //check whether owner and inverse are correctly saved
    EntityManager em = factory.createEntityManager();
    inverse = em.find(TableOneToOneInverse.class, 9);
    assertNull(inverse.getOwner());
    em.close();

    em = factory.createEntityManager();
    owner = em.find(TableOneToOneOwner.class, 10);
    assertNull(owner.getInverse());
    em.close();

    //create relationship between owner and inverse
    owner.setInverse(inverse);
    inverse.setOwner(owner);
    mergeAndCommit(inverse);
    mergeAndCommit(owner);

    //owner loading works correctly
    em = factory.createEntityManager();
    owner = em.find(TableOneToOneOwner.class, 10);
    assertEquals(9, owner.getInverse().getId());
    em.close();

    //inverse loading does not work
    em = factory.createEntityManager();
    inverse = em.find(TableOneToOneInverse.class, 9);
    //Whooo, if the join table would work, this would be the same
    assertNotSame(inverse.getId(), inverse.getOwner().getInverse().getId());
    assertEquals(5, inverse.getOwner().getInverse().getId());
    //Whooo, if the join table would work, this would have value 10!
    assertEquals(1, inverse.getOwner().getId());
    em.close();
  }

  /**
   * Test whether persisting of one-to-one
   * relationship with joined via primary ids 
   * works correctly.
   */
  @Test
  public void persistPrimaryOneToOne() {
    EntityManager em = factory.createEntityManager();
    em.getTransaction().begin();
    
    PrimaryOneToOneOwner owner = new PrimaryOneToOneOwner(10);
    owner.setInverse(new PrimaryOneToOneInverse(10));
    em.persist(owner.getInverse());
    em.persist(owner);
    
    em.getTransaction().commit();
    em.close();
    
    assertEntityExists(PrimaryOneToOneOwner.class, 10);
    assertEntityExists(PrimaryOneToOneInverse.class, 10);
  }

  /**
   * Test whether persisting of one-to-one
   * relationship joined via primary ids 
   * works correctly.
   */
  @Test
  public void loadPrimaryOneToOne() {
    EntityManager em = factory.createEntityManager();
    PrimaryOneToOneOwner owner = em.find(PrimaryOneToOneOwner.class, 1);
    em.close();
    
    assertEquals(1, owner.getInverse().getId());
  }

}
