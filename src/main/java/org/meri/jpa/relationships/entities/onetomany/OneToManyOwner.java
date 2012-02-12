package org.meri.jpa.relationships.entities.onetomany;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class OneToManyOwner {

  @Id
  private long id;

  @OneToMany
  private Collection<OneToManyInverse> inverses;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Collection<OneToManyInverse> getInverses() {
    return inverses;
  }

  public void setInverses(Collection<OneToManyInverse> inverses) {
    this.inverses = inverses;
  }
  
}
