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
