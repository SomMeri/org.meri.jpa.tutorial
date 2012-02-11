package org.meri.jpa.relationships;

import java.math.BigDecimal;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.meri.jpa.AbstractTestCase;

public class AbstractRelationshipTestCase extends AbstractTestCase {

  protected static final BigDecimal SIMON_SLASH_ID = RelationshipsConstants.SIMON_SLASH_ID;
  protected static final String PERSISTENCE_UNIT = RelationshipsConstants.PERSISTENCE_UNIT;
  protected static final String CHANGELOG_LOCATION = RelationshipsConstants.ONE_TO_ONE_CHANGELOG_PATH;

  protected static EntityManagerFactory factory;

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
