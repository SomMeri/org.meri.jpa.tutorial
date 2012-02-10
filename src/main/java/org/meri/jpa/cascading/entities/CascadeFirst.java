package org.meri.jpa.cascading.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class CascadeFirst {

  @Id
  private long id;
  //FIXME many to may with cascade remove; create new classes and 
  @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private List<CascadeSecond> second;
  private String someValue;

  public CascadeFirst() {
  }

  public CascadeFirst(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<CascadeSecond> getSecond() {
    return second;
  }

  public void setSecond(List<CascadeSecond> second) {
    this.second = second;
  }

  public String getSomeValue() {
    return someValue;
  }

  public void setSomeValue(String someValue) {
    this.someValue = someValue;
  }
  
}
