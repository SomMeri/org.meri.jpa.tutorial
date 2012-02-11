package org.meri.jpa.relationships;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Map;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.meri.jpa.relationships.entities.manytomany.MtmInverse;
import org.meri.jpa.relationships.entities.manytomany.MtmOwner;

//FIXME: upratat changelog
public class ManyToManyTestCase extends AbstractRelationshipTestCase {

  protected static final String CHANGELOG_LOCATION = RelationshipsConstants.MANY_TO_MANY_CHANGELOG_PATH;

  @Override
  protected String getInitialChangeLog() {
    return CHANGELOG_LOCATION;
  }

  /**
   * Relationship is lazy loaded by default.
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
   * Load sample entities and test both collection and map.
   */
  @Test
  public void basicTest() {
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
}
