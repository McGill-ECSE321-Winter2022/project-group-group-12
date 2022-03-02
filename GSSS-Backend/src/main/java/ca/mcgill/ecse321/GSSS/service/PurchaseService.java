package ca.mcgill.ecse321.GSSS.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.GSSS.dao.EmployeeRepository;
import ca.mcgill.ecse321.GSSS.dao.PurchaseRepository;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Purchase;

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


}
