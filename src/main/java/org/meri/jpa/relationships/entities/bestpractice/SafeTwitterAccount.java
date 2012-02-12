package org.meri.jpa.relationships.entities.bestpractice;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="twitter_account")
public class SafeTwitterAccount {
  
  @Id
  private long id;
  private String accountName;
  
  @ManyToOne
  @JoinColumn(name="owner_id")
  private SafePerson owner;

  @ManyToMany
  private Set<SafePerson> followers = new HashSet<SafePerson>();;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    Set<SafePerson> allFollowers = new HashSet<SafePerson>(followers);
    for (SafePerson follower : allFollowers) {
      follower.stopFollowingTwitter(this);
    }
    
    this.accountName = accountName;

    for (SafePerson follower : allFollowers) {
      follower.startFollowingTwitter(this);
    }
  }

  public SafePerson getOwner() {
    return owner;
  }

  /**
   * Set new twitter account owner. The method keeps 
   * relationships consistency:
   * * this account is removed from the previous owner
   * * this account is added to next owner
   * 
   * @param owner
   */
  public void setOwner(SafePerson owner) {
    //prevent endless loop
    if (sameAsFormer(owner))
      return ;
    //set new owner
    SafePerson oldOwner = this.owner;
    this.owner = owner;
    //remove from the old owner
    if (oldOwner!=null)
      oldOwner.removeTwitterAccount(this);
    //set myself into new owner
    if (owner!=null)
      owner.addTwitterAccount(this);
  }

  private boolean sameAsFormer(SafePerson newOwner) {
    return owner==null? newOwner == null : owner.equals(newOwner);
  }

  
  public Set<SafePerson> getFollowers() {
    //defensive copy, nobody will be able to change the list from the outside
    return new HashSet<SafePerson>(followers);
  }

  public void addFollower(SafePerson follower) {
    //prevent endless loop
    if (followers.contains(follower))
      return ;
    //add new follower
    followers.add(follower);
    //set myself into the follower
    follower.startFollowingTwitter(this);
  }

  public void removeFollower(SafePerson follower) {
    //prevent endless loop
    if (!followers.contains(follower))
      return ;
    //remove the follower
    followers.remove(follower);
    //remove myself from the follower
    follower.stopFollowingTwitter(this);
  }

  @Override
  public String toString() {
    return "TwitterAccount [id=" + id + ", accountName=" + accountName + ", owner=" + owner + "]";
  }

}
