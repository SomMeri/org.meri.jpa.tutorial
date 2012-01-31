package org.meri.jpa.relationships.entities.onetoone;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class PrimaryOneToOneInverse {

  @Id
  private long id;

  @OneToOne(mappedBy="inverse")
  private PrimaryOneToOneOwner owner;

  public PrimaryOneToOneInverse() {
  }

  public PrimaryOneToOneInverse(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public PrimaryOneToOneOwner getOwner() {
    return owner;
  }

  public void setOwner(PrimaryOneToOneOwner owner) {
    this.owner = owner;
  }
  
}
