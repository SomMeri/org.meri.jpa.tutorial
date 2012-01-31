package org.meri.jpa.relationships.entities.onetomany_manytoone;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="CollectionInverse")
public class LazyInverse {

  @Id
  private long id;

  @OneToMany(mappedBy="inverse")
  private Collection<LazyOwner> owners;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Collection<LazyOwner> getOwners() {
    return owners;
  }

  public void setOwners(Collection<LazyOwner> owners) {
    this.owners = owners;
  }

}
