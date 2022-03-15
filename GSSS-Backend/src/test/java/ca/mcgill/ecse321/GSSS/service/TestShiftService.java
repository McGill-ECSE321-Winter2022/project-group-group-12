package ca.mcgill.ecse321.GSSS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.GSSS.dao.ShiftRepository;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Shift;
import ca.mcgill.ecse321.GSSS.service.TestCustomerService.MockDatabase;

/**
 *
 * Tests for the shift services
 *
 * @author Wassim Jabbour
 *
 */
@ExtendWith(MockitoExtension.class)
public class TestShiftService {

  @Mock
  private ShiftRepository shiftRepository;

  @InjectMocks
  private ShiftService shiftService;

  @BeforeEach
  public void setMockOutput() {
    lenient().when(shiftRepository.findShiftById(anyString()))
        .thenAnswer(MockRepository::findShiftById);
    lenient().when(shiftRepository.findShiftsByDate(any(Date.class)))
        .thenAnswer(MockRepository::findShiftsByDate);
    lenient().when(shiftRepository.findAll()).thenAnswer(MockRepository::findAll);
    lenient().when(shiftRepository.save(any(Shift.class))).thenAnswer(MockRepository::save);
  }

  @Test
  public void testCreateShift_Success() {
    Shift created = shiftService.createShift(MockDatabase.shift1.getDate(),
        MockDatabase.shift1.getStartTime(), MockDatabase.shift1.getEndTime());
    assertNotNull(created);
    assertEquals(MockDatabase.shift1.getDate(), created.getDate());
    assertEquals(MockDatabase.shift1.getStartTime(), created.getStartTime());
    assertEquals(MockDatabase.shift1.getEndTime(), created.getEndTime());
  }

  @Test
  public void testCreateShift_NullDate() {
    try {
      shiftService.createShift(null, Time.valueOf("02:00:00"), Time.valueOf("03:00:00"));
    } catch (IllegalArgumentException e) {
      assertEquals("Shift date cannot be null!", e.getMessage());
      return;
    }
    fail();
  }

  @Test 
  public void testCreateShift_NullStartTime() {
    try {
      shiftService.createShift(Date.valueOf("2022-01-01"), null, Time.valueOf("03:00:00"));
    } catch (IllegalArgumentException e) {
      assertEquals("Shift start time cannot be null!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateShift_NullEndTime() {
    try {
      shiftService.createShift(Date.valueOf("2022-01-01"), Time.valueOf("02:00:00"), null);
    } catch (IllegalArgumentException e) {
      assertEquals("Shift end time cannot be null!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateShift_EndTimeBeforeStartTime() {
    try {
      shiftService.createShift(Date.valueOf("2022-01-01"), Time.valueOf("03:00:00"),
          Time.valueOf("02:00:00"));
    } catch (IllegalArgumentException e) {
      assertEquals("Shift end time cannot be before shift start time!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateShift_NullAll() {
    try {
      shiftService.createShift(null, null, null);
    } catch (IllegalArgumentException e) {
      assertEquals(
          "Shift date cannot be null! Shift start time cannot be null! Shift end time cannot be null!",
          e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyShift_Success() {
    Shift modified = shiftService.modifyShift(MockDatabase.shiftm.getId(),
        Date.valueOf("2022-01-01"), Time.valueOf("04:00:00"), Time.valueOf("05:00:00"));
    assertNotNull(modified);
    assertEquals(MockDatabase.shiftm.getId(), modified.getId());
    assertEquals(Date.valueOf("2022-01-01"), modified.getDate());
    assertEquals(Time.valueOf("04:00:00"), modified.getStartTime());
    assertEquals(Time.valueOf("05:00:00"), modified.getEndTime());
  }

  @Test
  public void testModifyShift_NullId() {
    try {
      shiftService.modifyShift(null, Date.valueOf("2022-01-01"), Time.valueOf("02:00:00"),
          Time.valueOf("03:00:00"));
    } catch (IllegalArgumentException e) {
      assertEquals("Shift id cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyShift_EmptyId() {
    try {
      shiftService.modifyShift("  ", Date.valueOf("2022-01-01"), Time.valueOf("02:00:00"),
          Time.valueOf("03:00:00"));
    } catch (IllegalArgumentException e) {
      assertEquals("Shift id cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyShift_NullDate() {
    try {
      shiftService.modifyShift(MockDatabase.shiftm.getId(), null, Time.valueOf("02:00:00"),
          Time.valueOf("03:00:00"));
    } catch (IllegalArgumentException e) {
      assertEquals("Shift date cannot be null!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyShift_NullStartTime() {
    try {
      shiftService.modifyShift(MockDatabase.shiftm.getId(), Date.valueOf("2022-01-01"), null,
          Time.valueOf("03:00:00"));
    } catch (IllegalArgumentException e) {
      assertEquals("Shift start time cannot be null!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyShift_NullEndTime() {
    try {
      shiftService.modifyShift(MockDatabase.shiftm.getId(), Date.valueOf("2022-01-01"),
          Time.valueOf("02:00:00"), null);
    } catch (IllegalArgumentException e) {
      assertEquals("Shift end time cannot be null!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyShift_EndTimeBeforeStartTime() {
    try {
      shiftService.modifyShift(MockDatabase.shiftm.getId(), Date.valueOf("2022-01-01"),
          Time.valueOf("03:00:00"), Time.valueOf("02:00:00"));
    } catch (IllegalArgumentException e) {
      assertEquals("Shift end time cannot be before shift start time!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyShift_NullAll() {
    try {
      shiftService.modifyShift(null, null, null, null);
    } catch (IllegalArgumentException e) {
      assertEquals(
          "Shift id cannot be empty! Shift date cannot be null! Shift start time cannot be null! Shift end time cannot be null!",
          e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyShift_NotInDb() {
    try {
      shiftService.modifyShift("123", Date.valueOf("2022-01-01"), Time.valueOf("02:00:00"),
          Time.valueOf("03:00:00"));
    } catch (NoSuchElementException e) {
      assertEquals("Shift with given ID does not exist!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetShift_Success() {
    Shift fetched = shiftService.getShift(MockDatabase.shift2.getId());
    assertNotNull(fetched);
    assertEquals(MockDatabase.shift2, fetched);
  }

  @Test
  public void testGetShift_NullId() {
    try {
      shiftService.getShift(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Shift id cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetShift_EmptyId() {
    try {
      shiftService.getShift("    ");
    } catch (IllegalArgumentException e) {
      assertEquals("Shift id cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetShift_NotInDb() {
    try {
      shiftService.getShift("123");
    } catch (NoSuchElementException e) {
      assertEquals("No shift with id 123 exists!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetShiftsByDate_Success() {
    List<Shift> shifts = shiftService.getShiftsByDate(MockDatabase.shift1.getDate());
    List<Shift> expected = new ArrayList<Shift>();
    expected.add(MockDatabase.shift1);
    expected.add(MockDatabase.shift2);
    assertNotNull(shifts);
    assertEquals(expected.size(), shifts.size());
    for (Shift s : shifts)
      assertTrue(expected.contains(s));
  }

  @Test
  public void testGetShiftByDate_NullDate() {
    try {
      shiftService.getShiftsByDate(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Shift date cannot be null! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetShiftsByEmployee_Success() {
    List<Shift> fetched = shiftService.getShiftsByEmployee(MockDatabase.employee1);
    assertNotNull(fetched);
    assertEquals(MockDatabase.shifts1.size(), fetched.size());
    for (Shift s : fetched)
      assertTrue(MockDatabase.shifts1.contains(s));
  }

  @Test
  public void testGetShiftsByEmployee_NullEmployee() {
    try {
      shiftService.getShiftsByEmployee(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Employee cannot be null!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testDeleteShift_Success() {
    shiftService.deleteShift(MockDatabase.shiftm.getId());

    verify(shiftRepository, times(1))
        .deleteById(argThat((String i) -> MockDatabase.shiftm.getId().equals(i)));
    verify(shiftRepository, times(0))
        .deleteById(argThat((String i) -> !MockDatabase.shiftm.getId().equals(i)));
  }

  @Test
  public void testDeleteShift_NullId() {
    try {
      shiftService.deleteShift(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Shift id cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testDeleteShift_EmptyId() {
    try {
      shiftService.deleteShift("   ");
    } catch (IllegalArgumentException e) {
      assertEquals("Shift id cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }


  private static class MockRepository {

    static Shift findShiftById(InvocationOnMock invocation) {
      String id = (String) invocation.getArgument(0);
      if (id.equals(MockDatabase.shift1.getId()))
        return MockDatabase.shift1;
      if (id.equals(MockDatabase.shift2.getId()))
        return MockDatabase.shift2;
      if (id.equals(MockDatabase.shift3.getId()))
        return MockDatabase.shift3;
      if (id.equals(MockDatabase.shiftm.getId()))
        return MockDatabase.shiftm;
      return null;
    }

    static List<Shift> findShiftsByDate(InvocationOnMock invocation) {
      Date date = (Date) invocation.getArgument(0);
      List<Shift> shifts = new ArrayList<Shift>();
      if (date.equals(MockDatabase.shift1.getDate()))
        shifts.add(MockDatabase.shift1);
      if (date.equals(MockDatabase.shift2.getDate()))
        shifts.add(MockDatabase.shift2);
      if (date.equals(MockDatabase.shift3.getDate()))
        shifts.add(MockDatabase.shift3);
      if (date.equals(MockDatabase.shiftm.getDate()))
        shifts.add(MockDatabase.shiftm);
      return shifts;
    }

    static List<Shift> findAll(InvocationOnMock invocation) {
      List<Shift> shifts = new ArrayList<Shift>();
      shifts.add(MockDatabase.shift1);
      shifts.add(MockDatabase.shift2);
      shifts.add(MockDatabase.shift3);
      shifts.add(MockDatabase.shiftm);
      return shifts;
    }

    static Shift save(InvocationOnMock invocation) {
      return (Shift) invocation.getArgument(0);
    }

  }

  final static class MockDatabase {

    static Shift shift1 = new Shift();
    static Shift shift2 = new Shift();
    static Shift shift3 = new Shift();
    static Shift shiftm = new Shift();

    static Employee employee1 = new Employee();
    static Set<Shift> shifts1 = new HashSet<Shift>();

    static {
      shift1.setId(UUID.randomUUID().toString());
      shift1.setDate(Date.valueOf("2022-03-08"));
      shift1.setStartTime(Time.valueOf("09:00:00"));
      shift1.setEndTime(Time.valueOf("17:00:00"));

      shift2.setId(UUID.randomUUID().toString());
      shift2.setDate(Date.valueOf("2022-03-08"));
      shift2.setStartTime(Time.valueOf("17:00:00"));
      shift2.setEndTime(Time.valueOf("23:00:00"));

      shift3.setId(UUID.randomUUID().toString());
      shift3.setDate(Date.valueOf("2022-03-07"));
      shift3.setStartTime(Time.valueOf("09:00:00"));
      shift3.setEndTime(Time.valueOf("17:00:00"));

      shiftm.setId(UUID.randomUUID().toString());
      shiftm.setDate(Date.valueOf("2022-03-06"));
      shiftm.setStartTime(Time.valueOf("12:00:00"));
      shiftm.setEndTime(Time.valueOf("00:00:00"));

      shifts1.add(shift2);
      shifts1.add(shift3);
      employee1.setShifts(shifts1);
    }

  }


}
