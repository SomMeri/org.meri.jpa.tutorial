package org.meri.jpa.relationships.entities.onetomany_manytoone;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UnidirectionalManyToOneInverse {

  @Id
  private long id;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

}
