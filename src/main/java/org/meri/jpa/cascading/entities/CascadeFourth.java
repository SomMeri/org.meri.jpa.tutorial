package org.meri.jpa.cascading.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class CascadeFourth {
  @Id
  private long id;
  
  @ManyToMany(mappedBy="fourth")
  private List<CascadeThird> third = new ArrayList<CascadeThird>();

  public CascadeFourth() {
  }

  public CascadeFourth(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<CascadeThird> getThird() {
    return third;
  }

  public void setThird(List<CascadeThird> third) {
    this.third = third;
  }

}
