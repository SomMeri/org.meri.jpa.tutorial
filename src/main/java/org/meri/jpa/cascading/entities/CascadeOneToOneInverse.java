package org.meri.jpa.cascading.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class CascadeOneToOneInverse {

  @Id
  private long id;

  @OneToOne(mappedBy="inverse", cascade={CascadeType.MERGE, CascadeType.PERSIST})
  private CascadeOneToOneOwner owner;

  public CascadeOneToOneInverse() {
  }

  public CascadeOneToOneInverse(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public CascadeOneToOneOwner getOwner() {
    return owner;
  }

  public void setOwner(CascadeOneToOneOwner owner) {
    this.owner = owner;
  }
  
}
