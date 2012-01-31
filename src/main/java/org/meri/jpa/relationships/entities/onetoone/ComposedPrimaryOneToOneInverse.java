package org.meri.jpa.relationships.entities.onetoone;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ComposedPrimaryOneToOneInverse {

  @Id
  private long id1;
  //FIXME: dokoncit!!
  private long id2;

  @OneToOne(mappedBy="inverse")
  private ComposedPrimaryOneToOneOwner owner;

  public ComposedPrimaryOneToOneInverse() {
  }

  public ComposedPrimaryOneToOneInverse(int id1, int id2) {
    this();
    this.id1 = id1;
    this.id2 = id2;
  }

  public long getId1() {
    return id1;
  }

  public void setId1(long id1) {
    this.id1 = id1;
  }

  public long getId2() {
    return id2;
  }

  public void setId2(long id2) {
    this.id2 = id2;
  }

  public ComposedPrimaryOneToOneOwner getOwner() {
    return owner;
  }

  public void setOwner(ComposedPrimaryOneToOneOwner owner) {
    this.owner = owner;
  }
  
}
