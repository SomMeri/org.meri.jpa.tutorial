package org.meri.jpa.relationships.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="twitter_account")
public class TwitterAccount {
  
  @Id
  private long id;
  private String accountName;
  
  @ManyToOne
  @JoinColumn(name="owner_id")
  private Person owner;

  @ManyToMany
  private Set<Person> followers;

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
    this.accountName = accountName;
  }

  public Person getOwner() {
    return owner;
  }

  public void setOwner(Person owner) {
    this.owner = owner;
  }

  
  public Set<Person> getFollowers() {
    return followers;
  }

  public void setFollowers(Set<Person> followers) {
    this.followers = followers;
  }

  @Override
  public String toString() {
    return "TwitterAccount [id=" + id + ", accountName=" + accountName + ", owner=" + owner + "]";
  }

}
