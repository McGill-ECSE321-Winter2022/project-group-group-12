package ca.mcgill.ecse321.GSSS.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.GSSS.dto.EmployeeDto;
import ca.mcgill.ecse321.GSSS.dto.ItemDto;
import ca.mcgill.ecse321.GSSS.dto.PurchaseDto;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.OrderStatus;
import ca.mcgill.ecse321.GSSS.model.OrderType;
import ca.mcgill.ecse321.GSSS.model.Owner;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.service.CustomerService;
import ca.mcgill.ecse321.GSSS.service.EmployeeService;
import ca.mcgill.ecse321.GSSS.service.OwnerService;
import ca.mcgill.ecse321.GSSS.service.PurchaseService;

@CrossOrigin(origins = "*")
@RestController
public class PurchaseRestController {

  @Autowired
  PurchaseService purchaseService;

  @Autowired
  EmployeeService employeeService;

  @Autowired
  CustomerService customerService;
  
  @Autowired
  OwnerService ownerService;

  /**
   * method to get purchase by id
   * 
   * @author Habib Jarweh
   * @param id the purchase's id
   * @return purchaseDto converted purchase
   * @throws IllegalArgumentException if argument is not valid
   * @throws NoSuchElementException if element is null
   */
  @GetMapping(value = {"/purchase/id", "/purchase/id/"})
  public PurchaseDto getPurchaseById(@PathVariable("id") String id)
      throws IllegalArgumentException, NoSuchElementException {
    Purchase purchase = purchaseService.getPurchase(id);
    return DtoUtility.convertToDto(purchase);
  }

  /**
   * method to get list of purchases by employee email
   * 
   * @author Habib Jarweh
   * @param employeeEmail
   * @return list of purchaseDto
   * @throws IllegalArgumentException if argument is not valid
   * @throws NoSuchElementException if element is null
   */
  @GetMapping(value = {"/purchases/{employeeEmail}", "/purchases/{employeeEmail}/"})
  public List<PurchaseDto> getPurchasesByEmployee(
      @PathVariable("employeeEmail") String employeeEmail)
      throws IllegalArgumentException, NoSuchElementException {

    Employee employee = employeeService.getEmployeeByEmail(employeeEmail);
    List<Purchase> allPurchases = purchaseService.getPurchasesByEmployee(employee);
    List<PurchaseDto> purchaseDtos = new ArrayList<PurchaseDto>();

    for (Purchase purchase : allPurchases) {

      purchaseDtos.add(DtoUtility.convertToDto(purchase));

    }
    return purchaseDtos;

  }

  /**
   * method to get list of purchases by date
   * 
   * @author Habib Jarweh
   * @param date of purchases
   * @return list of purchaseDto
   * @throws IllegalArgumentException if argument is not valid
   * @throws NoSuchElementException if element is null
   */
  @GetMapping(value = {"/purchasesbydate", "/purchasesbydate/"})
  public List<PurchaseDto> getPurchasesByDate(Date date)
      throws IllegalArgumentException, NoSuchElementException {

    List<Purchase> allPurchases = purchaseService.getPurchasesByDate(date);
    List<PurchaseDto> purchaseDtos = new ArrayList<PurchaseDto>();

    for (Purchase purchase : allPurchases) {

      purchaseDtos.add(DtoUtility.convertToDto(purchase));

    }
    return purchaseDtos;

  }

  /**
   * method to get sorted list of purchases by customer email
   * 
   * @author Habib Jarweh
   * @param customerEmail
   * @return list of purchaseDto
   * @throws IllegalArgumentException if argument is not valid
   * @throws NoSuchElementException if element is null
   */
  @GetMapping(value = {"/purchases/{customerEmail}/", "/purchases/{customerEmail}"})
  public List<PurchaseDto> getPurchasesByCustomer(
      @PathVariable("customerEmail") String customerEmail)
      throws IllegalArgumentException, NoSuchElementException {

    Customer customer = customerService.getCustomerByEmail(customerEmail);
    List<Purchase> allPurchases = purchaseService.getOrderHistory(customer);
    List<PurchaseDto> purchaseDtos = new ArrayList<PurchaseDto>();

    Collections.sort(allPurchases, new Comparator<Purchase>() {
      public int compare(Purchase p1, Purchase p2) {
        return Long.valueOf(p2.getDate().getTime()).compareTo(p1.getDate().getTime());
      }
    });

    for (Purchase purchase : allPurchases) {

      purchaseDtos.add(DtoUtility.convertToDto(purchase));

    }
    return purchaseDtos;

  }

  /**
   * Returns all purchases
   * 
   * @author Wassim Jabbour
   * @return All the purchases in the system
   */
  @GetMapping(value = {"/purchases", "/purchases/"})
  public List<PurchaseDto> getAllPurchases() {

    List<Purchase> allPurchases = purchaseService.getAllPurchases();

    List<PurchaseDto> allPurchaseDtos = new ArrayList<PurchaseDto>();

    for (Purchase purchase : allPurchases) {

      allPurchaseDtos.add(DtoUtility.convertToDto(purchase));

    }

    return allPurchaseDtos;

  }

  /**
   * Creates a purchase and returns its equivalent dto
   * 
   * @author Wassim Jabbour
   * @param orderType The type of purchase
   * @param orderStatus The status of purchase
   * @param data The map of itemDtos and their quantities
   * @return The Dto corresponding to the created object
   * @throws IllegalArgumentException In case the input is invalid
   */
  @PostMapping(value = {"/purchase", "/purchase/"})
  public PurchaseDto createPurchase(@RequestParam(name = "ordertype") String orderType,
      @RequestParam(name = "orderstatus") String orderStatus,
      @RequestBody HashMap<ItemDto, Integer> data) throws IllegalArgumentException {

    OrderType actualOrderType = DtoUtility.findOrderTypeByName(orderType);
    // Checking that it is not null
    if (actualOrderType == null)
      throw new IllegalArgumentException("Invalid order type!");

    OrderStatus actualOrderStatus = DtoUtility.findOrderStatusByName(orderStatus);
    // Checking that it is not null
    if (actualOrderStatus == null)
      throw new IllegalArgumentException("Invalid order status!");

    Employee employee = employeeService.getClosestEmployee();
    HashMap<Item, Integer> items = new HashMap<Item, Integer>();

    for (Map.Entry<ItemDto, Integer> entry : data.entrySet()) {
      items.put(DtoUtility.convertToDomainObject(entry.getKey()), entry.getValue());
    }

    Purchase purchase =
        purchaseService.createPurchase(actualOrderType, employee, actualOrderStatus, items);

    return DtoUtility.convertToDto(purchase);

  }

  /**
   * method to update/modify a purchase
   * 
   * @author Habib Jarweh
   * @param purchaseId id of purchase
   * @param orderType type of the purchase
   * @param orderStatus status of order
   * @param newItems items in the purchase
   * @param employeeDto employee assigned to purchase
   * @return purchaseDto
   * @throws IllegalArgumentException
   */

  /**\
   *
   * @param purchaseId
   * @param orderType
   * @param orderStatus
   * @param data
   * @param employeeDto
   * @return
   * @throws IllegalArgumentException
   */
  @PostMapping(value = {"purchase/modify/{purchaseid}", "/purchase/modify/{purchaseId}/"})
  public PurchaseDto modifyPurchase(@PathVariable(name = "purchaseid") String purchaseId,
      @RequestParam(name = "orderType") String orderType,
      @RequestParam(name = "orderStatus") String orderStatus,
      @RequestBody HashMap<ItemDto, Integer> data,
      @RequestParam(name = "employeeDto") EmployeeDto employeeDto) throws IllegalArgumentException {

    OrderType actualOrderType = DtoUtility.findOrderTypeByName(orderType);
    // Checking that it is not null
    if (actualOrderType == null)
      throw new IllegalArgumentException("Invalid order type!");

    OrderStatus actualOrderStatus = DtoUtility.findOrderStatusByName(orderStatus);
    // Checking that it is not null
    if (actualOrderStatus == null)
      throw new IllegalArgumentException("Invalid order status!");

    HashMap<Item, Integer> items = new HashMap<Item, Integer>();
    for (Map.Entry<ItemDto, Integer> entry : data.entrySet()) {
      items.put(DtoUtility.convertToDomainObject(entry.getKey()), entry.getValue());
    }

    Employee employee = employeeService.getEmployeeByEmail(employeeDto.getEmail());
    Purchase purchase = purchaseService.modifyPurchase(actualOrderType, actualOrderStatus,
        purchaseId, items, employee);

    return DtoUtility.convertToDto(purchase);

  }

  /**
   * Method that returns the total cost of a purchase
   * 
   * @author Wassim Jabbour
   * @param id The id of the purchase
   * @throws IllegalArgumentException In case the id is null or empty
   * @throws NoSuchElementException In case the id doesn't correspond to any purchase
   */
  @GetMapping(value = {"/purchase/cost/{id}", "/purchase/cost/{id}/"})
  public double computeTotalCost(@PathVariable("id") String id)
      throws IllegalArgumentException, NoSuchElementException {

    // Getting the purchase
    Purchase purchase = purchaseService.getPurchase(id);

    // To keep track of total cost
    double totalCost = 0;

    // Computing total cost of items
    for (Entry<Item, Integer> entry : purchase.getItems().entrySet()) {
      totalCost += entry.getKey().getPrice() * entry.getValue();
    }

    // Finding the associated customer if it exists (Will be null for an in person purchase)
    Customer customer;
    try {
      customer = customerService.getCustomerByPurchase(purchase);
    } catch (NoSuchElementException e) { // This exception is thrown if the purchase corresponds to no customer
      customer = null;
    }
    
    // Getting the system information
    Owner owner = ownerService.getOwner();
    String city = owner.getStoreCity();
    double fee = owner.getOutOfTownDeliveryFee();
    
    // Adding the fee if the order is a delivery out of the city
    if (customer != null && purchase.getOrderType().equals(OrderType.Delivery)) {
      if(!city.equals(customer.getAddress().getCity())) {
        totalCost += fee;
      }
    }
    
    // Returning the total cost
    return totalCost;
  }

  /**
   * method to delete purchase
   * 
   * @author Habib Jarweh
   * @param id id of purchase we want to delete
   * @throws IllegalArgumentException
   */
  @DeleteMapping(value = {"/purchase/{id}", "/purchase/{id}/"})
  public void deletePurchase(@PathVariable("id") String id) throws IllegalArgumentException {
    purchaseService.deletePurchase(id);
  }

}
