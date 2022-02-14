package ca.mcgill.ecse321.GSSS.dao;

import java.sql.Time;
import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.QuantityOrdered;


/**
 * 
 * This is the repository for retrieving data from the Purchase table in the database
 * 
 * @author Habib Jarweh
 *
 */
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {

  /**
   * 
   * method to find a specific Purchase by its unique ID
   * 
   * @author Habib Jarweh
   * @param long id
   * @return purchase we want to find
   * 
   */
  Purchase findPurchaseById(long id);

  /**
   * 
   * method to find a specific Purchase by a quantityOrdered
   * 
   * @author Habib Jarweh
   * @param quantityOrdered
   * @return purchase we want to find
   */
  Purchase findPurchaseByQuantitiesOrdered(QuantityOrdered quantityOrdered);

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
   * @param Time time
   * @return List<Purchase>
   * 
   */
  List<Purchase> findPurchasesByTime(Time time);
}
