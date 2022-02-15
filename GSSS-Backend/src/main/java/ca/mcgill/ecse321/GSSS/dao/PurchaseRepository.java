package ca.mcgill.ecse321.GSSS.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Purchase;

/**
 * 
 * This is the repository for retrieving data from the Purchase table in the database
 * 
 * @author Habib Jarweh
 *
 */
public interface PurchaseRepository extends CrudRepository<Purchase, String> {

  /**
   * 
   * method to find a specific Purchase by its unique ID
   * 
   * @author Habib Jarweh
   * @param id, unique id of purchase
   * @return purchase we want to find
   * 
   */
  Purchase findPurchaseById(String id);

  /**
   * 
   * method that returns a list of purchases based on their date
   * 
   * @author Habib Jarweh
   * @param date the date of purchase
   * @return List<Purchase>
   */
  List<Purchase> findPurchasesByDate(Date date);

  /**
   * 
   * method that returns a list of purchases based on their date and time
   * 
   * @author Habib Jarweh
   * @param date The date of the purchase
   * @param time The time of the purchase
   * @return List<Purchase>
   * 
   */
  List<Purchase> findPurchasesByDateAndTime(Date date, Time time);
  
  /**
   * 
   * method that returns list of purchases based on the employee that handled them
   * 
   * @author Wassim Jabbour
   * @param employee the employee who handled the purchase
   */
  List<Purchase> findPurchasesByEmployee(Employee employee);
}
