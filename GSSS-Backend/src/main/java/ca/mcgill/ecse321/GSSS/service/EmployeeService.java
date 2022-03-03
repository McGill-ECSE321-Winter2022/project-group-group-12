package ca.mcgill.ecse321.GSSS.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import ca.mcgill.ecse321.GSSS.dao.CustomerRepository;
import ca.mcgill.ecse321.GSSS.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.GSSS.dao.AddressRepository;
import ca.mcgill.ecse321.GSSS.dao.EmployeeRepository;
import ca.mcgill.ecse321.GSSS.dao.ShiftRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Shift;

@Service
public class EmployeeService {
	
	// CRUD Repositories
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	ShiftRepository shiftRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	// CREATE METHODS
	
	/**
	 * Method that creates a new employee, hashing and slating its password before saving it to the database
	 * 
	 * @author Enzo Benoit-Jeannin
	 * @param username The username of the employee
	 * @param email The email of the employee
	 * @param password The password of the employee
	 * @param isDisabled True if the employee is disabled
	 * @return The new created employee
	 */
	@Transactional
	public Employee createEmployee(String username, String email, String password, boolean isDisabled) {
		Employee employee = new Employee();
		employee.setUsername(username);
		employee.setEmail(email);
		employee.setSalt(HelperClass.getSalt());
		employee.setPassword(HelperClass.hashAndSaltPassword(password, employee.getSalt()));
		employee.setDisabled(isDisabled);
		employeeRepository.save(employee);
		return employee;
	}
	
	/**
	 * Method that creates a new non disabled employee, hashing and slating its password before saving it to the database
	 * 
	 * @author Enzo Benoit-Jeannin
	 * @param username The username of the employee
	 * @param email The email of the employee
	 * @param password The password of the employee
	 * @return The new created employee
	 */
	@Transactional
	public Employee createEmployee(String username, String email, String password) {
		Employee employee = new Employee();
		employee.setUsername(username);
		employee.setEmail(email);
		employee.setSalt(HelperClass.getSalt());
		employee.setPassword(HelperClass.hashAndSaltPassword(password, employee.getSalt()));
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
	public Employee getEmployeeByShift(String shiftId) {
		Shift shift = shiftRepository.findShiftById(shiftId);
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
	public Employee getEmployeeByAddress(String addressId) {
	  Address address = addressRepository.findAddressById(addressId);
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
    public List<Employee> getCustomersByAccountState(boolean disabled) {
      return employeeRepository.findEmployeesByDisabled(disabled);
    }  
	  
	/**
	 *  FInd all employees in the dataBase
	 *  
	 * @author Enzo Benoit-Jeannin
	 * @return A list of all the employees
	 */
	@Transactional
	public List<Employee> getAllEmployees(){
		return HelperClass.toList(employeeRepository.findAll());
	}

	// OTHER Methods
	/**
	 * Method to get all the shifts of an employee
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
	 * @author Chris Hatoum
	 * @param employeeEmail the employee's email
	 */
	public void disableEmployee(String employeeEmail){
		Employee employee = employeeRepository.findEmployeeByEmail(employeeEmail);
		employee.setDisabled(true);
	}

	/**
	 * Method that takes the employee's email and enables his account(setDisabled = false)
	 * @author Chris Hatoum
	 * @param employeeEmail the employee's email
	 */
	public void enableEmployee(String employeeEmail){
		Employee employee = employeeRepository.findEmployeeByEmail(employeeEmail);
		employee.setDisabled(false);
	}
}
