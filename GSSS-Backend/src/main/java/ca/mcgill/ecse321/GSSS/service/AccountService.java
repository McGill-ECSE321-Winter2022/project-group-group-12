package ca.mcgill.ecse321.GSSS.service;

import ca.mcgill.ecse321.GSSS.dao.CustomerRepository;
import ca.mcgill.ecse321.GSSS.dao.EmployeeRepository;
import ca.mcgill.ecse321.GSSS.dao.OwnerRepository;
import ca.mcgill.ecse321.GSSS.model.Account;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * The services of the account class
 *
 * @author Enzo Benoit-Jeannin
 */
@Service
public class AccountService {

  @Autowired CustomerRepository customerRepository;

  @Autowired EmployeeRepository employeeRepository;

  @Autowired OwnerRepository ownerRepository;

  /**
   * Method to authenticate an account
   *
   * @author Enzo Benoit-Jeannin
   * @param email The email of the account
   * @param password The password of the account
   * @return Returns a string indicating the type of account that just was logged in
   */
  @Transactional
  public Account authenticate(String email, String password) {

    // Input validation
    String error = "";
    if (email == null || email.trim().length() == 0) error += "Account email cannot be empty! ";
    if (password == null || password.trim().length() == 0)
      error += "Account password cannot be empty! ";
    if (!(error.length() == 0)) throw new IllegalArgumentException(error);

    Customer customer = customerRepository.findCustomerByEmail(email);
    if (customer != null) {
      if (customer
          .getPassword()
          .equals(Utility.hashAndSaltPassword(password, customer.getSalt()))) {
        return customer;
      } else {
        throw new IllegalArgumentException("Invalid password");
      }
    }

    Employee employee = employeeRepository.findEmployeeByEmail(email);
    if (employee != null) {
      if (employee
          .getPassword()
          .equals(Utility.hashAndSaltPassword(password, employee.getSalt()))) {
        return employee;
      } else {
        throw new IllegalArgumentException("Invalid password");
      }
    }

    Owner owner = ownerRepository.findOwnerByEmail(email);
    if (owner != null) {
      if (owner.getPassword().equals(Utility.hashAndSaltPassword(password, owner.getSalt()))) {
        return owner;
      } else {
        throw new IllegalArgumentException("Invalid password");
      }
    }

    if (customer == null && employee == null && owner == null)
      throw new NoSuchElementException("No account with email " + email + " exists!");

    return null;
  }
}
