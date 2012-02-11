package org.meri.jpa.cascading.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class CascadeRemoveFirst {

  @Id
  private long id;

  @OneToMany(cascade=CascadeType.ALL, mappedBy="first")
  private List<CascadeRemoveSecond> second;
  private String someValue;

  public CascadeRemoveFirst() {
  }

  public CascadeRemoveFirst(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<CascadeRemoveSecond> getSecond() {
    return second;
  }

  public void setSecond(List<CascadeRemoveSecond> second) {
    this.second = second;
  }

  public String getSomeValue() {
    return someValue;
  }

  public void setSomeValue(String someValue) {
    this.someValue = someValue;
  }
  
}
