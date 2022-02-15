package ca.mcgill.ecse321.GSSS.dao;

import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.Shift;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestEmployeePersistence {

    @Autowired
    private EmployeeRepository employeeRepository;
    private AddressRepository addressRepository;
    private ShiftRepository shiftRepository;
    private PurchaseRepository purchaseRepository;

    //Initialize variables that will be used to successfully create a complete employee
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
    String shiftId =  UUID.randomUUID().toString();
    Time purchaseTime = java.sql.Time.valueOf(LocalTime.of(12, 25));
    Date purchaseDate = Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
    String purchaseId =  UUID.randomUUID().toString();


    /**
     * Testing the persistence and loading for the Employee Repository.
     * This tests the findEmployeeByEmail method
     * @author Theo Ghanem
     */
    @Test
    public void testPersistAndLoadEmployeeByEmail(){


        Address address = new Address();
        address.setFullName(fullName);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setId(addressId);

        Employee employee = new Employee();
        employee.setEmail(email);
        employee.setUsername(username);
        employee.setPassword(password);
        employee.setAddress(address);


        Shift shift = new Shift();
        shift.setDate(date);
        shift.setEndTime(endTime);
        shift.setStartTime(startTime);
        HashSet<Shift> setOfShifts = new HashSet<>();
        employee.setShifts(setOfShifts);


        employeeRepository.save(employee);
        addressRepository.save(address);
        shiftRepository.save(shift);

        employee=null;

        employee = employeeRepository.findEmployeeByEmail(email);

        assertNotNull(employee);
        assertEquals(employee.getUsername(), username);
        assertEquals(employee.getEmail(), email);
        assertEquals(employee.getPassword(), password);
        assertEquals(employee.getShifts(), setOfShifts);
        assertEquals(employee.getAddress(), address);

    }

    /**
     * Testing the persistence and loading for the Employee Repository.
     * This tests the findEmployeesByUsername method
     * @author Theo Ghanem
     */
    @Test
    public void testPersistAndLoadEmployeesByUsername(){

        Address address = new Address();
        address.setFullName(fullName);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setId(addressId);

        Employee employee = new Employee();
        employee.setEmail(email);
        employee.setUsername(username);
        employee.setPassword(password);
        employee.setAddress(address);


        Shift shift = new Shift();
        shift.setDate(date);
        shift.setEndTime(endTime);
        shift.setStartTime(startTime);
        HashSet<Shift> setOfShifts = new HashSet<>();
        employee.setShifts(setOfShifts);


        employeeRepository.save(employee);
        addressRepository.save(address);
        shiftRepository.save(shift);

        employee=null;
        List<Employee> listOfEmployees;
        listOfEmployees = employeeRepository.findEmployeesByUsername(username);
        employee = listOfEmployees.get(0);

        assertNotNull(listOfEmployees);
        assertEquals(employee.getUsername(), username);
        assertEquals(employee.getEmail(), email);
        assertEquals(employee.getPassword(), password);
        assertEquals(employee.getShifts(), setOfShifts);
        assertEquals(employee.getAddress(), address);

    }

    /**
     * Testing the persistence and loading for the Employee Repository.
     * This tests the findEmployeesByShifts method
     * @author Theo Ghanem
     */
    @Test
    public void testPersistAndLoadEmployeesByShifts(){


        Address address = new Address();
        address.setFullName(fullName);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setId(addressId);

        Employee employee = new Employee();
        employee.setEmail(email);
        employee.setUsername(username);
        employee.setPassword(password);
        employee.setAddress(address);


        Shift shift = new Shift();
        shift.setDate(date);
        shift.setEndTime(endTime);
        shift.setStartTime(startTime);
        HashSet<Shift> setOfShifts = new HashSet<>();
        employee.setShifts(setOfShifts);


        employeeRepository.save(employee);
        addressRepository.save(address);
        shiftRepository.save(shift);

        employee=null;
        List<Employee> listOfEmployees;
        listOfEmployees = employeeRepository.findEmployeesByShifts(shift);
        employee = listOfEmployees.get(0);

        assertNotNull(listOfEmployees);
        assertEquals(employee.getUsername(), username);
        assertEquals(employee.getEmail(), email);
        assertEquals(employee.getPassword(), password);
        assertEquals(employee.getShifts(), setOfShifts);
        assertEquals(employee.getAddress(), address);

    }

    /**
     * Testing the persistence and loading for the Employee Repository.
     * This tests the findEmployeeByAddresses method
     * @author Theo Ghanem
     */
    @Test
    public void testPersistAndLoadEmployeeByAddress(){

        Address address = new Address();
        address.setFullName(fullName);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setId(addressId);

        Employee employee = new Employee();
        employee.setEmail(email);
        employee.setUsername(username);
        employee.setPassword(password);
        employee.setAddress(address);


        Shift shift = new Shift();
        shift.setDate(date);
        shift.setEndTime(endTime);
        shift.setStartTime(startTime);
        HashSet<Shift> setOfShifts = new HashSet<>();
        employee.setShifts(setOfShifts);


        employeeRepository.save(employee);
        addressRepository.save(address);
        shiftRepository.save(shift);

        employee=null;

        employee = employeeRepository.findEmployeeByAddress(address);

        assertNotNull(employee);
        assertEquals(employee.getUsername(), username);
        assertEquals(employee.getEmail(), email);
        assertEquals(employee.getPassword(), password);
        assertEquals(employee.getShifts(), setOfShifts);
        assertEquals(employee.getAddress(), address);
    }

    /**
     * Testing the persistence and loading for the Employee Repository.
     * This tests the findEmployeeByDisabled method
     * @author Theo Ghanem
     */
    @Test
    public void testPersistAndLoadEmployeeByDisabled(){

        Address address = new Address();
        address.setFullName(fullName);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setId(addressId);

        Employee employee = new Employee();
        employee.setEmail(email);
        employee.setUsername(username);
        employee.setPassword(password);
        employee.setAddress(address);


        employee.setDisabled(true);


        employeeRepository.save(employee);
        addressRepository.save(address);

        employee=null;

        List<Employee> listOfDisabledEmployees;
        listOfDisabledEmployees = employeeRepository.findEmployeesByDisabled(true);
        employee = listOfDisabledEmployees.get(0);

        assertNotNull(employee);
        assertEquals(employee.getUsername(), username);
        assertEquals(employee.getEmail(), email);
        assertEquals(employee.getPassword(), password);
        assertEquals(employee.getAddress(), address);

    }
}