package org.meri.jpa.relationships.entities.onetoone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class TableOneToOneInverse {

  @Id
  @Column(name="inverse_id")
  private long id;

  @OneToOne(mappedBy="inverse")
  private TableOneToOneOwner owner;

  public TableOneToOneInverse() {
  }

  public TableOneToOneInverse(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public TableOneToOneOwner getOwner() {
    return owner;
  }

  public void setOwner(TableOneToOneOwner owner) {
    this.owner = owner;
  }
  
}
