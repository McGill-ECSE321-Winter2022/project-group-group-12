package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.model.Account;
import ca.mcgill.ecse321.GSSS.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

/**
 * Controller methods for the account class
 *
 * @author Philippe Sarouphim Hochar
 */
@CrossOrigin(origins = "*")
@RestController
class AccountRestController {

  @Autowired private AccountService accountService;

  /**
   * To log in with an account
   *
   * @author Philippe Sarouphim Hochar
   * @param email The email of the account I'm logging in with
   * @param password The input password
   * @param response HTTP servlet response for the cookies
   * @throws NoSuchElementException If no account exists with the given credentials
   * @throws IllegalArgumentException If the inputs are invalid
   */
  @PostMapping(value = {"/account/login", "/account/login/"})
  public void logIn(
      @RequestParam() String email, @RequestParam String password, HttpServletResponse response)
      throws NoSuchElementException, IllegalArgumentException {

    Account account = accountService.authenticate(email, password);

    Cookie cookie = new Cookie("token", account.getEmail());

    cookie.setPath("/");
    cookie.setSecure(false);
    cookie.setHttpOnly(true);

    response.addCookie(cookie);
  }

  /**
   * To log out of a session
   *
   * @author Philippe Sarouphim Hochar
   * @param response The HTTP servlet response to remove the cookies
   */
  @GetMapping(value = {"/account/logout", "/account/logout/"})
  public void logOut(HttpServletResponse response) {
    Cookie cookie = new Cookie("token", "");

    cookie.setMaxAge(0);
    cookie.setPath("/");
    cookie.setSecure(false);
    cookie.setHttpOnly(true);

    response.addCookie(cookie);
  }
}
