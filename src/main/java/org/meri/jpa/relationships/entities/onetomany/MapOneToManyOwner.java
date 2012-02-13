package org.meri.jpa.relationships.entities.onetomany;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

@Entity
public class MapOneToManyOwner {

  @Id
  private long id;

  @OneToMany
  @MapKey(name="mapKey")
  private Map<String, MapOneToManyInverse> inverses;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Map<String, MapOneToManyInverse> getInverses() {
    return inverses;
  }

  public void setInverses(Map<String, MapOneToManyInverse> owners) {
    this.inverses = owners;
  }

}
