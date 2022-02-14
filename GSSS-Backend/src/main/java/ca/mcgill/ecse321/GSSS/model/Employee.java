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

}
