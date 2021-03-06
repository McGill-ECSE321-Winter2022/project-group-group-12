package ca.mcgill.ecse321.GSSS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.mcgill.ecse321.GSSS.dao.EmployeeRepository;
import ca.mcgill.ecse321.GSSS.dao.ShiftRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Shift;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests for the employee services
 *
 * @author Philippe Sarouphim Hochar
 */
@ExtendWith(MockitoExtension.class)
public class TestEmployeeService {

  @Mock
  private EmployeeRepository employeeRepository;

  @Mock
  private ShiftRepository shiftRepository;

  @InjectMocks
  private EmployeeService employeeService;

  @BeforeEach
  public void setMockOutput() {
    // Set each CRUD method to its mock
    lenient()
        .when(employeeRepository.findEmployeeByEmail(anyString()))
        .thenAnswer(MockEmployeeRepository::findEmployeeByEmail);
    lenient()
        .when(employeeRepository.findEmployeesByUsername(anyString()))
        .thenAnswer(MockEmployeeRepository::findEmployeeByUsername);
    lenient()
        .when(employeeRepository.findEmployeeByShifts(any(Shift.class)))
        .thenAnswer(MockEmployeeRepository::findEmployeeByShifts);
    lenient()
        .when(employeeRepository.findEmployeeByAddress(any(Address.class)))
        .thenAnswer(MockEmployeeRepository::findEmployeeByAddress);
    lenient()
        .when(employeeRepository.findEmployeesByDisabled(anyBoolean()))
        .thenAnswer(MockEmployeeRepository::findEmployeesByDisabled);
    lenient().when(employeeRepository.findAll()).thenAnswer(MockEmployeeRepository::findAll);
    lenient()
        .when(employeeRepository.save(any(Employee.class)))
        .thenAnswer(MockEmployeeRepository::save);

    lenient().when(shiftRepository.findAll()).thenAnswer(MockShiftRepository::findAll);
  }

  /**
   * method test to check that an employee is created successfully
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testCreateEmployee_Success() {
    Employee employee = new Employee();
    employee.setEmail("employee@email.com");
    employee.setUsername("John Smith");
    employee.setPassword("w34yfr1uy45324u");
    employee.setAddress(new Address());
    Employee created =
        employeeService.createEmployee(
            employee.getUsername(),
            employee.getEmail(),
            employee.getPassword(),
            employee.getAddress());
    assertNotNull(created);
    assertEquals(employee.getEmail(), created.getEmail());
    assertEquals(employee.getUsername(), created.getUsername());
    assertEquals(employee.getAddress(), created.getAddress());
  }

  /**
   * Method test to check that an error is thrown if we create an employee which already exists in
   * the dataBase
   *
   * @author Theo Ghanem
   */
  @Test
  public void testCreateEmployee_AlreadyInDb() {
    Employee employee = new Employee();
    employee.setEmail(MockDatabase.employee2.getEmail());
    employee.setUsername("John Smith");
    employee.setPassword("w34yfr1uy45324u");
    employee.setAddress(new Address());

    try {
      employeeService.createEmployee(
          employee.getUsername(),
          employee.getEmail(),
          employee.getPassword(),
          employee.getAddress());
    } catch (NoSuchElementException e) {
      assertEquals("The employee already exists in the system!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to create an employee with a null
   * email
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testCreateEmployee_NullEmail() {
    try {
      employeeService.createEmployee("username", null, "password", new Address());
    } catch (IllegalArgumentException e) {
      assertEquals("Employee email cannot be empty! Email not valid! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to create an employee with an empty
   * email or email that consists only of space
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testCreateEmployee_EmptyEmail() {
    try {
      employeeService.createEmployee("username", "    ", "password", new Address());
    } catch (IllegalArgumentException e) {
      assertEquals("Employee email cannot be empty! Email not valid! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to create an employee with an email
   * that is invalid (doesn't contain @)
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testCreateEmployee_InvalidEmail() {
    try {
      employeeService.createEmployee("username", "invalidemail", "password", new Address());
    } catch (IllegalArgumentException e) {
      assertEquals("Email not valid! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to create an employee with a null
   * username
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testCreateEmployee_NullUsername() {
    try {
      employeeService.createEmployee(null, "employee@email.com", "password", new Address());
    } catch (IllegalArgumentException e) {
      assertEquals("Employee username cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to create an employee with an empty
   * username or username that consists only of space
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testCreateEmployee_EmptyUsername() {
    try {
      employeeService.createEmployee("   ", "employee@email.com", "password", new Address());
    } catch (IllegalArgumentException e) {
      assertEquals("Employee username cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to create an employee with a null
   * password
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testCreateEmployee_NullPassword() {
    try {
      employeeService.createEmployee("username", "employee@email.com", null, new Address());
    } catch (IllegalArgumentException e) {
      assertEquals("Password has to be at least 6 characters! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to create an employee with an empty
   * password or password that consits only of space
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testCreateEmployee_EmptyPassword() {
    try {
      employeeService.createEmployee("username", "employee@email.com", "  ", new Address());
    } catch (IllegalArgumentException e) {
      assertEquals("Password has to be at least 6 characters! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to create an employee with a password
   * that is less than 6 characters
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testCreateEmployee_TooShortPassword() {
    try {
      employeeService.createEmployee("username", "employee@email.com", "12345", new Address());
    } catch (IllegalArgumentException e) {
      assertEquals("Password has to be at least 6 characters! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to create an employee with no address
   * or null address
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testCreateEmployee_NullAddress() {
    try {
      employeeService.createEmployee("username", "employee@email.com", "password", null);
    } catch (IllegalArgumentException e) {
      assertEquals("Address cannot be null! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to create an employee with everything
   * null
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testCreateEmployee_NullAll() {
    try {
      employeeService.createEmployee(null, null, null, null);
    } catch (IllegalArgumentException e) {
      assertEquals(
          "Employee email cannot be empty! Email not valid! Employee username cannot be empty! Password has to be at least 6 characters! Address cannot be null! ",
          e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an employee is modified successfully
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testModifyEmployee_Success() {
    Employee modified =
        employeeService.modifyEmployee(
            "new username",
            MockDatabase.employeem.getEmail(),
            MockDatabase.address3,
            true);
    assertNotNull(modified);
    assertEquals(MockDatabase.employeem.getEmail(), modified.getEmail());
    assertEquals("new username", modified.getUsername());
    assertEquals(MockDatabase.address3, modified.getAddress());
    assertEquals(true, modified.isDisabled());
    assertEquals(MockDatabase.employeem.getPassword(), modified.getPassword());
  }

  /**
   * method test to check that an error is thrown when we try to modify an employee with a null
   * email
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testModifyEmployee_NullEmail() {
    try {
      employeeService.modifyEmployee("username", null, new Address(), false);
    } catch (IllegalArgumentException e) {
      assertEquals("Employee email cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to modify an employee with an empty
   * email
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testModifyEmployee_EmptyEmail() {
    try {
      employeeService.modifyEmployee("username", "    ", new Address(), false);
    } catch (IllegalArgumentException e) {
      assertEquals("Employee email cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to modify an employee with a null
   * username
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testModifyEmployee_NullUsername() {
    try {
      employeeService.modifyEmployee(
          null, MockDatabase.employeem.getEmail(), new Address(), false);
    } catch (IllegalArgumentException e) {
      assertEquals("Employee username cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to modify an employee with an empty
   * username
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testModifyEmployee_EmptyUsername() {
    try {
      employeeService.modifyEmployee(
          "   ", MockDatabase.employeem.getEmail(), new Address(), false);
    } catch (IllegalArgumentException e) {
      assertEquals("Employee username cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to modify an employee with a null
   * address or no address
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testModifyEmployee_NullAddress() {
    try {
      employeeService.modifyEmployee(
          "username", MockDatabase.employeem.getEmail(), null, false);
    } catch (IllegalArgumentException e) {
      assertEquals("Address cannot be null! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to modify an employee that isn't in
   * the database
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testModifyEmployee_NotInDb() {
    try {
      employeeService.modifyEmployee(
          "username", "not_registered@email.com", new Address(), false);
    } catch (NoSuchElementException e) {
      assertEquals("Employee does not exist", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Method to test that an employee's password is updated correctly
   * 
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyPassword_Success(){
    Employee modified = employeeService.modifyPassword(MockDatabase.employeem.getEmail(), "new password" );
    assertNotNull(modified);
    assertEquals(MockDatabase.employeem.getEmail(), modified.getEmail());
    assertEquals(MockDatabase.employeem.getUsername(), modified.getUsername());
    assertEquals(MockDatabase.address_m, modified.getAddress());
    assertEquals(MockDatabase.employeem.isDisabled(), modified.isDisabled());
    assertEquals(MockDatabase.employeem.getPassword(), modified.getPassword());
  }

  /**
   * method test to check that an error is thrown when we try to modify an employee with a null
   * password
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyPassword_NullPassword() {
    try {
      employeeService.modifyPassword(MockDatabase.employeem.getEmail(), null);
    } catch (IllegalArgumentException e) {
      assertEquals("Password has to be at least 6 characters! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to modify an employee with an empty
   * password
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyPassword_EmptyPassword() {
    try {
      employeeService.modifyPassword(MockDatabase.employeem.getEmail(), "   ");
    } catch (IllegalArgumentException e) {
      assertEquals("Password has to be at least 6 characters! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to modify an employee with a password
   * that is too short
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyPassword_TooShortPassword() {
    try {
      employeeService.modifyPassword(MockDatabase.employeem.getEmail(), "short");
    } catch (IllegalArgumentException e) {
      assertEquals("Password has to be at least 6 characters! ", e.getMessage());
      return;
    }
    fail();
  }

/**
   * method test to check that an error is thrown when we try to modify an employee with a null
   * password
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyPassword_NullEmail() {
    try {
      employeeService.modifyPassword(null, "new password");
    } catch (IllegalArgumentException e) {
      assertEquals("Employee email cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to modify an employee with an empty
   * password
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyPassword_EmptyEmail() {
    try {
      employeeService.modifyPassword("  ", "new password");
    } catch (IllegalArgumentException e) {
      assertEquals("Employee email cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to modify an employee with a password
   * that is too short
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyPassword_wrongEmail() {
    try {
      employeeService.modifyPassword("wrongemail@gmail.fr", "new passwoord");
    } catch (NoSuchElementException e) {
      assertEquals("No employee with email wrongemail@gmail.fr exists!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an employee is deleted successfully
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testDeleteEmployee_Success() {
    employeeService.deleteEmployee("employee1@email.com");

    verify(employeeRepository, times(1))
        .deleteById(argThat((String i) -> MockDatabase.employee1.getEmail().equals(i)));
    verify(employeeRepository, times(0))
        .deleteById(argThat((String i) -> !MockDatabase.employee1.getEmail().equals(i)));
  }

  /**
   * method test to check that an error is thrown when we call deleteEmployee with a null email
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testDeleteEmployee_NullEmail() {
    try {
      employeeService.deleteEmployee(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Employee email cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we call deleteEmployee with an empty email
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testDeleteEmployee_EmptyEmail() {
    try {
      employeeService.deleteEmployee("  ");
    } catch (IllegalArgumentException e) {
      assertEquals("Employee email cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an employee is fetched by their email successfully
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testGetEmployeeByEmail_Success() {
    Employee fetched = employeeService.getEmployeeByEmail(MockDatabase.employee2.getEmail());
    assertNotNull(fetched);
    assertEquals(MockDatabase.employee2, fetched);
  }

  /**
   * method test to check that an error is thrown when we call getEmployeeByEmail with a null email
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testGetEmployeeByEmail_NullEmail() {
    try {
      employeeService.getEmployeeByEmail(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Employee email cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we call getEmployeeByEmail with a empty
   * email
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testGetEmployeeByEmail_EmptyEmail() {
    try {
      employeeService.getEmployeeByEmail("   ");
    } catch (IllegalArgumentException e) {
      assertEquals("Employee email cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to fetch an employee that isnt in the
   * database
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testGetEmployeeByEmail_NotInDb() {
    try {
      employeeService.getEmployeeByEmail("not_a_registered_employee@email.com");
    } catch (NoSuchElementException e) {
      assertEquals(
          "No employee with email not_a_registered_employee@email.com exists!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that employees with a same username are fetches successfully by username
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testGetEmployeesByUsername_Success() {
    List<Employee> fetched = employeeService.getEmployeesByUsername("John Smith");
    List<Employee> expected = new ArrayList<Employee>();
    expected.add(MockDatabase.employee1);
    expected.add(MockDatabase.employee3);
    assertNotNull(fetched);
    assertEquals(expected.size(), fetched.size());
    for (Employee e : fetched) {
      assertTrue(expected.contains(e));
    }
  }

  /**
   * method test to check that an error is thrown when we call getEmployeesByUsername with a null
   * username
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testGetEmployeesByUsername_NullUsername() {
    try {
      employeeService.getEmployeesByUsername(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Employee username cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we call getEmployeesByUsername with an empty
   * username
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testGetEmployeesByUsername_EmptyUsername() {
    try {
      employeeService.getEmployeesByUsername("     ");
    } catch (IllegalArgumentException e) {
      assertEquals("Employee username cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to fetch employees with a username
   * that does not exist
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testGetEmployeeByUsername_NotInDb() {
    try {
      employeeService.getEmployeesByUsername("John Not Registered");
    } catch (NoSuchElementException e) {
      assertEquals("No employee with username John Not Registered exists!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an employee is fetched successfully by their shift
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testGetEmployeeByShift_Success() {
    Employee fetched = employeeService.getEmployeeByShift(MockDatabase.shift3);
    assertNotNull(fetched);
    assertEquals(MockDatabase.employee3, fetched);
  }

  /**
   * method test to check that an error is thrown when we try to fetch employee with a null shift
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testGetEmployeeByShift_NullShift() {
    try {
      employeeService.getEmployeeByShift(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Shift cannot be null!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to fetch an employee with a shift not
   * assigned to any employee
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testGetEmployeeByShift_NotInDb() {
    Shift shift = new Shift();
    shift.setId(UUID.randomUUID().toString());
    shift.setDate(Date.valueOf("2017-03-03"));
    shift.setStartTime(Time.valueOf("08:00:00"));
    shift.setEndTime(Time.valueOf("02:00:00"));
    try {
      employeeService.getEmployeeByShift(shift);
    } catch (NoSuchElementException e) {
      assertEquals("No employee with the given shift exists!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that employees are sucessfully fetched by a specific account state
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testGetEmployeeByAccountState_Success() {
    List<Employee> fetched = employeeService.getEmployeesByAccountState(false);
    List<Employee> expected = new ArrayList<Employee>();
    expected.add(MockDatabase.employee1);
    expected.add(MockDatabase.employee2);
    assertNotNull(fetched);
    assertEquals(expected.size(), fetched.size());
    for (Employee e : fetched) {
      assertTrue(expected.contains(e));
    }
  }

  /**
   * method test to check that all employees are fetched successfully
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testGetAllEmployees_Success() {
    List<Employee> fetched = employeeService.getAllEmployees();
    List<Employee> expected = new ArrayList<Employee>();
    expected.add(MockDatabase.employee1);
    expected.add(MockDatabase.employee2);
    expected.add(MockDatabase.employee3);
    expected.add(MockDatabase.employeem);
    assertNotNull(fetched);
    assertEquals(expected.size(), fetched.size());
    for (Employee e : fetched) {
      assertTrue(expected.contains(e));
    }
  }

  /**
   * method test to check that a shift is added successfully to an employee
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testAddShift_Success() {
    Shift shift = new Shift();
    shift.setId(UUID.randomUUID().toString());
    shift.setDate(Date.valueOf("2022-03-08"));
    shift.setStartTime(Time.valueOf("02:00:00"));
    shift.setEndTime(Time.valueOf("21:00:00"));

    Employee employee = employeeService.addShift(MockDatabase.employeem, shift);

    assertNotNull(employee);
    assertTrue(employee.getShifts().contains(shift));
  }

  /**
   * method test to check that an error is thrown when we try to add a shift to a null employee
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testAddShift_NullEmployee() {
    Shift shift = new Shift();
    shift.setId(UUID.randomUUID().toString());
    shift.setDate(Date.valueOf("2022-03-08"));
    shift.setStartTime(Time.valueOf("02:00:00"));
    shift.setEndTime(Time.valueOf("21:00:00"));
    try {
      employeeService.addShift(null, shift);
    } catch (IllegalArgumentException e) {
      assertEquals("Employee cannot be null.", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to add a null shift to an employee
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testAddShift_NullShift() {
    try {
      employeeService.addShift(MockDatabase.employeem, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Shift cannot be null.", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that a shift is removed successfully from an employee
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testRemoveShift_Success() {
    Employee employee = employeeService.removeShift(MockDatabase.employeem, MockDatabase.shift_m);

    assertNotNull(employee);
    assertFalse(employee.getShifts().contains(MockDatabase.shift_m));
  }

  /**
   * method test to check that an error is thrown when we try to remove a shift from a null
   * employee
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testRemoveShift_NullEmployee() {
    try {
      employeeService.removeShift(null, MockDatabase.shift_m);
    } catch (IllegalArgumentException e) {
      assertEquals("Employee cannot be null.", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an error is thrown when we try to remove a null shift from an
   * employee
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testRemoveShift_NullShift() {
    try {
      employeeService.removeShift(MockDatabase.employeem, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Shift cannot be null.", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * method test to check that an employee with the closest shift (set in the present day) is
   * fetched successfully successfully
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testGetClosestEmployee_SuccessToday() {
    MockDatabase.shift_m.setDate(new Date(System.currentTimeMillis()));
    MockDatabase.shift_m.setEndTime(new Time(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
    MockDatabase.shifts_m.clear();
    MockDatabase.shifts_m.add(MockDatabase.shift_m);
    MockDatabase.employeem.setShifts(MockDatabase.shifts_m);
    Employee closest = employeeService.getClosestEmployee();
    assertNotNull(closest);
    assertEquals(MockDatabase.employeem, closest);
  }

  /**
   * method test to check that an employee with the closest shift (set in the future) is fetched
   * successfully successfully
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testGetClosestEmployee_SuccessFuture() {
    MockDatabase.shift_m.setDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
    MockDatabase.shift_m.setEndTime(Time.valueOf("23:59:59"));
    MockDatabase.shifts_m.clear();
    MockDatabase.shifts_m.add(MockDatabase.shift_m);
    MockDatabase.employeem.setShifts(MockDatabase.shifts_m);
    Employee closest = employeeService.getClosestEmployee();
    assertNotNull(closest);
    assertEquals(MockDatabase.employeem, closest);
  }

  /**
   * method to check that the employee with the latest shift is fetched although that shift is set
   * in the past
   *
   * @author Philippe Sarouphim Hochar
   */
  @Test
  public void testGetClosestEmployee_Failure() {
    MockDatabase.shift_m.setDate(new Date(0));
    MockDatabase.shifts_m.clear();
    MockDatabase.shifts_m.add(MockDatabase.shift_m);
    MockDatabase.employeem.setShifts(MockDatabase.shifts_m);
    Employee closest = employeeService.getClosestEmployee();
    assertNotNull(closest);
    assertEquals(MockDatabase.employee1, closest);
  }

  /**
   * This class holds all of the mock methods of the employee CRUD repository.
   */
  class MockEmployeeRepository {

    static Employee findEmployeeByEmail(InvocationOnMock invocation) {
      String email = (String) invocation.getArgument(0);
      if (email.equals(MockDatabase.employee1.getEmail())) {
        return MockDatabase.employee1;
      }
      if (email.equals(MockDatabase.employee2.getEmail())) {
        return MockDatabase.employee2;
      }
      if (email.equals(MockDatabase.employee3.getEmail())) {
        return MockDatabase.employee3;
      }
      if (email.equals(MockDatabase.employeem.getEmail())) {
        return MockDatabase.employeem;
      }
      return null;
    }

    static List<Employee> findEmployeeByUsername(InvocationOnMock invocation) {
      String username = (String) invocation.getArgument(0);
      List<Employee> employees = new ArrayList<Employee>();
      if (username.equals(MockDatabase.employee1.getUsername())) {
        employees.add(MockDatabase.employee1);
      }
      if (username.equals(MockDatabase.employee2.getUsername())) {
        employees.add(MockDatabase.employee2);
      }
      if (username.equals(MockDatabase.employee3.getUsername())) {
        employees.add(MockDatabase.employee3);
      }
      if (username.equals(MockDatabase.employeem.getUsername())) {
        employees.add(MockDatabase.employeem);
      }
      return employees;
    }

    static Employee findEmployeeByShifts(InvocationOnMock invocation) {
      Shift shift = (Shift) invocation.getArgument(0);
      if (MockDatabase.employee1.getShifts().contains(shift)) {
        return MockDatabase.employee1;
      }
      if (MockDatabase.employee2.getShifts().contains(shift)) {
        return MockDatabase.employee2;
      }
      if (MockDatabase.employee3.getShifts().contains(shift)) {
        return MockDatabase.employee3;
      }
      if (MockDatabase.employeem.getShifts().contains(shift)) {
        return MockDatabase.employeem;
      }
      return null;
    }

    static Employee findEmployeeByAddress(InvocationOnMock invocation) {
      Address address = (Address) invocation.getArgument(0);
      if (address.equals(MockDatabase.employee1.getAddress())) {
        return MockDatabase.employee1;
      }
      if (address.equals(MockDatabase.employee2.getAddress())) {
        return MockDatabase.employee2;
      }
      if (address.equals(MockDatabase.employee3.getAddress())) {
        return MockDatabase.employee3;
      }
      if (address.equals(MockDatabase.employeem.getAddress())) {
        return MockDatabase.employeem;
      }
      return null;
    }

    static List<Employee> findEmployeesByDisabled(InvocationOnMock invocation) {
      boolean disabled = (boolean) invocation.getArgument(0);
      List<Employee> employees = new ArrayList<Employee>();
      if (disabled == MockDatabase.employee1.isDisabled()) {
        employees.add(MockDatabase.employee1);
      }
      if (disabled == MockDatabase.employee2.isDisabled()) {
        employees.add(MockDatabase.employee2);
      }
      if (disabled == MockDatabase.employee3.isDisabled()) {
        employees.add(MockDatabase.employee3);
      }
      if (disabled == MockDatabase.employeem.isDisabled()) {
        employees.add(MockDatabase.employeem);
      }
      return employees;
    }

    static Employee save(InvocationOnMock invocation) {
      return (Employee) invocation.getArgument(0);
    }

    static List<Employee> findAll(InvocationOnMock invocation) {
      List<Employee> employees = new ArrayList<Employee>();
      employees.add(MockDatabase.employee1);
      employees.add(MockDatabase.employee2);
      employees.add(MockDatabase.employee3);
      employees.add(MockDatabase.employeem);
      return employees;
    }
  }

  /**
   * This class holds all of the mock methods of the shift CRUD repository.
   */
  class MockShiftRepository {

    static List<Shift> findAll(InvocationOnMock invocation) {
      List<Shift> shifts = new ArrayList<Shift>();
      shifts.add(MockDatabase.shift1);
      shifts.add(MockDatabase.shift2);
      shifts.add(MockDatabase.shift3);
      shifts.add(MockDatabase.shift_m);
      return shifts;
    }
  }

  /**
   * This class mock data for tests.
   */
  static final class MockDatabase {

    static Employee employee1 = new Employee();
    static Address address1 = new Address();
    static Set<Shift> shifts1 = new HashSet<Shift>();
    static Shift shift1 = new Shift();

    static Employee employee2 = new Employee();
    static Address address2 = new Address();
    static Set<Shift> shifts2 = new HashSet<Shift>();
    static Shift shift2 = new Shift();

    static Employee employee3 = new Employee();
    static Address address3 = new Address();
    static Set<Shift> shifts3 = new HashSet<Shift>();
    static Shift shift3 = new Shift();

    static Employee employeem = new Employee();
    static Address address_m = new Address();
    static Set<Shift> shifts_m = new HashSet<Shift>();
    static Shift shift_m = new Shift();

    static {
      employee1.setEmail("employee1@email.com");
      employee1.setUsername("John Smith");
      employee1.setPassword("w34yfr1uy45324u");
      employee1.setSalt(Utility.getSalt());
      employee1.setDisabled(false);

      address1.setFullName("John Smith");
      address1.setId(UUID.randomUUID().toString());
      address1.setStreetName("Street st");
      address1.setStreetNumber(1000);
      address1.setCity("Montreal");
      address1.setPostalCode("A1A 2B2");
      employee1.setAddress(address1);

      shift1.setId(UUID.randomUUID().toString());
      shift1.setDate(Date.valueOf("2022-03-08"));
      shift1.setStartTime(Time.valueOf("09:30:00"));
      shift1.setEndTime(Time.valueOf("17:00:00"));
      shifts1.add(shift1);
      employee1.setShifts(shifts1);

      employee2.setEmail("employee2@email.com");
      employee2.setUsername("John Doe");
      employee2.setPassword("dsagfdsa54g23dasg2d");
      employee2.setSalt(Utility.getSalt());
      employee2.setDisabled(false);

      address2.setFullName("John Doe");
      address2.setId(UUID.randomUUID().toString());
      address2.setStreetName("Drive st");
      address2.setStreetNumber(2000);
      address2.setCity("Montreal");
      address2.setPostalCode("A2A 1B1");
      employee2.setAddress(address2);

      shift2.setId(UUID.randomUUID().toString());
      shift2.setDate(Date.valueOf("2022-03-08"));
      shift2.setStartTime(Time.valueOf("17:00:00"));
      shift2.setEndTime(Time.valueOf("02:30:00"));
      shifts2.add(shift2);
      employee2.setShifts(shifts2);

      employee3.setEmail("employee3@email.com");
      employee3.setUsername("John Smith");
      employee3.setPassword("g43f2h14dg24dh");
      employee3.setSalt(Utility.getSalt());
      employee3.setDisabled(true);

      address3.setFullName("John Smith");
      address3.setId(UUID.randomUUID().toString());
      address3.setStreetName("Avenue st");
      address3.setStreetNumber(3000);
      address3.setCity("Toronto");
      address3.setPostalCode("B1B 2A2");
      employee3.setAddress(address3);

      shift3.setId(UUID.randomUUID().toString());
      shift3.setDate(Date.valueOf("2022-03-08"));
      shift3.setStartTime(Time.valueOf("17:00:00"));
      shift3.setEndTime(Time.valueOf("02:30:00"));
      shifts3.add(shift3);
      employee3.setShifts(shifts3);

      employeem.setEmail("employeem@email.com");
      employeem.setUsername("John Modifiable");
      employeem.setPassword("g43f2h14dg24dh");
      employeem.setSalt(Utility.getSalt());
      employeem.setDisabled(true);

      address_m.setFullName("John Modifiable");
      address_m.setId(UUID.randomUUID().toString());
      address_m.setStreetName("Boulevard st");
      address_m.setStreetNumber(3000);
      address_m.setCity("Vancouver");
      address_m.setPostalCode("B1B 2A2");
      employeem.setAddress(address_m);

      shift_m.setId(UUID.randomUUID().toString());
      shift_m.setDate(Date.valueOf("2022-03-08"));
      shift_m.setStartTime(Time.valueOf("17:00:00"));
      shift_m.setEndTime(Time.valueOf("02:30:00"));
      shifts_m.add(shift_m);
      employeem.setShifts(shifts_m);
    }
  }
}
