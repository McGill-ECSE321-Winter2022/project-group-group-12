package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.dto.CustomerDto;
import ca.mcgill.ecse321.GSSS.dto.EmployeeDto;
import ca.mcgill.ecse321.GSSS.dto.ShiftDto;
import ca.mcgill.ecse321.GSSS.model.Shift;
import ca.mcgill.ecse321.GSSS.service.AddressService;
import ca.mcgill.ecse321.GSSS.service.EmployeeService;
import ca.mcgill.ecse321.GSSS.service.ShiftService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This Rest Controller class exposes the API endpoints related to employees.
 *
 * @author Philippe Sarouphim Hochar.
 */
@CrossOrigin(origins = "*")
@RestController
public class EmployeeRestController {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private ShiftService shiftService;

  @Autowired
  private AddressService addressService;

  /**
   * This API endpoint fetches employees based on the query parameters.
   *
   * @return A list of employees corresponding to the query.
   * @author Philippe Sarouphim Hochar.
   */
  @GetMapping(value = {"/employees", "/employees/"})
  public List<EmployeeDto> getAllEmployees() {
    return employeeService.getAllEmployees().stream()
        .map(e -> DtoUtility.convertToDto(e))
        .collect(Collectors.toList());
  }

  /**
   * This API endpoint creates a new employee.
   *
   * @param employee Employee DTO (passed in the request body).
   * @return DTO of the newly created employee if successful, else return error
   * @author Philippe Sarouphim Hochar.
   */
  @PostMapping(value = {"/employee", "/employee/"})
  public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto employee){
	  try {
		  return ResponseEntity.ok(DtoUtility.convertToDto(
			        employeeService.createEmployee(
			            employee.getUsername(),
			            employee.getEmail(),
			            employee.getPassword(),
			            addressService.getAddress(employee.getAddress().getId()))));
	  }catch(Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());
	  }
    
  }

  /**
   * This API endpoint updates an employee.
   *
   * @param employee Employee DTO
   * @return DTO of the newly updated employee if successful, else return error
   * @author Philippe Sarouphim Hochar.
   */
  @PutMapping(value = {"/employee", "/employee/"})
  public ResponseEntity<?> modifyEmployee(@RequestBody EmployeeDto employee) {
	  try {
	    return ResponseEntity.ok(DtoUtility.convertToDto(
	        employeeService.modifyEmployee(
	            employee.getUsername(),
	            employee.getEmail(),
	            addressService.getAddress(employee.getAddress().getId()),
	            employee.isDisabled())));
	  }catch(Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());
	  }
  }

  /**
   * Method to modify/update a employee's password
   *
   * @param email    The email of the employee to modify
   * @param password the new password of the employee 
   * @return The modified employee as a DTO object if successful, else return error
   * @author Enzo Benoit-Jeannin
   */
  @PostMapping(value = {"/employee/password/{email}", "/employee/password/{email}/"})
  public ResponseEntity<?> modifyPassword(@PathVariable("email") String email,
      @RequestParam(name = "password") String password){
	  try {
		    return ResponseEntity.ok(DtoUtility.convertToDto(
		            employeeService.modifyPassword(email, password)));
	  }catch(Exception e) {
		  return ResponseEntity.badRequest().body(e.getMessage());
	  }


  }

  /**
   * This API endpoint gets and employee based on his email.
   *
   * @param email Email of the employee to fetch.
   * @return DTO of the employee corresponding to the email if successful, else return error
   * @author Philippe Sarouphim Hochar.
   */
  @GetMapping(value = {"/employee/{email}", "/employee/{email}/"})
  public ResponseEntity<?> getEmployee(@PathVariable("email") String email){
	  try {
    return ResponseEntity.ok(DtoUtility.convertToDto(employeeService.getEmployeeByEmail(email)));
	  }catch(Exception e) {
		 return ResponseEntity.badRequest().body(e.getMessage());
	  }
  }

  /**
   * @param shiftId
   * @return employee with designated shift if successful, else return the error
   * @author Habib Jarweh
   */
  @GetMapping(value = {"/employeebyshift/{shiftId}", "/employeebyshift/{shiftId}/"})
  public ResponseEntity<?> getEmployeeByShift(@PathVariable("shiftId") String shiftId){
	  
	  try {
	
	    Shift shift = shiftService.getShift(shiftId);
	
	    return ResponseEntity.ok(DtoUtility.convertToDto(employeeService.getEmployeeByShift(shift)));
	  }catch(Exception e) {
			 return ResponseEntity.badRequest().body(e.getMessage());
		  }
  }

  /**
   * This API endpoint deletes an employee based on his email.
   *
   * @param email Email of the employee to delete if successful, else return the error
   * @author Philippe Sarouphim Hochar.
   */
  @DeleteMapping(value = {"/employee/{email}", "/employee/{email}/"})
  public ResponseEntity<?> deleteEmployee(@PathVariable("email") String email) {
	  try {
		  employeeService.deleteEmployee(email);
		  return ResponseEntity.ok(null);
	  }catch(Exception e) {
		 return ResponseEntity.badRequest().body(e.getMessage());
	  }
  }

  /**
   * This API endpoint creates and adds a shift to an employee We don't need a shift controller
   * because this method takes care of creating the shifts anyways (You cannot have a shift without
   * an employee so might as well create both in one go)
   *
   * @param email Email of the employee (in the path).
   * @param shift Shift (in the req body).
   * @return DTO of the new employee if successful, else return error
   * @author Philippe Sarouphim Hochar.
   */
  @PostMapping(value = {"/employee/shift/{email}", "/employee/shift/{email}/"})
  public ResponseEntity<?> addShift(@PathVariable String email, @RequestBody ShiftDto shift){
	  try {
		  Shift newShift =
			        shiftService.createShift(shift.getDate(), shift.getStartTime(), shift.getEndTime());
	    return ResponseEntity.ok(DtoUtility.convertToDto(
	        employeeService.addShift(employeeService.getEmployeeByEmail(email), newShift)));
	  }catch(Exception e) {
		 return ResponseEntity.badRequest().body(e.getMessage());
	  }
    
  }

  /**
   * This API endpoint removes a shift from an employee.
   *
   * @param email Email of the employee (passed in path).
   * @param id    Id of the shift (passed in path).
   * @author Philippe Sarouphim Hochar.
   */
  @DeleteMapping(value = {"/employee/shift/{email}/{id}", "/employee/shift/{email}/{id}/"})
  public ResponseEntity<?> removeShift(@PathVariable("email") String email, @PathVariable("id") String id){
    try {
	  employeeService.removeShift(employeeService.getEmployeeByEmail(email), shiftService.getShift(id));
	  return ResponseEntity.ok(null);
    }catch(Exception e) {
		 return ResponseEntity.badRequest().body(e.getMessage());
	  }
   
  }


}
