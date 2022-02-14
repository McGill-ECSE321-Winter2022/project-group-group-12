package ca.mcgill.ecse321.GSSS.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.GSSS.model.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestShiftPersistence {

  @Autowired
  ShiftRepository shiftRepository;
  
 
  Date date = new Date(System.currentTimeMillis());
  
  @Test
  public void testPersistAndLoadEmployeeById(){
    
    Shift shift = new Shift();
    String id = UUID.randomUUID().toString();
    
    shiftRepository.save(shift);
    shift = null;
    
    shift = shiftRepository.findShiftById(id);
    
    assertNotNull(shift);
    assertEquals(id, shift.getId());
    
  }
  
  @Test
  public void testPersistAndLoadEmployeeByDate(){
    
    Shift shift = new Shift();
    shift.setDate(date);
    
    shiftRepository.save(shift);
    shift = null;
    
    List<Shift> listShift = new ArrayList<Shift>();
    listShift = shiftRepository.findShiftsByDate(date);
    
    assertNotNull(listShift);
    assertEquals(date, listShift.get(0).getDate());
    
  }
  
  
}
