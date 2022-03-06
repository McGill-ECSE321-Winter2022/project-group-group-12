package ca.mcgill.ecse321.GSSS.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GSSS.dto.EmployeeDto;
import ca.mcgill.ecse321.GSSS.dto.ShiftDto;
import ca.mcgill.ecse321.GSSS.model.Shift;
import ca.mcgill.ecse321.GSSS.service.EmployeeService;
import ca.mcgill.ecse321.GSSS.service.ShiftService;

/**
 * This Rest Controller class exposes the API endpoints related to employees.
 * 
 * @author Philippe Sarouphim Hochar.
 */
@CrossOrigin(origins = "*")
@RestController
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private ShiftService shiftService;

  /**
   * This API endpoint fetches employees based on the query parameters.
   * 
   * @author Philippe Sarouphim Hochar.
   * @return A list of employees corresponding to the query.
   */
  @GetMapping(value = {"/employee", "/employee/"})
  public List<EmployeeDto> getAllEmployees() {
    return employeeService.getAllEmployees().stream().map(e -> DtoConversion.convertToDto(e))
        .collect(Collectors.toList());
  }


  /**
   * This API endpoint fetches a list of all employees' emails.
   * 
   * @author Philippe Sarouphim Hochar.
   * @return List of all employees' emails.
   */
  @GetMapping(value = {"/employeeList", "/employeeList/"})
  public List<String> getEmployeeList() {
    return employeeService.getEmployeeList();
  }

  /**
   * This API endpoint creates a new employee.
   * 
   * @author Philippe Sarouphim Hochar.
   * 
   * @param employee Employee DTO (passed in the request body).
   * @return DTO of the newly created employee.
   */
  @PostMapping(value = {"/employee", "/employee/"})
  public EmployeeDto createEmployee(@RequestBody EmployeeDto employee) {
    return DtoConversion
        .convertToDto(employeeService.createEmployee(employee.getUsername(), employee.getEmail(),
            employee.getPassword(), DtoConversion.convertToDomainObject(employee.getAddress())));
  }

  /**
   * This API endpoint updates an employee.
   * 
   * @author Philippe Sarouphim Hochar.
   * 
   * @param employee Employee DTO with non-changing fields set as null (passed in the request body).
   * @return DTO of the newly updated employee.
   */
  @PutMapping(value = {"/employee", "/employee/"})
  public EmployeeDto updateEmployee(@RequestBody EmployeeDto employee) {
    return DtoConversion
        .convertToDto(employeeService.updateEmployee(employee.getUsername(), employee.getEmail(),
            DtoConversion.convertToDomainObject(employee.getAddress()), employee.isDisabled()));
  }

  /**
   * This API endpoint gets and employee based on his email.
   * 
   * @author Philippe Sarouphim Hochar.
   * 
   * @param email Email of the employee to fetch.
   * @return DTO of the employee corresponding to the email.
   */
  @GetMapping(value = {"/employee/{email}", "/employee/{email}/"})
  public EmployeeDto getEmployee(@PathVariable("email") String email) {
    return DtoConversion.convertToDto(employeeService.getEmployee(email));
  }

  /**
   * This API endpoint deletes an employee based on his email.
   * 
   * @author Philippe Sarouphim Hochar.
   * 
   * @param email Email of the employee to delete.
   */
  @DeleteMapping(value = {"/employee/{email}", "/employee/{email}/"})
  public void deleteEmployee(@PathVariable("email") String email) {
    employeeService.deleteEmployee(email);
  }

  /**
   * This API endpoint creates and adds a shift to an employee We don't need a shift controller
   * because this method takes care of creating the shifts anyways (You cannot have a shift without
   * an employee so might as well create both in one go)
   * 
   * @author Philippe Sarouphim Hochar.
   * 
   * @param email Email of the employee (in the path).
   * @param shift Shift (in the req body).
   * @return DTO of the new employee.
   */
  @PostMapping(value = {"/employee/shift/{email}", "/employee/shift/{email}/"})
  public EmployeeDto addShift(@PathVariable String email, @RequestBody ShiftDto shift) {
    Shift newShift =
        shiftService.createShift(shift.getDate(), shift.getStartTime(), shift.getEndTime());
    return DtoConversion
        .convertToDto(employeeService.addShift(employeeService.getEmployee(email), newShift));
  }

  /**
   * This API endpoint removes a shift from an employee.
   * 
   * @author Philippe Sarouphim Hochar.
   * 
   * @param email Email of the employee (passed in path).
   * @param id Id of the shift (passed in path).
   */
  @DeleteMapping(value = {"/employee/shift/{email}/{id}", "/employee/shift/{email}/{id}/"})
  public void removeShift(@PathVariable("email") String email, @PathVariable("id") String id) {
    employeeService.removeShift(employeeService.getEmployee(email), shiftService.getShift(id));
  }

}
