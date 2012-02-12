package org.meri.jpa.relationships.entities.onetomany;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ColumnOneToManyInverse {

  @Id
  private long id;

  public ColumnOneToManyInverse() {
  }

  public ColumnOneToManyInverse(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

}
