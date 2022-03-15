package ca.mcgill.ecse321.GSSS.service;

import ca.mcgill.ecse321.GSSS.dao.BusinessHourRepository;
import ca.mcgill.ecse321.GSSS.model.BusinessHour;
import ca.mcgill.ecse321.GSSS.model.Weekday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Services of the item class
 *
 * @author Chris Hatoum
 * @author Habib Jarweh
 */
@Service
public class BusinessHourService {
  @Autowired BusinessHourRepository businessHourRepository;

  /**
   * method that gets business hour by weekday
   *
   * @author Habib Jarweh
   * @param weekday desired day of the week
   * @return business hour we want to find
   * @throws illegal argument expection when inputted argument is null
   * @throws no such element exception when there is no business hour with the inputted weekday
   */
  @Transactional
  public BusinessHour getBusinessHourByWeekday(Weekday weekday) {
    if (weekday == null) {
      throw new IllegalArgumentException("Weekday of business hour cannot be null! ");
    }
    BusinessHour businessHour = businessHourRepository.findBusinessHourByWeekday(weekday);
    if (businessHour == null) {
      throw new NoSuchElementException(
          "No businessHour with weekday " + weekday.name() + " exits!");
    }
    return businessHour;
  }

  /**
   * method that gets all business hours
   *
   * @author Habib Jarweh
   * @return list<BusinessHour> list of all business hours
   */
  @Transactional
  public List<BusinessHour> getAllBusinessHours() {
    return Utility.toList(businessHourRepository.findAll());
  }

  /**
   * method that creates new business hour
   *
   * @author Habib Jarweh
   * @param weekday desired day of the week
   * @param startTime desired start time of businesshour
   * @param endTime desired end time of businesshour
   * @return business hour we created
   * @throws illegal argument expection when any or all parameters inputtted are null, or when start
   *     time comes after end time
   */
  @Transactional
  public BusinessHour createBusinessHour(Weekday weekday, Time startTime, Time endTime) {
    // Input validation
    String error = "";
    if (weekday == null) {
      error += "Weekday of business hour cannot be null! ";
    }
    if (startTime == null) {
      error += "Business hour start time cannot be null! ";
    }
    if (endTime == null) {
      error += "Business hour end time cannot be null! ";
    }
    if (endTime != null && startTime != null && endTime.before(startTime)) {
      error += "Business hour end time cannot be before business hour start time!";
    }
    if (error.length() > 0) {
      throw new IllegalArgumentException(error);
    }

    BusinessHour businessHour = new BusinessHour();
    businessHour.setWeekday(weekday);
    businessHour.setStartTime(startTime);
    businessHour.setEndTime(endTime);
    businessHourRepository.save(businessHour);
    return businessHour;
  }

  /**
   * method that deletes business hour
   *
   * @author Habib Jarweh
   * @param weekday day of the week of the business hour we want to remove
   * @return business hour we wanted to delete
   * @throws no such element exception when there is no business hour with the inputted weekday
   */
  @Transactional
  public BusinessHour deleteBusinessHour(Weekday weekday) {
    if (weekday == null) {
      throw new IllegalArgumentException("Weekday of business hour cannot be null! ");
    }
    BusinessHour businessHour = businessHourRepository.findBusinessHourByWeekday(weekday);
    businessHourRepository.delete(businessHour);
    return businessHour;
  }

  /**
   * method to edit/modify a business hour of a certain day
   *
   * @author Chris Hatoum
   * @param day specific weekday
   * @param startTime start time of the day we want
   * @param endTime end time of the day we want
   * @return Buisness hours ( opening and closing ) of the day we want to update
   * @throws illegal argument expection when any or all parameters inputtted are null, or when start
   *     time comes after end time
   */
  @Transactional
  public BusinessHour modifyBusinessHour(Weekday day, Time startTime, Time endTime) {
    // Input validation
    String error = "";
    if (day == null) {
      error += "Weekday of business hour cannot be null! ";
    }
    if (startTime == null) {
      error += "Business hour start time cannot be null! ";
    }
    if (endTime == null) {
      error += "Business hour end time cannot be null! ";
    }
    if (endTime != null && startTime != null && endTime.before(startTime)) {
      error += "Business hour end time cannot be before business hour start time!";
    }
    if (error.length() > 0) {
      throw new IllegalArgumentException(error);
    }

    BusinessHour businessHour = businessHourRepository.findBusinessHourByWeekday(day);
    businessHour.setStartTime(startTime);
    businessHour.setEndTime(endTime);
    businessHourRepository.save(businessHour);
    return businessHour;
  }
}
