package org.meri.jpa.relationships.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Person {

  @Id
  @Column(name = "user_id")
  private long id;

  private String userName;
  private String firstName;
  private String lastName;
  private String homePage;
  private String about;
  
  @OneToOne(mappedBy="owner")
  private FacebookAccount facebookAccount;
  
  @OneToMany(mappedBy="owner")
  private Collection<TwitterAccount> twitterAccounts;

  public Person() {
  }

  public Person(String userName, String firstName, String lastName) {
    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Person(long id, String userName, String firstName, String lastName) {
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

  public FacebookAccount getFacebookAccount() {
    return facebookAccount;
  }

  public void setFacebookAccount(FacebookAccount facebookAccount) {
    this.facebookAccount = facebookAccount;
  }

  public Collection<TwitterAccount> getTwitterAccounts() {
    return twitterAccounts;
  }

  public void setTwitterAccounts(Collection<TwitterAccount> twitterAccounts) {
    this.twitterAccounts = twitterAccounts;
  }

  @Override
  public String toString() {
    return "Person [id=" + id + ", " + userName + " - " + firstName + " " + lastName + "]";
  }
  
  
}
