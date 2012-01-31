package org.meri.jpa.relationships.entities.onetomany_manytoone;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CollectionOwner")
public class LazyOwner {

  @Id
  private long id;

  @ManyToOne(fetch=FetchType.LAZY)
  private LazyInverse inverse;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public LazyInverse getInverse() {
    return inverse;
  }

  public void setInverse(LazyInverse inverse) {
    this.inverse = inverse;
  }

  
}
