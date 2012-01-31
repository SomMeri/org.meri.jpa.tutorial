package org.meri.jpa.relationships.entities.onetomany_manytoone;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

@Entity
public class OrphanInverse {

  @Id
  private long id;

  @OneToMany(mappedBy="inverse", orphanRemoval=true)
  @MapKey(name="mapKey")
  private Map<String, OrphanOwner> owners;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Map<String, OrphanOwner> getOwners() {
    return owners;
  }

  public void setOwners(Map<String, OrphanOwner> owners) {
    this.owners = owners;
  }

}
