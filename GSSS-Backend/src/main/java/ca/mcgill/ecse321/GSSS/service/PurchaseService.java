package ca.mcgill.ecse321.GSSS.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.GSSS.dao.EmployeeRepository;
import ca.mcgill.ecse321.GSSS.dao.PurchaseRepository;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.OrderStatus;
import ca.mcgill.ecse321.GSSS.model.OrderType;
import ca.mcgill.ecse321.GSSS.model.Purchase;

/**
 * Service methods of the Purchase class
 * 
 * @author Wassim Jabbour
 *
 */
@Service
public class PurchaseService {


  // CRUD repositories

  @Autowired
  PurchaseRepository purchaseRepository;

  @Autowired
  EmployeeRepository employeeRepository;


  // GET methods

  /**
   * Finds a purchase by its ID
   * 
   * @author Wassim Jabbour
   * @param purchaseId The ID of the purchase
   * @return The purchase with that ID
   */
  public Purchase getPurchase(String purchaseId) {
    
    // Input validation
    if(purchaseId == null || purchaseId.trim().length() == 0)
      throw new IllegalArgumentException("Purchase ID cannot be empty!");
    
    Purchase purchase = purchaseRepository.findPurchaseById(purchaseId);
    return purchase;
  }
  
  /**
   * Returns a list of all the purchases in the system
   * 
   * @author Wassim Jabbour
   * @return The list of purchases
   */
  public List<Purchase> getAllPurchases() {
    
    return HelperClass.toList(purchaseRepository.findAll());
    
  }

  /**
   * Finds all purchases that happened on a given date
   * 
   * @author Wassim Jabbour
   * @param date The date on which we want the purchases
   * @return The purchases that happened on that date
   */
  public List<Purchase> getPurchasesByDate(Date date) {
    
    // Input validation
    if(date == null)
      throw new IllegalArgumentException("Date cannot be null!");
    
    List<Purchase> purchases = purchaseRepository.findPurchasesByDate(date);
    return purchases;
  }

  /**
   * Finds all purchases on a given date and time
   * 
   * @author Wassim Jabbour
   * @param date The date of the purchases
   * @param time The time of the purchases
   * @return The purchases on that date and time
   */
  public List<Purchase> getPurchasesByDateAndTime(Date date, Time time) {
    
    // Input validation
    String error = "";
    if(date == null)
      error += "Date cannot be null! ";
    if(time == null)
      error += "Time cannot be null! ";
    if(error.length() > 0)
      throw new IllegalArgumentException(error);
    
    List<Purchase> purchases = purchaseRepository.findPurchasesByDateAndTime(date, time);
    return purchases;
  }

  /**
   * Finds all purchases that are assigned to the same employee
   * 
   * @author Wassim Jabbour
   * @param employee The employee we want to search based on
   * @return The purchases that employee has assigned to him
   */
  public List<Purchase> getPurchasesByEmployee(Employee employee) {

    // Input validation
    if(employee == null)
      throw new IllegalArgumentException("Employee cannot be null!");
    
    List<Purchase> purchases = purchaseRepository.findPurchasesByEmployee(employee);
    return purchases;
  }


  // CREATE method

  /**
   * Creates a new purchase using the passed parameters. The date and time are set to the creation
   * time.
   * 
   * @author Wassim Jabbour
   * @param orderType The type of purchase
   * @param employee The employee associated with the purchase
   * @param orderStatus The status of the order (Delivered, etc...)
   * @param items The items purchased
   * @return The newly created purchase
   */
  public Purchase createPurchase(OrderType orderType, Employee employee, OrderStatus orderStatus,
      Map<Item, Integer> items) {
    
    // Input validation
    String error = "";
    if(orderType == null)
      error += "Order type cannot be null! ";
    if(employee == null)
      error += "Employee cannot be null! ";
    if(orderStatus == null)
      error += "Order status cannot be null! ";
    if(items == null)
      error += "Items cannot be null! ";
    if(error.length() > 0)
      throw new IllegalArgumentException(error);
    
    Purchase purchase = new Purchase();
    purchase.setOrderStatus(orderStatus);
    purchase.setOrderType(orderType);
    purchase.setItems(items);
    purchase.setDate(new Date(System.currentTimeMillis()));
    purchase.setTime(new Time(System.currentTimeMillis()));
    purchase.setEmployee(employee);
    purchase.setId(UUID.randomUUID().toString());
    purchaseRepository.save(purchase);
    return purchase;
  }


  // DELETE method

  /**
   * Deletes a purchase based on its ID
   * 
   * @author Wassim Jabbour
   * @param purchaseId The ID of the purchase to delete
   */
  public void deletePurchase(String purchaseId) {
    
    // Input validation
    if(purchaseId == null || purchaseId.trim().length() == 0)
      throw new IllegalArgumentException("Purchase ID cannot be empty!");
    
    purchaseRepository.deleteById(purchaseId);
  }


  // MODIFY method

  /**
   * Method used to modify a purchase
   * 
   * @author Wassim Jabbour
   * @param orderType The new order type
   * @param orderStatus The new order status
   * @param purchaseId The ID of the purchase to modify
   * @param newItems The new items of the purchase
   * @param employee The employee responsible for the purchase
   * @return The modified purchase
   */
  public Purchase modifyPurchase(OrderType orderType, OrderStatus orderStatus, String purchaseId,
      Map<Item, Integer> newItems, Employee employee) {
    
    // Input validation
    String error = "";
    if(orderType == null)
      error += "Order type cannot be null! ";
    if(employee == null)
      error += "Employee cannot be null! ";
    if(orderStatus == null)
      error += "Order status cannot be null! ";
    if(newItems == null)
      error += "Items cannot be null! ";
    if(purchaseId == null || purchaseId.trim().length() == 0)
      error += "Purchase ID cannot be empty! ";
    if(error.length() > 0)
      throw new IllegalArgumentException(error);
    
    Purchase purchase = getPurchase(purchaseId);
    purchase.setOrderStatus(orderStatus);
    purchase.setOrderType(orderType);
    purchase.setItems(newItems);
    purchase.setEmployee(employee);
    purchaseRepository.save(purchase);
    return purchase;
  }
  
  /**
   * Method used to modify the order status of a purchase only
   * 
   * @author Wassim Jabbour
   * @param orderStatus The new order status
   * @param purchaseId The ID of the purchase to modify
   * @return The modified purchase
   */
  public Purchase modifyPurchaseStatus(OrderStatus orderStatus, String purchaseId) {
    
    // Input validation
    String error = "";
    if(orderStatus == null)
      error += "Order status cannot be null! ";
    if(purchaseId == null || purchaseId.trim().length() == 0)
      error += "Purchase ID cannot be empty! ";
    if(error.length() > 0)
      throw new IllegalArgumentException(error);
    
    Purchase purchase = getPurchase(purchaseId);
    purchase.setOrderStatus(orderStatus);
    purchaseRepository.save(purchase);
    return purchase;
  }
  
  /**
   * Method used to modify the employee of a purchase only
   * 
   * @author Wassim Jabbour
   * @param purchaseId The ID of the purchase to modify
   * @param employee The employee responsible for the purchase
   * @return The modified purchase
   */
  public Purchase modifyPurchaseEmployee(Employee employee, String purchaseId) {
    
    // Input validation
    String error = "";
    if(employee == null)
      error += "Employee cannot be null! ";
    if(purchaseId == null || purchaseId.trim().length() == 0)
      error += "Purchase ID cannot be empty! ";
    if(error.length() > 0)
      throw new IllegalArgumentException(error);
    
    Purchase purchase = getPurchase(purchaseId);
    purchase.setEmployee(employee);
    purchaseRepository.save(purchase);
    return purchase;
  }
  
  // OTHER methods
  
  /**
   * method to get order history of customer
   * 
   * @author Habib Jarweh
   * @param customer The customer 
   * @return list of his purchases
   */
  public List<Purchase> getOrderHistory(Customer customer) {
    
    // Input validation
    if(customer == null)
      throw new IllegalArgumentException("Customer cannot be null!");
    
    List<Purchase> list = new ArrayList<>(customer.getPurchases()) ;
    return list;
  }

}
