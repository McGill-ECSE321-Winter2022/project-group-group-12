package ca.mcgill.ecse321.GSSS.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GSSS.dto.BusinessHourDto;
import ca.mcgill.ecse321.GSSS.dto.CustomerDto;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Weekday;
import ca.mcgill.ecse321.GSSS.service.CustomerService;

@CrossOrigin(origins = "*")
@RestController
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;
	
	/**
	   * Method to get a list of all Customers in DTO form
	   * 
	   * @author Enzo Benoit-Jeannin
	   * @return list of all customers DTOs
	   */
	  @GetMapping(value = {"/customers", "/customers/"})
	  public List<CustomerDto> getAllCustomers() throws IllegalArgumentException {
	    List<CustomerDto> customerDtos = new ArrayList<>();
		for (Customer customer : customerService.getAllCustomers()) {
			customerDtos.add(DtoConversion.convertToDto(customer));
		}
	    return customerDtos;
	  }
	  
	  /**
	   * GET method that retrieves the customer dto corresponding to an email
	   * 
	   * @author Enzo Benoit-Jeannin
	   * @param email The email to search the cutsomer from
	   * @return The corresponding customer dto
	   */
	  @GetMapping(value = {"/customer/{email}", "/customer/{email}/"})
	  public CustomerDto getCustomer(@PathVariable("email") String email) {

	    if (email == null)
	      throw new IllegalArgumentException("There is no such email!");

	    CustomerDto customerDto = DtoConversion.convertToDto(customerService.getCustomer(email));
	    return customerDto;

	  }
	  
	  
}
