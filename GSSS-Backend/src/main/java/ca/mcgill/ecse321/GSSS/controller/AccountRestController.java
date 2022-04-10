package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.model.Account;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Owner;
import ca.mcgill.ecse321.GSSS.service.AccountService;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller methods for the account class
 *
 * @author Philippe Sarouphim Hochar
 */
@CrossOrigin(origins = "*")
@RestController
class AccountRestController {

  @Autowired
  private AccountService accountService;

  /**
   * To log in with an account
   *
   * @param email The email of the account I'm logging in with
   * @param password The input password
   * @throws NoSuchElementException If no account exists with the given credentials
   * @throws IllegalArgumentException If the inputs are invalid
   * @author Philippe Sarouphim Hochar
   */
  @PostMapping(value = {"/account/login", "/account/login/"})
  public ResponseEntity<String> logIn(@RequestParam String email, @RequestParam String password){

	try {
	    Account account = accountService.authenticate(email, password);
	    if (account instanceof Customer)
	        return ResponseEntity.ok("Customer");
	      else if (account instanceof Employee) 
	        return ResponseEntity.ok("Employee");
	      else if (account instanceof Owner) 
	        return ResponseEntity.ok("Owner");
	      return null;

	}catch (Exception e){
		return ResponseEntity.badRequest().body(e.getMessage());
	}
  }
}
