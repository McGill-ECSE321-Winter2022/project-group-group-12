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
    List<Customer> customers = customerRepository.findCustomersByUsername(username);
    return customers;
  }

  /**
   * Finds the customer with a given purchase
   * 
   * @author Wassim Jabbour
   * @param purchaseId The ID of the purchase to search for
   * @return The customer with the purchase corresponding to that ID
   */
  @Transactional
  public Customer getCustomerByPurchase(String purchaseId) {
    Purchase purchase = purchaseRepository.findPurchaseById(purchaseId);
    Customer customer = customerRepository.findCustomerByPurchases(purchase);
    return customer;
  }

  /**
   * Finds the customer with a given address
   * 
   * @author Wassim Jabbour
   * @param addressId The ID of the address to search for
   * @return The customer with the address corresponding to that ID
   */
  @Transactional
  public Customer getCustomerByAddress(String addressId) {
    Address address = addressRepository.findAddressById(addressId);
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
    customerRepository.deleteById(email);
  }


  // OTHER methods


}
