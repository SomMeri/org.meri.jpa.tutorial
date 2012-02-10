package org.meri.jpa.cascading.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class CascadeSecond {

  @Id
  private long id;

  @ManyToMany(mappedBy="second")
  private List<CascadeFirst> first = new ArrayList<CascadeFirst>();
  
  //FIXME many to may with cascade remove test spravit
  @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private List<CascadeThird> third = new ArrayList<CascadeThird>();

  public CascadeSecond() {
  }

  public CascadeSecond(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<CascadeFirst> getFirst() {
    return first;
  }

  public void setFirst(List<CascadeFirst> first) {
    this.first = first;
  }

  public List<CascadeThird> getThird() {
    return third;
  }

  public void setThird(List<CascadeThird> third) {
    this.third = third;
  }
  
}
