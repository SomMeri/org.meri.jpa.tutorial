package org.meri.jpa.relationships.entities.onetomany_manytoone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CustomColumnMapOwner {

  @Id
  private long id;
  @Column(name="customMapKey")
  private String mapKey;

  @ManyToOne
  @JoinColumn(name="customcolumn")
  private CustomColumnMapInverse inverse;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public CustomColumnMapInverse getInverse() {
    return inverse;
  }

  public void setInverse(CustomColumnMapInverse inverse) {
    this.inverse = inverse;
  }

  public String getMapKey() {
    return mapKey;
  }

  public void setMapKey(String mapKey) {
    this.mapKey = mapKey;
  }

}
