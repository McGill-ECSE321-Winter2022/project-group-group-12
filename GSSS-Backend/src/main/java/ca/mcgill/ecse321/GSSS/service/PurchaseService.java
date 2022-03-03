package ca.mcgill.ecse321.GSSS.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.GSSS.dao.EmployeeRepository;
import ca.mcgill.ecse321.GSSS.dao.PurchaseRepository;
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
    Purchase purchase = purchaseRepository.findPurchaseById(purchaseId);
    return purchase;
  }

  /**
   * Finds all purchases that happened on a given date
   * 
   * @author Wassim Jabbour
   * @param date The date on which we want the purchases
   * @return The purchases that happened on that date
   */
  public List<Purchase> getPurchasesByDate(Date date) {
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
    List<Purchase> purchases = purchaseRepository.findPurchasesByDateAndTime(date, time);
    return purchases;
  }

  /**
   * Finds all purchases that are assigned to the same employee
   * 
   * @author Wassim Jabbour
   * @param employeeEmail The email of the employee we want to search based on
   * @return The purchases that employee has assigned to him
   */
  public List<Purchase> getPurchasesByEmployee(String employeeEmail) {
    Employee employee = employeeRepository.findEmployeeByEmail(employeeEmail);
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
    Purchase purchase = new Purchase();
    purchase.setOrderStatus(orderStatus);
    purchase.setOrderType(orderType);
    purchase.setItems(items);
    purchase.setDate(new Date(System.currentTimeMillis()));
    purchase.setTime(new Time(System.currentTimeMillis()));
    purchase.setEmployee(employee);
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
   * @return The modified purchase
   */
  public Purchase modifyPurchase(OrderType orderType, OrderStatus orderStatus, String purchaseId,
      Map<Item, Integer> newItems) {
    Purchase purchase = getPurchase(purchaseId);
    purchase.setOrderStatus(orderStatus);
    purchase.setOrderType(orderType);
    purchase.setItems(newItems);
    purchaseRepository.save(purchase);
    return purchase;
  }

}
