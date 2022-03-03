package ca.mcgill.ecse321.GSSS.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.GSSS.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.GSSS.dao.BusinessHourRepository;
import ca.mcgill.ecse321.GSSS.model.BusinessHour;
import ca.mcgill.ecse321.GSSS.model.Weekday;

@Service
public class BusinessHourService {
  @Autowired
  BusinessHourRepository businessHourRepository;

  /**
   * method that gets business hour by weekday
   * 
   * @author Habib Jarweh
   * @param weekday desired day of the week
   * @return business hour we want to find
   */
  @Transactional
  public BusinessHour getBusinessHourByWeekday(Weekday weekday) {
    if (weekday == null) {
      throw new IllegalArgumentException("weekday of business hour cannot be null! ");
    }
    return businessHourRepository.findBusinessHourByWeekday(weekday);
  }

  /**
   * method that gets all business hours
   * 
   * @author Habib Jarweh
   * @return list<BusinessHour> list of all business hours
   */
  @Transactional
  public List<BusinessHour> getAllBusinessHours() {
    return HelperClass.toList(businessHourRepository.findAll());
  }

  /**
   * method that creates new business hour
   * 
   * @author Habib Jarweh
   * @param weekday desired day of the week
   * @param startTime desired start time of businesshour
   * @param endTime desired end time of businesshour
   * @return business hour we created
   */
  @Transactional
  public BusinessHour createBusinessHour(Weekday weekday, Time startTime, Time endTime) {
    // Input validation
    String error = "";
    if (weekday == null) {
      error += "weekday of business hour cannot be null! ";
    }
    if (startTime == null) {
      error += "business hour start time cannot be null! ";
    }
    if (endTime == null) {
      error += "business hour end time cannot be null! ";
    }
    if (endTime != null && startTime != null && endTime.before(startTime)) {
      error += "business hour end time cannot be before business hour start time!";
    }
    error = error.trim();
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
   */
  @Transactional
  public BusinessHour deleteBusinessHour(Weekday weekday) {
    if (weekday == null) {
      throw new IllegalArgumentException("weekday of business hour cannot be null! ");
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
   */
  @Transactional
  public BusinessHour modifyBusinessHour(Weekday day, Time startTime, Time endTime) {

    BusinessHour businessHour = businessHourRepository.findBusinessHourByWeekday(day);
    businessHour.setStartTime(startTime);
    businessHour.setEndTime(endTime);
    businessHourRepository.save(businessHour);
    return businessHour;
  }
}
