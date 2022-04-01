package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.dto.CustomerDto;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.service.AddressService;
import ca.mcgill.ecse321.GSSS.service.CustomerService;
import ca.mcgill.ecse321.GSSS.service.PurchaseService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
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
   * @return The corresponding customer dto
   * @throws NoSuchElementException, IllegalArgumentException
   * @author Enzo Benoit-Jeannin
   */
  @GetMapping(value = {"/customer/{email}", "/customer/{email}/"})
  public CustomerDto getCustomer(@PathVariable("email") String email)
      throws IllegalArgumentException, NoSuchElementException {

    if (email == null) {
      throw new IllegalArgumentException("There is no such email!");
    }

    CustomerDto customerDto = DtoUtility.convertToDto(customerService.getCustomerByEmail(email));
    return customerDto;
  }

  /**
   * GET method that retrieves the customer dto corresponding to a purchase
   *
   * @param purchaseId The ID of the purchase
   * @return The customer with the passed purchase
   * @throws IllegalArgumentException If the purchase ID is null or empty
   * @throws NoSuchElementException If no purchase exists with the given purchase ID
   */
  @GetMapping(value = {"/customerByPurchase/{purchaseId}", "/customerByPurchase/{purchaseId}/"})
  public CustomerDto getCustomerByPurchase(@PathVariable("purchaseId") String purchaseId)
      throws IllegalArgumentException, NoSuchElementException {

    Purchase purchase = purchaseService.getPurchase(purchaseId);

    CustomerDto customerDto = DtoUtility.convertToDto(
        customerService.getCustomerByPurchase(purchase));

    return customerDto;
  }


  /**
   * Method to create a customer DTO with all parameters of customer
   *
   * @param username  Username of the Customer DTO to create
   * @param email     Email of the Customer DTO to create
   * @param password  Password of the Customer DTO to create
   * @param addressId Address of the Customer DTO to create
   * @return Created customer DTO
   * @throws IllegalArgumentException
   * @author Enzo Benoit-Jeannin
   */
  @PostMapping(value = {"/customer", "/customer/"})
  public CustomerDto createCustomer(@RequestParam(name = "username") String username,
      @RequestParam(name = "email") String email, @RequestParam(name = "password") String password,
      @RequestParam(name = "address") String addressId) throws IllegalArgumentException {
    Address address = addressService.getAddress(addressId);
    return DtoUtility
        .convertToDto(customerService.createCustomer(username, email, password, address));
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
   * @return Customer DTO
   * @author Enzo Benoit-Jeannin
   * @author Enzo Benoit-Jeannin
   * @thorws IllegalArgumentException
   */
  @PostMapping(value = {"/customer/purchase/{email}", "/customer/purhcase/{email}/"})
  public CustomerDto addPurchase(@PathVariable String email,
      @RequestParam(name = "purchase") String purchaseId) throws IllegalArgumentException {
    return DtoUtility.convertToDto(customerService.addPurchase(
        customerService.getCustomerByEmail(email), purchaseService.getPurchase(purchaseId)));
  }

  /**
   * Method to modify/update a customer
   *
   * @param email     Email of the customer to update
   * @param username  Username we want to update
   * @param password  Password we want to update
   * @param addressId Address we want to update
   * @param disabled  Change the disable state of the customer
   * @return The modified customer as a DTO object
   * @throws IllegalArgumentException
   * @author Enzo Benoit-Jeannin
   */
  @PostMapping(value = {"/customer/{email}", "/customer/{email}/"})
  public CustomerDto modifyCustomer(@PathVariable("email") String email,
      @RequestParam(name = "username") String username,
      @RequestParam(name = "address") String addressId,
      @RequestParam(name = "disabled") boolean disabled) throws IllegalArgumentException {
    Address address = addressService.getAddress(addressId);
    Customer customer =
        customerService.modifyCustomer(username, email, address, disabled);
    return DtoUtility.convertToDto(customer);
  }
}
