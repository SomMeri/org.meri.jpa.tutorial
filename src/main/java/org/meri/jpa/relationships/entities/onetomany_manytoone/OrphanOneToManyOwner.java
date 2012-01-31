package org.meri.jpa.relationships.entities.onetomany_manytoone;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class OrphanOneToManyOwner {

  @Id
  private long id;

  @OneToMany(orphanRemoval=true)
  private Collection<OrphanOneToManyInverse> inverses;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Collection<OrphanOneToManyInverse> getInverses() {
    return inverses;
  }

  public void setInverses(Collection<OrphanOneToManyInverse> inverses) {
    this.inverses = inverses;
  }

}
