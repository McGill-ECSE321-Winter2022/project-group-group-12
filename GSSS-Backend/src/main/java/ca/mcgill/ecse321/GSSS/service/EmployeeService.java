package ca.mcgill.ecse321.GSSS.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.GSSS.dao.EmployeeRepository;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Shift;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

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
    public Employee addShift(Employee employee, Shift shift){
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
    public List<Employee> addShift(Employee[] employees, Shift shift){
        List<Employee> employeeList = new ArrayList<>();
        for(Employee e: employees) employeeList.add(addShift(e, shift));
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
    public Employee removeShift(Employee employee, Shift shift){
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
    public List<Employee> removeShift(Employee[] employees, Shift shift){
        List<Employee> employeeList = new ArrayList<>();
        for(Employee e: employees) employeeList.add(removeShift(e, shift));
        return employeeList;
    }


}
