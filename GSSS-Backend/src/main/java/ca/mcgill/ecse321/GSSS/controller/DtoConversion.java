package ca.mcgill.ecse321.GSSS.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import ca.mcgill.ecse321.GSSS.dto.AddressDto;
import ca.mcgill.ecse321.GSSS.dto.BusinessHourDto;
import ca.mcgill.ecse321.GSSS.dto.EmployeeDto;
import ca.mcgill.ecse321.GSSS.dto.ItemCategoryDto;
import ca.mcgill.ecse321.GSSS.dto.ItemDto;
import ca.mcgill.ecse321.GSSS.dto.PurchaseDto;
import ca.mcgill.ecse321.GSSS.dto.ShiftDto;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.BusinessHour;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import ca.mcgill.ecse321.GSSS.model.OrderStatus;
import ca.mcgill.ecse321.GSSS.model.OrderType;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.Shift;
import ca.mcgill.ecse321.GSSS.model.Weekday;
import ca.mcgill.ecse321.GSSS.service.ItemService;
import ca.mcgill.ecse321.GSSS.service.PurchaseService;

public class DtoConversion {

  @Autowired
  PurchaseService purchaseService;
  
  @Autowired
  static
  ItemService itemService;

  /**
   * Helper method that converts a weekDayName string to its corresponding weekday
   * 
   * @author Wassim Jabbour
   * @param weekDayName The string representing the weekday name
   * @return The weekday
   */
  static Weekday findWeekdayByName(String weekDayName) {

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
  
  /**
   * Helper method that converts an OrderType string to its enum equivalent
   * 
   * @author Wassim Jabbour
   * @param weekDayName The string representing the type
   * @return The type
   */
  static OrderType findOrderTypeByName(String orderType) {

    if (orderType.equals("Delivery"))
      return OrderType.Delivery;

    else if (orderType.equals("Pickup"))
      return OrderType.Pickup;

    else if (orderType.equals("InPerson"))
      return OrderType.InPerson;

    else 
      return null;
  }
  
  /**
   * Helper method that converts an OrderStatus string to its enum equivalent
   * 
   * @author Wassim Jabbour
   * @param weekDayName The string representing the status
   * @return The status
   */
  static OrderStatus findOrderStatusByName(String orderStatus) {

    if (orderStatus.equals("BeingPrepared"))
      return OrderStatus.BeingPrepared;

    else if (orderStatus.equals("OutForDelivery"))
      return OrderStatus.OutForDelivery;

    else if (orderStatus.equals("Completed"))
      return OrderStatus.Completed;

    else 
      return null;
  }

  /**
   * Helper method that converts a BusinessHour to its DTO equivalent
   * 
   * @author Wassim Jabbour
   * @param businessHour The BusinessHour to convert
   * @return The converted DTO
   */
  static BusinessHourDto convertToDto(BusinessHour businessHour) throws IllegalArgumentException {

    if (businessHour == null)
      throw new IllegalArgumentException("There is no such business hour!");

    BusinessHourDto businessHourDto = new BusinessHourDto(businessHour.getWeekday(),
        businessHour.getStartTime(), businessHour.getEndTime());

    return businessHourDto;
  }

  /**
   * method to convert from type item to type itemDto while also specifying the item category
   * 
   * @author Habib Jarweh
   * @param i item we want to convert
   * @param ic its item category
   * @return item converted to type itemDto
   */
  static ItemDto convertToDto(Item i, ItemCategory ic) {
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
   * method to convert from type item to type itemDto
   * 
   * @author Wassim Jabbour
   * @param i item we want to convert
   * @return item converted to type itemDto
   */
  static ItemDto convertToDto(Item i) {
    if (i == null) {
      throw new IllegalArgumentException("There is no such Item!");
    }

    ItemDto itemDto = new ItemDto(i.getName(), i.getDescription(), i.getImageUrl(),
        i.getRemainingQuantity(), i.getPrice(), i.isAvailableForOrder(), i.isStillAvailable(),
        convertToDto(i.getCategory()));

    return itemDto;
  }

  /**
   * method to convert from type itemCategory to type itemCategoryDto
   * 
   * @author Habib Jarweh
   * @param ic item category we want to convert
   * @return item converted to type itemDto
   */
  static ItemCategoryDto convertToDto(ItemCategory ic) {
    if (ic == null) {
      throw new IllegalArgumentException("There is no such Item Category!");
    }
    ItemCategoryDto itemCategoryDto = new ItemCategoryDto(ic.getName());
    return itemCategoryDto;
  }

  /**
   * Method that converts a purchase to its dto equivalent
   * 
   * @author Wassim Jabbour
   * @param purchase The purchase to convert
   * @return The dto equivalent
   * @throws IllegalArgumentException
   */
  static PurchaseDto convertToDto(Purchase purchase) {

    // Checking the input is non null
    if (purchase == null)
      throw new IllegalArgumentException("There is no such purchase!");

    // Converting the map of items into a map of itemDtos
    HashMap<ItemDto, Integer> dtoMap =
        (HashMap<ItemDto, Integer>) convertItemMap(purchase.getItems());

    // Converting the employee to a dto


    PurchaseDto purchaseDto = new PurchaseDto(purchase.getId(), purchase.getOrderType(),
        purchase.getOrderStatus(), purchase.getDate(), purchase.getTime(),
        convertItemMap(purchase.getItems()), convertToDto(purchase.getEmployee()));;

    return purchaseDto;

  }
  
  /**
   * Helper method that converts an itemDto to its domain model equivalent
   * 
   * @author Wassim Jabbour
   * @param itemDto The DTO to convert
   * @return The domain model object
   */
  static Item convertToDomainObject(ItemDto itemDto) throws IllegalArgumentException {

    // Checking the input is non null
    if (itemDto == null)
      throw new IllegalArgumentException("There is no such item!");

    // Getting all the purchases in the system
    List<Item> allItems = itemService.getAllItems();

    // Cycling through them till we find the one with the desired weekday and returning it
    for (Item item : allItems) {

      if (item.getName().equals(itemDto.getName())) {
        return item;
      }

    }

    // Return null if no purchase with that ID was found
    return null;

  }

  /**
   * Helper method that converts a map of items and integers into a map of itemDtos and integers
   * 
   * @author Wassim Jabbour
   * @param itemMap The map of items and integers
   * @return The corresponding map of itemDtos and integers
   */
  static Map<ItemDto, Integer> convertItemMap(Map<Item, Integer> itemMap) {

    // Checking the input is non null
    if (itemMap == null)
      throw new IllegalArgumentException("There is no such item selection!");

    Map<ItemDto, Integer> itemDtoMap = new HashMap<ItemDto, Integer>();

    for (Map.Entry<Item, Integer> entry : itemMap.entrySet()) {
      ItemDto convertedItem = convertToDto(entry.getKey());
      itemDtoMap.put(convertedItem, entry.getValue());
    }

    return itemDtoMap;
  }


  /**
   * Converts an employee to its DTO equivalent
   * 
   * @author Wassim Jabbour
   * @param employee The object to convert
   * @return The corresponding dto
   */
  static EmployeeDto convertToDto(Employee employee) {

    EmployeeDto employeeDto = new EmployeeDto(employee.getUsername(), employee.getEmail(),
        employee.getPassword(), employee.getSalt(), employee.isDisabled(),
        convertToDto(employee.getAddress()), convertShiftList(employee.getShifts()));

    return employeeDto;
  }

  /**
   * Converts an address to its DTO equivalent
   * 
   * @author Wassim Jabbour
   * @param employee The object to convert
   * @return The corresponding dto
   */
  static AddressDto convertToDto(Address address) {

    // Checking the input is non null
    if (address == null)
      throw new IllegalArgumentException("There is no such address!");

    AddressDto addressDto = new AddressDto(address.getFullName(), address.getStreetName(),
        address.getStreetNumber(), address.getCity(), address.getPostalCode());

    return addressDto;
  }

  /**
   * Converts a shift to its DTO equivalent
   * 
   * @author Wassim Jabbour
   * @param employee The object to convert
   * @return The corresponding dto
   */
  static ShiftDto convertToDto(Shift shift) {

    // Checking the input is non null
    if (shift == null)
      throw new IllegalArgumentException("There is no such shift!");

    ShiftDto shiftDto =
        new ShiftDto(shift.getDate(), shift.getId(), shift.getStartTime(), shift.getEndTime());

    return shiftDto;

  }

  /**
   * Converts a set of shifts to a list of shift dtos
   * 
   * @author Wassim Jabbour
   * @param employee The object to convert
   * @return The corresponding dto
   */
  static List<ShiftDto> convertShiftList(Set<Shift> shifts) {

    // Checking the input is non null
    if (shifts == null)
      throw new IllegalArgumentException("There is no such shifts!");

    List<ShiftDto> shiftDtos = new ArrayList<ShiftDto>();

    for (Shift s : shifts) {
      shiftDtos.add(convertToDto(s));
    }

    return shiftDtos;
  }

}
