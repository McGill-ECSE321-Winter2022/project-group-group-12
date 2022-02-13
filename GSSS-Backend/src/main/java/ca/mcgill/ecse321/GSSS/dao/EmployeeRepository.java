package ca.mcgill.ecse321.GSSS.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.Shift;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Employee findEmployeeById(Long id);

    List<Employee> findCustomersByUsername(String username);

    List<Employee> findEmployeesByShift(Shift shift);

    Employee findEmployeeByAddress(Address address);

    Employee findEmployeeByPurchase(Purchase assignedPurchase);
    
}
