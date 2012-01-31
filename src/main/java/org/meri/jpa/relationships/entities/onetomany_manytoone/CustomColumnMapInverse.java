package org.meri.jpa.relationships.entities.onetomany_manytoone;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

@Entity
public class CustomColumnMapInverse {

  @Id
  private long id;

  @OneToMany(mappedBy="inverse")
  @MapKey(name="mapKey")
  private Map<String, CustomColumnMapOwner> owners;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Map<String, CustomColumnMapOwner> getOwners() {
    return owners;
  }

  public void setOwners(Map<String, CustomColumnMapOwner> owners) {
    this.owners = owners;
  }

}
