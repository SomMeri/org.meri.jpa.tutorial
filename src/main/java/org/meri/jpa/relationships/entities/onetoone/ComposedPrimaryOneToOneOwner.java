package org.meri.jpa.relationships.entities.onetoone;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class ComposedPrimaryOneToOneOwner {

  @Id
  private long id1;
  //FIXME: dokoncit!!
  private long id2;
  
  @OneToOne
  @PrimaryKeyJoinColumn
  private ComposedPrimaryOneToOneInverse inverse;

  public ComposedPrimaryOneToOneOwner() {
  }

  public ComposedPrimaryOneToOneOwner(int id1, int id2) {
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

  public ComposedPrimaryOneToOneInverse getInverse() {
    return inverse;
  }

  public void setInverse(ComposedPrimaryOneToOneInverse inverse) {
    this.inverse = inverse;
  }

}
