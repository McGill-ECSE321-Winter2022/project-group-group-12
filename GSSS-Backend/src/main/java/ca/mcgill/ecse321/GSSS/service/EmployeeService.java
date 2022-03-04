package ca.mcgill.ecse321.GSSS.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.GSSS.dao.EmployeeRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Shift;

@Service
public class EmployeeService {

  // CRUD Repositories

  @Autowired
  private EmployeeRepository employeeRepository;


  // CREATE METHODS

  /**
   * Method that creates a new employee, hashing and slating its password before saving it to the
   * database
   * 
   * @author Enzo Benoit-Jeannin
   * @param username The username of the employee
   * @param email The email of the employee
   * @param password The password of the employee
   * @param isDisabled True if the employee is disabled
   * @param address Address of the employee
   * @return The new created employee
   */
  @Transactional
  public Employee createEmployee(String username, String email, String password, Address address,
      boolean isDisabled) {

    // Input validation
    String error = "";
    if (email == null || email.trim().length() == 0)
      error += "Employee email cannot be empty! ";
    if (username == null || username.trim().length() == 0)
      error += "Employee username cannot be empty! ";
    if (password == null || password.trim().length() == 0)
      error += "Employee username cannot be empty! ";
    if (address == null)
      error += "Address cannot be null! ";
    if (error.length() > 0)
      throw new IllegalArgumentException(error);

    Employee employee = new Employee();
    employee.setUsername(username);
    employee.setEmail(email);
    employee.setSalt(HelperClass.getSalt());
    employee.setPassword(HelperClass.hashAndSaltPassword(password, employee.getSalt()));
    employee.setAddress(address);
    employee.setDisabled(isDisabled);
    employeeRepository.save(employee);
    return employee;
  }

  /**
   * Method that creates a new non disabled employee, hashing and slating its password before saving
   * it to the database
   * 
   * @author Enzo Benoit-Jeannin
   * @param username The username of the employee
   * @param email The email of the employee
   * @param password The password of the employee
   * @param address Address of the employee
   * @return The new created employee
   */
  @Transactional
  public Employee createEmployee(String username, String email, String password, Address address) {

    // Input validation
    String error = "";
    if (email == null || email.trim().length() == 0)
      error += "Employee email cannot be empty! ";
    if (username == null || username.trim().length() == 0)
      error += "Employee username cannot be empty! ";
    if (password == null || password.trim().length() == 0)
      error += "Employee username cannot be empty! ";
    if (address == null)
      error += "Address cannot be null! ";
    if (error.length() > 0)
      throw new IllegalArgumentException(error);

    Employee employee = new Employee();
    employee.setUsername(username);
    employee.setEmail(email);
    employee.setSalt(HelperClass.getSalt());
    employee.setPassword(HelperClass.hashAndSaltPassword(password, employee.getSalt()));
    employee.setAddress(address);
    employee.setDisabled(false);
    employeeRepository.save(employee);
    return employee;
  }

  // DELETE METHOD

  /**
   * Deletes an employee using their email
   * 
   * @author Enzo Benoit-Jeannin
   * @param email The email of the employee to delete
   */
  @Transactional
  public void deleteEmployee(String email) {
    // Input validation
    if (email == null || email.trim().length() == 0)
      throw new IllegalArgumentException("Employee email cannot be empty!");

    employeeRepository.deleteById(email);
  }


  // GET METHODS

  /**
   * Finds an employee by its email
   * 
   * @author Enzo Benoit-Jeannin
   * @param email The email of the employee
   * @return The found employee
   */
  @Transactional
  public Employee getEmployee(String email) {
    // Input validation
    if (email == null || email.trim().length() == 0)
      throw new IllegalArgumentException("Emnployee email cannot be empty!");

    return employeeRepository.findEmployeeByEmail(email);
  }

  /**
   * Finds an employee by its username
   * 
   * @author Enzo Benoit-Jeannin
   * @param username The username of the employee
   * @return The found employee
   */
  @Transactional
  public List<Employee> getEmployeeByUsername(String username) {
    // Input validation
    if (username == null || username.trim().length() == 0)
      throw new IllegalArgumentException("Employee username cannot be empty!");

    return employeeRepository.findEmployeesByUsername(username);
  }

  /**
   * Finds an employee by its shift
   * 
   * @author Enzo Benoit-Jeannin
   * @param shiftId The ID of the shift to search for
   * @return The found employee
   */
  @Transactional
  public Employee getEmployeeByShift(Shift shift) {
    // Input validation
    if (shift == null)
      throw new IllegalArgumentException("Shift cannot be null!");

    return employeeRepository.findEmployeeByShifts(shift);
  }

  /**
   * Finds the customer with a given address
   * 
   * @author Enzo Benoit-Jeannin
   * @param addressId The ID of the address to search for
   * @return The employee with the address corresponding to that ID
   */
  @Transactional
  public Employee getEmployeeByAddress(Address address) {
    // Input validation
    if (address == null)
      throw new IllegalArgumentException("Address cannot be null!");

    return employeeRepository.findEmployeeByAddress(address);
  }

  /**
   * Finds the employees with a given state of account (Disabled or not)
   * 
   * @author Enzo Benoit-Jeannin
   * @param disabled The state of the employee (True if disabled, false if not)
   * @return The employees with the corresponding state
   */
  @Transactional
  public List<Employee> getEmployeesByAccountState(boolean disabled) {
    return employeeRepository.findEmployeesByDisabled(disabled);
  }

  /**
   * FInd all employees in the dataBase
   * 
   * @author Enzo Benoit-Jeannin
   * @return A list of all the employees
   */
  @Transactional
  public List<Employee> getAllEmployees() {
    return HelperClass.toList(employeeRepository.findAll());
  }

  // OTHER Methods
  /**
   * Method to get all the shifts of an employee
   * 
   * @author Chris Hatoum
   * @param employeeEmail The employee's email
   * @return the list of the employee's shifts
   */
  @Transactional
  public List<Shift> getShifts(String employeeEmail) {
    Employee employee = employeeRepository.findEmployeeByEmail(employeeEmail);
    List<Shift> shiftList = new ArrayList<>(employee.getShifts());
    return shiftList;
  }

  /**
   * Method that takes the employee's email and disables his account(setDisabled = true)
   * 
   * @author Chris Hatoum
   * @param employeeEmail the employee's email
   */
  public void disableEmployee(String employeeEmail) {
    Employee employee = employeeRepository.findEmployeeByEmail(employeeEmail);
    employee.setDisabled(true);
  }

  /**
   * This service adds a shift to an employee.
   * 
   * @author Philippe Sarouphim Hochar.
   * 
   * @param employee Employee to add the shift to.
   * @param shift Shift to add to the employee.
   * @return The employee object with the newly added shift.
   */
  @Transactional
  public Employee addShift(Employee employee, Shift shift) {
    // Add shift to employee and save in database
    employee.getShifts().add(shift);
    return employeeRepository.save(employee);
  }

  /**
   * This service adds a shift to multiple employees.
   * 
   * @author Philippe Sarouphim Hochar.
   * 
   * @param employees Employees to add tghe shift to.
   * @param shift Shift to add to the employees.
   * @return Then list of employees with the newly added shifts.
   */
  @Transactional
  public List<Employee> addShift(Employee[] employees, Shift shift) {
    List<Employee> employeeList = new ArrayList<>();
    for (Employee e : employees)
      employeeList.add(addShift(e, shift));
    return employeeList;
  }

  /**
   * This service removes a shift from an employee.
   * 
   * @author Philippe Sarouphim Hochar.
   * 
   * @param employee Employee to the remove the shift from.
   * @param shift Shift to remove from the employee.
   * @return The employee without the shift to remove.
   */
  @Transactional
  public Employee removeShift(Employee employee, Shift shift) {
    employee.getShifts().remove(shift);
    return employeeRepository.save(employee);
  }

  /**
   * This service removes a shift from multiple employees.
   * 
   * @author Philippe Sarouphim Hochar.
   * 
   * @param employees Employees to the remove the shift from.
   * @param shift Shift to remove from the employees.
   * @return The employees without the shift to remove.
   */
  @Transactional
  public List<Employee> removeShift(Employee[] employees, Shift shift) {
    List<Employee> employeeList = new ArrayList<>();
    for (Employee e : employees)
      employeeList.add(removeShift(e, shift));
    return employeeList;
  }

  /**
   * Method that takes the employee's email and enables his account(setDisabled = false)
   * 
   * @author Chris Hatoum
   * @param employeeEmail the employee's email
   */
  public void enableEmployee(String employeeEmail) {
    Employee employee = employeeRepository.findEmployeeByEmail(employeeEmail);
    employee.setDisabled(false);
  }
}
