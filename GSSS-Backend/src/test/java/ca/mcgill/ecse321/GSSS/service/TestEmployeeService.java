package ca.mcgill.ecse321.GSSS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.GSSS.dao.EmployeeRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Shift;


@ExtendWith(MockitoExtension.class)
public class TestEmployeeService {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setMockOutput(){
        // Set each CRUD method to its mock
        lenient().when(employeeRepository.findEmployeeByEmail(anyString())).thenAnswer(MockRepository::findEmployeeByEmail);
        lenient().when(employeeRepository.findEmployeesByUsername(anyString())).thenAnswer(MockRepository::findEmployeeByUsername);
        lenient().when(employeeRepository.findEmployeeByShifts(any(Shift.class))).thenAnswer(MockRepository::findEmployeeByShifts);
        lenient().when(employeeRepository.findEmployeeByAddress(any(Address.class))).thenAnswer(MockRepository::findEmployeeByAddress);
        lenient().when(employeeRepository.findEmployeesByDisabled(anyBoolean())).thenAnswer(MockRepository::findEmployeesByDisabled);
        lenient().when(employeeRepository.save(any(Employee.class))).thenAnswer(MockRepository::save);
    }

    @Test
    public void testGetEmployeeByEmail(){
        Employee fetched = employeeService.getEmployeeByEmail(MockDatabase.employee2.getEmail());
        assertEquals(MockDatabase.employee2, fetched);
    }



    /**
     * This class holds all of the mock methods of the CRUD repository.
     */
    class MockRepository{

        static Employee findEmployeeByEmail(InvocationOnMock invocation){
            String email = (String) invocation.getArgument(0);
            if(email.equals(MockDatabase.employee1.getEmail())) return MockDatabase.employee1;
            if(email.equals(MockDatabase.employee2.getEmail())) return MockDatabase.employee2;
            if(email.equals(MockDatabase.employee3.getEmail())) return MockDatabase.employee3;
            return null;
        }

        static List<Employee> findEmployeeByUsername(InvocationOnMock invocation){
            String username = (String) invocation.getArgument(0);
            List<Employee> employees = new ArrayList<Employee>();
            if(username.equals(MockDatabase.employee1.getUsername())) employees.add(MockDatabase.employee1);
            if(username.equals(MockDatabase.employee2.getUsername())) employees.add(MockDatabase.employee2);
            if(username.equals(MockDatabase.employee3.getUsername())) employees.add(MockDatabase.employee3);
            return employees;
        }

        static Employee findEmployeeByShifts(InvocationOnMock invocation){
            Shift shift = (Shift) invocation.getArgument(0);
            if(MockDatabase.employee1.getShifts().contains(shift)) return MockDatabase.employee1;
            if(MockDatabase.employee2.getShifts().contains(shift)) return MockDatabase.employee2;
            if(MockDatabase.employee3.getShifts().contains(shift)) return MockDatabase.employee3;
            return null;
        }

        static Employee findEmployeeByAddress(InvocationOnMock invocation){
            Address address = (Address) invocation.getArgument(0);
            if(address.equals(MockDatabase.employee1.getAddress())) return MockDatabase.employee1;
            if(address.equals(MockDatabase.employee2.getAddress())) return MockDatabase.employee2;
            if(address.equals(MockDatabase.employee3.getAddress())) return MockDatabase.employee3;
            return null;
        }

        static List<Employee> findEmployeesByDisabled(InvocationOnMock invocation){
            boolean disabled = (boolean) invocation.getArgument(0);
            List<Employee> employees = new ArrayList<Employee>();
            if(disabled == MockDatabase.employee1.isDisabled()) employees.add(MockDatabase.employee1);
            if(disabled == MockDatabase.employee2.isDisabled()) employees.add(MockDatabase.employee2);
            if(disabled == MockDatabase.employee3.isDisabled()) employees.add(MockDatabase.employee3);
            return employees;
        }

        static Employee save(InvocationOnMock invocation){
            return (Employee) invocation.getArgument(0);
        }

        static List<Employee> findAll(InvocationOnMock invocation){
            List<Employee> employees = new ArrayList<Employee>();
            employees.add(MockDatabase.employee1);
            employees.add(MockDatabase.employee2);
            employees.add(MockDatabase.employee3);
            return employees;
        }

    }
    



    /**
     * This class mock data for tests.
     */
    final static class MockDatabase{

        static Employee employee1 = new Employee();
        static Address address1 = new Address();
        static Set<Shift> shifts1 = new HashSet<Shift>();
        static Shift shift1 = new Shift();

        static Employee employee2 = new Employee();
        static Address address2 = new Address();
        static Set<Shift> shifts2 = new HashSet<Shift>();
        static Shift shift2 = new Shift();

        static Employee employee3 = new Employee();
        static Address address3 = new Address();
        static Set<Shift> shifts3 = new HashSet<Shift>();
        static Shift shift3 = new Shift();

        static{
            employee1.setEmail("employee1@email.com");
            employee1.setUsername("John Smith");
            employee1.setPassword("w34yfr1uy45324u");
            employee1.setSalt("jg34fd1h243214fg142");
            employee1.setDisabled(false);

            address1.setFullName("John Smith");
            address1.setId(UUID.randomUUID().toString());
            address1.setStreetName("Street st");
            address1.setStreetNumber(1000);
            address1.setCity("Montreal");
            address1.setPostalCode("A1A 2B2");
            employee1.setAddress(address1);

            shift1.setId(UUID.randomUUID().toString());
            shift1.setDate(Date.valueOf("2022-03-08"));
            shift1.setStartTime(Time.valueOf("09:30:00"));
            shift1.setEndTime(Time.valueOf("17:00:00"));
            shifts1.add(shift1);
            employee1.setShifts(shifts1);

            employee2.setEmail("employee2@email.com");
            employee2.setUsername("John Doe");
            employee2.setPassword("dsagfdsa54g23dasg2d");
            employee2.setSalt("safddsa7df6576a");
            employee2.setDisabled(false);

            address2.setFullName("John Doe");
            address2.setId(UUID.randomUUID().toString());
            address2.setStreetName("Drive st");
            address2.setStreetNumber(2000);
            address2.setCity("Montreal");
            address2.setPostalCode("A2A 1B1");
            employee2.setAddress(address2);

            shift2.setId(UUID.randomUUID().toString());
            shift2.setDate(Date.valueOf("2022-03-08"));
            shift2.setStartTime(Time.valueOf("17:00:00"));
            shift2.setEndTime(Time.valueOf("02:30:00"));
            shifts2.add(shift2);
            employee2.setShifts(shifts2);
            
            employee3.setEmail("employee3@email.com");
            employee3.setUsername("John Smith");
            employee3.setPassword("g43f2h14dg24dh");
            employee3.setSalt("ghj123f4hj2314");
            employee3.setDisabled(true);

            address3.setFullName("John Smith");
            address3.setId(UUID.randomUUID().toString());
            address3.setStreetName("Avenue st");
            address3.setStreetNumber(3000);
            address3.setCity("Toronto");
            address3.setPostalCode("B1B 2A2");
            employee3.setAddress(address3);

            shift3.setId(UUID.randomUUID().toString());
            shift3.setDate(Date.valueOf("2022-03-08"));
            shift3.setStartTime(Time.valueOf("17:00:00"));
            shift3.setEndTime(Time.valueOf("02:30:00"));
            shifts3.add(shift3);
            employee3.setShifts(shifts3);
        }

    }

}