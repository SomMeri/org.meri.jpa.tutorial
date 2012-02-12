package org.meri.jpa.relationships.entities.bestpractice;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="facebook_account")
public class SafeFacebookAccount {
  
  @Id
  private long id;
  private String username;
  @OneToOne
  @JoinColumn(name="owner_id")
  private SafePerson owner;

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

  public SafePerson getOwner() {
    return owner;
  }

  public void setOwner(SafePerson owner) {
    //prevent endless loop
    if (sameAsFormer(owner))
      return ;
    //set new owner
    SafePerson oldOwner = this.owner;
    this.owner = owner;
    //remove from the old owner
    if (oldOwner!=null)
      oldOwner.setFacebookAccount(null);
    //set myself into new owner
    if (owner!=null)
      owner.setFacebookAccount(this);
  }

  private boolean sameAsFormer(SafePerson newOwner) {
    if (owner == null)
      return newOwner == null;
    
    return owner.equals(newOwner);
  }

  @Override
  public String toString() {
    return "FacebookAccount [id=" + id + ", username=" + username + ", owner=" + owner + "]";
  }
 
}
