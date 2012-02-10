package org.meri.jpa.cascading.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class CascadeThird {
  @Id
  private long id;
  
  @ManyToMany(mappedBy="third")
  private List<CascadeSecond> second;

  @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private List<CascadeFourth> fourth;

  private String someValue;
  
  public CascadeThird() {
  }

  public CascadeThird(int id) {
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

  public List<CascadeFourth> getFourth() {
    return fourth;
  }

  public void setFourth(List<CascadeFourth> fourth) {
    this.fourth = fourth;
  }

  public String getSomeValue() {
    return someValue;
  }

  public void setSomeValue(String someValue) {
    this.someValue = someValue;
  }
  
}
