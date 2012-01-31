package org.meri.jpa.relationships.entities.onetomany_manytoone;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UnidirectionalManyToOneOwner {

  @Id
  private long id;

  @ManyToOne
  private UnidirectionalManyToOneInverse inverse;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public UnidirectionalManyToOneInverse getInverse() {
    return inverse;
  }

  public void setInverse(UnidirectionalManyToOneInverse inverse) {
    this.inverse = inverse;
  }


}
