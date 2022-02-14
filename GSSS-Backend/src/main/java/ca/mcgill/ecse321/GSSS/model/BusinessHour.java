package ca.mcgill.ecse321.GSSS.model;

import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Time;


@Entity
public class BusinessHour {

  private Weekday weekday;
  private Time startTime;
  private Time endTime;

  /**
   *
   * enumeration Weekday, used by the BusinessHour class this is to define opening hours on a specific
   * day of the week
   *
   * @author Habib Jarweh
   *
   */
  public enum Weekday {
    Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
  }

  @Id
  public Weekday getWeekday() {
    return weekday;
  }
  
  private Time startTime;
  private Time endTime;

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
