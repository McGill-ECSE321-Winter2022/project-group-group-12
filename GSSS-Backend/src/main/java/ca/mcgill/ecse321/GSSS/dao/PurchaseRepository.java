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
   * @param long id
   * @return purchase we want to find
   * 
   */
  Purchase findPurchaseById(String id);

  /**
   * 
   * method that returns a list of purchases based on their date
   * 
   * @author Habib Jarweh
   * @param Date date
   * @return List<Purchase>
   */
  List<Purchase> findPurchasesByDate(Date date);

  /**
   * 
   * method that returns a list of purchases based on their time
   * 
   * @author Habib Jarweh
   * @param date The date of the purchase
   * @param time The time of the purchase
   * @return List<Purchase>
   * 
   */
  List<Purchase> findPurchasesByDateAndTime(Date date, Time time);
  
  /**
   * @author Wassim Jabbour
   */
  List<Purchase> findPurchasesByEmployee(Employee employee);
}
