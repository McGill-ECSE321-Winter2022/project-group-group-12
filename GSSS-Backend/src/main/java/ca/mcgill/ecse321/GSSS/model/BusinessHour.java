package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class BusinessHour extends TimeInterval {

  private Weekday dayOfWeek;

  @Id
  public Weekday getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(Weekday dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }
}
