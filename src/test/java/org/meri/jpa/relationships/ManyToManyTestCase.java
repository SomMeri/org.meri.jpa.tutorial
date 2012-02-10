package org.meri.jpa.relationships;



//FIXME: upratat changelog
public class ManyToManyTestCase extends AbstractRelationshipTestCase {

  protected static final String CHANGELOG_LOCATION = RelationshipsConstants.MANY_TO_MANY_CHANGELOG_PATH;

  @Override
  protected String getInitialChangeLog() {
    return CHANGELOG_LOCATION;
  }

}
