package ca.mcgill.ecse321.GSSS.dto;

import ca.mcgill.ecse321.GSSS.model.Weekday;

import java.sql.Time;

public class BusinessHourDto {
  private Weekday weekday;
  private Time startTime;
  private Time endTime;

  /**
   * Empty constructor for the BusinessHour DTO
   *
   * @author Enzo Benoit-Jeannin
   */
  public BusinessHourDto() {}

  /**
   * COnstructor for the BusinessHour DTO
   *
   * @author Enzo Benoit-Jeannin
   * @param weekday
   * @param startTime
   * @param endTime
   */
  public BusinessHourDto(Weekday weekday, Time startTime, Time endTime) {
    this.weekday = weekday;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  /**
   * Method to get the weekday of the businessHour DTO
   *
   * @author Enzo Benoit-Jeannin
   * @return weekday of the businessHour DTO
   */
  public Weekday getWeekday() {
    return weekday;
  }
  /**
   * Method to get the startTime of the businessHour DTO
   *
   * @author Enzo Benoit-Jeannin
   * @return startTime of the businessHour DTO
   */
  public Time getStartTime() {
    return startTime;
  }
  /**
   * Method to get the endTime of the businessHour DTO
   *
   * @author Enzo Benoit-Jeannin
   * @return endTime of the businessHour DTO
   */
  public Time getEndTime() {
    return endTime;
  }
}
