package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.dto.CustomerDto;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Owner;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.service.AddressService;
import ca.mcgill.ecse321.GSSS.service.CustomerService;
import ca.mcgill.ecse321.GSSS.service.OwnerService;
import ca.mcgill.ecse321.GSSS.service.PurchaseService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller methods for the customer class
 *
 * @author Enzo Benoit-Jeannin
 * @author Wassim Jabbour
 */
@CrossOrigin(origins = "*")
@RestController
public class CustomerRestController {

  @Autowired
  private CustomerService customerService;

  @Autowired
  private AddressService addressService;

  @Autowired
  private PurchaseService purchaseService;

  @Autowired
  private OwnerService ownerService;

  /**
   * Method to get a list of all Customers in DTO form
   *
   * @return list of all customers DTOs
   * @author Enzo Benoit-Jeannin
   */
  @GetMapping(value = {"/customers", "/customers/"})
  public List<CustomerDto> getAllCustomers() {
    List<CustomerDto> customerDtos = new ArrayList<>();
    for (Customer customer : customerService.getAllCustomers()) {
      customerDtos.add(DtoUtility.convertToDto(customer));
    }
    return customerDtos;
  }

  /**
   * GET method that retrieves the customer dto corresponding to an email
   *
   * @param email The email to search the cutsomer from
   * @return The corresponding customer dto if successful, else return the error
   * @author Enzo Benoit-Jeannin
   */
  @GetMapping(value = {"/customer/{email}", "/customer/{email}/"})
  public ResponseEntity<?> getCustomer(@PathVariable("email") String email){
	 
	try {
	    CustomerDto customerDto = DtoUtility.convertToDto(customerService.getCustomerByEmail(email));
	    return ResponseEntity.ok(customerDto);
	}catch(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
  }

  /**
   * GET method that retrieves the customer dto corresponding to a purchase
   *
   * @param purchaseId The ID of the purchase
   * @return The customer with the passed purchase if successful, else return the error
   */
  @GetMapping(value = {"/customerByPurchase/{purchaseId}", "/customerByPurchase/{purchaseId}/"})
  public ResponseEntity<?> getCustomerByPurchase(@PathVariable("purchaseId") String purchaseId){
	  try {
		  Purchase purchase = purchaseService.getPurchase(purchaseId);

		    CustomerDto customerDto = DtoUtility.convertToDto(
		        customerService.getCustomerByPurchase(purchase));

		    return ResponseEntity.ok(customerDto);
	  }catch(Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());
	  }	
    
  }


  /**
   * Method to create a customer DTO with all parameters of customer
   *
   * @param username  Username of the Customer DTO to create
   * @param email     Email of the Customer DTO to create
   * @param password  Password of the Customer DTO to create
   * @param addressId Address of the Customer DTO to create
   * @return Created customer DTO if successful, else return error
   * @author Enzo Benoit-Jeannin
   */
  @PostMapping(value = {"/customer", "/customer/"})
  public ResponseEntity<?> createCustomer(@RequestParam(name = "username") String username,
      @RequestParam(name = "email") String email, @RequestParam(name = "password") String password,
      @RequestParam(name = "address") String addressId){
	  try {
		  Address address = addressService.getAddress(addressId);
		    return ResponseEntity.ok(DtoUtility.convertToDto(customerService.createCustomer(username, email, password, address)));
	  }catch(Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());

	  }
   
  }

  /**
   * Method to delete a customer based on its email.
   *
   * @param email Email of the customer to delete
   * @author Enzo Benoit-Jeannin
   */
  @DeleteMapping(value = {"/customer/{email}", "/customer/{email}/"})
  public void deleteCustomer(@PathVariable("email") String email) {
    customerService.deleteCustomer(email);
  }

  /**
   * Method to add a purchase to the purchase history of the customer.
   *
   * @param email      Email of the cusotmer to add the purchase to
   * @param purchaseId id of the purchase to add to the customer's purchase history
   * @return Customer DTO if successful, else return error
   * @author Enzo Benoit-Jeannin
   * @author Enzo Benoit-Jeannin
   */
  @PostMapping(value = {"/customer/purchase/{email}", "/customer/purhcase/{email}/"})
  public ResponseEntity<?> addPurchase(@PathVariable String email,
      @RequestParam(name = "purchase") String purchaseId) {
	  try {
	    return ResponseEntity.ok(DtoUtility.convertToDto(customerService.addPurchase(
	        customerService.getCustomerByEmail(email), purchaseService.getPurchase(purchaseId)))); 
	  }catch(Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());

	  }
  }

  /**
   * Method to modify/update a customer
   *
   * @param email     Email of the customer to update
   * @param username  Username we want to update
   * @param addressId Address we want to update
   * @param disabled  Change the disable state of the customer
   * @return The modified customer as a DTO object if successful, else return error
   * @author Enzo Benoit-Jeannin
   */
  @PostMapping(value = {"/customer/{email}", "/customer/{email}/"})
  public ResponseEntity<?> modifyCustomer(@PathVariable("email") String email,
      @RequestParam(name = "username") String username,
      @RequestParam(name = "address") String addressId,
      @RequestParam(name = "disabled") boolean disabled) {
	  try {
	    Address address = addressService.getAddress(addressId);
	    Customer customer =
	        customerService.modifyCustomer(username, email, address, disabled);
	    return ResponseEntity.ok(DtoUtility.convertToDto(customer));
	  }catch(Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());

	  }
  }

  /**
   * Method to modify/update a customer's password
   *
   * @param email    The email of the customer to modify
   * @param password the new password of the customer
   * @return The modified customer as a DTO object if successful, else return error
   * @author Enzo Benoit-Jeannin
   */
  @PostMapping(value = {"/customer/password/{email}", "/customer/password/{email}/"})
  public ResponseEntity<?> modifyPassword(@PathVariable("email") String email,
      @RequestParam(name = "password") String password){
	  
	  try {
	    return ResponseEntity.ok(DtoUtility.convertToDto(
	        customerService.modifyPassword(email, password)));
	  }catch(Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());
	  }
  }

  /**
   * Finds the delivery fee associated with a customer
   *
   * @param email The email of the customer
   * @return The delivery fee if successful, else return error
   */
  @GetMapping(value = {"/deliveryfee/{email}", "/deliveryfee/{email}/"})
  public ResponseEntity<?> returnDeliveryFee(@PathVariable("email") String email){
	  try {
		// Finding the associated customer if it exists (Will be null for an in person purchase)
		    Customer customer = customerService.getCustomerByEmail(email);

		    // Getting the system information (city)
		    Owner owner = ownerService.getOwner();
		    String city = owner.getStoreCity();

		    // Return the out of town fee if the customer is out of town, 0 else
		    if (!city.equals(customer.getAddress().getCity())) {
		      return ResponseEntity.ok(owner.getOutOfTownDeliveryFee());
		    }
		    return ResponseEntity.ok(0);
	  }catch(Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());

	  }
    
  }

}
