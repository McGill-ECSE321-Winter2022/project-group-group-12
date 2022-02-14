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
import java.util.Set;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestEmployeePersistence {

    @Autowired
    private EmployeeRepository employeeRepository;

    //Initialize variables that will be used to succesfully create a complete employee
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
        employee.setAddresses((Set<Address>) address);

        Shift shift = new Shift();
        shift.setDate(date);
        shift.setEndTime(endTime);
        shift.setStartTime(startTime);
        employee.setShifts((Set<Shift>) shift);

        Purchase purchase = new Purchase();
        purchase.setDate(purchaseDate);
        purchase.setTime(purchaseTime);
        purchase.setId(purchaseId);
        employee.setAssignedPurchases((Set<Purchase>) purchase);

        employeeRepository.save(employee);

        employee=null;

        employee = employeeRepository.findEmployeeByEmail(email);

        assertNotNull(employee);
        assertEquals(employee.getUsername(), username);
        assertEquals(employee.getEmail(), email);
        assertEquals(employee.getPassword(), password);
        assertEquals(employee.getShifts(), shift);
        assertEquals(employee.getAddresses(), address);
        assertEquals(employee.getAssignedPurchases(), purchase);

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
        employee.setAddresses((Set<Address>) address);

        Shift shift = new Shift();
        shift.setDate(date);
        shift.setEndTime(endTime);
        shift.setStartTime(startTime);
        employee.setShifts((Set<Shift>) shift);

        Purchase purchase = new Purchase();
        purchase.setDate(purchaseDate);
        purchase.setTime(purchaseTime);
        purchase.setId(purchaseId);
        employee.setAssignedPurchases((Set<Purchase>) purchase);

        employeeRepository.save(employee);

        employee=null;

        employee = (Employee) employeeRepository.findEmployeesByUsername(username);

        assertNotNull(employee);
        assertEquals(employee.getUsername(), username);
        assertEquals(employee.getEmail(), email);
        assertEquals(employee.getPassword(), password);
        assertEquals(employee.getShifts(), shift);
        assertEquals(employee.getAddresses(), address);
        assertEquals(employee.getAssignedPurchases(), purchase);

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
        employee.setAddresses((Set<Address>) address);

        Shift shift = new Shift();
        shift.setDate(date);
        shift.setEndTime(endTime);
        shift.setStartTime(startTime);
        employee.setShifts((Set<Shift>) shift);

        Purchase purchase = new Purchase();
        purchase.setDate(purchaseDate);
        purchase.setTime(purchaseTime);
        purchase.setId(purchaseId);
        employee.setAssignedPurchases((Set<Purchase>) purchase);

        employeeRepository.save(employee);

        employee=null;

        employee = (Employee) employeeRepository.findEmployeesByShifts(shift);

        assertNotNull(employee);
        assertEquals(employee.getUsername(), username);
        assertEquals(employee.getEmail(), email);
        assertEquals(employee.getPassword(), password);
        assertEquals(employee.getShifts(), shift);
        assertEquals(employee.getAddresses(), address);
        assertEquals(employee.getAssignedPurchases(), purchase);

    }

    /**
     * Testing the persistence and loading for the Employee Repository.
     * This tests the findEmployeeByAddresses method
     * @author Theo Ghanem
     */
    @Test
    public void testPersistAndLoadEmployeeByAddresses(){

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
        employee.setAddresses((Set<Address>) address);

        Shift shift = new Shift();
        shift.setDate(date);
        shift.setEndTime(endTime);
        shift.setStartTime(startTime);
        employee.setShifts((Set<Shift>) shift);

        Purchase purchase = new Purchase();
        purchase.setDate(purchaseDate);
        purchase.setTime(purchaseTime);
        purchase.setId(purchaseId);
        employee.setAssignedPurchases((Set<Purchase>) purchase);

        employeeRepository.save(employee);

        employee=null;

        employee = employeeRepository.findEmployeeByAddresses(address);

        assertNotNull(employee);
        assertEquals(employee.getUsername(), username);
        assertEquals(employee.getEmail(), email);
        assertEquals(employee.getPassword(), password);
        assertEquals(employee.getShifts(), shift);
        assertEquals(employee.getAddresses(), address);
        assertEquals(employee.getAssignedPurchases(), purchase);
    }

    /**
     * Testing the persistence and loading for the Employee Repository.
     * This tests the findEmployeeByAssignedPurchases method
     * @author Theo Ghanem
     */
    @Test
    public void testPersistAndLoadEmployeeByAssignedPurchases(){

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
        employee.setAddresses((Set<Address>) address);

        Shift shift = new Shift();
        shift.setDate(date);
        shift.setEndTime(endTime);
        shift.setStartTime(startTime);
        employee.setShifts((Set<Shift>) shift);

        Purchase purchase = new Purchase();
        purchase.setDate(purchaseDate);
        purchase.setTime(purchaseTime);
        purchase.setId(purchaseId);
        employee.setAssignedPurchases((Set<Purchase>) purchase);

        employeeRepository.save(employee);

        employee=null;

        employee = employeeRepository.findEmployeeByAssignedPurchases(purchase);

        assertNotNull(employee);
        assertEquals(employee.getUsername(), username);
        assertEquals(employee.getEmail(), email);
        assertEquals(employee.getPassword(), password);
        assertEquals(employee.getShifts(), shift);
        assertEquals(employee.getAddresses(), address);
        assertEquals(employee.getAssignedPurchases(), purchase);

    }
}
