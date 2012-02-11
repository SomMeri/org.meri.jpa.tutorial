package org.meri.jpa.relationships.entities.manytomany;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

@Entity
public class TableMtmOwner {
  @Id
  @Column(name="owner_id")
  private long id;

  private String name;
  @ManyToMany
  @JoinTable(name="TableMtmJoin", 
    joinColumns=@JoinColumn(name="owner"), 
    inverseJoinColumns=@JoinColumn(name="inverse")
  )
  private Collection<TableMtmInverse> inverses = new ArrayList<TableMtmInverse>();

  public TableMtmOwner() {
  }

  public TableMtmOwner(int id, String name) {
    this();
    this.id = id;
    this.name = name;
  }

  public TableMtmOwner(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Collection<TableMtmInverse> getInverses() {
    return inverses;
  }

  public void setInverses(Collection<TableMtmInverse> inverses) {
    this.inverses = inverses;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
