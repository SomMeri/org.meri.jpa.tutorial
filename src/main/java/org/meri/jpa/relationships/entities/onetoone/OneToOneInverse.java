package org.meri.jpa.relationships.entities.onetoone;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OneToOneInverse {

  @Id
  private long id;

  @OneToOne(mappedBy="inverse")
  private OneToOneOwner owner;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public OneToOneOwner getOwner() {
    return owner;
  }

  public void setOwner(OneToOneOwner owner) {
    this.owner = owner;
  }
  
}
