package ca.mcgill.ecse321.GSSS.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Shift;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Class to test the persistence and loading of the Employee Repository
 *
 * @author Theo Ghanem & Chris Hatoum
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestEmployeePersistence {

  /**
   * Deletes all the database contents. Goes from the independent to the dependent classes to avoid
   * exceptions being thrown when deleting
   *
   * @author Wassim Jabbour
   */
  @AfterEach
  public void clearDatabase() {
    customerRepository.deleteAll();
    purchaseRepository.deleteAll();
    employeeRepository.deleteAll();
    shiftRepository.deleteAll();
    ownerRepository.deleteAll();
    addressRepository.deleteAll();
    itemRepository.deleteAll();
    itemCategoryRepository.deleteAll();
    businessHourRepository.deleteAll();
  }

  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private AddressRepository addressRepository;
  @Autowired
  private ShiftRepository shiftRepository;
  @Autowired
  private BusinessHourRepository businessHourRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private ItemCategoryRepository itemCategoryRepository;
  @Autowired
  private ItemRepository itemRepository;
  @Autowired
  private OwnerRepository ownerRepository;
  @Autowired
  private PurchaseRepository purchaseRepository;

  // Initialize variables that will be used to successfully create a complete
  // employee
  String username1 = "bobthebuilder";
  String email1 = "bob@email.com";
  String password1 = "bobpassword";
  String fullName1 = "Bob Builder";
  String streetName1 = "Crescent";
  String city1 = "Montreal";
  int streetNumber1 = 43;
  String postalCode1 = "W2S L0S";
  String addressId1 = UUID.randomUUID().toString();

  // Doing the same for another employee
  String username2 = "jackychan";
  String email2 = "jackychan@gmail.com";
  String password2 = "jackychan";
  String fullName2 = "Jacky Chan";
  String streetName2 = "Lordside";
  String city2 = "Beirut";
  int streetNumber2 = 4;
  String postalCode2 = "W2S L0S";
  String addressId2 = UUID.randomUUID().toString();

  Date date1 = Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
  Time startTime1 = java.sql.Time.valueOf(LocalTime.of(11, 35));
  Time endTime1 = java.sql.Time.valueOf(LocalTime.of(13, 25));
  String shiftId1 = UUID.randomUUID().toString();

  Date date2 = Date.valueOf(LocalDate.of(2020, Month.FEBRUARY, 20));
  Time startTime2 = java.sql.Time.valueOf(LocalTime.of(10, 35));
  Time endTime2 = java.sql.Time.valueOf(LocalTime.of(15, 25));
  String shiftId2 = UUID.randomUUID().toString();

  /**
   * This method asserts whether the actual Employee matches the expected one.
   *
   * @param expected The element we are expecting to get back from the database
   * @param actual   The actual element we are retrieving from the database
   * @author Chris Hatoum & Theo Ghanem
   */
  private void verify(Employee expected, Employee actual) {

    assertNotNull(actual);
    assertEquals(expected.getUsername(), actual.getUsername());
    assertEquals(expected.getEmail(), actual.getEmail());
    assertEquals(expected.getPassword(), actual.getPassword());
    assertNotNull(actual.getShifts());
    assertNotNull(expected.getShifts());
    assertEquals(expected.isDisabled(), actual.isDisabled());
    assertEquals(actual.getShifts().size(), expected.getShifts().size());
    for (Shift expectedShift : expected.getShifts()) {
      boolean contains = false;
      for (Shift actualShift : actual.getShifts()) {
        if (expectedShift.equals(actualShift)) {
          contains = true;
        }
      }
      assertTrue(contains);
    }

    assertEquals(expected.getAddress(), actual.getAddress());

  }

  /**
   * This method checks whether the desired employee is in the list.
   *
   * @author Wassim Jabbour
   * @employee The expected employee
   * @employees The list to check
   */
  private void verifyContains(Employee employee, List<Employee> employees) {

    boolean found = false;

    for (Employee e : employees) {
      if (e.getEmail() != null && e.getEmail().equals(employee.getEmail())) {
        found = true;
        verify(employee, e);
      }
    }

    assertTrue(found);
  }

  /**
   * This method creates an address from the specified parameters, and saves it to the database.
   *
   * @param fullName,     full name of the employee
   * @param streetName,   street name of the employee
   * @param city,         city of the employee
   * @param postalCode,   postal code of the employee
   * @param addressId,    unique UUID for the address
   * @param streetNumber, street number of the employee
   * @return The persisted address
   * @author Chris Hatoum & Theo Ghanem
   */
  private Address persistAddress(String fullName, String streetName, String city, String postalCode,
      String addressId,
      int streetNumber) {
    Address address = new Address();
    address.setFullName(fullName);
    address.setStreetName(streetName);
    address.setStreetNumber(streetNumber);
    address.setCity(city);
    address.setPostalCode(postalCode);
    address.setId(addressId);

    addressRepository.save(address);
    return address;
  }

  /**
   * This method creates a shift from the specified parameters, and saves it to the database.
   *
   * @param shiftId,   unique UUID for the shift
   * @param date,      date of the shift
   * @param endTime,   end time of the shift
   * @param startTime, start time of the shift
   * @return the persisted shift
   * @author Chris Hatoum & Theo Ghanem
   */
  private Shift persistShift(String shiftId, Date date, Time endTime, Time startTime) {
    Shift shift = new Shift();
    shift.setId(shiftId);
    shift.setDate(date);
    shift.setEndTime(endTime);
    shift.setStartTime(startTime);

    shiftRepository.save(shift);
    return shift;
  }

  /**
   * This method creates an employee from the specified parameters, and saves it to the database.
   *
   * @param email,       the employee's email
   * @param password,    the employee's password
   * @param address,     the address of the employee
   * @param setOfShifts, a set of shifts
   * @return the persisted employee
   * @author Chris Hatoum & Theo Ghanem
   */
  private Employee persistEmployee(String email, String username, String password, Address address,
      Set<Shift> setOfShifts, Boolean bool) {
    Employee employee = new Employee();
    employee.setEmail(email);
    employee.setUsername(username);
    employee.setPassword(password);
    employee.setAddress(address);
    employee.setShifts(setOfShifts);
    employee.setDisabled(bool);

    employeeRepository.save(employee);
    return employee;
  }

  /**
   * Testing the persistence and loading for the Employee Repository. This tests the
   * findEmployeeByEmail method
   *
   * @author Theo Ghanem & Chris Hatoum
   */
  @Test
  public void testPersistAndLoadEmployeeByEmail() {

    Address address = persistAddress(fullName1, streetName1, city1, postalCode1, addressId1,
        streetNumber1);
    Shift shift = persistShift(shiftId1, date1, endTime1, startTime1);

    Set<Shift> setOfShifts = new HashSet<>();
    setOfShifts.add(shift);

    Employee employee = persistEmployee(email1, username1, password1, address, setOfShifts, false);
    Employee actualEmployee = employeeRepository.findEmployeeByEmail(email1);
    verify(employee, actualEmployee);

  }

  /**
   * Testing the persistence and loading for the Employee Repository. This tests the
   * findEmployeesByUsername method
   *
   * @author Theo Ghanem
   * @author Wassim Jabbour
   */
  @Test
  public void testPersistAndLoadEmployeesByUsername() {

    Address address1 = persistAddress(fullName1, streetName1, city1, postalCode1, addressId1,
        streetNumber1);
    Address address2 = persistAddress(fullName2, streetName2, city2, postalCode2, addressId2,
        streetNumber2);

    Shift shift1 = persistShift(shiftId1, date1, endTime1, startTime1);
    Shift shift2 = persistShift(shiftId2, date2, endTime2, startTime2);

    Set<Shift> setOfShifts1 = new HashSet<>();
    setOfShifts1.add(shift1);

    Set<Shift> setOfShifts2 = new HashSet<>();
    setOfShifts1.add(shift2);

    Employee employee1 = persistEmployee(email1, username1, password1, address1, setOfShifts1,
        false);
    persistEmployee(email2, username2, password2, address2, setOfShifts2, false);

    List<Employee> listOfEmployees = employeeRepository.findEmployeesByUsername(username1);
    verifyContains(employee1, listOfEmployees);
    assertEquals(1, listOfEmployees.size()); // Checking that only employee1 was retrieved

  }

  /**
   * Testing the persistence and loading for the Employee Repository. This tests the
   * findEmployeesByShifts method
   *
   * @author Theo Ghanem & Chris Hatoum
   */
  @Test
  public void testPersistAndLoadEmployeesByShifts() {

    Address address = persistAddress(fullName1, streetName1, city1, postalCode1, addressId1,
        streetNumber1);
    Shift shift = persistShift(shiftId1, date1, endTime1, startTime1);

    Set<Shift> setOfShifts = new HashSet<>();
    setOfShifts.add(shift);

    Employee employee = persistEmployee(email1, username1, password1, address, setOfShifts, false);

    Employee loadedEmployee = employeeRepository.findEmployeeByShifts(shift);

    verify(employee, loadedEmployee);

  }

  /**
   * Testing the persistence and loading for the Employee Repository. This tests the
   * findEmployeeByAddresses method
   *
   * @author Theo Ghanem & Chris Hatoum
   */
  @Test
  public void testPersistAndLoadEmployeeByAddress() {

    Address address = persistAddress(fullName1, streetName1, city1, postalCode1, addressId1,
        streetNumber1);
    Shift shift = persistShift(shiftId1, date1, endTime1, startTime1);

    Set<Shift> setOfShifts = new HashSet<>();
    setOfShifts.add(shift);

    Employee employee = persistEmployee(email1, username1, password1, address, setOfShifts, false);
    Employee actualEmployee = employeeRepository.findEmployeeByAddress(address);
    verify(employee, actualEmployee);
  }

  /**
   * Testing the persistence and loading for the Employee Repository. This tests the
   * findEmployeeByDisabled method
   *
   * @author Theo Ghanem & Chris Hatoum
   */
  @Test
  public void testPersistAndLoadEmployeeByDisabled() {

    Address address = persistAddress(fullName1, streetName1, city1, postalCode1, addressId1,
        streetNumber1);
    Shift shift = persistShift(shiftId1, date1, endTime1, startTime1);

    Set<Shift> setOfShifts = new HashSet<>();
    setOfShifts.add(shift);
    Employee employee = persistEmployee(email1, username1, password1, address, setOfShifts, true);
    List<Employee> listOfEmployees = employeeRepository.findEmployeesByDisabled(true);
    verifyContains(employee, listOfEmployees);

  }

}
