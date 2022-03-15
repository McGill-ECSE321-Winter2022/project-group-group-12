package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * This class represents the customer. Its primary key is its email in its superclass User.
 *
 * @author Habib Jarweh
 */
@Entity
public class Customer extends Account {

  private Set<Purchase> purchases;

  @OneToMany(fetch = FetchType.EAGER)
  public Set<Purchase> getPurchases() {
    return purchases;
  }

  public void setPurchases(Set<Purchase> purchases) {
    this.purchases = purchases;
  }

  /**
   * Overrode the equals method to use it in tests
   *
   * @author Philippe Sarouphim Hochar
   * @param obj The object to compare
   * @return True if it's the same object, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Customer other = (Customer) obj;
    if (purchases == null) {
      if (other.purchases != null) return false;
    } else if (!purchases.equals(other.purchases)) return false;
    return true;
  }
}
