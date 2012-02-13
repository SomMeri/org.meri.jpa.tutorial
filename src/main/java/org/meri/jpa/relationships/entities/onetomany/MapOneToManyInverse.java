package org.meri.jpa.relationships.entities.onetomany;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MapOneToManyInverse {

  @Id
  private long id;
  private String mapKey;
  
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getMapKey() {
    return mapKey;
  }

  public void setMapKey(String mapKey) {
    this.mapKey = mapKey;
  }
  
}
