package ca.mcgill.ecse321.GSSS.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.GSSS.model.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * implementation of tests for shift persistence, testing find by id and by date
 * 
 * @author Habib Jarweh
 *
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestShiftPersistence {
  @Autowired
  ShiftRepository shiftRepository;


  /**
   * This method gets executed after each test, and clears the relevant tables.
   * 
   * @author Philippe Sarouphim Hochar.
   */
  @AfterEach
  public void clearTables(){
    shiftRepository.deleteAll();
  }

  /**
   * This method asserts whether the actual list of shifts matches the expected one.
   * 
   * @author Philippe Sarouphim Hochar.
   * 
   * @param expected Expected list of shifts.
   * @param actual Actual list of shifts.
   */
  private void verify(List<Shift> expected, List<Shift> actual) {
    assertNotNull(actual);
    assertEquals(expected.size(), actual.size());

    for (Shift e : expected) {
      boolean found = false;
      for (Shift a : actual) {
        if (e.getId().equals(a.getId())) {
          verify(e, a);
          found = true;
          break;
        }
      }
      assertTrue(found);
    }
  }

  /**
   * This method asserts whether the actual shift matches the expected one.
   * 
   * @author Philippe Sarouphim Hochar.
   * 
   * @param expected Expected shift.
   * @param actual Actual shift.
   */
  private void verify(Shift expected, Shift actual) {
    assertNotNull(actual);
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getDate(), actual.getDate());
    assertEquals(expected.getStartTime(), actual.getStartTime());
    assertEquals(expected.getEndTime(), actual.getEndTime());
  }

  /**
   * 
   * this method is to test if shift found by id is same as actual
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testPersistAndLoadEmployeeById() {
    Shift shift1 = new Shift();
    Time startTime = Time.valueOf("8:00:00");
    Time endTime = Time.valueOf("17:00:00");
    Date date = Date.valueOf("2022-02-15");
    String id = UUID.randomUUID().toString();
    shift1.setStartTime(startTime);
    shift1.setEndTime(endTime);
    shift1.setDate(date);
    shift1.setId(id);
    shiftRepository.save(shift1);

    Shift shift2 = shiftRepository.findShiftById(id);

    verify(shift1, shift2);
  }

  /**
   * 
   * this method is to test if shifts found by date is same as actual shifts of the same date
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testPersistAndLoadEmployeeByDate() {
    List<Shift> shifts1 = new ArrayList<Shift>();
    Time startTime1 = Time.valueOf("8:00:00");
    Time endTime1 = Time.valueOf("17:00:00");
    Date date = Date.valueOf("2022-02-15");;
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
    shift2.setDate(Date.valueOf("2022-02-14"));
    shift2.setId(id2);
    shiftRepository.save(shift2);
    shift2 = null;

    List<Shift> shifts2 = shiftRepository.findShiftsByDate(date);

    verify(shifts1, shifts2);
  }
}
