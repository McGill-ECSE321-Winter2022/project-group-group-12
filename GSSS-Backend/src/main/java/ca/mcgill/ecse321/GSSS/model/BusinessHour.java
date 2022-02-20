package ca.mcgill.ecse321.GSSS.model;

import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This class represents the Grocery Store's Weekly Business Hours. Its primary key is a weekday.
 *
 * @author Theo Ghanem
 */
@Entity
public class BusinessHour {

  private Weekday weekday;
  private Time startTime;
  private Time endTime;

  @Id
  public Weekday getWeekday() {
    return weekday;
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

  public void setWeekday(Weekday weekday) {
    this.weekday = weekday;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BusinessHour other = (BusinessHour) obj;
    if (weekday != other.weekday)
      return false;
    return true;
  }

}
