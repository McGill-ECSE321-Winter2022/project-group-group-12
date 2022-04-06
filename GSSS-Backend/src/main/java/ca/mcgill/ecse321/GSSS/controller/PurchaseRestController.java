package ca.mcgill.ecse321.GSSS.controller;

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
import ca.mcgill.ecse321.GSSS.service.ItemService;
import ca.mcgill.ecse321.GSSS.service.OwnerService;
import ca.mcgill.ecse321.GSSS.service.PurchaseService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller methods for the purchase class
 *
 * @author Wassim Jabbour
 * @author Habib Jarweh
 */
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
  ItemService itemService;

  @Autowired
  OwnerService ownerService;

  /**
   * method to get purchase by id
   *
   * @param id the purchase's id
   * @return purchaseDto converted purchase if successful, else return error
   * @author Habib Jarweh
   */
  @GetMapping(value = {"/purchase/{id}", "/purchase/{id}/"})
  public ResponseEntity<?> getPurchaseById(@PathVariable("id") String id){
	  try {
	    Purchase purchase = purchaseService.getPurchase(id);
	    return ResponseEntity.ok(DtoUtility.convertToDto(purchase));
	  }catch (Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());
	  }
  }

  /**
   * method to get list of purchases by employee email
   *
   * @param employeeEmail Email of the employee
   * @return list of purchaseDto if successful, else return the error
   * @author Habib Jarweh
   * @author Habib Jarweh
   */
  @GetMapping(
      value = {"/purchasesbyemployee/{employeeEmail}", "/purchasesbyemployee/{employeeEmail}/"})
  public ResponseEntity<?> getPurchasesByEmployee(
      @PathVariable("employeeEmail") String employeeEmail){
	  try {
	    Employee employee = employeeService.getEmployeeByEmail(employeeEmail);
	    List<Purchase> allPurchases = purchaseService.getPurchasesByEmployee(employee);
	    List<PurchaseDto> purchaseDtos = new ArrayList<PurchaseDto>();
	
	    for (Purchase purchase : allPurchases) {
	
	      purchaseDtos.add(DtoUtility.convertToDto(purchase));
	    }
	    return ResponseEntity.ok(purchaseDtos);
	  }catch (Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());
	  }
  }

  /**
   * method to get list of purchases by date
   *
   * @param date of purchases
   * @return list of purchaseDto  if successful, else return the error
   * @author Habib Jarweh
   */
  @GetMapping(value = {"/purchasesbydate", "/purchasesbydate/"})
  public ResponseEntity<?> getPurchasesByDate(@RequestParam(name = "date") Date date){
	  try {
	    List<Purchase> allPurchases = purchaseService.getPurchasesByDate(date);
	    List<PurchaseDto> purchaseDtos = new ArrayList<PurchaseDto>();
	
	    for (Purchase purchase : allPurchases) {
	
	      purchaseDtos.add(DtoUtility.convertToDto(purchase));
	    }
	    return ResponseEntity.ok(purchaseDtos);
	  }catch (Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());
	  }
  }

  /**
   * method to get sorted list of purchases by customer email it creates a list where the purchases
   * of a particular customer are sorted by date and time we override the comparator of
   * collections.Sort()
   *
   * @param customerEmail
   * @return list of purchaseDto  if successful, else return the error
   * @author Habib Jarweh
   */
  @GetMapping(
      value = {"/purchasesbycustomer/{customerEmail}/", "/purchasesbycustomer/{customerEmail}"})
  public ResponseEntity<?> getPurchasesByCustomer(
      @PathVariable("customerEmail") String customerEmail){
	  try {
	    Customer customer = customerService.getCustomerByEmail(customerEmail);
	    List<Purchase> allPurchases = purchaseService.getOrderHistory(customer);
	    List<PurchaseDto> purchaseDtos = new ArrayList<PurchaseDto>();
	
	    // sort by date and time
	    Collections.sort(allPurchases, new Comparator<Purchase>() {
	      public int compare(Purchase p1, Purchase p2) {
	        return Long.valueOf(p2.getDate().getTime()).compareTo(p1.getDate().getTime());
	      }
	    });
	
	    for (Purchase purchase : allPurchases) {
	
	      purchaseDtos.add(DtoUtility.convertToDto(purchase));
	    }
	    // return purchaseDtos from most recent to least recent
	    return ResponseEntity.ok(purchaseDtos);
	  }catch (Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());
	  }
  }

  /**
   * Returns all purchases
   *
   * @return All the purchases in the system
   * @author Wassim Jabbour
   */
  @GetMapping(value = {"/purchases", "/purchases/"})
  public List<PurchaseDto> getAllPurchases() {

    List<Purchase> allPurchases = purchaseService.getAllPurchases();  // get all the purchases in the system

    List<PurchaseDto> allPurchaseDtos = new ArrayList<PurchaseDto>();

    for (Purchase purchase : allPurchases) {

      allPurchaseDtos.add(DtoUtility.convertToDto(purchase));
    }

    return allPurchaseDtos;
  }

  /**
   * Creates a purchase and returns its equivalent dto
   *
   * @param orderType   The type of purchase
   * @param orderStatus The status of purchase
   * @param data        The map of itemDtos and their quantities
   * @return The Dto corresponding to the created object if successful, else return the error
   * @author Wassim Jabbour
   */
  @PostMapping(value = {"/purchase", "/purchase/"})
  public ResponseEntity<?> createPurchase(@RequestParam(name = "ordertype") String orderType,
      @RequestParam(name = "orderstatus") String orderStatus,
      @RequestBody HashMap<String, Integer> data){
	  try {
	    OrderType actualOrderType = DtoUtility.findOrderTypeByName(orderType);
	    // Checking that it is not null
	    if (actualOrderType == null) {
	      throw new IllegalArgumentException("Invalid order type!");
	    }
	
	    OrderStatus actualOrderStatus = DtoUtility.findOrderStatusByName(orderStatus);
	    // Checking that it is not null
	    if (actualOrderStatus == null) {
	      throw new IllegalArgumentException("Invalid order status!");
	    }
	
	    Employee employee = employeeService.getClosestEmployee();
	    HashMap<Item, Integer> items = new HashMap<Item, Integer>();
	
	    for (Map.Entry<String, Integer> entry : data.entrySet()) {
	      items.put(itemService.getItemByName(entry.getKey()), entry.getValue());
	    }
	
	    Purchase purchase =
	        purchaseService.createPurchase(actualOrderType, employee, actualOrderStatus, items);
	
	    // Going through the items and decreasing their remaining quantity
	    for (Entry<String, Integer> entry : data.entrySet()) {
	
	      String itemName = entry.getKey();
	
	      Item item = itemService.getItemByName(itemName);
	
	      if (item.getRemainingQuantity() - entry.getValue() < 0) {
	        throw new IllegalArgumentException(
	            "There is not enough stock to buy this many of " + itemName);
	      }
	
	      Item modifiedItem =
	          itemService.modifyItem(
	              item.getName(),
	              item.getDescription(),
	              item.getImageUrl(),
	              item.getRemainingQuantity() - entry.getValue(),
	              item.getPrice(),
	              item.isAvailableForOrder(),
	              item.isStillAvailable(),
	              item.getCategory());
	    }
	
	    return ResponseEntity.ok(DtoUtility.convertToDto(purchase));
	  }catch (Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());
	  }
  }

  /**
   * Creates a purchase (With a customer) and returns its equivalent dto
   *
   * @param customerEmail The email of the customer
   * @param orderType     The type of purchase
   * @param orderStatus   The status of purchase
   * @param data          The map of itemDtos and their quantities
   * @return The Dto corresponding to the created object if successful, else return the error
   * @author Wassim Jabbour
   */
  @PostMapping(value = {"/purchasewithcustomer", "/purchasewithcustomer/"})
  public ResponseEntity<?> createPurchaseWithCustomer(@RequestParam(name = "ordertype") String orderType,
      @RequestParam(name = "orderstatus") String orderStatus,
      @RequestParam(name = "email") String customerEmail,
      @RequestBody HashMap<String, Integer> data){
	  try {
	    OrderType actualOrderType = DtoUtility.findOrderTypeByName(orderType);
	    // Checking that it is not null
	    if (actualOrderType == null) {
	      throw new IllegalArgumentException("Invalid order type!");
	    }
	
	    OrderStatus actualOrderStatus = DtoUtility.findOrderStatusByName(orderStatus);
	    // Checking that it is not null
	    if (actualOrderStatus == null) {
	      throw new IllegalArgumentException("Invalid order status!");
	    }
	
	    Employee employee = employeeService.getClosestEmployee();
	    HashMap<Item, Integer> items = new HashMap<Item, Integer>();
	
	    for (Map.Entry<String, Integer> entry : data.entrySet()) {
	      items.put(itemService.getItemByName(entry.getKey()), entry.getValue());
	    }
	
	    Purchase purchase =
	        purchaseService.createPurchase(actualOrderType, employee, actualOrderStatus, items);
	
	    // Getting the customer (Throws the exceptions itself)
	    Customer customer = customerService.getCustomerByEmail(customerEmail);
	
	    // Adding the purchase to the customer
	    customerService.addPurchase(customer, purchase);
	
	    // Going through the items and decreasing their remaining quantity
	    for (Entry<String, Integer> entry : data.entrySet()) {
	
	      String itemName = entry.getKey();
	
	      Item item = itemService.getItemByName(itemName);
	
	      if (item.getRemainingQuantity() - entry.getValue() < 0) {
	        throw new IllegalArgumentException(
	            "There is not enough stock to buy this many of " + itemName);
	      }
	
	      Item modifiedItem =
	          itemService.modifyItem(
	              item.getName(),
	              item.getDescription(),
	              item.getImageUrl(),
	              item.getRemainingQuantity() - entry.getValue(),
	              item.getPrice(),
	              item.isAvailableForOrder(),
	              item.isStillAvailable(),
	              item.getCategory());
	    }
	
	    return ResponseEntity.ok(DtoUtility.convertToDto(purchase));
	  }catch (Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());
	  }
  }

  /**
   * method to update/modify a purchase
   *
   * @param purchaseId    id of purchase
   * @param orderType     type of the purchase
   * @param orderStatus   status of order
   * @param data          items in the purchase
   * @param employeeEmail employee assigned to purchase
   * @return purchaseDto The purchase we modified if successful, else return the error
   * @author Habib Jarweh
   */
  @PostMapping(value = {"purchase/modify/{purchaseid}", "/purchase/modify/{purchaseId}/"})
  public ResponseEntity<?> modifyPurchase(@PathVariable(name = "purchaseid") String purchaseId,
      @RequestParam(name = "orderType") String orderType,
      @RequestParam(name = "orderStatus") String orderStatus,
      @RequestBody HashMap<String, Integer> data,
      @RequestParam(name = "employeeEmail") String employeeEmail){
	  try {
	    OrderType actualOrderType = DtoUtility.findOrderTypeByName(orderType);
	    // Checking that it is not null
	    if (actualOrderType == null) {
	      throw new IllegalArgumentException("Invalid order type!");
	    }
	
	    OrderStatus actualOrderStatus = DtoUtility.findOrderStatusByName(orderStatus);
	    // Checking that it is not null
	    if (actualOrderStatus == null) {
	      throw new IllegalArgumentException("Invalid order status!");
	    }
	
	    HashMap<Item, Integer> items = new HashMap<Item, Integer>();
	    for (Map.Entry<String, Integer> entry : data.entrySet()) {
	      items.put(itemService.getItemByName(entry.getKey()), entry.getValue());
	    }
	
	    Employee employee = employeeService.getEmployeeByEmail(employeeEmail);
	    Purchase purchase = purchaseService.modifyPurchase(actualOrderType, actualOrderStatus,
	        purchaseId, items, employee);
	
	    return ResponseEntity.ok(DtoUtility.convertToDto(purchase));
	  }catch (Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());
	  }
  }

  /**
   * Method that returns the total cost of a purchase
   *
   * @param id The id of the purchase
   * @return totalCost The total cost of the given purchase if successful, else return the error
   * @author Wassim Jabbour
   */
  @GetMapping(value = {"/purchase/cost/{id}", "/purchase/cost/{id}/"})
  public ResponseEntity<?> computeTotalCost(@PathVariable("id") String id){
	  try {
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
	    } catch (NoSuchElementException e) { // This exception is thrown if the purchase corresponds to
	      // no customer
	      customer = null;
	    }
	
	    // Getting the system information
	    Owner owner = ownerService.getOwner();
	    String city = owner.getStoreCity();
	    double fee = owner.getOutOfTownDeliveryFee();
	
	    // Adding the fee if the order is a delivery out of the city
	    if (customer != null && purchase.getOrderType().equals(OrderType.Delivery)) {
	      if (!city.equals(customer.getAddress().getCity())) {
	        totalCost += fee;
	      }
	    }
	
	    // Returning the total cost
	    return ResponseEntity.ok(totalCost);
	  }catch (Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());
	  }
  }

  /**
   * method to delete purchase
   *
   * @param id id of purchase we want to delete
   * @return null if successful, else return the error
   * @author Habib Jarweh
   */
  @DeleteMapping(value = {"/purchase/{id}", "/purchase/{id}/"})
  public ResponseEntity<?> deletePurchase(@PathVariable("id") String id){
	  try {
		  purchaseService.deletePurchase(id);
		  return ResponseEntity.ok(null);
	  }catch (Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());
	  }
  }
}
