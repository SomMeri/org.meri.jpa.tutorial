package org.meri.jpa.relationships;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.meri.jpa.relationships.entities.manytomany.MtmInverse;
import org.meri.jpa.relationships.entities.manytomany.MtmOwner;
import org.meri.jpa.relationships.entities.manytomany.TableMtmInverse;
import org.meri.jpa.relationships.entities.manytomany.TableMtmOwner;

public class ManyToManyTestCase extends AbstractRelationshipTestCase {

  protected static final String CHANGELOG_LOCATION = RelationshipsConstants.MANY_TO_MANY_CHANGELOG_PATH;

  @Override
  protected String getInitialChangeLog() {
    return CHANGELOG_LOCATION;
  }

  /**
   * Many-to-many relationship is lazy loaded by default.
   */
  @Test
  public void lazyLoading() {
    EntityManager em = factory.createEntityManager();

    MtmOwner owner = em.find(MtmOwner.class, 1);
    em.close();

    // The relation ship is lazily loaded and the manager is already closed
    assertNull(owner.getInverses());
  }

  /**
   * Test the map. 
   */
  @Test
  public void basicMap() {
    EntityManager em = factory.createEntityManager();
    
    MtmOwner owner = em.find(MtmOwner.class, 1);
    MtmInverse inverse = em.find(MtmInverse.class, 5);
    
    // mapKey property is a key to the map
    Map<String, MtmOwner> owners = inverse.getOwners();
    assertEquals(owner, owners.get(owner.getName()));
    
    em.close();
  }
  /**
   * Test whether load of entities with
   * many-to-many relationship works
   * correctly.
   * 
   */
  @Test
  public void load() {
    EntityManager em = factory.createEntityManager();
    // load the owner
    MtmOwner owner = em.find(MtmOwner.class, 1);
    // check data - the owner should have some inverse
    assertFalse(owner.getInverses().isEmpty());
    
    // owners name should be a key to the map 
    for (MtmInverse inverse : owner.getInverses()) {
      Map<String, MtmOwner> owners = inverse.getOwners();
      assertEquals(owner, owners.get(owner.getName()));
    }

    em.close();
  }

  /**
   * Test whether update of many-to-many 
   * relationship between two entities works
   * correctly.
   * 
   */
  @Test
  public void update() {
    EntityManager em = factory.createEntityManager();
    // load both entities
    MtmOwner owner = em.find(MtmOwner.class, 6);
    MtmInverse inverse = em.find(MtmInverse.class, 6);
    // check data - they are not connected
    assertTrue(owner.getInverses().isEmpty());
    assertTrue(inverse.getOwners().isEmpty());
    //create new relationship and persist it
    em.getTransaction().begin();
    owner.getInverses().add(inverse);
    inverse.getOwners().put(owner.getName(), owner);
    em.getTransaction().commit();
    
    //load owner and inverse and check relationship
    EntityManager em1 = factory.createEntityManager();
    // load both entities
    MtmOwner ownerCheck = em1.find(MtmOwner.class, 6);
    MtmInverse inverseCheck = em1.find(MtmInverse.class, 6);
    
    assertTrue(ownerCheck.getInverses().contains(inverseCheck));
    assertTrue(inverseCheck.getOwners().containsValue(ownerCheck));
    em1.close();
  }

  /**
   * Test whether persistence of many-to-many 
   * relationship between two new entities works
   * correctly.
   * 
   */
  @Test
  public void persist() {
    EntityManager em = factory.createEntityManager();
    em.getTransaction().begin();
    // create new entities with relationship
    MtmOwner newOwner = new MtmOwner(22, "name");
    MtmInverse newInverse = new MtmInverse(22);
    newOwner.getInverses().add(newInverse);
    newInverse.getOwners().put(newOwner.getName(), newOwner);
    //persist them
    em.persist(newOwner);
    em.persist(newInverse);

    em.getTransaction().commit();
    em.close();
    
    // check data - the owner should have some inverse
    EntityManager em1 = factory.createEntityManager();
    MtmOwner owner = em1.find(MtmOwner.class, 22);
    // owner references only one inverse 
    assertEquals(1, owner.getInverses().size());
    
    // the inverse keeps the owner in the map 
    for (MtmInverse inverse : owner.getInverses()) {
      assertEquals(owner, inverse.getOwners().get("name"));
    }

    em1.close();
  }

  /**
   * Test whether load of entities with
   * many-to-many relationship with custom
   * join table works correctly.
   * 
   */
  @Test
  public void loadTable() {
    EntityManager em = factory.createEntityManager();
    // load the owner
    TableMtmOwner owner = em.find(TableMtmOwner.class, 1);
    // check data - the owner should have some inverse
    assertFalse(owner.getInverses().isEmpty());
    
    // owners name should be a key to the map 
    for (TableMtmInverse inverse : owner.getInverses()) {
      Map<String, TableMtmOwner> owners = inverse.getOwners();
      assertEquals(owner, owners.get(owner.getName()));
    }

    em.close();
  }

  /**
   * Test whether update of many-to-many 
   * relationship with custom table works 
   * correctly.
   * 
   */
  @Test
  public void updateTable() {
    EntityManager em = factory.createEntityManager();
    // load both entities
    TableMtmOwner owner = em.find(TableMtmOwner.class, 6);
    TableMtmInverse inverse = em.find(TableMtmInverse.class, 6);
    // check data - they are not connected
    assertTrue(owner.getInverses().isEmpty());
    assertTrue(inverse.getOwners().isEmpty());
    //create new relationship and persist it
    em.getTransaction().begin();
    owner.getInverses().add(inverse);
    inverse.getOwners().put(owner.getName(), owner);
    em.getTransaction().commit();
    
    //load owner and inverse and check relationship
    EntityManager em1 = factory.createEntityManager();
    // load both entities
    TableMtmOwner ownerCheck = em1.find(TableMtmOwner.class, 6);
    TableMtmInverse inverseCheck = em1.find(TableMtmInverse.class, 6);
    
    assertTrue(ownerCheck.getInverses().contains(inverseCheck));
    assertTrue(inverseCheck.getOwners().containsValue(ownerCheck));
    em1.close();
  }

  /**
   * Test whether persistence of many-to-many 
   * relationship between two new entities works
   * correctly.
   * 
   */
  @Test
  public void persistTable() {
    EntityManager em = factory.createEntityManager();
    em.getTransaction().begin();
    // create new entities with relationship
    TableMtmOwner newOwner = new TableMtmOwner(22, "name");
    TableMtmInverse newInverse = new TableMtmInverse(22);
    newOwner.getInverses().add(newInverse);
    newInverse.getOwners().put(newOwner.getName(), newOwner);
    //persist them
    em.persist(newOwner);
    em.persist(newInverse);

    em.getTransaction().commit();
    em.close();
    
    // check data - the owner should have some inverse
    EntityManager em1 = factory.createEntityManager();
    TableMtmOwner owner = em1.find(TableMtmOwner.class, 22);
    // owner references only one inverse 
    assertEquals(1, owner.getInverses().size());
    
    // the inverse keeps the owner in the map 
    for (TableMtmInverse inverse : owner.getInverses()) {
      assertEquals(owner, inverse.getOwners().get("name"));
    }

    em1.close();
  }
}
