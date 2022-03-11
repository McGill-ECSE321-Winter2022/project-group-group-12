package ca.mcgill.ecse321.GSSS.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GSSS.dao.CustomerRepository;
import ca.mcgill.ecse321.GSSS.dao.EmployeeRepository;
import ca.mcgill.ecse321.GSSS.dao.OwnerRepository;
import ca.mcgill.ecse321.GSSS.model.Account;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Owner;


@Service
public class AccountService {
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	OwnerRepository ownerRepository;
	
	
	@Transactional
	public Account logIn(String email, String password) {
		
		// Input validation
	    if(email == null || email.trim().length() == 0)
	      throw new IllegalArgumentException("Account email cannot be empty!");
	    
	    Customer customer = customerRepository.findCustomerByEmail(email);
	    if (customer != null) {
	    	if (password.equals(Utility.hashAndSaltPassword(customer.getPassword(), customer.getSalt()))) {
	    		return customer;
	    	}else {
	    		throw new IllegalArgumentException("Invalid password");
	    	}
	    	
	    }
	    
	    Employee employee = employeeRepository.findEmployeeByEmail(email);
	    if (employee != null) {
	    	if (password.equals(Utility.hashAndSaltPassword(employee.getPassword(), employee.getSalt()))) {
	    		return employee;
	    	}else {
	    		throw new IllegalArgumentException("Invalid password");
	    	}
	    }

	    Owner owner = ownerRepository.findOwnerByEmail(email);
	    if(owner != null) {
	    	if (password.equals(Utility.hashAndSaltPassword(owner.getPassword(), owner.getSalt()))) {
	    		return owner;
	    	}else {
	    		throw new IllegalArgumentException("Invalid password");
	    	}
	    }
	    
	    if(customer == null && employee == null && owner == null)
	    	throw new NoSuchElementException("No account with email "+email+" exists!");
	    return null;
	    
	    
	    
	}
}
