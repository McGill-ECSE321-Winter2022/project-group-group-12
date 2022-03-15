package ca.mcgill.ecse321.GSSS.service;

import ca.mcgill.ecse321.GSSS.dao.AddressRepository;
import ca.mcgill.ecse321.GSSS.dao.CustomerRepository;
import ca.mcgill.ecse321.GSSS.dao.PurchaseRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Services of the customer class
 *
 * @author Wassim Jabbour
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
   * @param email The email of the customer
   * @return The customer with that email
   * @author Wassim Jabbour
   */
  @Transactional
  public Customer getCustomerByEmail(String email) {

    // Input validation
    if (email == null || email.trim().length() == 0) {
      throw new IllegalArgumentException("Customer email cannot be empty!");
    }

    Customer customer = customerRepository.findCustomerByEmail(email);

    if (customer == null) {
      throw new NoSuchElementException("No customer with email " + email + " exists!");
    }
    return customer;
  }

  /**
   * Finds a list of customers with the given username (Not unique)
   *
   * @param username The username to search for
   * @return The customers with that username
   * @author Wassim Jabbour
   */
  @Transactional
  public List<Customer> getCustomersByUsername(String username) {

    // Input validation
    if (username == null || username.trim().length() == 0) {
      throw new IllegalArgumentException("Customer username cannot be empty!");
    }

    List<Customer> customers = customerRepository.findCustomersByUsername(username);
    if (customers.isEmpty()) {
      throw new NoSuchElementException("No customer with username " + username + " exists!");
    }
    return customers;
  }

  /**
   * Finds the customer with a given purchase
   *
   * @param purchase The purchase to search for
   * @return The customer with that purchase
   * @author Wassim Jabbour
   */
  @Transactional
  public Customer getCustomerByPurchase(Purchase purchase) {

    // Input validation
    if (purchase == null) {
      throw new IllegalArgumentException("Purchase cannot be null!");
    }

    Customer customer = customerRepository.findCustomerByPurchases(purchase);
    if (customer == null) {
      throw new NoSuchElementException("No customer with such purchase exists!");
    }
    return customer;
  }

  /**
   * Finds all customers in the database
   *
   * @return A list of all the customers
   * @author Wassim Jabbour
   */
  @Transactional
  public List<Customer> getAllCustomers() {
    return Utility.toList(customerRepository.findAll());
  }

  /**
   * Finds the customers with a given state of account (Disabled or not)
   *
   * @param disabled The state of the account (True if disabled, false if not)
   * @return The customers with the corresponding state
   * @author Wassim Jabbour
   */
  @Transactional
  public List<Customer> getCustomersByAccountState(boolean disabled) {
    List<Customer> customers = customerRepository.findCustomersByDisabled(disabled);
    // We don't throw an error if no disabled customers is found because it is possible that no
    // customer were banned.
    return customers;
  }

  // CREATE method

  /**
   * Creates a new customer, hashing and salting his password before saving him in the database
   *
   * @param email    The customer's email
   * @param username The customer's username
   * @param password The customer's password
   * @return The newly created customer
   * @author Wassim Jabbour
   */
  @Transactional
  public Customer createCustomer(String username, String email, String password, Address address) {

    // Input validation
    String error = "";
    if (email == null || email.trim().length() == 0) {
      error += "Customer email cannot be empty! ";
    }
    if (!Utility.isEmailValid(email)) {
      error += "Email not valid! ";
    }
    if (username == null || username.trim().length() == 0) {
      error += "Customer username cannot be empty! ";
    }
    if (password == null || password.length() < 6 || password.trim().length() == 0) {
      error += "Password has to be at least 6 characters! ";
    }
    if (address == null) {
      error += "Address cannot be null! ";
    }
    if (error.length() > 0) {
      throw new IllegalArgumentException(error);
    }

    Customer customer = null;
    String error2 = "";
    try {
      customer = getCustomerByEmail(email);
      error2 = "The customer already exists in the system!";
    } catch (NoSuchElementException e) {
      if (e.getMessage().equals("No customer with email " + email + " exists!")) {
        customer = new Customer();
        customer.setEmail(email);
        customer.setUsername(username);
        customer.setSalt(Utility.getSalt());
        customer.setPassword(Utility.hashAndSaltPassword(password, customer.getSalt()));
        customer.setAddress(address);
        customerRepository.save(customer);
      }
    }
    if (error2.length() > 0) {
      throw new NoSuchElementException(error2);
    }

    return customer;
  }

  // DELETE method

  /**
   * Deletes a customer using their email
   *
   * @param email The email of the customer to delete
   * @author Wassim Jabbour
   */
  @Transactional
  public void deleteCustomer(String email) {

    // Input validation
    if (email == null || email.trim().length() == 0) {
      throw new IllegalArgumentException("Customer email cannot be empty!");
    }

    customerRepository.deleteById(email);
  }

  // OTHER methods

  /**
   * This service updates a customer based on the inputs
   *
   * @param username Username.
   * @param email    Email.
   * @param password Password.
   * @param address  Address.
   * @param disabled Disabled.
   * @return The updated customer
   * @author Enzo Benoit-Jeannin
   */
  @Transactional
  public Customer modifyCustomer(
      String username, String password, String email, Address address, boolean disabled) {

    // Input validation
    String error = "";
    if (username == null || username.trim().length() == 0) {
      error += "Customer username cannot be empty! ";
    }
    if (email == null || email.trim().length() == 0) {
      error += "Customer email cannot be empty! ";
    }
    if (password == null || password.length() < 6 || password.trim().length() == 0) {
      error += "Password has to be at least 6 characters! ";
    }
    if (address == null) {
      error += "Customer address cannot be null! ";
    }
    if (error.length() > 0) {
      throw new IllegalArgumentException(error);
    }

    Customer customer = getCustomerByEmail(email);
    customer.setAddress(address);
    customer.setUsername(username);
    customer.setDisabled(disabled);
    customer.setPassword(Utility.hashAndSaltPassword(password, customer.getSalt()));
    customerRepository.save(customer);
    return customer;
  }

  /**
   * Service to add a purchase to a customer
   *
   * @param customer Customer to add the purchase to
   * @param purchase Purchase to add
   * @return The customer we added the purchase to
   * @author Enzo Benoit-Jeannin
   */
  @Transactional
  public Customer addPurchase(Customer customer, Purchase purchase) {
    if (customer == null) {
      throw new IllegalArgumentException("Customer cannot be null");
    }

    if (purchase == null) {
      throw new IllegalArgumentException("Purchase cannot be null");
    }

    // Add purchase to customer and save in database
    customer.getPurchases().add(purchase);
    return customerRepository.save(customer);
  }
}
