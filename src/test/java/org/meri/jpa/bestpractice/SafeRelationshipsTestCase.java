package org.meri.jpa.bestpractice;

import static org.junit.Assert.*;

import org.junit.Test;
import org.meri.jpa.relationships.entities.bestpractice.SafeFacebookAccount;
import org.meri.jpa.relationships.entities.bestpractice.SafePerson;
import org.meri.jpa.relationships.entities.bestpractice.SafeTwitterAccount;

public class SafeRelationshipsTestCase {

  @Test
  public void testSetFacebookToPerson() {
    SafePerson person = new SafePerson();
    SafeFacebookAccount account = new SafeFacebookAccount();
    //set new Facebook account
    person.setFacebookAccount(account);
    assertEquals(person, account.getOwner());
    assertEquals(account, person.getFacebookAccount());

    //change Facebook account
    SafeFacebookAccount secondAccount = new SafeFacebookAccount();
    person.setFacebookAccount(secondAccount);
    assertNull(account.getOwner());
    assertEquals(person, secondAccount.getOwner());
    assertEquals(secondAccount, person.getFacebookAccount());

    //remove Facebook account
    person.setFacebookAccount(null);
    assertNull(secondAccount.getOwner());
    assertNull(person.getFacebookAccount());
  }

  @Test
  public void testSetPersonToFacebook() {
    SafeFacebookAccount account = new SafeFacebookAccount();
    SafePerson person = new SafePerson();
    //set new Facebook owner
    account.setOwner(person);
    assertEquals(account, person.getFacebookAccount());
    assertEquals(person, account.getOwner());

    //change Facebook owner
    SafePerson secondPerson = new SafePerson();
    account.setOwner(secondPerson);
    assertNull(person.getFacebookAccount());
    assertEquals(account, secondPerson.getFacebookAccount());
    assertEquals(secondPerson, account.getOwner());

    //remove Facebook owner
    account.setOwner(null);
    assertNull(secondPerson.getFacebookAccount());
    assertNull(account.getOwner());
  }

  @Test
  public void testSetTwitterToPerson() {
    SafePerson person = new SafePerson();
    SafeTwitterAccount account = new SafeTwitterAccount();
    //set new Twitter account
    person.addTwitterAccount(account);
    assertEquals(person, account.getOwner());
    assertTrue(person.getTwitterAccounts().contains(account));

    //add second Twitter account
    SafeTwitterAccount secondAccount = new SafeTwitterAccount();
    person.addTwitterAccount(secondAccount);
    assertEquals(person, account.getOwner());
    assertTrue(person.getTwitterAccounts().contains(account));
    assertEquals(person, secondAccount.getOwner());
    assertTrue(person.getTwitterAccounts().contains(secondAccount));

    //remove first Twitter account
    person.removeTwitterAccount(account);
    assertNull(account.getOwner());
    assertFalse(person.getTwitterAccounts().contains(account));
    assertEquals(person, secondAccount.getOwner());
    assertTrue(person.getTwitterAccounts().contains(secondAccount));
    
    //remove second Twitter account
    person.removeTwitterAccount(secondAccount);
    assertNull(account.getOwner());
    assertFalse(person.getTwitterAccounts().contains(account));
    assertNull(secondAccount.getOwner());
    assertFalse(person.getTwitterAccounts().contains(secondAccount));
    assertTrue(person.getTwitterAccounts().isEmpty());
  }

  @Test
  public void testSetPersonToTwitter() {
    SafeTwitterAccount account = new SafeTwitterAccount();
    SafePerson person = new SafePerson();
    //set new Twitter owner
    account.setOwner(person);
    assertTrue(person.getTwitterAccounts().contains(account));
    assertEquals(person, account.getOwner());

    //change Twitter owner
    SafePerson secondPerson = new SafePerson();
    account.setOwner(secondPerson);
    assertFalse(person.getTwitterAccounts().contains(account));
    assertTrue(secondPerson.getTwitterAccounts().contains(account));
    assertEquals(secondPerson, account.getOwner());

    //remove Twitter owner
    account.setOwner(null);
    assertFalse(secondPerson.getTwitterAccounts().contains(account));
    assertNull(account.getOwner());
  }








  @Test
  public void testSetTwitterToFollower() {
    SafePerson person = new SafePerson();
    SafeTwitterAccount account = new SafeTwitterAccount();
    account.setAccountName("first");
    //set new Twitter account
    person.startFollowingTwitter(account);
    assertTrue(account.getFollowers().contains(person));
    assertEquals(account, person.getTwitterFollowing().get(account.getAccountName()));

    //add second Twitter account
    SafeTwitterAccount secondAccount = new SafeTwitterAccount();
    secondAccount.setAccountName("second");
    person.startFollowingTwitter(secondAccount);
    assertTrue(account.getFollowers().contains(person));
    assertEquals(account, person.getTwitterFollowing().get(account.getAccountName()));
    assertTrue(secondAccount.getFollowers().contains(person));
    assertEquals(secondAccount, person.getTwitterFollowing().get(secondAccount.getAccountName()));

    //remove first Twitter account
    person.stopFollowingTwitter(account);
    assertFalse(account.getFollowers().contains(person));
    assertFalse(person.getTwitterFollowing().containsValue(account));
    assertTrue(secondAccount.getFollowers().contains(person));
    assertEquals(secondAccount, person.getTwitterFollowing().get(secondAccount.getAccountName()));
    
    //remove second Twitter account
    person.stopFollowingTwitter(secondAccount);
    assertFalse(account.getFollowers().contains(person));
    assertFalse(person.getTwitterFollowing().containsValue(account));
    assertFalse(secondAccount.getFollowers().contains(person));
    assertFalse(person.getTwitterFollowing().containsValue(secondAccount));
    
    assertTrue(account.getFollowers().isEmpty());
    assertTrue(person.getTwitterFollowing().isEmpty());
  }

  @Test
  public void testSetFollowerToTwitter() {
    SafeTwitterAccount account = new SafeTwitterAccount();
    account.setAccountName("account");
    SafePerson person = new SafePerson();
    //add new Twitter follower
    account.addFollower(person);
    assertEquals(account, person.getTwitterFollowing().get(account.getAccountName()));
    assertTrue(account.getFollowers().contains(person));

    //add second Twitter follower
    SafePerson secondPerson = new SafePerson();
    account.addFollower(secondPerson);
    assertEquals(account, person.getTwitterFollowing().get(account.getAccountName()));
    assertTrue(account.getFollowers().contains(person));
    assertEquals(account, secondPerson.getTwitterFollowing().get(account.getAccountName()));
    assertTrue(account.getFollowers().contains(secondPerson));

    //remove first Twitter follower
    account.removeFollower(person);
    assertFalse(person.getTwitterFollowing().containsValue(account));
    assertFalse(account.getFollowers().contains(person));
    assertEquals(account, secondPerson.getTwitterFollowing().get(account.getAccountName()));
    assertTrue(account.getFollowers().contains(secondPerson));
    
    //remove second Twitter follower
    account.removeFollower(secondPerson);
    assertFalse(person.getTwitterFollowing().containsValue(account));
    assertFalse(account.getFollowers().contains(person));
    assertFalse(secondPerson.getTwitterFollowing().containsValue(account));
    assertFalse(account.getFollowers().contains(secondPerson));

    assertTrue(person.getTwitterFollowing().isEmpty());
    assertTrue(secondPerson.getTwitterFollowing().isEmpty());
    assertTrue(account.getFollowers().isEmpty());
  }

  @Test
  public void testChangeTwitterName() {
    SafeTwitterAccount account = new SafeTwitterAccount();
    account.setAccountName("account");
    SafePerson person = new SafePerson();

    //person will follow the account
    account.addFollower(person);
    assertEquals(account, person.getTwitterFollowing().get(account.getAccountName()));

    //change account name
    account.setAccountName("new name");
    assertEquals(account, person.getTwitterFollowing().get(account.getAccountName()));
    assertFalse(person.getTwitterFollowing().containsKey("account"));
  }

}
