package org.meri.jpa.relationships.entities.onetomany_manytoone;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MapOwner {

  @Id
  private long id;
  private String mapKey;
  
  @ManyToOne
  private MapInverse inverse;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public MapInverse getInverse() {
    return inverse;
  }

  public void setInverse(MapInverse inverse) {
    this.inverse = inverse;
  }

  public String getMapKey() {
    return mapKey;
  }

  public void setMapKey(String mapKey) {
    this.mapKey = mapKey;
  }
  
}
