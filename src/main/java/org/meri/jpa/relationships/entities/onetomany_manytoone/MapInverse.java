package org.meri.jpa.relationships.entities.onetomany_manytoone;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

@Entity
public class MapInverse {

  @Id
  private long id;

  @OneToMany(mappedBy="inverse")
  @MapKey(name="mapKey")
  private Map<String, MapOwner> owners;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Map<String, MapOwner> getOwners() {
    return owners;
  }

  public void setOwners(Map<String, MapOwner> owners) {
    this.owners = owners;
  }

}
