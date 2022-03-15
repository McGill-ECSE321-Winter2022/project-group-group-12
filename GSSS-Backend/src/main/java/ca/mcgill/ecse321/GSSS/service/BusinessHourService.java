package ca.mcgill.ecse321.GSSS.service;

import ca.mcgill.ecse321.GSSS.dao.BusinessHourRepository;
import ca.mcgill.ecse321.GSSS.model.BusinessHour;
import ca.mcgill.ecse321.GSSS.model.Weekday;
import java.sql.Time;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Services of the item class
 *
 * @author Chris Hatoum
 * @author Habib Jarweh
 */
@Service
public class BusinessHourService {

  @Autowired
  BusinessHourRepository businessHourRepository;

  /**
   * method that gets business hour by weekday
   *
   * @param weekday desired day of the week
   * @return business hour we want to find
   * @author Habib Jarweh
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
   * @return list<BusinessHour> list of all business hours
   * @author Habib Jarweh
   */
  @Transactional
  public List<BusinessHour> getAllBusinessHours() {
    return Utility.toList(businessHourRepository.findAll());
  }

  /**
   * method that creates new business hour
   *
   * @param weekday   desired day of the week
   * @param startTime desired start time of businesshour
   * @param endTime   desired end time of businesshour
   * @return business hour we created
   * @author Habib Jarweh
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
   * @param weekday day of the week of the business hour we want to remove
   * @return business hour we wanted to delete
   * @author Habib Jarweh
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
   * @param day       specific weekday
   * @param startTime start time of the day we want
   * @param endTime   end time of the day we want
   * @return Buisness hours ( opening and closing ) of the day we want to update
   * @author Chris Hatoum
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
