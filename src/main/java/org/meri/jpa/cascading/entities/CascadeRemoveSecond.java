package org.meri.jpa.cascading.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class CascadeRemoveSecond {

  @Id
  private long id;

  @ManyToOne
  private CascadeRemoveFirst first;
  
  @OneToMany(cascade=CascadeType.ALL, mappedBy="second")
  private List<CascadeRemoveThird> third = new ArrayList<CascadeRemoveThird>();

  public CascadeRemoveSecond() {
  }

  public CascadeRemoveSecond(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public CascadeRemoveFirst getFirst() {
    return first;
  }

  public void setFirst(CascadeRemoveFirst first) {
    this.first = first;
  }

  public List<CascadeRemoveThird> getThird() {
    return third;
  }

  public void setThird(List<CascadeRemoveThird> third) {
    this.third = third;
  }
  
}
