package ca.mcgill.ecse321.GSSS.dto;

import java.sql.Date;
import java.sql.Time;

/**
 * This is the DTO-equivalent class of Shift.
 * 
 * @author Philippe Sarouphim Hochar.
 */
public class ShiftDto {

  private Date date;
  private String id;
  private Time startTime;
  private Time endTime;

  public ShiftDto() {
  }

  public ShiftDto(Date date, String id, Time startTime, Time endTime) {
    this.date = date;
    this.id = id;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public Date getDate() {
    return date;
  }

  public String getId() {
    return id;
  }

  public Time getStartTime() {
    return startTime;
  }

  public Time getEndTime() {
    return endTime;
  }

}
