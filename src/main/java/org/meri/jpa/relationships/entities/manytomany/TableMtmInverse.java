package org.meri.jpa.relationships.entities.manytomany;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;

@Entity
public class TableMtmInverse {

  @Id
  @Column(name="inverse_id")
  private long id;

  @ManyToMany(mappedBy="inverses")
  @MapKey(name="name")
  private Map<String, TableMtmOwner> owners = new HashMap<String, TableMtmOwner>();

  public TableMtmInverse() {
  }

  public TableMtmInverse(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Map<String, TableMtmOwner> getOwners() {
    return owners;
  }

  public void setOwners(Map<String, TableMtmOwner> owners) {
    this.owners = owners;
  }

}
