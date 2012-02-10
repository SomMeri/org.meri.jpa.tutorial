package org.meri.jpa.relationships;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.junit.Test;
import org.meri.jpa.relationships.entities.manytomany.MtmCollectionInverse;
import org.meri.jpa.relationships.entities.manytomany.MtmCollectionOwner;
import org.meri.jpa.relationships.entities.onetoone.OneToOneInverse;
import org.meri.jpa.relationships.entities.onetoone.OneToOneOwner;

public class PersistRelationshipTestCase extends AbstractRelationshipTestCase {

  protected static final String CHANGELOG_LOCATION = RelationshipsConstants.PERSIST_CHANGELOG_PATH;

  @Override
  protected String getInitialChangeLog() {
    return CHANGELOG_LOCATION;
  }

  /**
   * Any decent JPA provider can be configured to automatically
   * resolve relationship dependencies. However, it is not 
   * required by the specification.
   * 
   * It will reorder those inserts so that no exception is thrown.
   */
  @Test
  public void wrongOrder_oto() {
    // create a relationship between entities
    OneToOneOwner owner = new OneToOneOwner(888);
    OneToOneInverse inverse = new OneToOneInverse(888);
    owner.setInverse(inverse);
    inverse.setOwner(owner);

    // persist the owner first - JPA is configured to solve dependencies
    EntityManager em = getFactory().createEntityManager();
    em.getTransaction().begin();

    em.persist(owner);
    em.persist(inverse);

    em.getTransaction().commit();
    
    //check saved data
    EntityManager em1 = getFactory().createEntityManager();
    OneToOneInverse inverseCheck = em1.find(OneToOneInverse.class, 888);
    assertNotNull(inverseCheck.getOwner());
    em1.close();
  }

  /**
   * Any decent JPA provider can be configured to automatically
   * resolve relationship dependencies. However, it is not 
   * required by the specification.
   * 
   * It will reorder those inserts so that no exception is thrown.
   */
  @Test
  public void wrongOrder_mtm() {
    // create a relationship between entities
    MtmCollectionOwner owner = new MtmCollectionOwner(888);
    MtmCollectionInverse inverse = new MtmCollectionInverse(888);
    owner.getInverses().add(inverse);
    inverse.getOwners().add(owner);

    // persist the owner first - JPA is configured to solve dependencies
    EntityManager em = getFactory().createEntityManager();
    em.getTransaction().begin();

    em.persist(owner);
    em.persist(inverse);

    em.getTransaction().commit();
    
    //check saved data
    EntityManager em1 = getFactory().createEntityManager();
    MtmCollectionInverse inverseCheck = em1.find(MtmCollectionInverse.class, 888);
    assertFalse(inverseCheck.getOwners().isEmpty());
    em1.close();
  }

  /**
   * The relationship between two entities is saved whenever the owner is saved.
   */
  @Test
  public void relationshipSaveOnlyOwner_oto() {
    EntityManager em = getFactory().createEntityManager();
    // load two entities
    OneToOneOwner owner = em.find(OneToOneOwner.class, 6);
    OneToOneInverse inverse = em.find(OneToOneInverse.class, 6);
    // create a relationship between entities
    owner.setInverse(inverse);
    inverse.setOwner(owner);
    // detached inverse will be ignored by commit
    em.detach(inverse);
    em.getTransaction().begin();
    // persist only the owner - it is the only loaded entity
    em.getTransaction().commit();
    em.close();

    // relationship was saved
    EntityManager em1 = getFactory().createEntityManager();
    OneToOneInverse inverseCheck = em1.find(OneToOneInverse.class, 6);
    assertNotNull(inverseCheck.getOwner());
    em1.close();
  }

  /**
   * The relationship between two entities is saved whenever the owner is 
   * saved. Saving the inverse does nothing to the relationship, it is 
   * only the owner who counts. 
   */
  @Test
  public void relationshipSaveOnlyInverse_oto() {
    EntityManager em = getFactory().createEntityManager();
    // load two entities
    OneToOneOwner owner = em.find(OneToOneOwner.class, 7);
    OneToOneInverse inverse = em.find(OneToOneInverse.class, 7);
    // create a relationship between entities
    owner.setInverse(inverse);
    inverse.setOwner(owner);
    // detached owner will be ignored by commit
    em.detach(owner);
    em.getTransaction().begin();
    // persist only the inverse - it is the only loaded entity
    em.getTransaction().commit();
    em.close();

    // relationship was not saved
    EntityManager em1 = getFactory().createEntityManager();
    OneToOneOwner ownerCheck = em1.find(OneToOneOwner.class, 7);
    assertNull(ownerCheck.getInverse());
    em1.close();
  }

  /**
   * The relationship between two entities is deleted whenever the owner is deleted.
   */
  @Test
  public void relationshipDeleteOnlyOwner_oto() {
    EntityManager em = getFactory().createEntityManager();
    // check initial data
    OneToOneOwner owner = em.find(OneToOneOwner.class, 3);
    OneToOneInverse inverse = em.find(OneToOneInverse.class, 3);
    assertEquals(owner, inverse.getOwner());
    // detached owner will be ignored by commit
    em.detach(inverse);
    // delete only the owner
    em.getTransaction().begin();
    em.remove(owner);
    em.getTransaction().commit();
    em.close();
    
    // relationship was deleted
    EntityManager em1 = getFactory().createEntityManager();
    OneToOneInverse inverseCheck = em1.find(OneToOneInverse.class, 3);
    assertNull(inverseCheck.getOwner());
    em1.close();
  }

  /**
   * Any decent JPA provider can be configured to automatically
   * resolve relationship dependencies.
   * 
   * It will reorder those deletes so that no exception is thrown.
   */
  @Test
  public void relationshipDeleteBothWrongOrder_oto() {
    EntityManager em = getFactory().createEntityManager();
    // check initial data
    OneToOneOwner owner = em.find(OneToOneOwner.class, 8);
    OneToOneInverse inverse = em.find(OneToOneInverse.class, 8);
    assertEquals(owner, inverse.getOwner());
    // delete both - the order does not matter
    em.getTransaction().begin();
    em.remove(inverse);
    em.remove(owner);
    em.getTransaction().commit();
    
    assertEntityNOTExists(OneToOneOwner.class, 8);
    assertEntityNOTExists(OneToOneInverse.class, 8);
  }

  /**
   * If you delete an entity, you have to delete it from all 
   * relationships. Especially if you are deleting the inverse 
   * entity.
   */
  @Test
  public void relationshipDeleteInverse_correct_oto() {
    
    EntityManager em = getFactory().createEntityManager();
    // check initial data
    OneToOneOwner owner = em.find(OneToOneOwner.class, 10);
    OneToOneInverse inverse = em.find(OneToOneInverse.class, 10);
    assertEquals(owner, inverse.getOwner());
    //delete inverse and clean up the relationship
    em.getTransaction().begin();
    em.remove(inverse);
    owner.setInverse(null);
    em.getTransaction().commit();
    em.close();
  }

  /**
   * The relationship between two entities is saved whenever the owner is saved.
   */
  @Test
  public void relationshipSaveOnlyOwner_mtm() {
    EntityManager em = getFactory().createEntityManager();
    // load two entities
    MtmCollectionOwner owner = em.find(MtmCollectionOwner.class, 6);
    MtmCollectionInverse inverse = em.find(MtmCollectionInverse.class, 6);
    //check initial data - there is no relationship
    assertTrue(inverse.getOwners().isEmpty());
    
    // create a relationship between entities
    owner.getInverses().add(inverse);
    inverse.getOwners().add(owner);
    // detached inverse will be ignored by commit
    em.detach(inverse);
    em.getTransaction().begin();
    // persist only the owner - it is the only loaded entity
    em.getTransaction().commit();
    em.close();

    // relationship was saved
    EntityManager em1 = getFactory().createEntityManager();
    MtmCollectionInverse inverseCheck = em1.find(MtmCollectionInverse.class, 6);
    assertFalse(inverseCheck.getOwners().isEmpty());
    em1.close();
  }

  /**
   * The relationship between two entities is saved whenever the owner is 
   * saved. Saving the inverse does nothing to the relationship, it is 
   * only the owner who counts. 
   */
  @Test
  public void relationshipSaveOnlyInverse_mtm() {
    EntityManager em = getFactory().createEntityManager();
    // load two entities
    MtmCollectionOwner owner = em.find(MtmCollectionOwner.class, 7);
    MtmCollectionInverse inverse = em.find(MtmCollectionInverse.class, 7);
    //check initial data - there is no relationship
    assertTrue(inverse.getOwners().isEmpty());

    // create a relationship between entities
    owner.getInverses().add(inverse);
    inverse.getOwners().add(owner);
    // detached owner will be ignored by commit
    em.detach(owner);
    em.getTransaction().begin();
    // persist only the inverse - it is the only loaded entity
    em.getTransaction().commit();
    em.close();

    // relationship was not saved
    EntityManager em1 = getFactory().createEntityManager();
    MtmCollectionOwner ownerCheck = em1.find(MtmCollectionOwner.class, 7);
    assertTrue(ownerCheck.getInverses().isEmpty());
    em1.close();
  }

  /**
   * The relationship between two entities is deleted whenever the owner is deleted.
   */
  @Test
  public void relationshipDeleteOnlyOwner_mtm() {
    EntityManager em = getFactory().createEntityManager();
    // check initial data
    MtmCollectionOwner owner = em.find(MtmCollectionOwner.class, 3);
    MtmCollectionInverse inverse = em.find(MtmCollectionInverse.class, 3);
    assertTrue(inverse.getOwners().contains(owner));
    // detached owner will be ignored by commit
    em.detach(inverse);
    // persist only the inverse
    em.getTransaction().begin();
    em.remove(owner);
    em.getTransaction().commit();
    em.close();
    
    // relationship was deleted
    EntityManager em1 = getFactory().createEntityManager();
    MtmCollectionInverse inverseCheck = em1.find(MtmCollectionInverse.class, 3);
    assertTrue(inverseCheck.getOwners().isEmpty());
    em1.close();
  }

  /**
   * This test case demonstrate OpenJPA specific behavior. 
   * 
   * WARNING: It may does not have to work with newer OpenJPA versions. 
   */
  @Test
  public void relationshipDeleteOnlyInverse_mtm_nonStandard() {
    EntityManager em = getFactory().createEntityManager();
    // check initial data
    MtmCollectionOwner owner = em.find(MtmCollectionOwner.class, 4);
    MtmCollectionInverse inverse = em.find(MtmCollectionInverse.class, 4);
    assertTrue(inverse.getOwners().contains(owner));
    // detached owner will be ignored by commit
    em.detach(owner);
    // remove the inverse
    em.getTransaction().begin();
    em.remove(inverse);
    em.getTransaction().commit();
    
    //the owner was not saved, but the relationship was removed anyway
    EntityManager em1 = getFactory().createEntityManager();
    MtmCollectionOwner ownerCheck = em1.find(MtmCollectionOwner.class, 4);
    assertTrue(ownerCheck.getInverses().isEmpty());
    em1.close();
  }

  /**
   * This test case demonstrate OpenJPA specific behavior. 
   * 
   * WARNING: It may does not have to work with newer OpenJPA versions. 
   */
  @Test
  public void relationshipDeleteOnlyInverse_oto_nonStandard() {
    EntityManager em = getFactory().createEntityManager();
    // check initial data
    OneToOneOwner owner = em.find(OneToOneOwner.class, 4);
    OneToOneInverse inverse = em.find(OneToOneInverse.class, 4);
    assertEquals(owner, inverse.getOwner());
    // detached owner will be ignored by commit
    em.detach(owner);
    // delete only the inverse
    em.getTransaction().begin();
    try {
      em.remove(inverse);
      em.getTransaction().commit();
    } catch (PersistenceException ex) {
      em.close();
      return;
    }
    
    fail("The delete was supposed to throw an exception.");

  }

}
