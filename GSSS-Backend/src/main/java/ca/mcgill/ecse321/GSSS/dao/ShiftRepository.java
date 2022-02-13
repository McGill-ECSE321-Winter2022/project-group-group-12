package ca.mcgill.ecse321.GSSS.dao;

import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Shift;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface ShiftRepository extends CrudRepository<Shift, Integer> {

    Shift findShiftById(long id);

    Shift findShiftByEmployee(String email);

    List<Employee> findEmployeesByShift(Shift shift);

    Shift findShiftByDate(Date date);

}
