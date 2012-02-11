package org.meri.jpa.relationships.entities.manytomany;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;

@Entity
public class MtmInverse {

  @Id
  private long id;

  @ManyToMany(mappedBy="inverses")
  @MapKey(name="name")
  private Map<String, MtmOwner> owners = new HashMap<String, MtmOwner>();

  public MtmInverse() {
  }

  public MtmInverse(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Map<String, MtmOwner> getOwners() {
    return owners;
  }

  public void setOwners(Map<String, MtmOwner> owners) {
    this.owners = owners;
  }

}
