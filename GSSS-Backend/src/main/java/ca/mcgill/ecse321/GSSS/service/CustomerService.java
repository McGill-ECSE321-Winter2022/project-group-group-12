package ca.mcgill.ecse321.GSSS.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.GSSS.dao.AddressRepository;
import ca.mcgill.ecse321.GSSS.dao.CustomerRepository;
import ca.mcgill.ecse321.GSSS.dao.PurchaseRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Purchase;

/**
 * Services of the customer class
 * 
 * @author Wassim Jabbour
 *
 */
@Service
public class CustomerService {

  // CRUD repositories

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  PurchaseRepository purchaseRepository;

  @Autowired
  AddressRepository addressRepository;



  // GET methods

  /**
   * Finds a customer by their email
   * 
   * @author Wassim Jabbour
   * @param email The email of the customer
   * @return The customer with that emaila
   */
  @Transactional
  public Customer getCustomer(String email) {
    
    // Input validation
    if(email == null || email.trim().length() == 0)
      throw new IllegalArgumentException("Customer email cannot be empty!");
    
    Customer customer = customerRepository.findCustomerByEmail(email);
    return customer;
  }

  /**
   * Finds a list of customers with the given username (Not unique)
   * 
   * @author Wassim Jabbour
   * @param username The username to search for
   * @return The customers with that username
   */
  @Transactional
  public List<Customer> getCustomersByUsername(String username) {
    
    // Input validation
    if(username == null || username.trim().length() == 0)
      throw new IllegalArgumentException("Customer username cannot be empty!");
    
    List<Customer> customers = customerRepository.findCustomersByUsername(username);
    return customers;
  }

  /**
   * Finds the customer with a given purchase
   * 
   * @author Wassim Jabbour
   * @param purchase The purchase to search for
   * @return The customer with that purchase
   */
  @Transactional
  public Customer getCustomerByPurchase(Purchase purchase) {
    
    // Input validation
    if(purchase == null)
      throw new IllegalArgumentException("Purchase cannot be null!");
    
    Customer customer = customerRepository.findCustomerByPurchases(purchase);
    return customer;
  }

  /**
   * Finds the customer with a given address
   * 
   * @author Wassim Jabbour
   * @param address The address to search for
   * @return The customer with that address
   */
  @Transactional
  public Customer getCustomerByAddress(Address address) {
    
    // Input validation
    if(address == null)
      throw new IllegalArgumentException("Address cannot be null!");
    
    Customer customer = customerRepository.findCustomerByAddress(address);
    return customer;
  }

  /**
   * Finds all customers in the database
   * 
   * @author Wassim Jabbour
   * @return A list of all the customers
   */
  @Transactional
  public List<Customer> getAllCustomers() {
    return HelperClass.toList(customerRepository.findAll());
  }

  /**
   * Finds the customers with a given state of account (Disabled or not)
   * 
   * @author Wassim Jabbour
   * @param disabled The state of the account (True if disabled, false if not)
   * @return The customers with the corresponding state
   */
  @Transactional
  public List<Customer> getCustomersByAccountState(boolean disabled) {
    List<Customer> customers = customerRepository.findCustomersByDisabled(disabled);
    return customers;
  }


  // CREATE method

  /**
   * Creates a new customer, hashing and salting his password before saving him in the database
   * 
   * @author Wassim Jabbour
   * @param email The customer's email
   * @param username The customer's username
   * @param password The customer's password
   * @return The newly created customer
   */
  @Transactional
  public Customer createCustomer(String email, String username, String password, Address address) {
    
    // Input validation
    String error = "";
    if(email == null || email.trim().length() == 0)
      error += "Customer email cannot be empty! ";
    if(username == null || username.trim().length() == 0)
      error += "Customer username cannot be empty! ";
    if(password == null || password.trim().length() == 0)
      error += "Customer username cannot be empty! ";
    if(address == null)
      error += "Address cannot be null! ";
    if(error.length() > 0)
      throw new IllegalArgumentException(error);
    
    Customer customer = new Customer();
    customer.setEmail(email);
    customer.setUsername(username);
    customer.setSalt(HelperClass.getSalt());
    customer.setPassword(HelperClass.hashAndSaltPassword(password, customer.getSalt()));
    customer.setAddress(address);
    customerRepository.save(customer);
    return customer;
  }


  // DELETE method

  /**
   * Deletes a customer using their email
   * 
   * @author Wassim Jabbour
   * @param email The email of the customer to delete
   */
  @Transactional
  public void deleteCustomer(String email) {
    
    // Input validation
    if(email == null || email.trim().length() == 0)
      throw new IllegalArgumentException("Customer email cannot be empty!");
    
    customerRepository.deleteById(email);
  }


  // OTHER methods
  
  /**
   * This service updates a customer based on the inputs if they are not null.
   * 
   * @author Enzo Benoit-Jeannin
   * 
   * @param username Username (may be null).
   * @param email Email (may not be null).
   * @param address Address (may be null).
   * @param disabled Disabled.
   * @return The updated customer
   */
  @Transactional
  public Customer updateCustomer(String username, String email, Address address, boolean disabled) {

    // Input validation
    String error = "";
    if (email == null || email.trim().length() == 0)
      error += "Customer email cannot be empty! ";
    if (error.length() > 0)
      throw new IllegalArgumentException(error);

    Customer customer = customerRepository.findCustomerByEmail(email);
    if(customer == null)
      throw new IllegalArgumentException("Customer does not exist");

    if(username != null)
      customer.setUsername(username);
    if(address != null)
      customer.setAddress(address);
    customer.setDisabled(disabled);

    customerRepository.save(customer);
    return customer;
  }
  
  /**
   * Service to add a purchase to a customer
   * 
   * @author Enzo Benoit-Jeannin
   * 
   * @param customer Customer to add the purchase to
   * @param purchase Purchase to add
   * @return The cutomer we added the purchase to
   */
  @Transactional
  public Customer addPurchase(Customer customer, Purchase purchase) {
	  if (customer == null)
	      throw new IllegalArgumentException("Customer cannot be null.");

	    if (purchase == null)
	      throw new IllegalArgumentException("Purchase cannot be null.");

	    // Add purchase to customer and save in database
	    customer.getPurchases().add(purchase);
	    return customerRepository.save(customer);
  }

}
