package ca.mcgill.ecse321.GSSS.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * implementation of tests for shift persistence, testing find by id and by date
 * @author Habib Jarweh
 *
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestShiftPersistence {

  @Autowired
  ShiftRepository shiftRepository;

  private void verify(List<Shift> shifts1, List<Shift> shifts2) {
    assertNotNull(shifts2);
    assertEquals(shifts1.size(), shifts2.size());
    for (int i = 0; i < shifts1.size(); i++) {
      assertTrue(shifts2.contains(shifts1.get(i)));
    }
  }

  @Test
  public void testPersistAndLoadEmployeeById() {

    Shift shift = new Shift();

    Time startTime = Time.valueOf("8:00:00");
    Time endTime = Time.valueOf("17:00:00");
    Date date = new Date(System.currentTimeMillis());

    String id = UUID.randomUUID().toString();
    shift.setStartTime(startTime);
    shift.setEndTime(endTime);
    shift.setDate(date);
    shift.setId(id);

    shiftRepository.save(shift);
    shift = null;

    shift = shiftRepository.findShiftById(id);

    assertNotNull(shift);
    assertEquals(date, shift.getDate());
    assertEquals(startTime, shift.getStartTime());
    assertEquals(endTime, shift.getEndTime());
    assertEquals(id, shift.getId());

  }

  @Test
  public void testPersistAndLoadEmployeeByDate() {

    List<Shift> shifts1 = new ArrayList<Shift>();

    Time startTime1 = Time.valueOf("8:00:00");
    Time endTime1 = Time.valueOf("17:00:00");
    Date date = new Date(System.currentTimeMillis());
    String id1 = UUID.randomUUID().toString();

    Shift shift1 = new Shift();
    shift1.setStartTime(startTime1);
    shift1.setEndTime(endTime1);
    shift1.setDate(date);
    shift1.setId(id1);

    shiftRepository.save(shift1);
    shifts1.add(shift1);
    shift1 = null;

    Time startTime2 = Time.valueOf("8:00:00");
    Time endTime2 = Time.valueOf("12:00:00");
    String id2 = UUID.randomUUID().toString();

    Shift shift2 = new Shift();
    shift2.setStartTime(startTime2);
    shift2.setEndTime(endTime2);
    shift2.setDate(date);
    shift2.setId(id2);

    shiftRepository.save(shift2);
    shifts1.add(shift2);
    shift2 = null;


    List<Shift> shifts2 = new ArrayList<Shift>();
    shifts2 = shiftRepository.findShiftsByDate(date);

    verify(shifts1, shifts2);

  }


}
