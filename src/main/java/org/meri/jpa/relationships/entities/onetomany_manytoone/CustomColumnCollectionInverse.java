package org.meri.jpa.relationships.entities.onetomany_manytoone;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class CustomColumnCollectionInverse {

  @Id
  private long id;

  @OneToMany(mappedBy="inverse")
  private Collection<CustomColumnCollectionOwner> owners;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Collection<CustomColumnCollectionOwner> getOwners() {
    return owners;
  }

  public void setOwners(Collection<CustomColumnCollectionOwner> owners) {
    this.owners = owners;
  }

}
