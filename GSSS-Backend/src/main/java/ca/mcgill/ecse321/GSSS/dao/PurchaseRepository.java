package ca.mcgill.ecse321.GSSS.dao;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.QuantityOrdered;


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
  Purchase findPurchasebyID(long id);

  /**
<<<<<<< HEAD
   * @author Habib Jarweh
   * @param quantityOrdered
   * @return purchase we want to find method to find a specific Purchase by a quantityOrdered
=======
   * 
   * method to find a specific Purchase by a quantityOrdered
   * 
   * @author Habib Jarweh
   * @param quantityOrdered
   * @return purchase we want to find
>>>>>>> 4360585d62861e70942da8491805799de7401130
   */
  Purchase findPurchaseByQuantityOrdered(QuantityOrdered quantityOrdered);

  /**
<<<<<<< HEAD
   * @author Habib Jarweh
   * @param Date date
   * @return List<Purchase> method that returns a list of purchases based on their date
=======
   * 
   * method that returns a list of purchases based on their date
   * 
   * @author Habib Jarweh
   * @param Date date
   * @return List<Purchase>
>>>>>>> 4360585d62861e70942da8491805799de7401130
   */
  List<Purchase> findPurchasesByDate(Date date);

  /**
<<<<<<< HEAD
   * @author Habib Jarweh
   * @param Time time
   * @return List<Purchase> method that returns a list of purchases based on their time
=======
   * 
   * method that returns a list of purchases based on their time
   * 
   * @author Habib Jarweh
   * @param Time time
   * @return List<Purchase>
   * 
>>>>>>> 4360585d62861e70942da8491805799de7401130
   */
  List<Purchase> findPurchasesByTime(Time time);
}
