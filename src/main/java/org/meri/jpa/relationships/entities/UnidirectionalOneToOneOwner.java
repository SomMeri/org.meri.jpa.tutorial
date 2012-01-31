package org.meri.jpa.relationships.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

//FIXME: do I use this???
@Entity
public class UnidirectionalOneToOneOwner {

  @Id
  private long id;
  @OneToOne
  private UnidirectionalOneToOneInverse otherEntity;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public UnidirectionalOneToOneInverse getOtherEntity() {
    return otherEntity;
  }

  public void setOtherEntity(UnidirectionalOneToOneInverse otherEntity) {
    this.otherEntity = otherEntity;
  }

}
