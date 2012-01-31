package org.meri.jpa.relationships.entities.onetoone;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class LazyOneToOneOwner {

  @Id
  private long id;
  @OneToOne(optional=false,fetch=FetchType.LAZY)
  private LazyOneToOneInverse inverse;

  public LazyOneToOneOwner() {
  }

  public LazyOneToOneOwner(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public LazyOneToOneInverse getInverse() {
    return inverse;
  }

  public void setInverse(LazyOneToOneInverse inverse) {
    this.inverse = inverse;
  }

}
