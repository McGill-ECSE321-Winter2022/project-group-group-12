package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class BusinessHour extends TimeInterval {

  private Weekday weekday;

  @Id
  public Weekday getDayOfWeek() {
    return weekday;
  }

  public void setDayOfWeek(Weekday weekday) {
    this.weekday = weekday;
  }
}
