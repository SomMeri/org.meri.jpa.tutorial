package org.meri.jpa.relationships;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.meri.jpa.relationships.entities.manytoone.UnidirectionalManyToOneInverse;
import org.meri.jpa.relationships.entities.manytoone.UnidirectionalManyToOneOwner;

public class ManyToOneTestCase extends AbstractRelationshipTestCase {

  protected static final String CHANGELOG_LOCATION = RelationshipsConstants.MANY_TO_ONE_CHANGELOG_PATH;

  @Override
  protected String getInitialChangeLog() {
    return CHANGELOG_LOCATION;
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

}
