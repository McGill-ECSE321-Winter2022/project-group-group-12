package ca.mcgill.ecse321.GSSS.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Shift;

/**
 * This interface defines the repository for the CRUD functionalities relating to the Employees.
 *
 * @author Theo Ghanem
 */
public interface EmployeeRepository extends CrudRepository<Employee, String> {

  /**
   * This method queries the database for an employee associated to a particular email.
   * @param email the employee's email
   * @return the employee associated to that particular email
   * @author Theo Ghanem
   */
  Employee findEmployeeByEmail(String email);

  /**
   * This method queries the database for a list of employees associated to a particular username (that isn't unique).
   * @param username the employees that have that particular username
   * @return a list of employees associated to that particular username
   * @author Theo Ghanem
   */
  List<Employee> findEmployeesByUsername(String username);
  
  /**
   * This method queries the database for employees associated to the shift.
   * @param shift the employee's shift
   * @return the list of employees associated to that particular shift
   * @author Theo Ghanem
   */
  Employee findEmployeeByShifts(Shift shift);

  /**
   * This method queries the database an employee associated to an address.
   * @param address One of the employee's address
   * @return the employee associated to that particular address
   * @author Theo Ghanem
   */
  Employee findEmployeeByAddress(Address address);
  
  /**
   * 
   * @author Wassim Jabbour
   * @param disabled boolean to see if employee is banned, or in other words his account disabled
   * @return list of employees we want to find
   */
  List<Employee> findEmployeesByDisabled(boolean disabled);

}
