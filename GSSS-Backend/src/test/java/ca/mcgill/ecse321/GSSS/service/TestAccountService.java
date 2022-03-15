package ca.mcgill.ecse321.GSSS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import ca.mcgill.ecse321.GSSS.dao.CustomerRepository;
import ca.mcgill.ecse321.GSSS.dao.EmployeeRepository;
import ca.mcgill.ecse321.GSSS.dao.OwnerRepository;
import ca.mcgill.ecse321.GSSS.model.Account;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Owner;

/**
 * Tests for the account service class
 * 
 * @author Wassim Jabbour
 *
 */
@ExtendWith(MockitoExtension.class)
public class TestAccountService {

  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private EmployeeRepository employeeRepository;

  @Mock
  private OwnerRepository ownerRepository;

  @InjectMocks
  private AccountService accountService;

  @BeforeEach
  public void setMockOutput() {
    lenient().when(employeeRepository.findEmployeeByEmail(anyString()))
        .thenAnswer(MockRepository::findEmployeeByEmail);
    lenient().when(customerRepository.findCustomerByEmail(anyString()))
        .thenAnswer(MockRepository::findCustomerByEmail);
    lenient().when(ownerRepository.findOwnerByEmail(anyString()))
        .thenAnswer(MockRepository::findOwnerByEmail);
  }

  /**
   * Test to make sure the account can't have a null email
   *
   * @author Wassim Jabbour
   *
   */
  @Test
  public void testAuthenticate_EmailNull() {

    try {
      accountService.authenticate(null, "pw");
    } catch (IllegalArgumentException e) {
      assertEquals("Account email cannot be empty! ", e.getMessage());
      return;
    }
    fail();

  }
  /**
   * Test to make sure the account can't have an empty email
   *
   * @author Wassim Jabbour
   *
   */
  @Test
  public void testAuthenticate_EmailEmpty() {

    try {
      accountService.authenticate("   ", "pw");
    } catch (IllegalArgumentException e) {
      assertEquals("Account email cannot be empty! ", e.getMessage());
      return;
    }
    fail();

  }

  /**
   * Test to make sure the account can't have a null password
   *
   * @author Wassim Jabbour
   *
   */
  @Test
  public void testAuthenticate_PasswordNull() {

    try {
      accountService.authenticate("account@email.com", null);
    } catch (IllegalArgumentException e) {
      assertEquals("Account password cannot be empty! ", e.getMessage());
      return;
    }
    fail();

  }

  /**
   * Test to make sure the account can't have an empty password
   *
   * @author Wassim Jabbour
   *
   */
  @Test
  public void testAuthenticate_PasswordEmpty() {

    try {
      accountService.authenticate("account@email.com", "     ");
    } catch (IllegalArgumentException e) {
      assertEquals("Account password cannot be empty! ", e.getMessage());
      return;
    }
    fail();

  }
  /**
   * Test to make sure the account can't have an empty email and password
   *
   * @author Wassim Jabbour
   *
   */
  @Test
  public void testAuthenticate_AllEmpty() {

    try {
      accountService.authenticate(" ", "  ");
    } catch (IllegalArgumentException e) {
      assertEquals("Account email cannot be empty! Account password cannot be empty! ",
          e.getMessage());
      return;
    }
    fail();

  }
  /**
   * Tests that the account with the given email exists
   *
   * @author Wassim Jabbour
   *
   */
  @Test
  public void testAuthenticate_NoAccountMatchesEmail() {

    try {
      accountService.authenticate("NoAccountHasThisEmail@email.com", "pw");
    } catch (NoSuchElementException e) {
      assertEquals("No account with email NoAccountHasThisEmail@email.com exists!", e.getMessage());
      return;
    }
    fail();

  }

  /**
   * Test to make sure the customer has a valid password
   *
   * @author Wassim Jabbour
   *
   */
  @Test
  public void testAuthenticate_CustomerGoodPassword() {
    Account test = accountService.authenticate("customer1@email.com", "password");
    assertEquals(test, MockDatabase.customer1);
  }
  /**
   * Test to make sure the employee has a valid password
   *
   * @author Wassim Jabbour
   *
   */
  @Test
  public void testAuthenticate_EmployeeGoodPassword() {
    Account test = accountService.authenticate("employee1@email.com", "password");
    assertEquals(test, MockDatabase.employee1);
  }

  /**
   * Test to make sure the owner has a valid password
   *
   * @author Wassim Jabbour
   *
   */
  @Test
  public void testAuthenticate_OwnerGoodPassword() {
    Account test = accountService.authenticate("owner1@email.com", "password");
    assertEquals(test, MockDatabase.owner1);
  }
  /**
   * Method to test the case where the customer has a bad password
   *
   * @author Wassim Jabbour
   *
   */
  @Test
  public void testAuthenticate_CustomerBadPassword() {

    try {
      accountService.authenticate("customer1@email.com", "nothepassword");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid password", e.getMessage());
      return;
    }
    fail();

  }

  /**
   * Method to test the case where the employee has a bad password
   *
   * @author Wassim Jabbour
   *
   */
  @Test
  public void testAuthenticate_EmployeeBadPassword() {

    try {
      accountService.authenticate("employee1@email.com", "nothepassword");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid password", e.getMessage());
      return;
    }
    fail();

  }
  /**
   * Method to test the case where the owner has a bad password
   *
   * @author Wassim Jabbour
   *
   */
  @Test
  public void testAuthenticate_OwnerBadPassword() {

    try {
      accountService.authenticate("owner1@email.com", "nothepassword");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid password", e.getMessage());
      return;
    }
    fail();

  }



  /**
   * This class holds all of the mock methods of the CRUD repository.
   *
   * @author Wassim Jabbour
   */
  class MockRepository {

    static Customer findCustomerByEmail(InvocationOnMock invocation) {
      String email = (String) invocation.getArgument(0);
      if (email.equals(MockDatabase.customer1.getEmail()))
        return MockDatabase.customer1;
      return null;
    }

    static Employee findEmployeeByEmail(InvocationOnMock invocation) {
      String email = (String) invocation.getArgument(0);
      if (email.equals(MockDatabase.employee1.getEmail()))
        return MockDatabase.employee1;
      return null;
    }

    static Owner findOwnerByEmail(InvocationOnMock invocation) {
      String email = (String) invocation.getArgument(0);
      if (email.equals(MockDatabase.owner1.getEmail()))
        return MockDatabase.owner1;
      return null;
    }

  }



  /**
   * This class mock data for tests.
   *
   * @author Wassim Jabbour
   */
  final static class MockDatabase {


    static Customer customer1 = new Customer();
    static Employee employee1 = new Employee();
    static Owner owner1 = new Owner();

    static {

      customer1.setEmail("customer1@email.com");
      customer1.setUsername("John Smith");
      customer1.setSalt(Utility.getSalt());
      customer1.setPassword(Utility.hashAndSaltPassword("password", customer1.getSalt()));
      customer1.setDisabled(false);

      employee1.setEmail("employee1@email.com");
      employee1.setUsername("John Smither");
      employee1.setSalt(Utility.getSalt());
      employee1.setPassword(Utility.hashAndSaltPassword("password", employee1.getSalt()));
      employee1.setDisabled(false);

      owner1.setEmail("owner1@email.com");
      owner1.setUsername("John Smithest");
      owner1.setSalt(Utility.getSalt());
      owner1.setPassword(Utility.hashAndSaltPassword("password", owner1.getSalt()));
      owner1.setDisabled(false);

    }

  }
}
