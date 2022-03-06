package ca.mcgill.ecse321.GSSS.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * This class represents the Employees.
 *
 * @author Wassim Jabbour
 */
@Entity
public class Employee extends Account {

  private Set<Shift> shifts;

  @OneToMany(fetch = FetchType.EAGER)
  public Set<Shift> getShifts() {
    return shifts;
  }

  public void setShifts(Set<Shift> shifts) {
    this.shifts = shifts;
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
