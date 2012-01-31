package org.meri.jpa.relationships.entities.onetoone;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class LazyOneToOneInverse {

  @Id
  private long id;

  //lazy loading on the inverse side does not work
  //the parameter fetch=FetchType.LAZY is useless here
  @OneToOne(mappedBy="inverse",optional=false,fetch=FetchType.LAZY)
  private LazyOneToOneOwner owner;

  public LazyOneToOneInverse() {
  }

  public LazyOneToOneInverse(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public LazyOneToOneOwner getOwner() {
    return owner;
  }

  public void setOwner(LazyOneToOneOwner owner) {
    this.owner = owner;
  }
  
}
