package org.meri.jpa.relationships.entities.onetomany_manytoone;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class OrderedInverse {

  @Id
  private long id;

  @OneToMany(mappedBy="inverse")
  @OrderBy("ordering ASC, id DESC")
  private List<OrderedOwner> owners;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<OrderedOwner> getOwners() {
    return owners;
  }

  public void setOwners(List<OrderedOwner> owners) {
    this.owners = owners;
  }

}
