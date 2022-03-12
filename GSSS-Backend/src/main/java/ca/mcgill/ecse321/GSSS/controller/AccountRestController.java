package ca.mcgill.ecse321.GSSS.controller;

import java.util.NoSuchElementException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
  public void logIn(@RequestParam String email, @RequestParam String password, HttpServletResponse response)
      throws NoSuchElementException, IllegalArgumentException {

    Account account = accountService.authenticate(email, password);

    Cookie cookie = new Cookie("token", JwtUtility.generateJWT(account));

    cookie.setPath("/");
    cookie.setSecure(false);
    cookie.setHttpOnly(true);

    response.addCookie(cookie);
  }

  @GetMapping(value = { "/account/logout", "/account/logout/" })
  public void logOut(HttpServletResponse response){
    Cookie cookie = new Cookie("token", "");

    cookie.setMaxAge(-1);
    cookie.setPath("/");
    cookie.setSecure(false);
    cookie.setHttpOnly(true);

    response.addCookie(cookie);
  }

}
