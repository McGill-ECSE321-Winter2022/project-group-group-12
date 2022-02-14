package ca.mcgill.ecse321.GSSS.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Purchase;

public interface CustomerRepository extends CrudRepository<Customer, String> {

  Customer findCustomerByEmail(String email);

  List<Customer> findCustomersByUsername(String username);

  /**
   * 
   * @author Habib Jarweh
   * @param purchase
   * @return
   */
  Customer findCustomerByPurchases(Purchase purchase);

  /**
   * 
   * @author Wassim Jabbour
   * @param address
   * @return
   */
  Customer findCustomerByAddress(Address address);
  
  /**
   * 
   * @author Habib Jarweh
   * 
   */
  List<Customer> findCustomersByDisabled(boolean disabled);

}
