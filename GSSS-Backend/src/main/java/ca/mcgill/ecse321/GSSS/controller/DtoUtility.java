package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.dto.AddressDto;
import ca.mcgill.ecse321.GSSS.dto.BusinessHourDto;
import ca.mcgill.ecse321.GSSS.dto.CustomerDto;
import ca.mcgill.ecse321.GSSS.dto.EmployeeDto;
import ca.mcgill.ecse321.GSSS.dto.ItemCategoryDto;
import ca.mcgill.ecse321.GSSS.dto.ItemDto;
import ca.mcgill.ecse321.GSSS.dto.OwnerDto;
import ca.mcgill.ecse321.GSSS.dto.PurchaseDto;
import ca.mcgill.ecse321.GSSS.dto.ShiftDto;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.BusinessHour;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import ca.mcgill.ecse321.GSSS.model.OrderStatus;
import ca.mcgill.ecse321.GSSS.model.OrderType;
import ca.mcgill.ecse321.GSSS.model.Owner;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.Shift;
import ca.mcgill.ecse321.GSSS.model.Weekday;
import ca.mcgill.ecse321.GSSS.service.ItemService;
import ca.mcgill.ecse321.GSSS.service.PurchaseService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Helper class for methods used across all controller classes
 *
 * @author Wassim Jabbour
 * @author Enzo Benoit Jeannin
 * @author Philippe Sarouphim Hochar
 */
public class DtoUtility {

  @Autowired
  PurchaseService purchaseService;

  @Autowired
  static ItemService itemService;

  /**
   * Helper method that converts a weekDayName string to its corresponding weekday
   *
   * @param weekDayName The string representing the weekday name
   * @return The weekday
   * @author Wassim Jabbour
   */
  static Weekday findWeekdayByName(String weekDayName) {

    if (weekDayName.equals("Monday")) {
      return Weekday.Monday;
    } else if (weekDayName.equals("Tuesday")) {
      return Weekday.Tuesday;
    } else if (weekDayName.equals("Wednesday")) {
      return Weekday.Wednesday;
    } else if (weekDayName.equals("Thursday")) {
      return Weekday.Thursday;
    } else if (weekDayName.equals("Friday")) {
      return Weekday.Friday;
    } else if (weekDayName.equals("Saturday")) {
      return Weekday.Saturday;
    } else if (weekDayName.equals("Sunday")) {
      return Weekday.Sunday;
    } else {
      return null;
    }
  }

  /**
   * Helper method that converts an OrderType string to its enum equivalent
   *
   * @param orderType The string representing the type
   * @return The type
   * @author Wassim Jabbour
   */
  static OrderType findOrderTypeByName(String orderType) {

    if (orderType.equals("Delivery")) {
      return OrderType.Delivery;
    } else if (orderType.equals("Pickup")) {
      return OrderType.Pickup;
    } else if (orderType.equals("InPerson")) {
      return OrderType.InPerson;
    } else {
      return null;
    }
  }

  /**
   * Helper method that converts an OrderStatus string to its enum equivalent
   *
   * @param orderStatus The string representing the status
   * @return The status
   * @author Wassim Jabbour
   */
  static OrderStatus findOrderStatusByName(String orderStatus) {

    if (orderStatus.equals("BeingPrepared")) {
      return OrderStatus.BeingPrepared;
    } else if (orderStatus.equals("OutForDelivery")) {
      return OrderStatus.OutForDelivery;
    } else if (orderStatus.equals("Completed")) {
      return OrderStatus.Completed;
    } else {
      return null;
    }
  }

  /**
   * Helper method that converts a BusinessHour to its DTO equivalent
   *
   * @param businessHour The BusinessHour to convert
   * @return The converted DTO
   * @author Wassim Jabbour
   */
  static BusinessHourDto convertToDto(BusinessHour businessHour) throws IllegalArgumentException {

    if (businessHour == null) {
      throw new IllegalArgumentException("There is no such business hour!");
    }

    BusinessHourDto businessHourDto =
        new BusinessHourDto(
            businessHour.getWeekday(), businessHour.getStartTime(), businessHour.getEndTime());

    return businessHourDto;
  }

  /**
   * method to convert from type item to type itemDto while also specifying the item category
   *
   * @param i  item we want to convert
   * @param ic its item category
   * @return item converted to type itemDto
   * @author Habib Jarweh
   */
  static ItemDto convertToDto(Item i, ItemCategory ic) {
    if (i == null) {
      throw new IllegalArgumentException("There is no such Item!");
    }
    if (ic == null) {
      throw new IllegalArgumentException("There is no such Item Category!");
    }
    ItemDto itemDto =
        new ItemDto(
            i.getName(),
            i.getDescription(),
            i.getImageUrl(),
            i.getRemainingQuantity(),
            i.getPrice(),
            i.isAvailableForOrder(),
            i.isStillAvailable(),
            convertToDto(ic));
    return itemDto;
  }

  /**
   * method to convert from type item to type itemDto
   *
   * @param i item we want to convert
   * @return item converted to type itemDto
   * @author Wassim Jabbour
   */
  static ItemDto convertToDto(Item i) {
    if (i == null) {
      throw new IllegalArgumentException("There is no such Item!");
    }

    ItemDto itemDto =
        new ItemDto(
            i.getName(),
            i.getDescription(),
            i.getImageUrl(),
            i.getRemainingQuantity(),
            i.getPrice(),
            i.isAvailableForOrder(),
            i.isStillAvailable(),
            convertToDto(i.getCategory()));

    return itemDto;
  }

  /**
   * method to convert from type itemCategory to its Dto equivalent
   *
   * @param ic item category we want to convert
   * @return item converted to type itemDto
   * @author Habib Jarweh
   */
  static ItemCategoryDto convertToDto(ItemCategory ic) {
    if (ic == null) {
      throw new IllegalArgumentException("There is no such Item Category!");
    }
    ItemCategoryDto itemCategoryDto = new ItemCategoryDto(ic.getName());
    return itemCategoryDto;
  }

  /**
   * Method that converts a purchase to its Dto equivalent
   *
   * @param purchase The purchase to convert
   * @return The dto equivalent
   * @throws IllegalArgumentException
   * @author Wassim Jabbour
   */
  static PurchaseDto convertToDto(Purchase purchase) {

    // Checking the input is non null
    if (purchase == null) {
      throw new IllegalArgumentException("There is no such purchase!");
    }

    PurchaseDto purchaseDto =
        new PurchaseDto(
            purchase.getId(),
            purchase.getOrderType(),
            purchase.getOrderStatus(),
            purchase.getDate(),
            purchase.getTime(),
            convertItemMap(purchase.getItems()),
            convertToDto(purchase.getEmployee()));
    ;

    return purchaseDto;
  }

  /**
   * Helper method that converts an itemDto to its domain model equivalent
   *
   * @param itemDto The DTO to convert
   * @return The domain model object
   * @author Wassim Jabbour
   */
  static Item convertToDomainObject(ItemDto itemDto) throws IllegalArgumentException {

    // Checking the input is non null
    if (itemDto == null) {
      throw new IllegalArgumentException("There is no such item!");
    }

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
   * @param itemMap The map of items and integers
   * @return The corresponding map of itemDtos and integers
   * @author Wassim Jabbour
   */
  static Map<ItemDto, Integer> convertItemMap(Map<Item, Integer> itemMap) {

    // Checking the input is non null
    if (itemMap == null) {
      throw new IllegalArgumentException("There is no such item selection!");
    }

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
   * @param employee The object to convert
   * @return The corresponding dto
   * @author Wassim Jabbour
   */
  static EmployeeDto convertToDto(Employee employee) {

    EmployeeDto employeeDto =
        new EmployeeDto(
            employee.getUsername(),
            employee.getEmail(),
            employee.getPassword(),
            employee.getSalt(),
            employee.isDisabled(),
            convertToDto(employee.getAddress()),
            convertShiftList(employee.getShifts()));

    return employeeDto;
  }

  /**
   * Converts an address to its DTO equivalent
   *
   * @param address The object to convert
   * @return The corresponding dto
   * @author Wassim Jabbour
   */
  static AddressDto convertToDto(Address address) {

    // Checking the input is non null
    if (address == null) {
      throw new IllegalArgumentException("There is no such address!");
    }

    AddressDto addressDto =
        new AddressDto(
            address.getId(),
            address.getFullName(),
            address.getStreetName(),
            address.getStreetNumber(),
            address.getCity(),
            address.getPostalCode());

    return addressDto;
  }

  /**
   * Converts a shift to its DTO equivalent
   *
   * @param shift The object to convert
   * @return The corresponding dto
   * @author Wassim Jabbour
   */
  static ShiftDto convertToDto(Shift shift) {

    // Checking the input is non null
    if (shift == null) {
      throw new IllegalArgumentException("There is no such shift!");
    }

    ShiftDto shiftDto =
        new ShiftDto(shift.getDate(), shift.getId(), shift.getStartTime(), shift.getEndTime());

    return shiftDto;
  }

  /**
   * Converts a set of shifts to a list of shift dtos
   *
   * @param shifts The object to convert
   * @return The corresponding dto
   * @author Wassim Jabbour
   */
  static List<ShiftDto> convertShiftList(Set<Shift> shifts) {

    // Checking the input is non null
    if (shifts == null) {
      return new ArrayList<ShiftDto>();
    }

    List<ShiftDto> shiftDtos = new ArrayList<ShiftDto>();

    for (Shift s : shifts) {
      shiftDtos.add(convertToDto(s));
    }

    return shiftDtos;
  }

  /**
   * Converts a customer to its DTO equivalent
   *
   * @param customer The customer object to convert
   * @return The corresponding dto
   * @author Enzo Benoit-Jeannin
   */
  static CustomerDto convertToDto(Customer customer) {

    CustomerDto customerDto =
        new CustomerDto(
            customer.getUsername(),
            customer.getEmail(),
            customer.getPassword(),
            customer.getSalt(),
            customer.isDisabled(),
            convertToDto(customer.getAddress()),
            convertToDto(customer.getPurchases()));

    return customerDto;
  }

  /**
   * Method that converts a set of purchase to a list of purchase dto equivalents
   *
   * @param purchases The set of purchase to convert
   * @return A list of dto equivalent to the purchase in the set
   * @throws IllegalArgumentException
   * @author Enzo Benoit-Jeannin
   */
  static List<PurchaseDto> convertToDto(Set<Purchase> purchases) {

    // Checking the input is non null
    if (purchases == null) {
      purchases = new HashSet<Purchase>();
    }

    // Initialize the list of purchase dto to return
    List<PurchaseDto> result = new ArrayList<PurchaseDto>();

    // For each purchase in the set, convert it to its dto equivalent and add it to the list
    for (Purchase p : purchases) {
      PurchaseDto purchaseDto = convertToDto(p);
      result.add(purchaseDto);
    }

    return result;
  }

  /**
   * This method converts an address DTO to DAO.
   *
   * @param addressDto Address DTO.
   * @return Address DAO.
   * @author Philippe Sarouphim Hochar.
   */
  static Address convertToDomainObject(AddressDto addressDto) {
    Address address = new Address();
    address.setCity(addressDto.getCity());
    address.setFullName(addressDto.getFullName());
    address.setId(addressDto.getId());
    address.setPostalCode(addressDto.getPostalCode());
    address.setStreetName(addressDto.getStreetName());
    address.setStreetNumber(addressDto.getStreetNumber());
    return address;
  }

  /**
   * This method converts a shift DTO to DAO.
   *
   * @param shiftDto Shift DTO.
   * @return Shift DAO.
   * @author Philippe Sarouphim Hochar.
   */
  static Shift convertToDomainObject(ShiftDto shiftDto) {
    Shift shift = new Shift();
    shift.setDate(shiftDto.getDate());
    shift.setEndTime(shiftDto.getEndTime());
    shift.setId(shiftDto.getId());
    shift.setStartTime(shiftDto.getStartTime());
    return shift;
  }

  /**
   * This method converts an employee DTO to DAO
   *
   * @param employeeDto Employee DTO
   * @return Employee DAO
   * @author Enzo Benoit-Jeannin
   */
  static Employee convertToDomainObject(EmployeeDto employeeDto) {
    Employee employee = new Employee();
    employee.setUsername(employeeDto.getUsername());
    employee.setPassword(employeeDto.getPassword());
    employee.setEmail(employeeDto.getEmail());
    employee.setDisabled(employeeDto.isDisabled());
    employee.setAddress(convertToDomainObject(employeeDto.getAddress()));
    employee.setSalt(employeeDto.getSalt());
    employee.setShifts(convertToDomainObject(employeeDto.getShifts()));
    return employee;
  }

  static Set<Shift> convertToDomainObject(List<ShiftDto> shiftDtos) {
    // Checking the input is non null
    if (shiftDtos == null) {
      return new HashSet<Shift>();
    }

    Set<Shift> shifts = new HashSet<Shift>();

    for (ShiftDto s : shiftDtos) {
      shifts.add(convertToDomainObject(s));
    }

    return shifts;
  }

  /**
   * Helper method that converts a map of itemDtos and integers into a map of items and integers
   *
   * @param itemMapDto The map of itemDtos and integers
   * @return The corresponding map of items and integers
   * @author Enzo Benoit-Jeannin
   */
  static Map<Item, Integer> convertItemMapDto(Map<ItemDto, Integer> itemMapDto) {

    // Checking the input is non null
    if (itemMapDto == null) {
      throw new IllegalArgumentException("There is no such item selection!");
    }

    Map<Item, Integer> itemMap = new HashMap<Item, Integer>();

    for (Map.Entry<ItemDto, Integer> entry : itemMapDto.entrySet()) {
      Item convertedItem = convertToDomainObject(entry.getKey());
      itemMap.put(convertedItem, entry.getValue());
    }
    return itemMap;
  }

  /**
   * Converts the owner to its DTO equivalent
   *
   * @param owner The owner object to convert
   * @return The corresponding dto
   * @author Enzo Benoit-Jeannin
   */
  static OwnerDto convertToDto(Owner owner) {

    OwnerDto ownerDto =
        new OwnerDto(
            owner.getUsername(),
            owner.getEmail(),
            owner.getPassword(),
            owner.getSalt(),
            owner.isDisabled(),
            convertToDto(owner.getAddress()));

    return ownerDto;
  }
}
