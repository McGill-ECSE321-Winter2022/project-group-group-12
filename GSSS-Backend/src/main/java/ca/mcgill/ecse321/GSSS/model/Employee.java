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
  private Set<Purchase> assignedPurchase;

  @OneToMany
  public Set<Shift> getShifts() {
    return shifts;
  }

  public void setShifts(Set<Shift> shifts) {
    this.shifts = shifts;
  }

  @OneToMany
  public Set<Purchase> getAssignedPurchase() {
    return assignedPurchase;
  }

  public void setAssignedPurchase(Set<Purchase> assignedPurchase) {
    this.assignedPurchase = assignedPurchase;
  }

}
