package org.meri.jpa.relationships.entities.onetomany;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class ColumnOneToManyOwner {

  @Id
  private long id;

  @OneToMany
  @JoinColumn(name="owner_id")
  private List<ColumnOneToManyInverse> inverses;

  public ColumnOneToManyOwner() {
  }

  public ColumnOneToManyOwner(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<ColumnOneToManyInverse> getInverses() {
    return inverses;
  }

  public void setInverses(List<ColumnOneToManyInverse> inverses) {
    this.inverses = inverses;
  }
  
}
