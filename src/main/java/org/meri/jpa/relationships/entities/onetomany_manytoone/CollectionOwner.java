package org.meri.jpa.relationships.entities.onetomany_manytoone;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CollectionOwner {

  @Id
  private long id;

  @ManyToOne
  private CollectionInverse inverse;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public CollectionInverse getInverse() {
    return inverse;
  }

  public void setInverse(CollectionInverse inverse) {
    this.inverse = inverse;
  }

  
}
