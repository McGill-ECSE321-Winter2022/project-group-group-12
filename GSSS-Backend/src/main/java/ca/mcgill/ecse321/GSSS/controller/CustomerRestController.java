package ca.mcgill.ecse321.GSSS.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.GSSS.dto.AddressDto;
import ca.mcgill.ecse321.GSSS.dto.CustomerDto;

import ca.mcgill.ecse321.GSSS.dto.PurchaseDto;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.service.AddressService;
import ca.mcgill.ecse321.GSSS.service.CustomerService;
import ca.mcgill.ecse321.GSSS.service.PurchaseService;

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
	   * @author Enzo Benoit-Jeannin
	   * @return list of all customers DTOs
	   */
	  @GetMapping(value = {"/customers", "/customers/"})
	  public List<CustomerDto> getAllCustomers(){
	    List<CustomerDto> customerDtos = new ArrayList<>();
		for (Customer customer : customerService.getAllCustomers()) {
			customerDtos.add(DtoUtility.convertToDto(customer));
		}
	    return customerDtos;
	  }
	  
	  /**
	   * GET method that retrieves the customer dto corresponding to an email
	   * 
	   * @author Enzo Benoit-Jeannin
	   * @param email The email to search the cutsomer from
	   * @return The corresponding customer dto
	   * @throws NoSuchElementException, IllegalArgumentException
	   */
	  @GetMapping(value = {"/customer/{email}", "/customer/{email}/"})
	  public CustomerDto getCustomer(@PathVariable("email") String email) throws IllegalArgumentException, NoSuchElementException {

	    if (email == null)
	      throw new IllegalArgumentException("There is no such email!");

	    CustomerDto customerDto = DtoUtility.convertToDto(customerService.getCustomerByEmail(email));
	    return customerDto;

	  }
	  
	  /**
	   * Method to create a customer DTO with all parameters of customer
	   * 
	   * @author Enzo Benoit-Jeannin
	   * @param username Username of the Customer DTO to create
	   * @param email Email of the Customer DTO to create
	   * @param password Password of the Customer DTO to create
	   * @param address	Address of the Customer DTO to create
	   * @return Created customer DTO
	   * @throws IllegalArgumentException
	   */
	  @PostMapping(value = {"/customer", "/customer/"})
	  public CustomerDto createCustomer(@RequestParam(name = "username") String username,
	      @RequestParam(name = "email") String email,
	      @RequestParam(name = "password") String password,
	      @RequestParam(name = "address") AddressDto addressDto)
	      throws IllegalArgumentException {
		  Address address = addressService.getAddress(addressDto.getId());
		  return DtoUtility.convertToDto(customerService.createCustomer(username, email, password, address));
	  }
	  
	  /**
	   * Method to delete a customer based on its email.
	   * 
	   * @author Enzo Benoit-Jeannin
	   * @param email Email of the customer to delete
	   */
	  @DeleteMapping(value = {"/customer/{email}", "/customer/{email}/"})
	  public void deleteCustomer(@PathVariable("email") String email) {
		  customerService.deleteCustomer(email);
	  }
	  
	  /**
	   * Method to create a purchase and add it to the purchase history of the customer.
	   * 
	   * @author Enzo Benoit-Jeannin
	   * @param email Email of the cusotmer to add the purchase to 
	   * @param purchaseDto Purchase Dto to use to create the Purchase object
	   * @return Customer DTO 
	   * @thorws IllegalArgumentException
	   */
	  @PostMapping(value = {"/customer/purchase/{email}", "/customer/purhcase/{email}/"})
	  public CustomerDto addPurchase(@PathVariable String email, @RequestBody PurchaseDto purchaseDto) throws IllegalArgumentException {
		  Purchase purchase = purchaseService.createPurchase(purchaseDto.getOrderType(), DtoUtility.convertToDomainObject(purchaseDto.getEmployee()), purchaseDto.getOrderStatus(), DtoUtility.convertItemMapDto(purchaseDto.getItems()));
		  return DtoUtility.convertToDto(customerService.addPurchase(customerService.getCustomerByEmail(email), purchase));
	  }
	 
	  /**
	   * Method to modify/update a customer
	   * 
	   * @param email Email of the customer to update
	   * @param username Username we want to update
	   * @param password Password we want to update
	   * @param addressDto Address we want to update 
	   * @param disabled Change the disable state of the customer
	   * @return The modified customer as a DTO object
	   * @throws IllegalArgumentException
	   */
	  @PostMapping(value = {"/customer/{email}", "/cusotmer/{email}/"})
	  public CustomerDto modifyCustomer(@PathVariable("email") String email,
	      @RequestParam(name = "username") String username,
	      @RequestParam(name = "password") String password,
	      @RequestParam(name = "address") AddressDto addressDto,
	      @RequestParam(name = "disabled") boolean disabled)
	      throws IllegalArgumentException {
		  Address address = addressService.getAddress(addressDto.getId());
		  Customer customer = customerService.modifyCustomer(username, email, password, address, disabled);
	    return DtoUtility.convertToDto(customer);
	  }
}
