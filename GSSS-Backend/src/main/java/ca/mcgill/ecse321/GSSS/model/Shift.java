package ca.mcgill.ecse321.GSSS.model;

import java.sql.Date;
import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This class represents the employee's shifts. Its primary key is a string.
 *
 * @author Theo Ghanem
 */
@Entity
public class Shift {

  private Date date;
  private String id;
  private Time startTime;
  private Time endTime;

  @Id
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Time getStartTime() {
    return startTime;
  }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  public Time getEndTime() {
    return endTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
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
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Shift other = (Shift) obj;
    if (date == null) {
      if (other.date != null)
        return false;
    } else if (!date.equals(other.date))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
