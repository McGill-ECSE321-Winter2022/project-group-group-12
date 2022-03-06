package ca.mcgill.ecse321.GSSS.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.GSSS.dao.EmployeeRepository;
import ca.mcgill.ecse321.GSSS.dao.PurchaseRepository;
import ca.mcgill.ecse321.GSSS.dao.ShiftRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.OrderStatus;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.Shift;

@Service
public class EmployeeService {

  // CRUD Repositories

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private PurchaseRepository purchaseRepository;

  @Autowired
  private ShiftRepository shiftRepository;

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
   * @author Chris Hatoum and Enzo Benoit-Jeannin
   * @param employee The employee to retrieve shifts from
   * @return the list of the employee's shifts
   */
  @Transactional
  public List<Shift> getShifts(Employee employee) {
    // Input validation
    if (employee == null)
      throw new IllegalArgumentException("Employee cannot be null!");

    List<Shift> shiftList = new ArrayList<>(employee.getShifts());
    return shiftList;
  }

  /**
   * Method that takes the employee's email and disables his account(setDisabled = true)
   * 
   * @author Chris Hatoum and Enzo Benoit-Jeannin
   * @param employee the employee to disable
   */
  @Transactional
  public void disableEmployee(Employee employee) {
    // Input validation
    if (employee == null)
      throw new IllegalArgumentException("Employee cannot be null!");

    employee.setDisabled(true);
    employeeRepository.save(employee);
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
    if (employee == null)
      throw new IllegalArgumentException("Employee cannot be null.");

    if (shift == null)
      throw new IllegalArgumentException("Shift cannot be null.");

    // Add shift to employee and save in database
    employee.getShifts().add(shift);
    return employeeRepository.save(employee);
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
    if (employee == null)
      throw new IllegalArgumentException("Employee cannot be null.");

    if (shift == null)
      throw new IllegalArgumentException("Shift cannot be null.");

    employee.getShifts().remove(shift);
    return employeeRepository.save(employee);
  }

  /**
   * Method that takes the employee's email and enables his account(setDisabled = false)
   * 
   * @author Chris Hatoum and Enzo Benoit-Jeannin
   * @param employee the employee to enable
   */
  public void enableEmployee(Employee employee) {
    // Input validation
    if (employee == null)
      throw new IllegalArgumentException("Employee cannot be null!");

    employee.setDisabled(false);
    employeeRepository.save(employee);
  }

  /**
   * Method that returns the number of purchases an employee currently has assigned to him that are
   * not completed
   * 
   * @author Wassim Jabbour
   * @param employee The employee
   * @return The number of ongoing purchases
   */
  public int getNumOfOngoingPurchases(Employee employee) {

    // Input validation
    if (employee == null)
      throw new IllegalArgumentException("Employee cannot be null!");

    List<Purchase> purchases = purchaseRepository.findPurchasesByEmployee(employee);
    int total = 0;

    for (Purchase p : purchases) {
      if (p.getOrderStatus() == OrderStatus.Completed) {
        total++;
      }
    }

    return total;
  }

  /**
   * Method that returns the employee with the shift closest to the current time
   * 
   * @author Wassim Jabbour
   * @return The employee
   */
  public Employee getClosestEmployee() {
    
    // Getting all shifts
    List<Shift> shifts = HelperClass.toList(shiftRepository.findAll());
    
    // Current date and time
    Date currentDate = new Date(System.currentTimeMillis());
    Time currentTime = new Time(System.currentTimeMillis());
    
    // Sorting based on date first, then start time
    Collections.sort(shifts, new Comparator<Shift>() {

      @Override
      public int compare(Shift o1, Shift o2) {
        if(o1.getDate().before(o2.getDate())) {
          return -1;
        }
        else if(o1.getDate().after(o2.getDate())) {
          return 1;
        }
        else {
          if(o1.getStartTime().before(o2.getStartTime())) {
            return -1;
          }
          else if(o1.getStartTime().after(o2.getStartTime())) {
            return 1;
          }
          else {
            return 0;
          }
        }
      }
      
    });
    
    Shift bestShift = null;
    
    // Finding the first shift that is in the future
    for(int i = 0; i < shifts.size(); i++) {
      
      // Current shift
      Shift shift = shifts.get(i);
      
      // If its date is today, check it ends after now
      if(shift.getDate().equals(currentDate)) {
        if(shift.getEndTime().after(currentTime)) {
          bestShift = shift;
          break;
        }
      }
      
      // If its date is in the future, take it
      else if(shift.getDate().after(currentDate)) {
        bestShift = shift;
        break;
      }
      
    }
    
    // If we found a shift, we return its employee
    // Else we return any employee
    if(bestShift == null) return employeeRepository.findEmployeeByShifts(shifts.get(0));
    else return employeeRepository.findEmployeeByShifts(bestShift);
  }


}
