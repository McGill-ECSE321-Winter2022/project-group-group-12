package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class BusinessHour extends TimeInterval {

  private Weekday weekday;

  @Id
  public Weekday getWeekday() {
    return weekday;
  }

  public void setWeekday(Weekday weekday) {
    this.weekday = weekday;
  }
}
