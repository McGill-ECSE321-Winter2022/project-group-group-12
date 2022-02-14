package ca.mcgill.ecse321.GSSS.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * 
 * @author Wassim Jabbour
 *
 */
@Entity
public class Employee extends User {
  
  Set<Shift> shifts;
  
  @OneToMany
  public Set<Shift> getShifts() {
    return shifts;
  }

  public void setShifts(Set<Shift> shifts) {
    this.shifts = shifts;
  }
  
}