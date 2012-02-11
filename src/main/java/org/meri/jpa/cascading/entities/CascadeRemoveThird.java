package org.meri.jpa.cascading.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CascadeRemoveThird {
  @Id
  private long id;
  
  @ManyToOne
  private CascadeRemoveSecond second;

  private String someValue;
  
  public CascadeRemoveThird() {
  }

  public CascadeRemoveThird(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public CascadeRemoveSecond getSecond() {
    return second;
  }

  public void setSecond(CascadeRemoveSecond second) {
    this.second = second;
  }

  public String getSomeValue() {
    return someValue;
  }

  public void setSomeValue(String someValue) {
    this.someValue = someValue;
  }
  
}
