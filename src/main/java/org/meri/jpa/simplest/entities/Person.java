package org.meri.jpa.simplest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class Person {

  @Id
  @Column(name = "user_id")
  private long id;

  private String userName;
  private String firstName;
  private String lastName;
  private String homePage;
  private String about;

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

}
