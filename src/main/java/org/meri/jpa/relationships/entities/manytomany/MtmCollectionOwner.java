package org.meri.jpa.relationships.entities.manytomany;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class MtmCollectionOwner {

  @Id
  private long id;

  @ManyToMany
  @JoinTable(name="MtmCollectionJoinTable")
  private Collection<MtmCollectionInverse> inverses = new ArrayList<MtmCollectionInverse>();


  public MtmCollectionOwner() {
  }

  public MtmCollectionOwner(int id) {
    this();
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Collection<MtmCollectionInverse> getInverses() {
    return inverses;
  }

  public void setInverses(Collection<MtmCollectionInverse> inverses) {
    this.inverses = inverses;
  }

}
