package org.meri.jpa.relationships.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="facebook_account")
public class FacebookAccount {
  
  @Id
  private long id;
  private String username;
  @OneToOne
  @JoinColumn(name="owner_id")
  private Person owner;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Person getOwner() {
    return owner;
  }

  public void setOwner(Person owner) {
    this.owner = owner;
  }

  @Override
  public String toString() {
    return "FacebookAccount [id=" + id + ", username=" + username + ", owner=" + owner + "]";
  }
 
}
