package ca.mcgill.ecse321.GSSS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
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
import ca.mcgill.ecse321.GSSS.dao.CustomerRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import ca.mcgill.ecse321.GSSS.model.OrderStatus;
import ca.mcgill.ecse321.GSSS.model.OrderType;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.Shift;

@ExtendWith(MockitoExtension.class)
public class TestCustomerService {

  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private CustomerService customerService;

  @BeforeEach
  public void setMockOutput() {
    // Set each CRUD method to its mock
    lenient().when(customerRepository.findCustomerByEmail(anyString()))
        .thenAnswer(MockRepository::findCustomerByEmail);
    lenient().when(customerRepository.findCustomersByUsername(anyString()))
        .thenAnswer(MockRepository::findCustomersByUsername);
    lenient().when(customerRepository.findCustomerByPurchases(any(Purchase.class)))
        .thenAnswer(MockRepository::findCustomerByPurchases);
    lenient().when(customerRepository.findCustomerByAddress(any(Address.class)))
        .thenAnswer(MockRepository::findCustomerByAddress);
    lenient().when(customerRepository.findCustomersByDisabled(anyBoolean()))
        .thenAnswer(MockRepository::findCustomersByDisabled);
    lenient().when(customerRepository.findAll()).thenAnswer(MockRepository::findAll);
    lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(MockRepository::save);
  }

  @Test
  public void testCreateEmployee_Success() {
    Customer customer = new Customer();
    customer.setEmail("customer@email.com");
    customer.setUsername("Test Smither");
    customer.setPassword("w34yfr1uy45324u");
    customer.setAddress(new Address());
    Customer created = customerService.createCustomer(customer.getUsername(), customer.getEmail(),
        customer.getPassword(), customer.getAddress());
    assertNotNull(created);
    assertEquals(customer.getEmail(), created.getEmail());
    assertEquals(customer.getUsername(), created.getUsername());
    assertEquals(customer.getAddress(), created.getAddress());
  }

  @Test
  public void testCreateCustomer_NullEmail() {
    try {
      customerService.createCustomer("username", null, "password", new Address());
    } catch (IllegalArgumentException e) {
      assertEquals("Customer email cannot be empty! Email not valid! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateCustomer_EmptyEmail() {
    try {
      customerService.createCustomer("username", "    ", "password", new Address());
    } catch (IllegalArgumentException e) {
      assertEquals("Customer email cannot be empty! Email not valid! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateCustomer_InvalidEmail() {
    try {
      customerService.createCustomer("username", "invalidemail", "password", new Address());
    } catch (IllegalArgumentException e) {
      assertEquals("Email not valid! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateCustomer_NullUsername() {
    try {
      customerService.createCustomer(null, "customer@email.com", "password", new Address());
    } catch (IllegalArgumentException e) {
      assertEquals("Customer username cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateCustomer_EmptyUsername() {
    try {
      customerService.createCustomer("   ", "employee@email.com", "password", new Address());
    } catch (IllegalArgumentException e) {
      assertEquals("Customer username cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateCustomer_NullPassword() {
    try {
      customerService.createCustomer("username", "employee@email.com", null, new Address());
    } catch (IllegalArgumentException e) {
      assertEquals("Customer password cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateCustomer_EmptyPassword() {
    try {
      customerService.createCustomer("username", "customer@email.com", "  ", new Address());
    } catch (IllegalArgumentException e) {
      assertEquals("Customer password cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateCustomer_NullAddress() {
    try {
      customerService.createCustomer("username", "employee@email.com", "password", null);
    } catch (IllegalArgumentException e) {
      assertEquals("Address cannot be null! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateCustomer_NullAll() {
    try {
      customerService.createCustomer(null, null, null, null);
    } catch (IllegalArgumentException e) {
      assertEquals(
          "Customer email cannot be empty! Email not valid! Customer username cannot be empty! Customer password cannot be empty! Address cannot be null! ",
          e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetCustomerByEmail_Success() {
    Customer fetched = customerService.getCustomerByEmail(MockDatabase.customer2.getEmail());
    assertNotNull(fetched);
    assertEquals(MockDatabase.customer2, fetched);
  }

  @Test
  public void testGetCustomerByEmail_NullEmail() {
    try {
      customerService.getCustomerByEmail(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Customer email cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetCustomerByEmail_EmptyEmail() {
    try {
      customerService.getCustomerByEmail("   ");
    } catch (IllegalArgumentException e) {
      assertEquals("Customer email cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetCustomerByEmail_NotInDb() {
    try {
      customerService.getCustomerByEmail("not_a_registered_customer@email.com");
    } catch (NoSuchElementException e) {
      assertEquals("No customer with email not_a_registered_customer@email.com exists!",
          e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetCustomersByUsername_Success() {
    List<Customer> fetched = customerService.getCustomersByUsername("John Smith");
    List<Customer> expected = new ArrayList<Customer>();
    expected.add(MockDatabase.customer1);
    expected.add(MockDatabase.customer3);
    assertNotNull(fetched);
    assertEquals(expected.size(), fetched.size());
    for (Customer e : fetched)
      assertTrue(expected.contains(e));
  }

  @Test
  public void testGetCustomersByUsername_NullUsername() {
    try {
      customerService.getCustomersByUsername(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Customer username cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetEmployeesByUsername_EmptyUsername() {
    try {
      customerService.getCustomersByUsername("     ");
    } catch (IllegalArgumentException e) {
      assertEquals("Customer username cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetEmployeeByUsername_NotInDb() {
    try {
      customerService.getCustomersByUsername("John Not Registered");
    } catch (NoSuchElementException e) {
      assertEquals("No customer with username John Not Registered exists!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetCustomerByPurchase_Success() {
    Customer fetched = customerService.getCustomerByPurchase(MockDatabase.purchase1);
    assertNotNull(fetched);
    assertEquals(MockDatabase.customer1, fetched);
  }

  @Test
  public void testGetCustomerByPurchase_NullPurchase() {
    try {
      customerService.getCustomerByPurchase(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Purchase cannot be null!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetCustomerByPurchase_NotInDb() {
    Purchase purchase = new Purchase();
    purchase.setId(UUID.randomUUID().toString());
    purchase.setDate(Date.valueOf("2017-03-03"));
    purchase.setItems(MockDatabase.items1);
    purchase.setEmployee(MockDatabase.employee1);
    purchase.setOrderStatus(OrderStatus.Completed);
    purchase.setOrderType(OrderType.Pickup);
    try {
      customerService.getCustomerByPurchase(purchase);
    } catch (NoSuchElementException e) {
      assertEquals("No customer with such purchase exists!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetAllCustomers_Success() {
    List<Customer> fetched = customerService.getAllCustomers();
    List<Customer> expected = new ArrayList<Customer>();
    expected.add(MockDatabase.customer1);
    expected.add(MockDatabase.customer2);
    expected.add(MockDatabase.customer3);
    assertNotNull(fetched);
    assertEquals(expected.size(), fetched.size());
    for (Customer e : fetched)
      assertTrue(expected.contains(e));
  }

  @Test
  public void testGetCustomerByAccountState_Success() {
    List<Customer> fetched = customerService.getCustomersByAccountState(false);
    List<Customer> expected = new ArrayList<Customer>();
    expected.add(MockDatabase.customer1);
    expected.add(MockDatabase.customer2);
    assertNotNull(fetched);
    assertEquals(expected.size(), fetched.size());
    for (Customer e : fetched)
      assertTrue(expected.contains(e));
  }

  @Test
  public void testDeleteCustomer_Success() {
    customerService.deleteCustomer("customer1@email.com");

    verify(customerRepository, times(1))
        .deleteById(argThat((String i) -> MockDatabase.customer1.getEmail().equals(i)));
    verify(customerRepository, times(0))
        .deleteById(argThat((String i) -> !MockDatabase.customer1.getEmail().equals(i)));
  }

  @Test
  public void testDeleteCustomer_NullEmail() {
    try {
      customerService.deleteCustomer(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Customer email cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testDeleteCustomer_EmptyEmail() {
    try {
      customerService.deleteCustomer("  ");
    } catch (IllegalArgumentException e) {
      assertEquals("Customer email cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyCustomer_Success() {
    Customer modified = customerService.modifyCustomer("new username", "new pw",
        MockDatabase.customer1.getEmail(), MockDatabase.customerAddress3, true);
    assertNotNull(modified);
    assertEquals(MockDatabase.customer1.getEmail(), modified.getEmail());
    assertEquals("new username", modified.getUsername());
    assertEquals(MockDatabase.customerAddress3, modified.getAddress());
    assertEquals(true, modified.isDisabled());
    assertEquals(Utility.hashAndSaltPassword("new pw", MockDatabase.customer1.getSalt()),
        modified.getPassword());
  }

  @Test
  public void testModifyCustomer_NullEmail() {
    try {
      customerService.modifyCustomer("username", "new pw", null, new Address(), false);
    } catch (IllegalArgumentException e) {
      assertEquals("Customer email cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyCustomer_EmptyEmail() {
    try {
      customerService.modifyCustomer("username", "new pw", "    ", new Address(), false);
    } catch (IllegalArgumentException e) {
      assertEquals("Customer email cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyCustomer_NullUsername() {
    try {
      customerService.modifyCustomer(null, "new pw", MockDatabase.customer2.getEmail(),
          new Address(), false);
    } catch (IllegalArgumentException e) {
      assertEquals("Customer username cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyCustomer_EmptyUsername() {
    try {
      customerService.modifyCustomer("   ", "new pw", MockDatabase.customer2.getEmail(),
          new Address(), false);
    } catch (IllegalArgumentException e) {
      assertEquals("Customer username cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyCustomer_NullAddress() {
    try {
      customerService.modifyCustomer("username", "new pw", MockDatabase.customer2.getEmail(), null,
          false);
    } catch (IllegalArgumentException e) {
      assertEquals("Customer address cannot be null! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyCustomer_NotInDb() {
    try {
      customerService.modifyCustomer("username", "new pw", "not_registered@email.com",
          new Address(), false);
    } catch (NoSuchElementException e) {
      assertEquals("No customer with email not_registered@email.com exists!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyCustomer_NullPassword() {
    try {
      customerService.modifyCustomer("username", null, "not_registered@email.com", new Address(),
          false);
    } catch (IllegalArgumentException e) {
      assertEquals("Customer password cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyCustomer_EmptyPassword() {
    try {
      customerService.modifyCustomer("username", "   ", "not_registered@email.com", new Address(),
          false);
    } catch (IllegalArgumentException e) {
      assertEquals("Customer password cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testAddPurchase_Success() {
    Customer modified = customerService.addPurchase(MockDatabase.customer1, MockDatabase.purchase4);
    assertTrue(modified.getPurchases().contains(MockDatabase.purchase4));
  }

  @Test
  public void testAddPurchase_CustomerNull() {
    try {
      customerService.addPurchase(null, MockDatabase.purchase4);
    } catch (IllegalArgumentException e) {
      assertEquals("Customer cannot be null", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testAddPurchase_PurchaseNull() {
    try {
      customerService.addPurchase(MockDatabase.customer1, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Purchase cannot be null", e.getMessage());
      return;
    }
    fail();
  }



  /**
   * This class holds all of the mock methods of the CRUD repository.
   */
  class MockRepository {

    static Customer findCustomerByEmail(InvocationOnMock invocation) {
      String email = (String) invocation.getArgument(0);
      if (email.equals(MockDatabase.customer1.getEmail()))
        return MockDatabase.customer1;
      if (email.equals(MockDatabase.customer2.getEmail()))
        return MockDatabase.customer2;
      if (email.equals(MockDatabase.customer3.getEmail()))
        return MockDatabase.customer3;
      return null;
    }

    static List<Customer> findCustomersByUsername(InvocationOnMock invocation) {
      String username = (String) invocation.getArgument(0);
      List<Customer> customers = new ArrayList<Customer>();
      if (username.equals(MockDatabase.customer1.getUsername()))
        customers.add(MockDatabase.customer1);
      if (username.equals(MockDatabase.customer2.getUsername()))
        customers.add(MockDatabase.customer2);
      if (username.equals(MockDatabase.customer3.getUsername()))
        customers.add(MockDatabase.customer3);
      return customers;
    }

    static Customer findCustomerByPurchases(InvocationOnMock invocation) {
      Purchase purchase = (Purchase) invocation.getArgument(0);
      if (MockDatabase.customer1.getPurchases().contains(purchase))
        return MockDatabase.customer1;
      if (MockDatabase.customer2.getPurchases().contains(purchase))
        return MockDatabase.customer2;
      if (MockDatabase.customer3.getPurchases().contains(purchase))
        return MockDatabase.customer3;
      return null;
    }

    static Customer findCustomerByAddress(InvocationOnMock invocation) {
      Address address = (Address) invocation.getArgument(0);
      if (address.equals(MockDatabase.customer1.getAddress()))
        return MockDatabase.customer1;
      if (address.equals(MockDatabase.customer2.getAddress()))
        return MockDatabase.customer2;
      if (address.equals(MockDatabase.customer3.getAddress()))
        return MockDatabase.customer3;
      return null;
    }

    static List<Customer> findCustomersByDisabled(InvocationOnMock invocation) {
      boolean disabled = (boolean) invocation.getArgument(0);
      List<Customer> customers = new ArrayList<Customer>();
      if (disabled == MockDatabase.customer1.isDisabled())
        customers.add(MockDatabase.customer1);
      if (disabled == MockDatabase.customer2.isDisabled())
        customers.add(MockDatabase.customer2);
      if (disabled == MockDatabase.customer3.isDisabled())
        customers.add(MockDatabase.customer3);
      return customers;
    }

    static Customer save(InvocationOnMock invocation) {
      return (Customer) invocation.getArgument(0);
    }

    static List<Customer> findAll(InvocationOnMock invocation) {
      List<Customer> customers = new ArrayList<Customer>();
      customers.add(MockDatabase.customer1);
      customers.add(MockDatabase.customer2);
      customers.add(MockDatabase.customer3);
      return customers;
    }


  }



  /**
   * This class mock data for tests.
   */
  final static class MockDatabase {

    static ItemCategory category1 = new ItemCategory();
    static ItemCategory category2 = new ItemCategory();

    static Customer customer1 = new Customer();
    static Employee employee1 = new Employee();
    static Address customerAddress1 = new Address();
    static Address employeeAddress1 = new Address();
    static Set<Purchase> purchases1 = new HashSet<Purchase>();
    static Purchase purchase1 = new Purchase();
    static Set<Shift> shifts1 = new HashSet<Shift>();
    static Shift shift1 = new Shift();
    static Item item1 = new Item();
    static HashMap<Item, Integer> items1 = new HashMap<Item, Integer>();

    static Customer customer2 = new Customer();
    static Employee employee2 = new Employee();
    static Address customerAddress2 = new Address();
    static Address employeeAddress2 = new Address();
    static Set<Purchase> purchases2 = new HashSet<Purchase>();
    static Purchase purchase2 = new Purchase();
    static Set<Shift> shifts2 = new HashSet<Shift>();
    static Shift shift2 = new Shift();
    static Item item2 = new Item();
    static HashMap<Item, Integer> items2 = new HashMap<Item, Integer>();

    static Customer customer3 = new Customer();
    static Employee employee3 = new Employee();
    static Address customerAddress3 = new Address();
    static Address employeeAddress3 = new Address();
    static Set<Purchase> purchases3 = new HashSet<Purchase>();
    static Purchase purchase3 = new Purchase();
    static Set<Shift> shifts3 = new HashSet<Shift>();
    static Shift shift3 = new Shift();
    static Item item3 = new Item();
    static HashMap<Item, Integer> items3 = new HashMap<Item, Integer>();

    static Purchase purchase4 = new Purchase();
    static HashMap<Item, Integer> items4 = new HashMap<Item, Integer>();

    static {

      category1.setName("Food");
      category2.setName("Clothing");

      // Customer & Employee set 1

      customer1.setEmail("customer1@email.com");
      customer1.setUsername("John Smith");
      customer1.setPassword("w34yfr1uy45324u");
      customer1.setSalt(Utility.getSalt());
      customer1.setDisabled(false);

      employee1.setEmail("employee1@email.com");
      employee1.setUsername("John Smither");
      employee1.setPassword("w34yfr1uy45324u");
      employee1.setSalt(Utility.getSalt());
      employee1.setDisabled(false);

      customerAddress1.setFullName("John Smith");
      customerAddress1.setId(UUID.randomUUID().toString());
      customerAddress1.setStreetName("Street st");
      customerAddress1.setStreetNumber(1000);
      customerAddress1.setCity("Montreal");
      customerAddress1.setPostalCode("A1A 2B2");
      customer1.setAddress(customerAddress1);

      employeeAddress1.setFullName("John Smither");
      employeeAddress1.setId(UUID.randomUUID().toString());
      employeeAddress1.setStreetName("Street st");
      employeeAddress1.setStreetNumber(1000);
      employeeAddress1.setCity("Montreal");
      employeeAddress1.setPostalCode("A1A 2B2");
      employee1.setAddress(employeeAddress1);

      shift1.setId(UUID.randomUUID().toString());
      shift1.setDate(Date.valueOf("2022-03-08"));
      shift1.setStartTime(Time.valueOf("09:30:00"));
      shift1.setEndTime(Time.valueOf("17:00:00"));
      shifts1.add(shift1);
      employee1.setShifts(shifts1);

      item1.setName("Orange");
      item1.setAvailableForOrder(true);
      item1.setCategory(category1);
      item1.setDescription("An orange");
      item1.setImageUrl("imgur.com/orange");
      item1.setPrice(1);
      item1.setRemainingQuantity(9);
      item1.setStillAvailable(true);
      items1.put(item1, 2);

      purchase1.setDate(Date.valueOf("2022-03-08"));
      purchase1.setTime(Time.valueOf("09:30:00"));
      purchase1.setEmployee(employee1);
      purchase1.setId(UUID.randomUUID().toString());
      purchase1.setItems(items1);
      purchase1.setOrderStatus(OrderStatus.BeingPrepared);
      purchase1.setOrderType(OrderType.Delivery);
      purchases1.add(purchase1);
      customer1.setPurchases(purchases1);


      // Customer & Employee set 2

      customer2.setEmail("customer2@email.com");
      customer2.setUsername("Maya Raad");
      customer2.setPassword("w34yfr1uy45324u");
      customer2.setSalt(Utility.getSalt());
      customer2.setDisabled(false);

      employee2.setEmail("employee2@email.com");
      employee2.setUsername("Maya Raader");
      employee2.setPassword("w34yfr1uy45324u");
      employee2.setSalt(Utility.getSalt());
      employee2.setDisabled(false);

      customerAddress2.setFullName("Maya Raad");
      customerAddress2.setId(UUID.randomUUID().toString());
      customerAddress2.setStreetName("Street st");
      customerAddress2.setStreetNumber(1020);
      customerAddress2.setCity("Montreal");
      customerAddress2.setPostalCode("A1B 2B2");
      customer2.setAddress(customerAddress2);

      employeeAddress2.setFullName("Maya Raader");
      employeeAddress2.setId(UUID.randomUUID().toString());
      employeeAddress2.setStreetName("Street st");
      employeeAddress2.setStreetNumber(1000);
      employeeAddress2.setCity("Montreal");
      employeeAddress2.setPostalCode("ALA 2B2");
      employee2.setAddress(employeeAddress2);

      shift2.setId(UUID.randomUUID().toString());
      shift2.setDate(Date.valueOf("2022-03-10"));
      shift2.setStartTime(Time.valueOf("09:30:20"));
      shift2.setEndTime(Time.valueOf("17:32:00"));
      shifts2.add(shift2);
      employee2.setShifts(shifts2);

      item2.setName("Vest");
      item2.setAvailableForOrder(true);
      item2.setCategory(category2);
      item2.setDescription("A vest");
      item2.setImageUrl("imgur.com/vest");
      item2.setPrice(1);
      item2.setRemainingQuantity(9);
      item2.setStillAvailable(true);
      items2.put(item2, 1);

      purchase2.setDate(Date.valueOf("2022-03-02"));
      purchase2.setTime(Time.valueOf("09:30:00"));
      purchase2.setEmployee(employee2);
      purchase2.setId(UUID.randomUUID().toString());
      purchase2.setItems(items2);
      purchase2.setOrderStatus(OrderStatus.BeingPrepared);
      purchase2.setOrderType(OrderType.Delivery);
      purchases2.add(purchase2);
      customer2.setPurchases(purchases2);


      // Customer & Employee set 3

      customer3.setEmail("customer3@email.com");
      customer3.setUsername("John Smith");
      customer3.setPassword("w34yfr1uy45324u");
      customer3.setSalt(Utility.getSalt());
      customer3.setDisabled(true);

      employee3.setEmail("employee3@email.com");
      employee3.setUsername("Enzo Ferrarier");
      employee3.setPassword("w34yfr1uy45324u");
      employee3.setSalt(Utility.getSalt());
      employee3.setDisabled(false);

      customerAddress3.setFullName("John Smith");
      customerAddress3.setId(UUID.randomUUID().toString());
      customerAddress3.setStreetName("Street st");
      customerAddress3.setStreetNumber(1022);
      customerAddress3.setCity("Montreal");
      customerAddress3.setPostalCode("A1B 2B2");
      customer3.setAddress(customerAddress3);

      employeeAddress3.setFullName("Enzo Ferrarier");
      employeeAddress3.setId(UUID.randomUUID().toString());
      employeeAddress3.setStreetName("Street st");
      employeeAddress3.setStreetNumber(1010);
      employeeAddress3.setCity("Montreal");
      employeeAddress3.setPostalCode("ALA 2B2");
      employee3.setAddress(employeeAddress3);

      shift3.setId(UUID.randomUUID().toString());
      shift3.setDate(Date.valueOf("2022-03-10"));
      shift3.setStartTime(Time.valueOf("11:30:20"));
      shift3.setEndTime(Time.valueOf("17:10:00"));
      shifts3.add(shift3);
      employee3.setShifts(shifts3);

      item3.setName("Blue shirt");
      item3.setAvailableForOrder(true);
      item3.setCategory(category2);
      item3.setDescription("A vest");
      item3.setImageUrl("imgur.com/blueshirt");
      item3.setPrice(23);
      item3.setRemainingQuantity(2);
      item3.setStillAvailable(true);
      items3.put(item3, 2);

      purchase3.setDate(Date.valueOf("2022-03-02"));
      purchase3.setTime(Time.valueOf("09:30:00"));
      purchase3.setEmployee(employee3);
      purchase3.setId(UUID.randomUUID().toString());
      purchase3.setItems(items3);
      purchase3.setOrderStatus(OrderStatus.BeingPrepared);
      purchase3.setOrderType(OrderType.Delivery);
      purchases3.add(purchase3);
      customer3.setPurchases(purchases3);


      // Purchase to use for testing the addPurchase method

      items4.put(item1, 3);
      items4.put(item2, 5);
      purchase4.setDate(Date.valueOf("2022-03-03"));
      purchase4.setTime(Time.valueOf("09:30:20"));
      purchase4.setEmployee(employee2);
      purchase4.setId(UUID.randomUUID().toString());
      purchase4.setItems(items4);
      purchase4.setOrderStatus(OrderStatus.BeingPrepared);
      purchase4.setOrderType(OrderType.Delivery);

    }

  }


}
