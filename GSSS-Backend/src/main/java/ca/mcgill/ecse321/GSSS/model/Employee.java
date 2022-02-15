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
public class Employee extends Account {
  
  private Set<Shift> shifts;
  
  @OneToMany
  public Set<Shift> getShifts() {
    return shifts;
  }

  public void setShifts(Set<Shift> shifts) {
    this.shifts = shifts;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    Employee other = (Employee) obj;
    if (shifts == null) {
      if (other.shifts != null)
        return false;
    } else if (!shifts.equals(other.shifts))
      return false;
    return true;
  }

  
  
}