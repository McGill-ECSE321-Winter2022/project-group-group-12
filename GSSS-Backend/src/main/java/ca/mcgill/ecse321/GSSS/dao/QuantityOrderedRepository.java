package ca.mcgill.ecse321.GSSS.dao;

import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.QuantityOrdered;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Used to execute queries for the QuantityOrdered class
 * 
 * @author Wassim Jabbour
 *
 */
public interface QuantityOrderedRepository extends CrudRepository<QuantityOrdered, String> {

  /**
   * To find a QuantityOrdered object by its ID
   * 
   * @param id The ID of the object
   * @return The object we are searching for
   */
  QuantityOrdered findQuantityOrderedById(String id);

  /**
   * To find a list of QuantityOrdered objects based on the item that was ordered
   * 
   * @param itemOrdered The item we want to find all the quantities associated to
   * @return A list of quantities associated to that order
   */
  List<QuantityOrdered> findQuantitiesOrderedByItemOrdered(Item itemOrdered);

}
