package org.meri.jpa.relationships.entities.onetomany;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OneToManyInverse {

  @Id
  private long id;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

}
