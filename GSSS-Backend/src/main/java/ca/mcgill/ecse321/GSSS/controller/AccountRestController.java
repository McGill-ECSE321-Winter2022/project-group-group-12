package ca.mcgill.ecse321.GSSS.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GSSS.model.Account;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.service.AccountService;

@CrossOrigin(origins = "*")
@RestController
class AccountRestController {

  @Autowired
  AccountService accountService;

  @GetMapping(value = {"/account/login", "/account/login/"})
  public String logIn(@RequestParam String email, @RequestParam String password)
      throws NoSuchElementException, IllegalArgumentException {

    Account account = accountService.authenticate(email, password);

    if (account instanceof Customer) {
      return "Customer";
    } else if (account instanceof Employee) {
      return "Employee";
    } else {
      return "Owner";
    }
  }

}
