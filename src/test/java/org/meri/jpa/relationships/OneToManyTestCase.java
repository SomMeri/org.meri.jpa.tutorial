package org.meri.jpa.relationships;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.meri.jpa.relationships.entities.onetomany.ColumnOneToManyInverse;
import org.meri.jpa.relationships.entities.onetomany.ColumnOneToManyOwner;
import org.meri.jpa.relationships.entities.onetomany.OneToManyInverse;
import org.meri.jpa.relationships.entities.onetomany.OneToManyOwner;
import org.meri.jpa.relationships.entities.onetomany.OrphanOneToManyInverse;
import org.meri.jpa.relationships.entities.onetomany.OrphanOneToManyOwner;
import org.meri.jpa.relationships.entities.onetomany.TableOneToManyInverse;
import org.meri.jpa.relationships.entities.onetomany.TableOneToManyOwner;

public class OneToManyTestCase extends AbstractRelationshipTestCase {

  protected static final String CHANGELOG_LOCATION = RelationshipsConstants.ONE_TO_MANY_CHANGELOG_PATH;

  @Override
  protected String getInitialChangeLog() {
    return CHANGELOG_LOCATION;
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
  public void tableOneToMany() {
    EntityManager em = factory.createEntityManager();
    
    TableOneToManyOwner owner = em.find(TableOneToManyOwner.class, 1);

    Collection<TableOneToManyInverse> inverses = owner.getInverses();
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
    mergeAndCommit(inverse);
    mergeAndCommit(owner);

    //load the owner again 
    em = factory.createEntityManager();
    owner = em.find(ColumnOneToManyOwner.class, 10);
    
    //if the reference would be saved, the owner would have 1 inverse
    assertEquals(0, owner.getInverses().size());
    em.close();
  }

  @Test
  public void orphanRemovalOneToMany() {
    // check initial data - inverse is available
    assertEntityExists(OrphanOneToManyInverse.class, 5);

    // load the owner and remove orphan
    EntityManager em = factory.createEntityManager();
    OrphanOneToManyOwner owner;
    owner = em.find(OrphanOneToManyOwner.class, 1);
    OrphanOneToManyInverse inverse;
    inverse = em.find(OrphanOneToManyInverse.class, 5);

    em.getTransaction().begin();
    owner.getInverses().remove(inverse);
    // commit the transaction and close manager
    em.getTransaction().commit();
    em.close();
  
    // inverse without owner has been removed
    assertEntityNOTExists(OrphanOneToManyInverse.class, 5);
  }

}
