package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This class represents the Addresses of the users.
 *
 * @author Wassim Jabbour
 */
@Entity
public class Address {

  private String fullName;
  private String streetName;
  private Integer streetNumber;
  private String city;
  private String postalCode;
  private String id;

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

  /**
   * Overrode the equals method to use it in tests
   *
   * @param obj The object to compare
   * @return True if it's the same object, false otherwise
   * @author Philippe Sarouphim Hochar
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Address other = (Address) obj;
    if (city == null) {
      if (other.city != null) {
        return false;
      }
    } else if (!city.equals(other.city)) {
      return false;
    }
    if (fullName == null) {
      if (other.fullName != null) {
        return false;
      }
    } else if (!fullName.equals(other.fullName)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (postalCode == null) {
      if (other.postalCode != null) {
        return false;
      }
    } else if (!postalCode.equals(other.postalCode)) {
      return false;
    }
    if (streetName == null) {
      if (other.streetName != null) {
        return false;
      }
    } else if (!streetName.equals(other.streetName)) {
      return false;
    }
    if (streetNumber == null) {
      if (other.streetNumber != null) {
        return false;
      }
    } else if (!streetNumber.equals(other.streetNumber)) {
      return false;
    }
    return true;
  }
}
