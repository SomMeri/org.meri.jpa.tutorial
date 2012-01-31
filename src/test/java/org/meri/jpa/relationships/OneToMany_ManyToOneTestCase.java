package org.meri.jpa.relationships;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.meri.jpa.relationships.entities.onetomany_manytoone.CollectionInverse;
import org.meri.jpa.relationships.entities.onetomany_manytoone.CollectionOwner;
import org.meri.jpa.relationships.entities.onetomany_manytoone.ColumnOneToManyInverse;
import org.meri.jpa.relationships.entities.onetomany_manytoone.ColumnOneToManyOwner;
import org.meri.jpa.relationships.entities.onetomany_manytoone.CustomColumnCollectionInverse;
import org.meri.jpa.relationships.entities.onetomany_manytoone.CustomColumnCollectionOwner;
import org.meri.jpa.relationships.entities.onetomany_manytoone.CustomColumnMapInverse;
import org.meri.jpa.relationships.entities.onetomany_manytoone.CustomColumnMapOwner;
import org.meri.jpa.relationships.entities.onetomany_manytoone.LazyOwner;
import org.meri.jpa.relationships.entities.onetomany_manytoone.MapInverse;
import org.meri.jpa.relationships.entities.onetomany_manytoone.MapOwner;
import org.meri.jpa.relationships.entities.onetomany_manytoone.OneToManyInverse;
import org.meri.jpa.relationships.entities.onetomany_manytoone.OneToManyOwner;
import org.meri.jpa.relationships.entities.onetomany_manytoone.OrderedInverse;
import org.meri.jpa.relationships.entities.onetomany_manytoone.OrderedOwner;
import org.meri.jpa.relationships.entities.onetomany_manytoone.OrphanInverse;
import org.meri.jpa.relationships.entities.onetomany_manytoone.OrphanOneToManyInverse;
import org.meri.jpa.relationships.entities.onetomany_manytoone.OrphanOneToManyOwner;
import org.meri.jpa.relationships.entities.onetomany_manytoone.OrphanOwner;
import org.meri.jpa.relationships.entities.onetomany_manytoone.TableOneToManyInverse;
import org.meri.jpa.relationships.entities.onetomany_manytoone.TableOneToManyOwner;
import org.meri.jpa.relationships.entities.onetomany_manytoone.UnidirectionalManyToOneInverse;
import org.meri.jpa.relationships.entities.onetomany_manytoone.UnidirectionalManyToOneOwner;


public class OneToMany_ManyToOneTestCase extends AbstractRelationshipTestCase {

  protected static final String CHANGELOG_LOCATION = RelationshipsConstants.ONE_TO_MANY_CHANGELOG_LOCATION;

  @Override
  protected String getInitialChangeLog() {
    return CHANGELOG_LOCATION;
  }

  @Test
  public void basicCollection() {
    EntityManager em = factory.createEntityManager();
    
    CollectionOwner owner = em.find(CollectionOwner.class, 1);
    
    CollectionInverse inverse = owner.getInverse();
    assertEquals(5, inverse.getId());
    assertEquals(1, inverse.getOwners().size());

    em.close();
  }

  @Test
  public void eagerLoadingCollection() {
    EntityManager em = factory.createEntityManager();
    
    CollectionOwner owner = em.find(CollectionOwner.class, 1);
    em.close();

    //this side is eagerly loaded by default
    CollectionInverse inverse = owner.getInverse();
    assertEquals(5, inverse.getId());
  }

  @Test
  public void lazyLoadingCollection() {
    EntityManager em = factory.createEntityManager();
    
    LazyOwner owner = em.find(LazyOwner.class, 1);
    em.close();

    // it is possible to configure it to be lazy
    assertNull(owner.getInverse());
  }

  @Test
  public void basicMap() {
    EntityManager em = factory.createEntityManager();
    
    MapOwner owner = em.find(MapOwner.class, 1);
    
    MapInverse inverse = owner.getInverse();
    assertEquals(5, inverse.getId());
    // mapKey property is a key to the map
    assertEquals(owner, inverse.getOwners().get(owner.getMapKey()));
    
    em.close();
  }
  
  @Test
  public void orphanRemoval() {
    EntityManager em = factory.createEntityManager();
    em.getTransaction().begin();
    // load the inverse
    OrphanInverse inverse = em.find(OrphanInverse.class, 5);
    // check whether the owner exists
    assertEquals(1, inverse.getOwners().get("first").getId());
    // remove the owner
    inverse.getOwners().remove("first");
    // commit the transaction and close manager
    em.getTransaction().commit();
    em.close();
  
    // owner without inverse has been removed
    assertEntityNOTExists(OrphanOwner.class, 1);
  }

  @Test
  public void customColumnCollection() {
    EntityManager em = factory.createEntityManager();
    
    CustomColumnCollectionOwner owner = em.find(CustomColumnCollectionOwner.class, 1);
    
    CustomColumnCollectionInverse inverse = owner.getInverse();
    assertEquals(5, inverse.getId());
    assertEquals(1, inverse.getOwners().size());

    em.close();
  }

  @Test
  public void customColumnMap() {
    EntityManager em = factory.createEntityManager();
    
    CustomColumnMapOwner owner = em.find(CustomColumnMapOwner.class, 1);
    
    CustomColumnMapInverse inverse = owner.getInverse();
    assertEquals(5, inverse.getId());
    // mapKey property is a key to the map
    assertEquals(owner, inverse.getOwners().get(owner.getMapKey()));
    
    em.close();
  }
  
  @Test
  public void orderedCollection() {
    EntityManager em = factory.createEntityManager();
    
    OrderedOwner owner = em.find(OrderedOwner.class, 1);
    
    OrderedInverse inverse = owner.getInverse();
    assertOrdered(inverse.getOwners());

    em.close();
  }

  private void assertOrdered(List<OrderedOwner> owners) {
    for (int i = 1; i < owners.size(); i++) {
      OrderedOwner current = owners.get(i);
      OrderedOwner previous = owners.get(i-1);
      
      if (current.getOrdering() == previous.getOrdering()) {
        assertTrue(current.getId() < previous.getId());
      } else {
        assertTrue(current.getOrdering() > previous.getOrdering());
      }
    }
  }

  @Test
  public void unidirectionalManyToOne() {
    EntityManager em = factory.createEntityManager();
    
    UnidirectionalManyToOneOwner owner = em.find(UnidirectionalManyToOneOwner.class, 1);
    em.close();

    //it is eager by default
    UnidirectionalManyToOneInverse inverse = owner.getInverse();
    assertEquals(5, inverse.getId());
  }

  @Test
  public void unidirectionalOneToMany() {
    EntityManager em = factory.createEntityManager();
    
    OneToManyOwner owner = em.find(OneToManyOwner.class, 1);

    Collection<OneToManyInverse> inverses = owner.getInverses();
    assertEquals(1, inverses.size());

    em.close();
  }

  @Test
  public void columnOneToMany() {
    EntityManager em = factory.createEntityManager();
    
    ColumnOneToManyOwner owner = em.find(ColumnOneToManyOwner.class, 1);

    Collection<ColumnOneToManyInverse> inverses = owner.getInverses();
    assertEquals(1, inverses.size());

    em.close();
  }

  @Test
  public void insertUpdateLoadColumnOneToManyOwner() {
    //WARNING: this test may behave unpredictably
    ColumnOneToManyOwner owner = new ColumnOneToManyOwner(10);
    insert(owner);

    ColumnOneToManyInverse inverse = new ColumnOneToManyInverse(9);
    insert(inverse);

    //reload owner and inverse with another entity manager
    EntityManager em = factory.createEntityManager();
    inverse = em.find(ColumnOneToManyInverse.class, 9);
    owner = em.find(ColumnOneToManyOwner.class, 10);
    em.close();

    //create reference between owner and inverse and save them
    owner.setInverses(Arrays.asList(inverse));
    updateOrInsert(inverse);
    updateOrInsert(owner);

    //load the owner again 
    em = factory.createEntityManager();
    owner = em.find(ColumnOneToManyOwner.class, 10);
    
    //if the reference would be saved, the owner would have 1 inverse
    assertEquals(0, owner.getInverses().size());
    em.close();
  }

  @Test
  public void tableOneToMany() {
    EntityManager em = factory.createEntityManager();
    
    TableOneToManyOwner owner = em.find(TableOneToManyOwner.class, 1);

    Collection<TableOneToManyInverse> inverses = owner.getInverses();
    assertEquals(1, inverses.size());

    em.close();
  }

  @Test
  public void orphanRemovalOneToMany() {
    EntityManager em = factory.createEntityManager();
    em.getTransaction().begin();
    // check initial data - inverse is available
    OrphanOneToManyInverse inverse = em.find(OrphanOneToManyInverse.class, 5);
    assertNotNull(inverse);
    // load the owner and remove orphan
    OrphanOneToManyOwner owner = em.find(OrphanOneToManyOwner.class, 1);
    owner.getInverses().remove(inverse);
    // commit the transaction and close manager
    em.getTransaction().commit();
    em.close();
  
    // inverse without owner has been removed
    assertEntityNOTExists(OrphanOneToManyInverse.class, 5);
  }

}
