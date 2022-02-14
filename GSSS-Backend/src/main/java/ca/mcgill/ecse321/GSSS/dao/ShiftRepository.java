package ca.mcgill.ecse321.GSSS.dao;

import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Shift;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

/**
 * This interface defines the repository for the CRUD functionalities relating to the Shifts.
 *
 * @author Theo Ghanem
 */
public interface ShiftRepository extends CrudRepository<Shift, Integer> {

    /**
     * This method queries the database for the unique shift associated to the shift's ID.
     * @param id unique id of the Shift
     * @return the shift
     * @author Theo Ghanem
     */
    Shift findShiftById(long id);

    /**
     * This method queries the database for the list of shifts associated to the employee.
     * @param employee The employee whose shifts we want to find
     * @return the list of shifts
     * @author Theo Ghanem
     */
    List<Shift> findShiftsByEmployee(Employee employee);

    /**
     * This method queries the database for the lists of shifts associated to a particular date.
     * @param date a particular date
     * @return the list of shifts associated to that date
     * @author Theo Ghanem
     */
    List<Shift> findShiftsByDate(Date date);

}
