package ca.mcgill.ecse321.GSSS.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Purchase;

/**
 * 
 * To find the a customer in the system.
 * 
 * @author Habib Jarweh
 * @author Wassim Jabbour
 *
 */

public interface CustomerRepository extends CrudRepository<Customer, String> {

  /**
   * 
   * this method finds a customer based on their email
   * @author Wassim Jabbour
   * @param email email of the customer, it is the primary key
   * @return customer
   */
  
  Customer findCustomerByEmail(String email);

  /**
   * 
   * this method finds a list of customers with a particular username
   * @author Habib Jarweh
   * @param username username of user, here a customer
   * @return list of customers
   */
  List<Customer> findCustomersByUsername(String username);

  /**
   * 
   * method to find a customer based on their purchases
   * 
   * @author Habib Jarweh
   * @param purchase the purchase of a client
   * @return customer
   */
  Customer findCustomerByPurchases(Purchase purchase);

  /**
   * 
   * method to find customer based on their address
   * 
   * @author Wassim Jabbour
   * @param address the address of the customer
   * @return customer
   */
  Customer findCustomerByAddress(Address address);
  
  /**
   * 
   * method to find list of customers that are banned or have their accounts disabled
   * 
   * @author Habib Jarweh
   * @param disabled boolean to see if customer is banned, or in other words their account is disabled
   */
  List<Customer> findCustomersByDisabled(boolean disabled);

}
