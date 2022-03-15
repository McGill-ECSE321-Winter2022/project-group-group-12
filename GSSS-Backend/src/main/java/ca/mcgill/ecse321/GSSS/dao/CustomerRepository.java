package ca.mcgill.ecse321.GSSS.dao;

import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Implements methods to find a customer in the system
 *
 * @author Habib Jarweh and Wassim Jabbour
 */
public interface CustomerRepository extends CrudRepository<Customer, String> {

  /**
   * this method finds a customer based on their email
   *
   * @author Wassim Jabbour
   * @param email email of the customer, it is the primary key
   * @return customer with the particular email
   */
  Customer findCustomerByEmail(String email);

  /**
   * this method finds a list of customers with a particular username
   *
   * @author Habib Jarweh
   * @param username username of user, here a customer
   * @return list of customers we want to find with the particular username
   */
  List<Customer> findCustomersByUsername(String username);

  /**
   * method to find a customer based on their purchases
   *
   * @author Habib Jarweh
   * @param purchase the purchase of a client
   * @return customer with the particular purchase
   */
  Customer findCustomerByPurchases(Purchase purchase);

  /**
   * method to find customer based on their address
   *
   * @author Wassim Jabbour
   * @param address the address of the customer
   * @return customer with the particular address
   */
  Customer findCustomerByAddress(Address address);

  /**
   * method to find list of customers that are banned or have their accounts disabled
   *
   * @author Habib Jarweh
   * @param disabled boolean to see if customer is banned, or in other words their account is
   *     disabled
   */
  List<Customer> findCustomersByDisabled(boolean disabled);
}
