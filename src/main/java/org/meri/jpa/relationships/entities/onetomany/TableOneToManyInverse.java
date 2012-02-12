package org.meri.jpa.relationships.entities.onetomany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TableOneToManyInverse {

  @Id
  @Column(name="inverse_id")
  private long id;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

}
