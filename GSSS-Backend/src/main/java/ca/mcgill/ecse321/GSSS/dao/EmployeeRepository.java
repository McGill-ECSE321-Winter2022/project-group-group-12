package ca.mcgill.ecse321.GSSS.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.Shift;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

  Employee findEmployeeByEmail(String email);

  List<Employee> findEmployeesByUsername(String username);
  
  /**
   * This method queries the database for employees associated to the shift.
   * @param shift the employee's shift
   * @return the list of employees associated to that particular shift
   * @author Theo Ghanem
   */
  List<Employee> findEmployeesByShift(Shift shift);

  Employee findEmployeeByAddress(Address address);

  Employee findEmployeeByAssignedPurchase(Purchase assignedPurchase);

}
