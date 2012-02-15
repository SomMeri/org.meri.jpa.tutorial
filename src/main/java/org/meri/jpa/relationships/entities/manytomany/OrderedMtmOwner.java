package org.meri.jpa.relationships.entities.manytomany;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;

@Entity
public class OrderedMtmOwner {

  @Id
  private long id;

  private String name;
  @ManyToMany
  @OrderBy("ordering ASC, id DESC")
  private List<OrderedMtmInverse> inverses = new ArrayList<OrderedMtmInverse>();

  public OrderedMtmOwner() {
  }

  public OrderedMtmOwner(int id, String name) {
    this();
    this.id = id;
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<OrderedMtmInverse> getInverses() {
    return inverses;
  }

  public void setInverses(List<OrderedMtmInverse> inverses) {
    this.inverses = inverses;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
