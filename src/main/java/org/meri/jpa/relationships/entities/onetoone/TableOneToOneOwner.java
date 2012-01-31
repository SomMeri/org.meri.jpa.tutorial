package org.meri.jpa.relationships.entities.onetoone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

@Entity
public class TableOneToOneOwner {

  @Id
  @Column(name="owner_id")
  private long id;
  
  //INCORRECT, DOES NOT WORK
  @OneToOne
  @JoinTable(name="TableOneToOneJoin", 
    joinColumns=@JoinColumn(name="join_owner_id", referencedColumnName="owner_id"), 
    inverseJoinColumns=@JoinColumn(name="join_inverse_id", referencedColumnName="inverse_id")
  )
  private TableOneToOneInverse inverse;

  public TableOneToOneOwner(int id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public TableOneToOneInverse getInverse() {
    return inverse;
  }

  public void setInverse(TableOneToOneInverse inverse) {
    this.inverse = inverse;
  }

}
