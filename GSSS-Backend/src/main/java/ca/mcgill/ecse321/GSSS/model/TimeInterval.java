package ca.mcgill.ecse321.GSSS.model;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@Entity
public abstract class TimeInterval {

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
}
