package org.meri.jpa.relationships.entities.onetoone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ColumnOneToOneInverse {

  @Id
  @Column(name="inverse_id")
  private long id;

  @OneToOne(mappedBy="inverse")
  private ColumnOneToOneOwner owner;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public ColumnOneToOneOwner getOwner() {
    return owner;
  }

  public void setOwner(ColumnOneToOneOwner owner) {
    this.owner = owner;
  }
  
}
