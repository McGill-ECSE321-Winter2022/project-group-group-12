package ca.mcgill.ecse321.GSSS.controller;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.GSSS.dto.BusinessHourDto;
import ca.mcgill.ecse321.GSSS.model.BusinessHour;
import ca.mcgill.ecse321.GSSS.model.Weekday;
import ca.mcgill.ecse321.GSSS.service.BusinessHourService;

@CrossOrigin(origins = "*")
@RestController
public class BusinessHourRestController {

  @Autowired
  BusinessHourService businessHourService;


  /**
   * GET method that retrieves the business hour corresponding to a certain day of the week
   * 
   * @author Wassim Jabbour
   * @param weekday A string with the weekday we want to find
   * @return The corresponding business hour dto
   * @throws IllegalArgumentException, NoSuchElementException
   */
  @GetMapping(value = {"/businesshour/{weekday}", "/businesshour/{weekday}/"})
  public BusinessHourDto getBusinessHourByWeekday(@PathVariable("weekday") String weekday) throws NoSuchElementException, IllegalArgumentException{

    Weekday correspondingWeekday = ConversionUtility.findWeekdayByName(weekday); // Helper method
                                                                             // defined below

    if (correspondingWeekday == null)
      throw new IllegalArgumentException("There is no such weekday!");

    BusinessHourDto businessHourDto = ConversionUtility
        .convertToDto(businessHourService.getBusinessHourByWeekday(correspondingWeekday));

    return businessHourDto;

  }

  /**
   * Method that overrides the business hour for a particular day If the business hour doesn't
   * exist, it creates it Otherwise, it just updates it
   * 
   * @author Wassim Jabbour
   * @param weekday The day of the week the BusinessHour is happening on
   * @param startTime The start time of the opening hours for that day
   * @param endTime The end time of the opening hours for that day
   * @return The Dto corresponding to the created business hour
   * @throws IllegalArgumentException
   */
  @PostMapping(value = {"/businesshour", "/businesshour/"})
  public BusinessHourDto updateBusinessHour(@RequestParam(name = "weekday") String weekday,
      @RequestParam(name = "starttime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME,
          pattern = "HH:mm") LocalTime startTime,
      @RequestParam(name = "endtime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME,
          pattern = "HH:mm") LocalTime endTime)
      throws IllegalArgumentException {

    Weekday correspondingWeekday = ConversionUtility.findWeekdayByName(weekday); // Helper method
                                                                             // defined below

    if (correspondingWeekday == null)
      throw new IllegalArgumentException("There is no such weekday!");

    BusinessHour businessHour = businessHourService.createBusinessHour(correspondingWeekday,
        Time.valueOf(startTime), Time.valueOf(endTime));

    return ConversionUtility.convertToDto(businessHour);

  }

  /**
   * Method to get a list of all Business Hours in DTO form
   * 
   * @author Wassim Jabbour
   * @return list of all business hour DTOS
   */
  @GetMapping(value = {"/businesshours", "/businesshours/"})
  public List<BusinessHourDto> getAllBusinessHours(){
    List<BusinessHourDto> businessHourDtos = new ArrayList<>();
    for (BusinessHour businessHour : businessHourService.getAllBusinessHours()) {
      businessHourDtos.add(ConversionUtility.convertToDto(businessHour));
    }
    return businessHourDtos;
  }

  /**
   * No need for a delete method since we always have 7 business hours in the system, one for each
   * day
   */

}
