package ca.mcgill.ecse321.GSSS.dao;

import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.Shift;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test the persistence and loading of the Employee Repository
 *
 * @author Theo Ghanem & Chris Hatoum
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestEmployeePersistence {

	/**
	 * Deletes all the database contents. Goes from the independent to the dependent
	 * classes to avoid exceptions being thrown when deleting
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
	String username = "bobthebuilder";
	String email = "bob@email.com";
	String password = "bobpassword";
	String fullName = "Bob Builder";
	String streetName = "Crescent";
	String city = "Montreal";
	int streetNumber = 43;
	String postalCode = "W2S L0S";
	String addressId = UUID.randomUUID().toString();
	Date date = Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
	Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
	Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
	String shiftId = UUID.randomUUID().toString();

	/**
	 * This method asserts whether the actual Employee matches the expected one.
	 * 
	 * @author Chris Hatoum & Theo Ghanem
	 * @param expected
	 * @param actual
	 */
	private void verify(Employee expected, Employee actual) {

		assertNotNull(actual);
		assertEquals(expected.getUsername(), actual.getUsername());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getPassword(), actual.getPassword());

		assertNotNull(actual.getShifts());
		assertNotNull(expected.getShifts());
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
	 *
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
	 * This method creates an address from the specified parameters, and saves it to
	 * the database.
	 * 
	 * @author Chris Hatoum & Theo Ghanem
	 * 
	 * @param fullName,     full name of the employee
	 * @param streetName,   street name of the employee
	 * @param city,         city of the employee
	 * @param postalCode,   postal code of the employee
	 * @param addressId,    unique UUID for the address
	 * @param streetNumber, street number of the employee
	 * @return
	 */
	private Address persistAddress(String fullName, String streetName, String city, String postalCode, String addressId,
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
	 * 
	 * This method creates a shift from the specified parameters, and saves it to
	 * the database.
	 * 
	 * @author Chris Hatoum & Theo Ghanem
	 * 
	 * @param shiftId,   unique UUID for the shift
	 * @param date,      date of the shift
	 * @param endTime,   end time of the shift
	 * @param startTime, start time of the shift
	 * @return
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
	 * This method creates an employee from the specified parameters, and saves it
	 * to the database.
	 * 
	 * @author Chris Hatoum & Theo Ghanem
	 * 
	 * @param email,       the employee's email
	 * @param password,    the employee's password
	 * @param address,     the address of the employee
	 * @param setOfShifts, a set of shifts
	 * @return
	 */
	private Employee persistEmployee(String email, String password, Address address, Set<Shift> setOfShifts,
			Boolean bool) {
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
	 * Testing the persistence and loading for the Employee Repository. This tests
	 * the findEmployeeByEmail method
	 * 
	 * @author Theo Ghanem & Chris Hatoum
	 */
	@Test
	public void testPersistAndLoadEmployeeByEmail() {

		Address address = persistAddress(fullName, streetName, city, postalCode, addressId, streetNumber);
		Shift shift = persistShift(shiftId, date, endTime, startTime);

		Set<Shift> setOfShifts = new HashSet<>();
		setOfShifts.add(shift);

		Employee employee = persistEmployee(email, password, address, setOfShifts, false);
		Employee actualEmployee = employeeRepository.findEmployeeByEmail(email);
		verify(employee, actualEmployee);

	}

	/**
	 * Testing the persistence and loading for the Employee Repository. This tests
	 * the findEmployeesByUsername method
	 * 
	 * @author Theo Ghanem & Chris Hatoum
	 */
	@Test
	public void testPersistAndLoadEmployeesByUsername() {

		Address address = persistAddress(fullName, streetName, city, postalCode, addressId, streetNumber);
		Shift shift = persistShift(shiftId, date, endTime, startTime);

		Set<Shift> setOfShifts = new HashSet<>();
		setOfShifts.add(shift);

		Employee employee = persistEmployee(email, password, address, setOfShifts, false);

		List<Employee> listOfEmployees = new ArrayList<Employee>();
		listOfEmployees = employeeRepository.findEmployeesByUsername(username);
		verify(employee, listOfEmployees.get(0));

	}

	/**
	 * Testing the persistence and loading for the Employee Repository. This tests
	 * the findEmployeesByShifts method
	 * 
	 * @author Theo Ghanem & Chris Hatoum
	 */
	@Test
	public void testPersistAndLoadEmployeesByShifts() {

		Address address = persistAddress(fullName, streetName, city, postalCode, addressId, streetNumber);
		Shift shift = persistShift(shiftId, date, endTime, startTime);

		Set<Shift> setOfShifts = new HashSet<>();
		setOfShifts.add(shift);

		Employee employee = persistEmployee(email, password, address, setOfShifts, false);

		Employee loadedEmployee = employeeRepository.findEmployeeByShifts(shift);

		verify(employee, loadedEmployee);

	}

	/**
	 * Testing the persistence and loading for the Employee Repository. This tests
	 * the findEmployeeByAddresses method
	 * 
	 * @author Theo Ghanem & Chris Hatoum
	 */
	@Test
	public void testPersistAndLoadEmployeeByAddress() {

		Address address = persistAddress(fullName, streetName, city, postalCode, addressId, streetNumber);
		Shift shift = persistShift(shiftId, date, endTime, startTime);

		Set<Shift> setOfShifts = new HashSet<>();
		setOfShifts.add(shift);

		Employee employee = persistEmployee(email, password, address, setOfShifts, false);
		Employee actualEmployee = employeeRepository.findEmployeeByAddress(address);
		verify(employee, actualEmployee);
	}

	/**
	 * Testing the persistence and loading for the Employee Repository. This tests
	 * the findEmployeeByDisabled method
	 * 
	 * @author Theo Ghanem & Chris Hatoum
	 */
	@Test
	public void testPersistAndLoadEmployeeByDisabled() {

		Address address = persistAddress(fullName, streetName, city, postalCode, addressId, streetNumber);
		Shift shift = persistShift(shiftId, date, endTime, startTime);

		Set<Shift> setOfShifts = new HashSet<>();
		setOfShifts.add(shift);
		Employee employee = persistEmployee(email, password, address, setOfShifts, true);
		List<Employee> listOfEmployees = employeeRepository.findEmployeesByDisabled(true);
		verifyContains(employee, listOfEmployees);

	}

}
