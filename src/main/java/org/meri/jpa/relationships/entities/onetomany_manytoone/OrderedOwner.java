package org.meri.jpa.relationships.entities.onetomany_manytoone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrderedOwner {

  @Id
  private long id;
  @Column(name="orderby")
  private long ordering;

  @ManyToOne
  private OrderedInverse inverse;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public OrderedInverse getInverse() {
    return inverse;
  }

  public void setInverse(OrderedInverse inverse) {
    this.inverse = inverse;
  }

  public long getOrdering() {
    return ordering;
  }

  public void setOrdering(long orderby) {
    this.ordering = orderby;
  }

}
