package org.meri.jpa.relationships.entities.onetomany_manytoone;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class CollectionInverse {

  @Id
  private long id;

  @OneToMany(mappedBy="inverse")
  private Collection<CollectionOwner> owners;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Collection<CollectionOwner> getOwners() {
    return owners;
  }

  public void setOwners(Collection<CollectionOwner> owners) {
    this.owners = owners;
  }

}
