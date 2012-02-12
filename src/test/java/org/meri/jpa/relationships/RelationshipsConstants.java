package org.meri.jpa.relationships;

import java.math.BigDecimal;

class RelationshipsConstants {
  public static final String ONE_TO_ONE_CHANGELOG_PATH = "src/test/java/org/meri/jpa/relationships/db.onetoone.changelog.xml";
  public static final String ONE_TO_MANY_MANY_TO_ONE_CHANGELOG_PATH = "src/test/java/org/meri/jpa/relationships/db.onetomany_manytoone.changelog.xml";
  public static final String ONE_TO_MANY_CHANGELOG_PATH = "src/test/java/org/meri/jpa/relationships/db.onetomany.changelog.xml";
  public static final String MANY_TO_ONE_CHANGELOG_PATH = "src/test/java/org/meri/jpa/relationships/db.manytoone.changelog.xml";
  public static final String MANY_TO_MANY_CHANGELOG_PATH = "src/test/java/org/meri/jpa/relationships/db.manytomany.changelog.xml";
  public static final String PERSIST_CHANGELOG_PATH = "src/test/java/org/meri/jpa/relationships/db.persist.changelog.xml";

  public static final String PERSISTENCE_UNIT = "Relationships";
  public static final BigDecimal SIMON_SLASH_ID = BigDecimal.valueOf(1);

}
