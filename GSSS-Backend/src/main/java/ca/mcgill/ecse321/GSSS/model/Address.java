package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @author Wassim Jabbour
 *
 */

@Entity
public class Address {
  
  String fullName;
  String streetName;
  Integer streetNumber;
  String city;
  String postalCode;
  String id;
  
  public String getFullName() {
    return fullName;
  }
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
  public String getStreetName() {
    return streetName;
  }
  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }
  public Integer getStreetNumber() {
    return streetNumber;
  }
  public void setStreetNumber(Integer streetNumber) {
    this.streetNumber = streetNumber;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getPostalCode() {
    return postalCode;
  }
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }
  
  @Id
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  
}
