package org.meri.jpa.relationships.entities.manytomany;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;

@Entity
public class OrderedMtmInverse {

  @Id
  private long id;
  private String ordering;

  @ManyToMany(mappedBy="inverses")
  @MapKey(name="name")
  private Map<String, OrderedMtmOwner> owners = new HashMap<String, OrderedMtmOwner>();

  public OrderedMtmInverse() {
  }

  public OrderedMtmInverse(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Map<String, OrderedMtmOwner> getOwners() {
    return owners;
  }

  public void setOwners(Map<String, OrderedMtmOwner> owners) {
    this.owners = owners;
  }

  public String getOrdering() {
    return ordering;
  }

  public void setOrdering(String ordering) {
    this.ordering = ordering;
  }
   
}
