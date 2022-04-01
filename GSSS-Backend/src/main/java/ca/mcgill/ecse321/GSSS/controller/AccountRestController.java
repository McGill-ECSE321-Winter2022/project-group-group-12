package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.model.Account;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Owner;
import ca.mcgill.ecse321.GSSS.service.AccountService;
import java.util.NoSuchElementException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
   * @param response HTTP servlet response for the cookies
   * @throws NoSuchElementException If no account exists with the given credentials
   * @throws IllegalArgumentException If the inputs are invalid
   * @author Philippe Sarouphim Hochar
   */
  @PostMapping(value = {"/account/login", "/account/login/"})
  public String logIn(@RequestParam() String email, @RequestParam String password) throws NoSuchElementException, IllegalArgumentException {

    Account account = accountService.authenticate(email, password);

    Cookie cookie = new Cookie("token", account.getEmail());

    // Creating the cookie and setting it
    cookie.setPath("/");
    cookie.setSecure(false);
    cookie.setHttpOnly(true);
    
    if (account instanceof Customer)
      return "Customer";
    else if (account instanceof Employee) 
      return "Employee";
    else if (account instanceof Owner) 
      return "Owner";
    
    return null;

  }

  /**
   * To log out of a session
   *
   * @param response The HTTP servlet response to remove the cookies
   * @author Philippe Sarouphim Hochar
   */
  @GetMapping(value = {"/account/logout", "/account/logout/"})
  public void logOut(HttpServletResponse response) {
    Cookie cookie = new Cookie("token", "");

    // Removing the cookie
    cookie.setMaxAge(0);
    cookie.setPath("/");
    cookie.setSecure(false);
    cookie.setHttpOnly(true);

    response.addCookie(cookie);
  }
}
