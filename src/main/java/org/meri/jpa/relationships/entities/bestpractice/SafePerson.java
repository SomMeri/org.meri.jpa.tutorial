package org.meri.jpa.relationships.entities.bestpractice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class SafePerson {

  @Id
  @Column(name = "user_id")
  private long id;

  private String userName;
  private String firstName;
  private String lastName;
  private String homePage;
  private String about;

  @OneToOne(mappedBy = "owner")
  private SafeFacebookAccount facebookAccount;

  @OneToMany(mappedBy = "owner")
  private Collection<SafeTwitterAccount> twitterAccounts = new ArrayList<SafeTwitterAccount>();

  @ManyToMany(mappedBy = "followers")
  @MapKey(name = "accountName")
  private Map<String, SafeTwitterAccount> twitterFollowing = new HashMap<String, SafeTwitterAccount>();

  public SafePerson() {
  }

  public SafePerson(String userName, String firstName, String lastName) {
    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public SafePerson(long id, String userName, String firstName, String lastName) {
    this(userName, firstName, lastName);
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getHomePage() {
    return homePage;
  }

  public void setHomePage(String homePage) {
    this.homePage = homePage;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

  public SafeFacebookAccount getFacebookAccount() {
    return facebookAccount;
  }

  public void setFacebookAccount(SafeFacebookAccount facebookAccount) {
    //prevent endless loop
    if (sameAsFormer(facebookAccount))
      return;
    //set new facebook account
    SafeFacebookAccount oldAccount = this.facebookAccount;
    this.facebookAccount = facebookAccount;
    //remove from the old facebook account
    if (oldAccount!=null)
      oldAccount.setOwner(null);
    //set myself into new facebook account
    if (facebookAccount!=null)
      facebookAccount.setOwner(this);
  }

  private boolean sameAsFormer(SafeFacebookAccount newAccount) {
    return facebookAccount == null ? 
      newAccount == null : facebookAccount.equals(newAccount);
  }

  /**
   * Returns a collection with owned twitter accounts. The 
   * returned collection is a defensive copy.
   *  
   * @return a collection with owned twitter accounts
   */
  public Collection<SafeTwitterAccount> getTwitterAccounts() {
    //defensive copy, nobody will be able to change 
    //the list from the outside
    return new ArrayList<SafeTwitterAccount>(twitterAccounts);
  }

  /**
   * Add new account to the person. The method keeps 
   * relationships consistency:
   * * this person is set as the account owner
   */
  public void addTwitterAccount(SafeTwitterAccount account) {
    //prevent endless loop
    if (twitterAccounts.contains(account))
      return ;
    //add new account
    twitterAccounts.add(account);
    //set myself into the twitter account
    account.setOwner(this);
  }
  
  /**
   * Removes the account from the person. The method keeps 
   * relationships consistency:
   * * the account will no longer reference this person as its owner
   */
  public void removeTwitterAccount(SafeTwitterAccount account) {
    //prevent endless loop
    if (!twitterAccounts.contains(account))
      return ;
    //remove the account
    twitterAccounts.remove(account);
    //remove myself from the twitter account
    account.setOwner(null);
  }
  
  /**
   * Returns a collection with followed twitter accounts. The 
   * returned collection is a defensive copy.
   *  
   * @return a collection with followed twitter accounts
   */
  public Map<String, SafeTwitterAccount> getTwitterFollowing() {
    //defensive copy, nobody will be able to change the list from the outside
    return new HashMap<String, SafeTwitterAccount>(twitterFollowing);
  }

  /**
   * Add new account to the list of followed twitter accounts. The method 
   * keeps relationships consistency:
   * * this person is set as the account follower also at the twitter side
   */
  public void startFollowingTwitter(SafeTwitterAccount account) {
    //prevent endless loop
    if (twitterFollowing.containsValue(account))
      return ;
    //add new account
    twitterFollowing.put(account.getAccountName(), account);
    //set myself into the twitter account
    account.addFollower(this);
  }
  
  /**
   * Removes the account from the list of followed twitter 
   * accounts. The method keeps relationships consistency:
   * * this person is removed from the account followers also at the twitter side
   */
  public void stopFollowingTwitter(SafeTwitterAccount account) {
    //prevent endless loop
    if (!twitterFollowing.containsValue(account))
      return ;
    //remove the account
    twitterFollowing.remove(account.getAccountName());
    //remove myself from the twitter account
    account.removeFollower(this);
  }

  @Override
  public String toString() {
    return "Person [id=" + id + ", " + userName + " - " + firstName + " " + lastName + "]";
  }

}
