package ca.mcgill.ecse321.GSSS.dao;

import ca.mcgill.ecse321.GSSS.model.BusinessHour;
import ca.mcgill.ecse321.GSSS.model.Weekday;
import org.springframework.data.repository.CrudRepository;

/**
 * This interface defines the repository for the CRUD functionalities relating to the Business Hours.
 *
 * @author Theo Ghanem
 */
public interface BusinessHourRepository extends CrudRepository<BusinessHour, Weekday> {

    /**
     * This method queries the database for the Business Hours associated to a day of the week.
     * @param dayOfWeek the desired day of the week
     * @return the Business Hour during that particular day of the week
     * @author Theo Ghanem
     */
    BusinessHour findBusinessHourByDayOfWeek(Weekday dayOfWeek);

}
