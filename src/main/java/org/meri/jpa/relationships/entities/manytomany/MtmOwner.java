package org.meri.jpa.relationships.entities.manytomany;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="tblMtmOwner")
public class MtmOwner {

  @Id
  private long id;

  private String name;
  @ManyToMany
  private Collection<MtmInverse> inverses = new ArrayList<MtmInverse>();

  public MtmOwner() {
  }

  public MtmOwner(int id, String name) {
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

  public Collection<MtmInverse> getInverses() {
    return inverses;
  }

  public void setInverses(Collection<MtmInverse> inverses) {
    this.inverses = inverses;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
