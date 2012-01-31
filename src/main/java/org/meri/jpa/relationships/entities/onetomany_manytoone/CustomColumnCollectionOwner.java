package org.meri.jpa.relationships.entities.onetomany_manytoone;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CustomColumnCollectionOwner {

  @Id
  private long id;

  @ManyToOne
  @JoinColumn(name="customcolumn")
  private CustomColumnCollectionInverse inverse;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public CustomColumnCollectionInverse getInverse() {
    return inverse;
  }

  public void setInverse(CustomColumnCollectionInverse inverse) {
    this.inverse = inverse;
  }

  
}
