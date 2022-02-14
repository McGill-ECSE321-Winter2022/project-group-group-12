package ca.mcgill.ecse321.GSSS.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * This class represents User objects. It is a mapped superclass and is therefore not present in its
 * database. However, in its email field, it holds the primary key of its subclasses.
 * 
 * @author Philippe Sarouphim Hochar.
 */
@Entity
public abstract class User {

  private String username;
  private String email;
  private String password;
  private Set<Address> addresses;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Id
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @OneToMany
  public Set<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(Set<Address> addresses) {
    this.addresses = addresses;
  }

}
