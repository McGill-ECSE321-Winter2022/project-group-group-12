package ca.mcgill.ecse321.GSSS.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.GSSS.model.BusinessHour;
import ca.mcgill.ecse321.GSSS.model.Weekday;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Time;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


/**
 * 
 * class to test persistence of businessHour, by weekday (primary key)
 * 
 * @author Habib Jarweh
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestBusinessHourPersistence {

  @Autowired
  private BusinessHourRepository businessHourRepository;

  @Test
  public void testPersistAndLoadBusinessHourByWeekday() {

    Weekday weekday = Weekday.Monday;
    Time startTime = Time.valueOf("8:00:00");
    Time endTime = Time.valueOf("17:00:00");

    BusinessHour businessHour = new BusinessHour();
    businessHour.setWeekday(weekday);
    businessHour.setStartTime(startTime);
    businessHour.setEndTime(endTime);
    businessHourRepository.save(businessHour);
    businessHour = null;
    businessHour = businessHourRepository.findBusinessHourByWeekday(weekday);
    assertNotNull(businessHour);
    assertEquals(startTime, businessHour.getStartTime());
    assertEquals(endTime, businessHour.getEndTime());
    assertEquals(weekday, businessHour.getWeekday());
  }

  @AfterEach
  public void clearDatabase() {
    businessHourRepository.deleteAll();
  }
}
