package ca.mcgill.ecse321.GSSS.dao;

import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * This is the repository for retrieving data from the Purchase table in the database
 *
 * @author Habib Jarweh
 * @author Wassim Jabbour
 */
public interface PurchaseRepository extends CrudRepository<Purchase, String> {

  /**
   * Method to find a specific Purchase by its unique ID
   *
   * @param id Unique id of purchase
   * @return purchase we want to find
   * @author Habib Jarweh
   */
  Purchase findPurchaseById(String id);

  /**
   * Method that returns a list of purchases based on their date
   *
   * @param date The date of purchase
   * @return List<Purchase> list of purchases we want to find
   * @author Habib Jarweh
   */
  List<Purchase> findPurchasesByDate(Date date);

  /**
   * method that returns a list of purchases based on their date and time
   *
   * @param date The date of the purchase
   * @param time The time of the purchase
   * @return List<Purchase> list of purchases we want to find
   * @author Habib Jarweh
   */
  List<Purchase> findPurchasesByDateAndTime(Date date, Time time);

  /**
   * Method that returns a list of purchases based on the employee that handled them
   *
   * @param employee The employee who handled the purchase
   * @return list of purchases we want to find
   * @author Wassim Jabbour
   */
  List<Purchase> findPurchasesByEmployee(Employee employee);
}
