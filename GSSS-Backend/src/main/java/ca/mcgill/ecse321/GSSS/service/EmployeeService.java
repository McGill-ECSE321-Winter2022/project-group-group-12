package ca.mcgill.ecse321.GSSS.service;

import ca.mcgill.ecse321.GSSS.dao.EmployeeRepository;
import ca.mcgill.ecse321.GSSS.dao.ShiftRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Shift;
import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The services of the Employee class
 *
 * @author Enzo Benoit-Jeannin
 * @author Wassim Jabbour
 */
@Service
public class EmployeeService {

  // CRUD Repositories

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private ShiftRepository shiftRepository;

  // CREATE METHODS

  /**
   * Method that creates a new non disabled employee, hashing and salting its password before saving
   * it to the database
   *
   * @param username The username of the employee
   * @param email    The email of the employee
   * @param password The password of the employee
   * @param address  Address of the employee
   * @return The new created employee
   * @author Enzo Benoit-Jeannin & Theo Ghanem
   */
  @Transactional
  public Employee createEmployee(String username, String email, String password, Address address) {

    // Input validation
    String error = "";
    if (email == null || email.trim().length() == 0) {
      error += "Employee email cannot be empty! ";
    }
    if (!Utility.isEmailValid(email)) {
      error += "Email not valid! ";
    }
    if (username == null || username.trim().length() == 0) {
      error += "Employee username cannot be empty! ";
    }
    if (password == null || password.length() < 6 || password.trim().length() == 0) {
      error += "Password has to be at least 6 characters! ";
    }
    if (address == null) {
      error += "Address cannot be null! ";
    }
    if (error.length() > 0) {
      throw new IllegalArgumentException(error);
    }

    // Check if that employee already exists
    Employee employee = null;
    String error2 = "";
    try {
      employee = getEmployeeByEmail(email);
      error2 = "The employee already exists in the system!";
    } catch (NoSuchElementException e) {
      if (e.getMessage().equals("No employee with email " + email + " exists!")) {
        employee = new Employee();
        employee.setUsername(username);
        employee.setEmail(email);
        employee.setSalt(Utility.getSalt());
        employee.setPassword(Utility.hashAndSaltPassword(password, employee.getSalt()));
        employee.setAddress(address);
        employee.setDisabled(false);
        employeeRepository.save(employee);
      }
    }
    if (error2.length() > 0) {
      throw new NoSuchElementException(error2);
    }

    return employee;
  }

  /**
   * This service updates an employee based on the inputs.
   *
   * @param username Username
   * @param email    Email
   * @param address  Address
   * @param disabled Disabled.
   * @return The updated employee
   * @author Philippe Sarouphim Hochar
   */
  @Transactional
  public Employee modifyEmployee(
      String username, String password, String email, Address address, boolean disabled) {

    // Input validation
    String error = "";
    if (email == null || email.trim().length() == 0) {
      error += "Employee email cannot be empty! ";
    }
    if (username == null || username.trim().length() == 0) {
      error += "Employee username cannot be empty! ";
    }
    if (password == null || password.length() < 6 || password.trim().length() == 0) {
      error += "Password has to be at least 6 characters! ";
    }
    if (address == null) {
      error += "Address cannot be null! ";
    }
    if (error.length() > 0) {
      throw new IllegalArgumentException(error);
    }

    Employee employee = employeeRepository.findEmployeeByEmail(email);
    if (employee == null) {
      throw new NoSuchElementException("Employee does not exist");
    }

    employee.setUsername(username);
    employee.setAddress(address);
    employee.setDisabled(disabled);
    employee.setPassword(Utility.hashAndSaltPassword(password, employee.getSalt()));

    employeeRepository.save(employee);
    return employee;
  }

  // DELETE METHOD

  /**
   * Deletes an employee using their email
   *
   * @param email The email of the employee to delete
   * @author Enzo Benoit-Jeannin
   */
  @Transactional
  public void deleteEmployee(String email) {
    // Input validation
    if (email == null || email.trim().length() == 0) {
      throw new IllegalArgumentException("Employee email cannot be empty!");
    }

    employeeRepository.deleteById(email);
  }

  // GET METHODS

  /**
   * Finds an employee by its email
   *
   * @param email The email of the employee
   * @return The found employee
   * @author Enzo Benoit-Jeannin
   */
  @Transactional
  public Employee getEmployeeByEmail(String email) {

    // Input validation
    if (email == null || email.trim().length() == 0) {
      throw new IllegalArgumentException("Employee email cannot be empty!");
    }

    // Finding the employee with that email
    Employee employee = employeeRepository.findEmployeeByEmail(email);
    if (employee == null) {
      throw new NoSuchElementException("No employee with email " + email + " exists!");
    }

    return employee;
  }

  /**
   * Finds a list of employees by their username
   *
   * @param username The username of the employee
   * @return The found employee
   * @author Enzo Benoit-Jeannin
   */
  @Transactional
  public List<Employee> getEmployeesByUsername(String username) {

    // Input validation
    if (username == null || username.trim().length() == 0) {
      throw new IllegalArgumentException("Employee username cannot be empty!");
    }

    // Finding the employees with that username
    List<Employee> employees = employeeRepository.findEmployeesByUsername(username);
    if (employees.size() == 0) {
      throw new NoSuchElementException("No employee with username " + username + " exists!");
    }

    return employees;
  }

  /**
   * Finds an employee by its shift
   *
   * @param shift The ID of the shift to search for
   * @return The found employee
   * @author Enzo Benoit-Jeannin
   */
  @Transactional
  public Employee getEmployeeByShift(Shift shift) {

    // Input validation
    if (shift == null) {
      throw new IllegalArgumentException("Shift cannot be null!");
    }

    // Finding the employee with that email
    Employee employee = employeeRepository.findEmployeeByShifts(shift);
    if (employee == null) {
      throw new NoSuchElementException("No employee with the given shift exists!");
    }

    return employee;
  }

  /**
   * Finds the employees with a given state of account (Disabled or not)
   *
   * @param disabled The state of the employee (True if disabled, false if not)
   * @return The employees with the corresponding state
   * @author Enzo Benoit-Jeannin
   */
  @Transactional
  public List<Employee> getEmployeesByAccountState(boolean disabled) {

    // Finding the employee with that state
    List<Employee> employees = employeeRepository.findEmployeesByDisabled(disabled);

    // We don't throw an exception if nobody exists for this one cause it's normal to have nobody
    // disabled
    return employees;
  }

  /**
   * Find all employees in the dataBase
   *
   * @return A list of all the employees
   * @author Enzo Benoit-Jeannin
   */
  @Transactional
  public List<Employee> getAllEmployees() {
    return Utility.toList(employeeRepository.findAll());
  }

  // OTHER Methods

  /**
   * This service adds a shift to an employee.
   *
   * @param employee Employee to add the shift to.
   * @param shift    Shift to add to the employee.
   * @return The employee object with the newly added shift.
   * @author Philippe Sarouphim Hochar.
   */
  @Transactional
  public Employee addShift(Employee employee, Shift shift) {
    if (employee == null) {
      throw new IllegalArgumentException("Employee cannot be null.");
    }

    if (shift == null) {
      throw new IllegalArgumentException("Shift cannot be null.");
    }

    // Add shift to employee and save in database
    employee.getShifts().add(shift);
    return employeeRepository.save(employee);
  }

  /**
   * This service removes a shift from an employee.
   *
   * @param employee Employee to the remove the shift from.
   * @param shift    Shift to remove from the employee.
   * @return The employee without the shift to remove.
   * @author Philippe Sarouphim Hochar.
   */
  @Transactional
  public Employee removeShift(Employee employee, Shift shift) {
    if (employee == null) {
      throw new IllegalArgumentException("Employee cannot be null.");
    }

    if (shift == null) {
      throw new IllegalArgumentException("Shift cannot be null.");
    }

    employee.getShifts().remove(shift);
    return employeeRepository.save(employee);
  }

  /**
   * Method that returns the employee with the shift closest to the current time
   *
   * @return The employee
   * @author Wassim Jabbour
   */
  @Transactional
  public Employee getClosestEmployee() {

    // Getting all shifts
    List<Shift> shifts = Utility.toList(shiftRepository.findAll());

    // Current date and time
    Date currentDate = new Date(System.currentTimeMillis());
    Time currentTime = new Time(System.currentTimeMillis());

    // Sorting based on date first, then start time
    Collections.sort(
        shifts,
        new Comparator<Shift>() {

          @Override
          public int compare(Shift o1, Shift o2) {
            if (o1.getDate().before(o2.getDate())) {
              return -1;
            } else if (o1.getDate().after(o2.getDate())) {
              return 1;
            } else {
              if (o1.getStartTime().before(o2.getStartTime())) {
                return -1;
              } else if (o1.getStartTime().after(o2.getStartTime())) {
                return 1;
              } else {
                return 0;
              }
            }
          }
        });

    Shift bestShift = null;

    // Finding the first shift that is in the future
    for (int i = 0; i < shifts.size(); i++) {

      // Current shift
      Shift shift = shifts.get(i);

      // If its date is today, check it ends after now
      if (shift.getDate().toString().equals(currentDate.toString())) {
        if (shift.getEndTime().after(currentTime)) {
          bestShift = shift;
          break;
        }
      }

      // If its date is in the future, take it
      else if (shift.getDate().after(currentDate)) {
        bestShift = shift;
        break;
      }
    }

    // If we found a shift, we return its employee
    // Else we return any employee
    if (bestShift == null) {
      return employeeRepository.findAll().iterator().next();
    } else {
      return employeeRepository.findEmployeeByShifts(bestShift);
    }
  }
}
