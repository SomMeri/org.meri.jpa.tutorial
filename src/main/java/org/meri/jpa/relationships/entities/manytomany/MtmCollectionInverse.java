package org.meri.jpa.relationships.entities.manytomany;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class MtmCollectionInverse {

  @Id
  private long id;

  @ManyToMany(mappedBy="inverses")
  private Collection<MtmCollectionOwner> owners = new ArrayList<MtmCollectionOwner>();

  public MtmCollectionInverse() {
  }

  public MtmCollectionInverse(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Collection<MtmCollectionOwner> getOwners() {
    return owners;
  }

  public void setOwners(Collection<MtmCollectionOwner> owners) {
    this.owners = owners;
  }

}
