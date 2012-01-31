package org.meri.jpa.cascading.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OneToOneOwner {

  @Id
  private long id;
  @OneToOne
  private OneToOneInverse inverse;

  public OneToOneOwner() {
  }

  public OneToOneOwner(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public OneToOneInverse getInverse() {
    return inverse;
  }

  public void setInverse(OneToOneInverse inverse) {
    this.inverse = inverse;
  }

}
