package org.meri.jpa.relationships.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UnidirectionalOneToOneInverse {

  @Id
  private long id;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

}
