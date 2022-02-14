package ca.mcgill.ecse321.GSSS.dao;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.GSSS.model.BusinessHour;
import ca.mcgill.ecse321.GSSS.model.Weekday;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestBusinessHourPersistence {
  
  @Autowired
  private BusinessHourRepository businessHourRepository;
  Weekday weekday = Weekday.Monday;
  
  @Test
  public void testPersistAndLoadBusinessHourByWeekday(){
    BusinessHour businessHour = new BusinessHour();
    businessHour.setWeekday(weekday);
    
    businessHourRepository.save(businessHour);
    businessHour = null;
    
    businessHour = businessHourRepository.findBusinessHourByWeekday(weekday);
    
    assertNotNull(businessHour);
    assertEquals(weekday, businessHour.getWeekday());
  }

}
