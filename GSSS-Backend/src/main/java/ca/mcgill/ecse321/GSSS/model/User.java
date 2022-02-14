package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * 
 * @author Wassim Jabbour
 *
 */
@Entity
public abstract class User {

  String username;
  String email;
  String password;
  boolean disabled;
  Address address;
  
  @OneToOne
  public Address getAddress() {
    return address;
  }
  public void setAddress(Address address) {
    this.address = address;
  }
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
  public boolean isDisabled() {
    return disabled;
  }
  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }
}
