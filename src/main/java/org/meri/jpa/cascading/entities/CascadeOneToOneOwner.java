package org.meri.jpa.cascading.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class CascadeOneToOneOwner {

  @Id
  private long id;
  @OneToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
  private CascadeOneToOneInverse inverse;

  public CascadeOneToOneOwner() {
  }

  public CascadeOneToOneOwner(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public CascadeOneToOneInverse getInverse() {
    return inverse;
  }

  public void setInverse(CascadeOneToOneInverse inverse) {
    this.inverse = inverse;
  }

}
