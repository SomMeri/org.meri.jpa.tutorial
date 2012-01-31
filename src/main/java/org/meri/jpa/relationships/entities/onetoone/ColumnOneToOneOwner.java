package org.meri.jpa.relationships.entities.onetoone;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ColumnOneToOneOwner {

  @Id
  private long id;
  @OneToOne
  @JoinColumn(name="customcolumn")
  private ColumnOneToOneInverse inverse;

  public ColumnOneToOneOwner(int id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public ColumnOneToOneInverse getInverse() {
    return inverse;
  }

  public void setInverse(ColumnOneToOneInverse inverse) {
    this.inverse = inverse;
  }

}
