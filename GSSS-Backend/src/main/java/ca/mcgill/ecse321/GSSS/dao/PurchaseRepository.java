package ca.mcgill.ecse321.GSSS.dao;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.QuantityOrdered;


public interface PurchaseRepository extends CrudRepository<Purchase, Long>{

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
	 * 
	 * method to find a specific Purchase by a quantityOrdered
	 * 
	 * @author Habib Jarweh
	 * @param quantityOrdered
	 * @return purchase we want to find
	 */
	Purchase findPurchaseByQuantityOrdered(QuantityOrdered quantityOrdered);
	
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
