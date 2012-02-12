package org.meri.jpa.relationships.entities.onetomany;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class TableOneToManyOwner {

  @Id
  @Column(name="owner_id")
  private long id;

  @OneToMany
  @JoinTable(name="OneToManyJoinTable",
    joinColumns=@JoinColumn(name="owner"),
    inverseJoinColumns=@JoinColumn(name="inverse")
  )
  private List<TableOneToManyInverse> inverses;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<TableOneToManyInverse> getInverses() {
    return inverses;
  }

  public void setInverses(List<TableOneToManyInverse> inverses) {
    this.inverses = inverses;
  }

}
