package org.meri.jpa.relationships.entities.onetomany_manytoone;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrphanOwner {

  @Id
  private long id;
  private String mapKey;
  
  @ManyToOne
  private OrphanInverse inverse;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public OrphanInverse getInverse() {
    return inverse;
  }

  public void setInverse(OrphanInverse inverse) {
    this.inverse = inverse;
  }

  public String getMapKey() {
    return mapKey;
  }

  public void setMapKey(String mapKey) {
    this.mapKey = mapKey;
  }
  
}
