package ca.mcgill.ecse321.GSSS.dao;

import ca.mcgill.ecse321.GSSS.model.BusinessHour;
import ca.mcgill.ecse321.GSSS.model.Weekday;
import org.springframework.data.repository.CrudRepository;

public interface BusinessHourRepository extends CrudRepository<BusinessHour, Weekday> {

    BusinessHour findBusinessHourByDayOfWeek(Weekday dayOfWeek);

}
