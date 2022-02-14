package ca.mcgill.ecse321.GSSS.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * This class represents an Employee.
 * Its primary key is its email present in its superclass User.
 * 
 * @author Philippe Sarouphim Hochar.
 */
@Entity
public class Employee extends FireableUser {

  private Set<Shift> shifts;
  private Set<Purchase> assignedPurchases;

  @OneToMany
  public Set<Shift> getShifts() {
    return shifts;
  }

  public void setShifts(Set<Shift> shifts) {
    this.shifts = shifts;
  }

  @OneToMany
  public Set<Purchase> getAssignedPurchases() {
    return assignedPurchases;
  }

  public void setAssignedPurchases(Set<Purchase> assignedPurchases) {
    this.assignedPurchases = assignedPurchases;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((assignedPurchases == null) ? 0 : assignedPurchases.hashCode());
    result = prime * result + ((shifts == null) ? 0 : shifts.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Employee other = (Employee) obj;
    if (assignedPurchases == null) {
      if (other.assignedPurchases != null)
        return false;
    } else if (!assignedPurchases.equals(other.assignedPurchases))
      return false;
    if (shifts == null) {
      if (other.shifts != null)
        return false;
    } else if (!shifts.equals(other.shifts))
      return false;
    return true;
  }

}
