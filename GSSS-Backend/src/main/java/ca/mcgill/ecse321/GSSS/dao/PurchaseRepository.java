package ca.mcgill.ecse321.GSSS.dao;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.QuantityOrdered;


public interface PurchaseRepository extends CrudRepository<Purchase, Long>{

	/**
	 * @author Habib Jarweh
	 * @param long id
	 * @return purchase we want to find
	 * method to find a specific Purchase by its unique ID
	 */
	Purchase findPurchasebyID(long id);
	
	/**
	 * @author Habib Jarweh
	 * @param quantityOrdered
	 * @return purchase we want to find
	 * method to find a specific Purchase by a quantityOrdered
	 */
	Purchase findPurchaseByQuantityOrdered(QuantityOrdered quantityOrdered);
	
	/**
	 * @author Habib Jarweh
	 * @param Date date
	 * @return List<Purchase>
	 * method that returns a list of purchases based on their date
	 */
	List<Purchase> findPurchasesByDate(Date date);
	
	/**
	 * @author Habib Jarweh
	 * @param Time time
	 * @return List<Purchase>
	 * method that returns a list of purchases based on their time
	 */
	List<Purchase> findPurchasesByTime(Time time);
}
