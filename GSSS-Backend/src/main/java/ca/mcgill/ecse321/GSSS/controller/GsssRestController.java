package ca.mcgill.ecse321.GSSS.controller;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GSSS.dto.ItemCategoryDto;
import ca.mcgill.ecse321.GSSS.dto.ItemDto;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import ca.mcgill.ecse321.GSSS.service.ItemCategoryService;
import ca.mcgill.ecse321.GSSS.service.ItemService;

import ca.mcgill.ecse321.GSSS.dto.BusinessHourDto;
import ca.mcgill.ecse321.GSSS.model.BusinessHour;
import ca.mcgill.ecse321.GSSS.model.Weekday;
import ca.mcgill.ecse321.GSSS.service.BusinessHourService;


@CrossOrigin(origins = "*")
@RestController
public class GsssRestController {


  @Autowired
  BusinessHourService businessHourService;


  /**
   * GET method that retrieves the business hour corresponding to a certain day of the week
   * 
   * @author Wassim Jabbour
   * @param weekday A string with the weekday we want to find
   * @return The corresponding business hour dto
   */
  @GetMapping(value = {"/businesshour/{weekday}", "/businesshour/{weekday}/"})
  public BusinessHourDto getBusinessHourByWeekday(@PathVariable("weekday") String weekday) {

    Weekday correspondingWeekday = findWeekdayByName(weekday); // Helper method defined below

    if (correspondingWeekday == null)
      throw new IllegalArgumentException("There is no such weekday!");

    BusinessHourDto businessHourDto =
        convertToDto(businessHourService.getBusinessHourByWeekday(correspondingWeekday));

    return businessHourDto;

  }

  /**
   * Method that overrides the business hour for a particular day
   * 
   * @author Wassim Jabbour
   * @param weekday The day of the week the BusinessHour is happening on
   * @param startTime The start time of the opening hours for that day
   * @param endTime The end time of the opening hours for that day
   * @return The Dto corresponding to the created business hour
   */
  @PostMapping(value = {"/businesshour", "/businesshour/"})
  public BusinessHourDto overrideBusinessHour(@RequestParam(name = "weekday") String weekday,
      @RequestParam(name = "starttime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME,
          pattern = "HH:mm") LocalTime startTime,
      @RequestParam(name = "endtime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME,
          pattern = "HH:mm") LocalTime endTime) {

    Weekday correspondingWeekday = findWeekdayByName(weekday); // Helper method defined below

    if (correspondingWeekday == null)
      throw new IllegalArgumentException("There is no such weekday!");

    BusinessHour businessHour = businessHourService.createBusinessHour(correspondingWeekday,
        Time.valueOf(startTime), Time.valueOf(endTime));

    return convertToDto(businessHour);

  }

  /**
   * Helper method that converts a BusinessHour to its DTO equivalent
   * 
   * @author Wassim Jabbour
   * @param businessHour The BusinessHour to convert
   * @return The converted DTO
   */
  private BusinessHourDto convertToDto(BusinessHour businessHour) {

    if (businessHour == null)
      throw new IllegalArgumentException("There is no such business hour!");

    BusinessHourDto businessHourDto = new BusinessHourDto(businessHour.getWeekday(),
        businessHour.getStartTime(), businessHour.getEndTime());

    return businessHourDto;
  }

  /**
   * Helper method that converts a BusinessHourDto to its domain model equivalent
   * 
   * @author Wassim Jabbour
   * @param businessHourDto The DTO to convert
   * @return The domain model object
   */
  private BusinessHour convertToDomainObject(BusinessHourDto businessHourDto) {

    // Checking the input is non null
    if (businessHourDto == null)
      throw new IllegalArgumentException("There is no such business hour!");

    // Getting all the business hours in the system
    List<BusinessHour> allBusinessHours = businessHourService.getAllBusinessHours();

    // Cycling through them till we find the one with the desired weekday and returning it
    for (BusinessHour businessHour : allBusinessHours) {

      if (businessHour.getWeekday() == businessHourDto.getWeekday()) {
        return businessHour;
      }

    }

    // Returning null if we don't find it
    return null;

  }

  /**
   * Helper method that converts a weekDayName string to its corresponding weekday
   * 
   * @param weekDayName The string representing the weekday name
   * @return The weekday
   */
  private Weekday findWeekdayByName(String weekDayName) {

    if (weekDayName.equals("Monday"))
      return Weekday.Monday;

    else if (weekDayName.equals("Tuesday"))
      return Weekday.Tuesday;

    else if (weekDayName.equals("Wednesday"))
      return Weekday.Wednesday;

    else if (weekDayName.equals("Thursday"))
      return Weekday.Thursday;

    else if (weekDayName.equals("Friday"))
      return Weekday.Friday;

    else if (weekDayName.equals("Saturday"))
      return Weekday.Saturday;

    else if (weekDayName.equals("Sunday"))
      return Weekday.Sunday;

    else
      return null;
  }

  @Autowired
  private ItemService itemService;
  @Autowired
  private ItemCategoryService itemCategoryService;

  /**
   * method to convert from type item to type itemDto
   * 
   * @author Habib Jarweh
   * @param i item we want to convert
   * @return item converted to type itemDto
   */
  private ItemDto convertToDto(Item i, ItemCategory ic) {
    if (i == null) {
      throw new IllegalArgumentException("There is no such Item!");
    }
    if (ic == null) {
      throw new IllegalArgumentException("There is no such Item Category!");
    }
    ItemDto itemDto =
        new ItemDto(i.getName(), i.getDescription(), i.getImageUrl(), i.getRemainingQuantity(),
            i.getPrice(), i.isAvailableForOrder(), i.isStillAvailable(), convertToDto(ic));
    return itemDto;
  }

  /**
   * method to convert from type itemCategory to type itemCategoryDto
   * 
   * @author Habib Jarweh
   * @param ic item category we want to convert
   * @return item converted to type itemDto
   */
  private ItemCategoryDto convertToDto(ItemCategory ic) {
    if (ic == null) {
      throw new IllegalArgumentException("There is no such Item Category!");
    }
    ItemCategoryDto itemCategoryDto = new ItemCategoryDto(ic.getName());
    return itemCategoryDto;
  }

  /**
   * method to get itemDto by name
   * 
   * @author Habib Jarweh
   * @param name name we want to find item by
   * @return itemDto item we want to find
   * @throws IllegalArgumentException
   */
  @GetMapping(value = {"/item/{name}", "/item/{name}/"})
  public ItemDto getItemByName(@PathVariable("name") String name) throws IllegalArgumentException {
    Item item = itemService.getItemByName(name);
    return convertToDto(item, item.getCategory());
  }

  /**
   * method to get list of all itemDtos
   * 
   * @author Habib Jarweh
   * @return list of all itemDtos
   */
  @GetMapping(value = {"/items", "/items/"})
  public List<ItemDto> getAllItems() throws IllegalArgumentException {
    List<ItemDto> itemDtos = new ArrayList<>();
    for (Item item : itemService.getAllItems()) {
      itemDtos.add(convertToDto(item, item.getCategory()));
    }
    return itemDtos;
  }

  /**
   * method to create itemDto with all parameters of item
   * 
   * @author Habib Jarweh
   * @param name of item
   * @param description of item
   * @param imageUrl of item
   * @param remainingQuantity of item
   * @param price of item
   * @param availableForOrder if item is available for order
   * @param stillAvailable if item is still available
   * @return itemDto we created
   * @throws IllegalArgumentException
   */
  @PostMapping(value = {"/item", "/item/"})
  public ItemDto createItem(@RequestParam(name = "name") String name,
      @RequestParam(name = "description") String description,
      @RequestParam(name = "imageUrl") String imageUrl,
      @RequestParam(name = "remainingQuantity") int remainingQuantity,
      @RequestParam(name = "price") double price,
      @RequestParam(name = "availableForOrder") boolean availableForOrder,
      @RequestParam(name = "stillAvailable") boolean stillAvailable,
      @RequestParam(name = "itemCategory") ItemCategoryDto itemCategoryDto)
      throws IllegalArgumentException {
    ItemCategory itemCategory = itemCategoryService.getCategoryByName(itemCategoryDto.getName());
    Item item = itemService.createItem(name, description, imageUrl, remainingQuantity, price,
        availableForOrder, stillAvailable, itemCategory);
    return convertToDto(item, itemCategory);
  }

  /**
   * method to modify/update an item
   * 
   * @author Habib Jarweh
   * @param name of item we want to update, name is pk, does not change
   * @param description of item we want to update
   * @param imageUrl of item we want to update
   * @param remainingQuantity of item we want to update
   * @param price of item we want to update
   * @param availableForOrder availability for order of item we want to update
   * @param stillAvailable availability of item we want to update
   * @return itemDto we want to update
   * @throws IllegalArgumentException
   */
  @PostMapping(value = {"/item/{name}", "/item/{name}/"})
  public ItemDto modifyItem(@PathVariable("name") String name,
      @RequestParam(name = "description") String description,
      @RequestParam(name = "imageUrl") String imageUrl,
      @RequestParam(name = "remainingQuantity") int remainingQuantity,
      @RequestParam(name = "price") double price,
      @RequestParam(name = "availableForOrder") boolean availableForOrder,
      @RequestParam(name = "stillAvailable") boolean stillAvailable,
      @RequestParam(name = "itemCategory") ItemCategoryDto itemCategoryDto)
      throws IllegalArgumentException {
    ItemCategory itemCategory = itemCategoryService.getCategoryByName(itemCategoryDto.getName());
    Item item = itemService.modifyItem(name, description, imageUrl, remainingQuantity, price,
        availableForOrder, stillAvailable, itemCategory);
    return convertToDto(item, itemCategory);
  }
}
