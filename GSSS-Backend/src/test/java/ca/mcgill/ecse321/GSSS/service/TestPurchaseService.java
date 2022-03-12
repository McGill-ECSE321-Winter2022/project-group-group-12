package ca.mcgill.ecse321.GSSS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import ca.mcgill.ecse321.GSSS.dao.PurchaseRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import ca.mcgill.ecse321.GSSS.model.OrderStatus;
import ca.mcgill.ecse321.GSSS.model.OrderType;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.Shift;
import ca.mcgill.ecse321.GSSS.service.TestCustomerService.MockDatabase;


@ExtendWith(MockitoExtension.class)
public class TestPurchaseService {

	@Mock
	PurchaseRepository purchaseRepository;
	
	@InjectMocks
	PurchaseService purchaseService;
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(purchaseRepository.findPurchasesByDate(any(Date.class))).thenAnswer(MockRepository::findPurchasesByDate);
		lenient().when(purchaseRepository.findPurchasesByDateAndTime(any(Date.class), any(Time.class))).thenAnswer(MockRepository::findPurchasesByDateAndTime);
		lenient().when(purchaseRepository.findPurchasesByEmployee(any(Employee.class))).thenAnswer(MockRepository::findPurchasesByEmployee);
	    lenient().when(purchaseRepository.findAll()).thenAnswer(MockRepository::findAll);
		lenient().when(purchaseRepository.findPurchaseById(anyString())).thenAnswer(MockRepository::findPurchaseById);
	    lenient().when(purchaseRepository.save(any(Purchase.class))).thenAnswer(MockRepository::save);  
	}
	
  // ========================================================================
  // Get methods
  // ========================================================================
	
	/**
	   * Method that checks if a purchase is fetched successfully
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testGetPurchaseById_Successful() {
	    Purchase purchase = purchaseService.getPurchase(MockDatabase.purchase1.getId());
	    assertNotNull(purchase);
	    assertEquals(purchase.getId(), MockDatabase.purchase1.getId());
	  }
	  
	  /**
	   * Method that checks if an error is thrown when getting a purchase from a null Id
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testGetPurchaseById_NullId(){
		Exception error = assertThrows(IllegalArgumentException.class, () -> purchaseService.getPurchase(null));
	    assertEquals("Purchase ID cannot be empty!", error.getMessage());
	  }
	  
	  /**
	   * Method to check error is thrown when we input an empty name when we try to fetch a purchase
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testGetPurchaseById_EmptyId() {
	    Exception error = assertThrows(IllegalArgumentException.class, () -> purchaseService.getPurchase(" "));
	    assertEquals("Purchase ID cannot be empty!", error.getMessage());
	  }
	  
	  
	  /**
	   * Method to check error is thrown when we try to fetch a purchase not in the database
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testGetPurchaseById_NotInDb() {
	    String wrongId = UUID.randomUUID().toString();
	    Exception error = assertThrows(NoSuchElementException.class, () -> purchaseService.getPurchase(wrongId));
	    assertEquals("The purchase with id" + wrongId + "does not exist!", error.getMessage());
	  }
	  
	  /**
	   * Method to check that all purchases in the database are fetched successfully
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testGetAllPurchases_Successful() {
	    List<Purchase> purchases = purchaseService.getAllPurchases();

	    List<Purchase> expected = new ArrayList<Purchase>();
	    expected.add(MockDatabase.purchase1);
	    expected.add(MockDatabase.purchase2);
	    expected.add(MockDatabase.purchase3);
	    expected.add(MockDatabase.purchase4);

	    assertNotNull(purchases);
	    assertEquals(expected.size(), purchases.size());
	    for (Purchase p : purchases)
	      assertTrue(expected.contains(p));
	  }
	  
	  /**
	   * Method to check that all purchases with same date are fetched successfully
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test 
	  public void testGetPurchasesByDate_Successful() {
	    List<Purchase> purchases = purchaseService.getPurchasesByDate(MockDatabase.purchase2.getDate());
	    List<Purchase> expected = new ArrayList<Purchase>();
	    expected.add(MockDatabase.purchase2);
	    expected.add(MockDatabase.purchase3);

	    assertNotNull(purchases);
	    assertEquals(expected.size(), purchases.size());
	    for (Purchase p : purchases)
	      assertTrue(expected.contains(p));
	  }
	  
	  /**
	   * Method to check that an error is thrown when we get purchases with same date from a  null date
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test 
	  public void testGetPurchasesByDate_NullDate() {
		  try {
		      purchaseService.getPurchasesByDate(null);
		    } catch (IllegalArgumentException e) {
		      assertEquals("Date cannot be null!", e.getMessage());
		      return;
		    }
		    fail();
	  }
	  
	  /**
	   * Method to check that an error is thrown when we get purchases with a wrong date 
	   * (No purchase associated with that date)
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testGetPurchasesByDate_NotInDb() {
		Date wrongDate = Date.valueOf("2002-06-28");
	    try {
	      purchaseService.getPurchasesByDate(wrongDate);
	    } catch (NoSuchElementException e) {
	      assertEquals("There are no purchases on " + wrongDate.toString() + " !", e.getMessage());
	      return;
	    }
	    fail();
	  }
	  
	  /**
	   * Method to check that all purchases with same date and time are fetched successfully
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test 
	  public void testGetPurchasesByDateAndTime_Successful() {
	    List<Purchase> purchases = purchaseService.getPurchasesByDateAndTime(MockDatabase.purchase2.getDate(), MockDatabase.purchase2.getTime());
	    List<Purchase> expected = new ArrayList<Purchase>();
	    expected.add(MockDatabase.purchase2);
	    expected.add(MockDatabase.purchase3);

	    assertNotNull(purchases);
	    assertEquals(expected.size(), purchases.size());
	    for (Purchase p : purchases)
	      assertTrue(expected.contains(p));
	  }
	  
	  /**
	   * Method to check that an error is thrown when we get purchases with date and time from a  null date
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test 
	  public void testGetPurchasesByDateAndTime_NullDate() {
		  try {
		      purchaseService.getPurchasesByDateAndTime(null, MockDatabase.purchase1.getTime());
		    } catch (IllegalArgumentException e) {
		      assertEquals("Date cannot be null! ", e.getMessage());
		      return;
		    }
		    fail();
	  }
	  
	  /**
	   * Method to check that an error is thrown when we get purchases with date and time from a null time
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test 
	  public void testGetPurchasesByDateAndTime_NullTime() {
		  try {
		      purchaseService.getPurchasesByDateAndTime(MockDatabase.purchase1.getDate(), null);
		    } catch (IllegalArgumentException e) {
		      assertEquals("Time cannot be null! ", e.getMessage());
		      return;
		    }
		    fail();
	  }
	  
	  /**
	   * Method to check that an error is thrown when we get purchases with date and time from a null date and a null time
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test 
	  public void testGetPurchasesByDateAndTime_AllNull() {
		  try {
		      purchaseService.getPurchasesByDateAndTime(null, null);
		    } catch (IllegalArgumentException e) {
		      assertEquals("Date cannot be null! Time cannot be null! ", e.getMessage());
		      return;
		    }
		    fail();
	  }
	  
	  /**
	   * Method to check that an error is thrown when we get purchases with date and time with a wrong date
	   * (No purchase associated with that date and time)
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testGetPurchasesByDateAndTime_WrongDate() {
		Date wrongDate = Date.valueOf("2002-06-28");
	    try {
	      purchaseService.getPurchasesByDateAndTime(wrongDate, MockDatabase.purchase2.getTime());
	    } catch (NoSuchElementException e) {
	      assertEquals("There are no purchases on " +wrongDate.toString()+  " and time" +MockDatabase.purchase2.getTime().toString()+ " !", e.getMessage());
	      return;
	    }
	    fail();
	  }
	  
	  /**
	   * Method to check that an error is thrown when we get purchases with date and time with a wrong time
	   * (No purchase associated with that date and time)
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testGetPurchasesByDateAndTime_WrongTime() {
		Time wrongTime = Time.valueOf("17:30:00");
	    try {
	      purchaseService.getPurchasesByDateAndTime(MockDatabase.purchase2.getDate(), wrongTime);
	    } catch (NoSuchElementException e) {
	      assertEquals("There are no purchases on " +MockDatabase.purchase2.getDate().toString()+  " and time" +wrongTime.toString()+ " !", e.getMessage());
	      return;
	    }
	    fail();
	  }
	  
	  /**
	   * Method to check that an error is thrown when we get purchases with a wrong date and time
	   * (No purchase associated with that date and time)
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testGetPurchasesByDateAndTime_AllWrong() {
		Time wrongTime = Time.valueOf("17:30:00");
		Date wrongDate = Date.valueOf("2002-06-28");

	    try {
	      purchaseService.getPurchasesByDateAndTime(wrongDate, wrongTime);
	    } catch (NoSuchElementException e) {
	      assertEquals("There are no purchases on " +wrongDate.toString()+  " and time" +wrongTime.toString()+ " !", e.getMessage());
	      return;
	    }
	    fail();
	  }
	
	  /**
	   * Method to check that all purchases with same employee are fetched successfully
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test 
	  public void testGetPurchasesByEmployee_Successful() {
	    List<Purchase> purchases = purchaseService.getPurchasesByEmployee(MockDatabase.purchase3.getEmployee());
	    List<Purchase> expected = new ArrayList<Purchase>();
	    expected.add(MockDatabase.purchase3);
	    expected.add(MockDatabase.purchase4);
	    assertNotNull(purchases);
	    assertEquals(expected.size(), purchases.size());
	    for (Purchase p : purchases)
	      assertTrue(expected.contains(p));
	  }
	  
	  /**
	   * Method to check that an error is thrown when we get purchases with same date from a  null date
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test 
	  public void testGetPurchasesByEmployee_NullEmployee() {
		  try {
		      purchaseService.getPurchasesByEmployee(null);
		    } catch (IllegalArgumentException e) {
		      assertEquals("Employee cannot be null!", e.getMessage());
		      return;
		    }
		    fail();
	  }
	  
	  /**
	   * Method to check that an error is thrown when we get purchases with a wrong date 
	   * (No purchase associated with that date)
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testGetPurchasesByEmployee_NotInDb() {
		Employee wrongEmployee = new Employee();
		wrongEmployee.setEmail("wrongemployee@gmail.com");
		wrongEmployee.setUsername("Enzo Smith");
		wrongEmployee.setPassword("y45324u");
		wrongEmployee.setSalt("jg34fd1h243214fg142");
		wrongEmployee.setDisabled(false);

		Address wrongEmployeeAddress = new Address();
		wrongEmployeeAddress.setFullName("Enzo Smith");
        wrongEmployeeAddress.setId(UUID.randomUUID().toString());
        wrongEmployeeAddress.setStreetName("Lorne Street st");
        wrongEmployeeAddress.setStreetNumber(3660);
        wrongEmployeeAddress.setCity("Montreal");
        wrongEmployeeAddress.setPostalCode("B1A 2B2");
        wrongEmployee.setAddress(wrongEmployeeAddress);

        Shift wrongEmployeeShift = new Shift();
        Set<Shift> wrongEmployeeShifts = new HashSet<Shift>();

        wrongEmployeeShift.setId(UUID.randomUUID().toString());
        wrongEmployeeShift.setDate(Date.valueOf("2022-03-08"));
        wrongEmployeeShift.setStartTime(Time.valueOf("09:30:00"));
        wrongEmployeeShift.setEndTime(Time.valueOf("17:00:00"));
        wrongEmployeeShifts.add(wrongEmployeeShift);
        wrongEmployee.setShifts(wrongEmployeeShifts);

	    try {
	      purchaseService.getPurchasesByEmployee(wrongEmployee);
	    } catch (NoSuchElementException e) {
	      assertEquals("There are no purchases assigned to given employee!", e.getMessage());
	      return;
	    }
	    fail();
	  }
	  
	  // ========================================================================
	  // Create methods
	  // ========================================================================
	  
	  /**
	   * Method to test that a purchase is created successfully
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testCreatePurchase_Successful() {
	    assertEquals(4, purchaseService.getAllPurchases().size());
	   
	    Employee employee = new Employee();
	    employee.setEmail("employee@gmail.com");
	    employee.setUsername("Enzo Benoit-Jeannin");
	    employee.setPassword("y45675324u");
	    employee.setSalt("jg34fd1h243214fg142");
	    employee.setDisabled(false);

		Address address = new Address();
		address.setFullName("Enzo Benoit-Jeannin");
		address.setId(UUID.randomUUID().toString());
		address.setStreetName("Lorne Street st");
		address.setStreetNumber(3660);
		address.setCity("Montreal");
		address.setPostalCode("B1A 2B2");
        employee.setAddress(address);

        Shift shift = new Shift();
        Set<Shift> shifts = new HashSet<Shift>();

        shift.setId(UUID.randomUUID().toString());
        shift.setDate(Date.valueOf("2022-03-08"));
        shift.setStartTime(Time.valueOf("09:30:00"));
        shift.setEndTime(Time.valueOf("17:00:00"));
        shifts.add(shift);
        employee.setShifts(shifts);
        
        HashMap<Item, Integer> items = new HashMap<Item, Integer>();

        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setName("Dishes");
        
        Item item1 = new Item();
        item1.setName("Spaghetti");
        item1.setDescription("Italian dish");
        item1.setImageUrl("www.pasta/spaghetti.com");
        item1.setRemainingQuantity(45);
        item1.setPrice(23.00);
        item1.setAvailableForOrder(false);
        item1.setStillAvailable(false);
        item1.setCategory(itemCategory);
        OrderType orderType = OrderType.InPerson;
        OrderStatus orderStatus = OrderStatus.OutForDelivery;

	    Purchase purchase = null;
	    try {
	      purchase = purchaseService.createPurchase(orderType, employee, orderStatus, items);
	    } catch (IllegalArgumentException e) {
	      // Check that no error occurred
	      fail();
	    }
	    assertNotNull(purchase);
	    assertEquals(orderType, purchase.getOrderType());
	    assertEquals(orderStatus, purchase.getOrderStatus());
	    assertEquals(employee, purchase.getEmployee());
	    assertEquals(items, purchase.getItems());
	  }

	  /**
	   * Method to test that an error is thrown when creating a Purchase with null OrderType
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testCreatePurchase_NullOrderType() {
		  Employee employee = new Employee();
		    employee.setEmail("employee@gmail.com");
		    employee.setUsername("Enzo Benoit-Jeannin");
		    employee.setPassword("y45675324u");
		    employee.setSalt("jg34fd1h243214fg142");
		    employee.setDisabled(false);

			Address address = new Address();
			address.setFullName("Enzo Benoit-Jeannin");
			address.setId(UUID.randomUUID().toString());
			address.setStreetName("Lorne Street st");
			address.setStreetNumber(3660);
			address.setCity("Montreal");
			address.setPostalCode("B1A 2B2");
	        employee.setAddress(address);

	        Shift shift = new Shift();
	        Set<Shift> shifts = new HashSet<Shift>();

	        shift.setId(UUID.randomUUID().toString());
	        shift.setDate(Date.valueOf("2022-03-08"));
	        shift.setStartTime(Time.valueOf("09:30:00"));
	        shift.setEndTime(Time.valueOf("17:00:00"));
	        shifts.add(shift);
	        employee.setShifts(shifts);
	        
	        HashMap<Item, Integer> items = new HashMap<Item, Integer>();

	        ItemCategory itemCategory = new ItemCategory();
	        itemCategory.setName("Dishes");
	        
	        Item item1 = new Item();
	        item1.setName("Spaghetti");
	        item1.setDescription("Italian dish");
	        item1.setImageUrl("www.pasta/spaghetti.com");
	        item1.setRemainingQuantity(45);
	        item1.setPrice(23.00);
	        item1.setAvailableForOrder(false);
	        item1.setStillAvailable(false);
	        item1.setCategory(itemCategory);
	        OrderType orderType = null;
	        OrderStatus orderStatus = OrderStatus.OutForDelivery;

		    Purchase purchase = null;
		    String error = null;
		    try {
		    	 purchase = purchaseService.createPurchase(orderType, employee, orderStatus, items);
		    } catch (IllegalArgumentException e) {
		      error = e.getMessage();
		    }
	
		    assertNull(purchase);
		    // check error
		    assertEquals("Order type cannot be null! ", error);
	  }
	  
	  /**
	   * Method to test that an error is thrown when creating a Purchase with null Employee
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testCreatePurchase_NullEmployee() {
		  	Employee employee = null;
	        
	        HashMap<Item, Integer> items = new HashMap<Item, Integer>();

	        ItemCategory itemCategory = new ItemCategory();
	        itemCategory.setName("Dishes");
	        
	        Item item1 = new Item();
	        item1.setName("Spaghetti");
	        item1.setDescription("Italian dish");
	        item1.setImageUrl("www.pasta/spaghetti.com");
	        item1.setRemainingQuantity(45);
	        item1.setPrice(23.00);
	        item1.setAvailableForOrder(false);
	        item1.setStillAvailable(false);
	        item1.setCategory(itemCategory);
	        OrderType orderType = OrderType.InPerson;
	        OrderStatus orderStatus = OrderStatus.Completed;

		    Purchase purchase = null;
		    String error = null;
		    try {
		    	 purchase = purchaseService.createPurchase(orderType, employee, orderStatus, items);
		    } catch (IllegalArgumentException e) {
		      error = e.getMessage();
		    }
	
		    assertNull(purchase);
		    // check error
		    assertEquals("Employee cannot be null! ", error);
	  }
	  
	  /**
	   * Method to test that an error is thrown when creating a Purchase with null OrderStatus
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testCreatePurchase_NullOrderStatus() {
		  Employee employee = new Employee();
		    employee.setEmail("employee@gmail.com");
		    employee.setUsername("Enzo Benoit-Jeannin");
		    employee.setPassword("y45675324u");
		    employee.setSalt("jg34fd1h243214fg142");
		    employee.setDisabled(false);

			Address address = new Address();
			address.setFullName("Enzo Benoit-Jeannin");
			address.setId(UUID.randomUUID().toString());
			address.setStreetName("Lorne Street st");
			address.setStreetNumber(3660);
			address.setCity("Montreal");
			address.setPostalCode("B1A 2B2");
	        employee.setAddress(address);

	        Shift shift = new Shift();
	        Set<Shift> shifts = new HashSet<Shift>();

	        shift.setId(UUID.randomUUID().toString());
	        shift.setDate(Date.valueOf("2022-03-08"));
	        shift.setStartTime(Time.valueOf("09:30:00"));
	        shift.setEndTime(Time.valueOf("17:00:00"));
	        shifts.add(shift);
	        employee.setShifts(shifts);
	        
	        HashMap<Item, Integer> items = new HashMap<Item, Integer>();

	        ItemCategory itemCategory = new ItemCategory();
	        itemCategory.setName("Dishes");
	        
	        Item item1 = new Item();
	        item1.setName("Spaghetti");
	        item1.setDescription("Italian dish");
	        item1.setImageUrl("www.pasta/spaghetti.com");
	        item1.setRemainingQuantity(45);
	        item1.setPrice(23.00);
	        item1.setAvailableForOrder(false);
	        item1.setStillAvailable(false);
	        item1.setCategory(itemCategory);
	        OrderType orderType = OrderType.InPerson;
	        OrderStatus orderStatus = null;

		    Purchase purchase = null;
		    String error = null;
		    try {
		    	 purchase = purchaseService.createPurchase(orderType, employee, orderStatus, items);
		    } catch (IllegalArgumentException e) {
		      error = e.getMessage();
		    }
	
		    assertNull(purchase);
		    // check error
		    assertEquals("Order status cannot be null! ", error);
	  }
	  
	  /**
	   * Method to test that an error is thrown when creating a Purchase with null items
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testCreatePurchase_NullItems() {
		  Employee employee = new Employee();
		    employee.setEmail("employee@gmail.com");
		    employee.setUsername("Enzo Benoit-Jeannin");
		    employee.setPassword("y45675324u");
		    employee.setSalt("jg34fd1h243214fg142");
		    employee.setDisabled(false);

			Address address = new Address();
			address.setFullName("Enzo Benoit-Jeannin");
			address.setId(UUID.randomUUID().toString());
			address.setStreetName("Lorne Street st");
			address.setStreetNumber(3660);
			address.setCity("Montreal");
			address.setPostalCode("B1A 2B2");
	        employee.setAddress(address);

	        Shift shift = new Shift();
	        Set<Shift> shifts = new HashSet<Shift>();

	        shift.setId(UUID.randomUUID().toString());
	        shift.setDate(Date.valueOf("2022-03-08"));
	        shift.setStartTime(Time.valueOf("09:30:00"));
	        shift.setEndTime(Time.valueOf("17:00:00"));
	        shifts.add(shift);
	        employee.setShifts(shifts);
	        
	        HashMap<Item, Integer> items = null;

	        OrderType orderType = OrderType.InPerson;
	        OrderStatus orderStatus = OrderStatus.Completed;

		    Purchase purchase = null;
		    String error = null;
		    try {
		    	 purchase = purchaseService.createPurchase(orderType, employee, orderStatus, items);
		    } catch (IllegalArgumentException e) {
		      error = e.getMessage();
		    }
	
		    assertNull(purchase);
		    // check error
		    assertEquals("Items cannot be null! ", error);
	  }
	  
	  /**
	   * Method to test that an error is thrown when every parameter is null when creating a purchase
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testCreatePurchase_AllNull() {

	    String error = null;
	    Purchase purchase = null;

	    try {
	      purchase = purchaseService.createPurchase(null, null, null, null);
	    } catch (IllegalArgumentException e) {
	      error = e.getMessage();
	    }

	    assertNull(purchase);
	    // check error
	    assertEquals(
	        "Order type cannot be null! Employee cannot be null! Order status cannot be null! Items cannot be null! ", error);
	  }
	  
	// ========================================================================
	// Update methods
	// ========================================================================
	  
	  /**
	   * Method to test that a purchase is modified successfully
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testModifyPurchase_Successful() {	   

	    Purchase purchase = null;
	    try {
	      purchase = purchaseService.modifyPurchase(OrderType.InPerson, OrderStatus.OutForDelivery, MockDatabase.purchase1.getId(), MockDatabase.items3, MockDatabase.employee2);
	    } catch (IllegalArgumentException e) {
	      // Check that no error occurred
	      fail();
	    }
	    assertNotNull(purchase);
	    assertEquals(MockDatabase.purchase1.getId(), purchase.getId());
	    assertEquals(OrderType.InPerson, purchase.getOrderType());
	    assertEquals(OrderStatus.OutForDelivery, purchase.getOrderStatus());
	    assertEquals(MockDatabase.employee2, purchase.getEmployee());
	    assertEquals(MockDatabase.items3, purchase.getItems());
	  }

	  /**
	   * Method to test that an error is thrown when modifying a Purchase with null OrderType
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testModifyPurchase_NullOrderType() {

		    Purchase purchase = null;
		    String error = null;
		    try {
		    	purchase = purchaseService.modifyPurchase(null, OrderStatus.OutForDelivery, MockDatabase.purchase1.getId(), MockDatabase.items3, MockDatabase.employee2);
		    } catch (IllegalArgumentException e) {
		      error = e.getMessage();
		    }
	
		    assertNull(purchase);
		    // check error
		    assertEquals("Order type cannot be null! ", error);
	  }
	  
	  /**
	   * Method to test that an error is thrown when modifying a Purchase with null Employee
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testModifyPurchase_NullEmployee() {

		    Purchase purchase = null;
		    String error = null;
		    try {
		    	purchase = purchaseService.modifyPurchase(OrderType.InPerson,  OrderStatus.Completed, MockDatabase.purchase1.getId(), MockDatabase.items3, null);
		    } catch (IllegalArgumentException e) {
		      error = e.getMessage();
		    }
	
		    assertNull(purchase);
		    // check error
		    assertEquals("Employee cannot be null! ", error);
	  }
	  
	  /**
	   * Method to test that an error is thrown when creating a Purchase with null OrderStatus
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testModifyPurchase_NullOrderStatus() {

		    Purchase purchase = null;
		    String error = null;
		    try {
		    	purchase = purchaseService.modifyPurchase(OrderType.InPerson, null, MockDatabase.purchase1.getId(), MockDatabase.items3, MockDatabase.employee2);
		    } catch (IllegalArgumentException e) {
		      error = e.getMessage();
		    }
	
		    assertNull(purchase);
		    // check error
		    assertEquals("Order status cannot be null! ", error);
	  }
	  
	  /**
	   * Method to test that an error is thrown when modifying a Purchase with null items
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testModifyPurchase_NullItems() {

		    Purchase purchase = null;
		    String error = null;
		    try {
		    	purchase = purchaseService.modifyPurchase(OrderType.InPerson, OrderStatus.BeingPrepared, MockDatabase.purchase1.getId(), null, MockDatabase.employee2);
		    } catch (IllegalArgumentException e) {
		      error = e.getMessage();
		    }
	
		    assertNull(purchase);
		    // check error
		    assertEquals("Items cannot be null! ", error);
	  }
	  
	  /**
	   * Method to test that an error is thrown when modifying a Purchase with null id
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testModifyPurchase_NullId() {

		    Purchase purchase = null;
		    String error = null;
		    try {
		    	purchase = purchaseService.modifyPurchase(OrderType.InPerson, OrderStatus.BeingPrepared, null , MockDatabase.items1, MockDatabase.employee1);
		    } catch (IllegalArgumentException e) {
		      error = e.getMessage();
		    }
	
		    assertNull(purchase);
		    // check error
		    assertEquals("Purchase ID cannot be empty! ", error);
	  }
	  
	  /**
	   * Method to test that an error is thrown when modifying a Purchase with empty id
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testModifyPurchase_EmptyId() {
		    Purchase purchase = null;
		    String error = null;
		    try {
		    	purchase = purchaseService.modifyPurchase(OrderType.InPerson, OrderStatus.BeingPrepared, " ", MockDatabase.items1, MockDatabase.employee1);
		    } catch (IllegalArgumentException e) {
		      error = e.getMessage();
		    }
	 
		    assertNull(purchase);
		    // check error
		    assertEquals("Purchase ID cannot be empty! ", error);
	  }
	  
	  /**
	   * Method to test that an error is thrown when every parameter is null when modifying a purchase
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testModifyPurchase_AllNull() {

	    String error = null;
	    Purchase purchase = null;

	    try {
	    	purchase = purchaseService.modifyPurchase(null, null, null, null, null);
	    } catch (IllegalArgumentException e) {
	      error = e.getMessage();
	    }

	    assertNull(purchase);
	    // check error
	    assertEquals(
	        "Order type cannot be null! Employee cannot be null! Order status cannot be null! Items cannot be null! Purchase ID cannot be empty! ", error);
	  }
	  
	// ========================================================================
	// Delete methods
	// ========================================================================
	  
	  /**
	   * Method to check that a purchase was deleted successfully
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testDeletePurchase_Successful() {
	    purchaseService.deletePurchase(MockDatabase.purchase2.getId());

	    verify(purchaseRepository, times(1))
	        .deleteById(argThat((String id) -> MockDatabase.purchase2.getId().equals(id)));
	    verify(purchaseRepository, times(0))
	        .deleteById(argThat((String id) -> !MockDatabase.purchase2.getId().equals(id)));
	  }

	  /**
	   * Method to check that error is thrown when we input a null Id when deleting a purchase
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testDeletePurchase_NullId() {
	    Exception error = assertThrows(IllegalArgumentException.class, () -> purchaseService.deletePurchase(null));
	    assertEquals("Purchase ID cannot be empty!", error.getMessage());
	    verify(purchaseRepository, times(0)).delete(any(Purchase.class));
	  }

	  /**
	   * Method to check that error is thrown when we input an empty id when deleting a purchase
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testDeletePurchase_EmptyId() {
	    Exception error = assertThrows(IllegalArgumentException.class, () -> purchaseService.deletePurchase(" "));
	    assertEquals("Purchase ID cannot be empty!", error.getMessage());
	    verify(purchaseRepository, times(0)).delete(any(Purchase.class));
	  }
	  
	// ========================================================================
	// Other methods
	// ========================================================================
		
	  /**
	   * Method to check that we successfully get the orderHistory from a Customer
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
		@Test
		public void testGetOrderHistory_Successful() {
			List<Purchase> expected = new ArrayList<Purchase>();
			List<Purchase> purchases = new ArrayList<Purchase>();
			expected.add(MockDatabase.purchase2);
			expected.add(MockDatabase.purchase3);

			try {
			 purchases = purchaseService.getOrderHistory(MockDatabase.customer);
			} catch (IllegalArgumentException e) {
			      // Check that no error occurred
			      fail();
			 }
			
			assertNotNull(purchases);
			assertEquals(expected.size(), purchases.size());
			for (Purchase p : purchases) {
			      assertTrue(expected.contains(p));
			}
		}
		/**
		 * Method to test that an error is thrown if we try to get the order history from a null customer
		 * 
		 * @author Enzo Benoit-Jeannin
		 */
		@Test
		public void testGetOrderHsitory_NullCustomer() {
			String error = null;
			List<Purchase> purchases = null;
			try {
				purchases = purchaseService.getOrderHistory(null);
			} catch (IllegalArgumentException e) {
			      error = e.getMessage();
			}
			assertNull(purchases);
			assertEquals("Customer cannot be null!" , error);
		}
		
	  
	 /**
     * This class holds all of the mock methods of the CRUD repository.
     */
	class MockRepository{
		/**
		 * 
		 * @param invocation
		 * @return
		 */
		static List<Purchase> findPurchasesByDate(InvocationOnMock invocation){
            Date date = (Date) invocation.getArgument(0);
            List<Purchase> purchases = new ArrayList<Purchase>();
            if(date.equals(MockDatabase.purchase1.getDate())) purchases.add(MockDatabase.purchase1);
            if(date.equals(MockDatabase.purchase2.getDate())) purchases.add(MockDatabase.purchase2);
            if(date.equals(MockDatabase.purchase3.getDate())) purchases.add(MockDatabase.purchase3);
            if(date.equals(MockDatabase.purchase4.getDate())) purchases.add(MockDatabase.purchase4);

            return purchases;
        }
		
		static List<Purchase> findPurchasesByDateAndTime(InvocationOnMock invocation){
            Date date = (Date) invocation.getArgument(0);
            Time time = (Time) invocation.getArgument(1);
            
            List<Purchase> purchases = new ArrayList<Purchase>();
            if(date.equals(MockDatabase.purchase1.getDate()) && time.equals(MockDatabase.purchase1.getTime())) {
            	purchases.add(MockDatabase.purchase1);
            }
            if(date.equals(MockDatabase.purchase2.getDate()) && time.equals(MockDatabase.purchase2.getTime())) {
            	purchases.add(MockDatabase.purchase2);
            }
            if(date.equals(MockDatabase.purchase3.getDate()) && time.equals(MockDatabase.purchase3.getTime())) {
            	purchases.add(MockDatabase.purchase3);
            }
            if(date.equals(MockDatabase.purchase4.getDate()) && time.equals(MockDatabase.purchase4.getTime())) {
            	purchases.add(MockDatabase.purchase4);
            }
            return purchases;
        }
		
		static List<Purchase> findPurchasesByEmployee(InvocationOnMock invocation){
            Employee employee = (Employee) invocation.getArgument(0);
            
            List<Purchase> purchases = new ArrayList<Purchase>();
            if(employee.equals(MockDatabase.purchase1.getEmployee())) {
            	purchases.add(MockDatabase.purchase1);
            }
            if(employee.equals(MockDatabase.purchase2.getEmployee())) {
            	purchases.add(MockDatabase.purchase2);
            }
            if(employee.equals(MockDatabase.purchase3.getEmployee())) {
            	purchases.add(MockDatabase.purchase3);
            }
            if(employee.equals(MockDatabase.purchase4.getEmployee())) {
            	purchases.add(MockDatabase.purchase4);
            }
            return purchases;
        }
		
		static Purchase findPurchaseById(InvocationOnMock invocation) {
		      String id = (String) invocation.getArgument(0);
		      if (id.equals(MockDatabase.purchase1.getId())) return MockDatabase.purchase1;
		      if (id.equals(MockDatabase.purchase2.getId())) return MockDatabase.purchase2;
		      if (id.equals(MockDatabase.purchase3.getId())) return MockDatabase.purchase3;
		      if (id.equals(MockDatabase.purchase4.getId())) return MockDatabase.purchase4;
		      return null;
		    }
		
		static Purchase save(InvocationOnMock invocation){
            return (Purchase) invocation.getArgument(0);
        }
		
		static List<Purchase> findAll(InvocationOnMock invocation) {
		      List<Purchase> purchases = new ArrayList<Purchase>();
		      purchases.add(MockDatabase.purchase1);
		      purchases.add(MockDatabase.purchase2);
		      purchases.add(MockDatabase.purchase3);
		      purchases.add(MockDatabase.purchase4);
		      return purchases;
		}
	}
	
	
	
	/**
     * This class mock data for tests.
     */
    final static class MockDatabase{
    	
    	// EMPLOYEES
    	
    	// Employee 1
    	static Employee employee1 = new Employee();
        static Address address1 = new Address();
        static Set<Shift> shifts1 = new HashSet<Shift>();
        static Shift shift1 = new Shift();

        // Employee 2
        static Employee employee2 = new Employee();
        static Address address2 = new Address();
        static Set<Shift> shifts2 = new HashSet<Shift>();
        static Shift shift2 = new Shift();

        // Employee 3
        static Employee employee3 = new Employee();
        static Address address3 = new Address();
        static Set<Shift> shifts3 = new HashSet<Shift>();
        static Shift shift3 = new Shift();
        
        
        // CUSTOMER
        
        static Customer customer = new Customer();
        static Address customerAddress = new Address();
        static Set<Purchase> purchases = new HashSet<Purchase>();
        
       
        // ITEMS
        
        // Item 1 
        private static final String ITEM_KEY1 = "TestItem1";
        private static final String ITEM_DESCRIPTION1 = "very tasty";
        private static final String ITEM_IMAGEURL1 = "www.niceimage.com";
        private static final int ITEM_REMAININGQUANTITY1 = 50;
        private static final double ITEM_PRICE1 = 3.75;
        private static final boolean ITEM_AVAILABILITYFORORDER1 = true;
        private static final boolean ITEM_AVAILABILITY1 = true;
        
        private static final String ITEMCATEGORY_KEY1 = "Meat";
        
        static Item item1 = new Item();
        static ItemCategory itemCategory1 = new ItemCategory();

        // Item 2 
        private static final String ITEM_KEY2 = "TestItem2";
        private static final String ITEM_DESCRIPTION2 = "extremely tasty";
        private static final String ITEM_IMAGEURL2 = "www.veryniceimage.com";
        private static final int ITEM_REMAININGQUANTITY2 = 10;
        private static final double ITEM_PRICE2 = 13.99;
        private static final boolean ITEM_AVAILABILITYFORORDER2 = false;
        private static final boolean ITEM_AVAILABILITY2 = true;

        private static final String ITEMCATEGORY_KEY2 = "Hygiene";
        
        static Item item2 = new Item();
        static ItemCategory itemCategory2 = new ItemCategory();
        
        // Item 3
        private static final String ITEM_KEY3 = "TestItem3";
        private static final String ITEM_DESCRIPTION3 = "tasty";
        private static final String ITEM_IMAGEURL3 = "www.image.com";
        private static final int ITEM_REMAININGQUANTITY3 = 2;
        private static final double ITEM_PRICE3 = 50.99;
        private static final boolean ITEM_AVAILABILITYFORORDER3 = true;
        private static final boolean ITEM_AVAILABILITY3 = true;

        private static final String ITEMCATEGORY_KEY3 = "Vegetables";
        
        static Item item3 = new Item();
        static ItemCategory itemCategory3 = new ItemCategory();
        
        // Item 4
        private static final String ITEM_KEY4 = "TestItem4";
        private static final String ITEM_DESCRIPTION4 = "Good fires";
        private static final String ITEM_IMAGEURL4 = "www.checkTheseFries.com";
        private static final int ITEM_REMAININGQUANTITY4 = 2000;
        private static final double ITEM_PRICE4 = 8.99;
        private static final boolean ITEM_AVAILABILITYFORORDER4 = false;
        private static final boolean ITEM_AVAILABILITY4 = true;

        private static final String ITEMCATEGORY_KEY4 = "Fast Food";
        
        static Item item4 = new Item();
        static ItemCategory itemCategory4 = new ItemCategory();
        
        
        // PURCHASES
        
        // Purchase 1
        static Purchase purchase1 = new Purchase();
        static HashMap<Item, Integer> items1 = new HashMap<Item, Integer>();

        // Purchase 2
        static Purchase purchase2 = new Purchase();
        static HashMap<Item, Integer> items2 = new HashMap<Item, Integer>();

        // Purchase 3
        static Purchase purchase3 = new Purchase();
        static HashMap<Item, Integer> items3 = new HashMap<Item, Integer>();
        
        // Purchase 4
        static Purchase purchase4 = new Purchase();
        static HashMap<Item, Integer> items4 = new HashMap<Item, Integer>();

        
        static{
        	// Employee 1
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

            
            // Employee 2
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
            
            
            // Employee 3
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
            
            
            // Item 1
            itemCategory1.setName(ITEMCATEGORY_KEY1);
            item1.setName(ITEM_KEY1);
            item1.setDescription(ITEM_DESCRIPTION1);
            item1.setImageUrl(ITEM_IMAGEURL1);
            item1.setRemainingQuantity(ITEM_REMAININGQUANTITY1);
            item1.setPrice(ITEM_PRICE1);
            item1.setAvailableForOrder(ITEM_AVAILABILITYFORORDER1);
            item1.setStillAvailable(ITEM_AVAILABILITY1);
            item1.setCategory(itemCategory1);

            
            // Item 2
            itemCategory2.setName(ITEMCATEGORY_KEY2);
            item2.setName(ITEM_KEY2);
            item2.setDescription(ITEM_DESCRIPTION2);
            item2.setImageUrl(ITEM_IMAGEURL2);
            item2.setRemainingQuantity(ITEM_REMAININGQUANTITY2);
            item2.setPrice(ITEM_PRICE2);
            item2.setAvailableForOrder(ITEM_AVAILABILITYFORORDER2);
            item2.setStillAvailable(ITEM_AVAILABILITY2);
            item2.setCategory(itemCategory2);
            
            
            // Item 3
            itemCategory3.setName(ITEMCATEGORY_KEY3);
            item3.setName(ITEM_KEY3);
            item3.setDescription(ITEM_DESCRIPTION3);
            item3.setImageUrl(ITEM_IMAGEURL3);
            item3.setRemainingQuantity(ITEM_REMAININGQUANTITY3);
            item3.setPrice(ITEM_PRICE3);
            item3.setAvailableForOrder(ITEM_AVAILABILITYFORORDER3);
            item3.setStillAvailable(ITEM_AVAILABILITY3);
            item3.setCategory(itemCategory3);
            
            
            // Item 4
            itemCategory4.setName(ITEMCATEGORY_KEY4);
            item4.setName(ITEM_KEY4);
            item4.setDescription(ITEM_DESCRIPTION4);
            item4.setImageUrl(ITEM_IMAGEURL4);
            item4.setRemainingQuantity(ITEM_REMAININGQUANTITY4);
            item4.setPrice(ITEM_PRICE4);
            item4.setAvailableForOrder(ITEM_AVAILABILITYFORORDER4);
            item4.setStillAvailable(ITEM_AVAILABILITY4);
            item4.setCategory(itemCategory4);
            
            
            // Purchase 1
            purchase1.setDate(Date.valueOf("2022-03-08"));
            purchase1.setTime(Time.valueOf("09:30:00"));
            purchase1.setEmployee(employee1);
            purchase1.setId(UUID.randomUUID().toString());
            purchase1.setOrderStatus(OrderStatus.BeingPrepared);
            purchase1.setOrderType(OrderType.Delivery);
            items1.put(item1, 2);
            items1.put(item4, 20);
            purchase1.setItems(items1);

            
            // Purchase 2
            purchase2.setDate(Date.valueOf("2022-03-02"));
            purchase2.setTime(Time.valueOf("09:30:00"));
            purchase2.setEmployee(employee2);
            purchase2.setId(UUID.randomUUID().toString());
            purchase2.setOrderStatus(OrderStatus.Completed);
            purchase2.setOrderType(OrderType.Delivery);
            items2.put(item2, 2);
            items2.put(item3, 1);
            purchase2.setItems(items2);
            
            
            // Purchase 3
            purchase3.setDate(Date.valueOf("2022-03-02"));
            purchase3.setTime(Time.valueOf("09:30:00"));
            purchase3.setEmployee(employee3);
            purchase3.setId(UUID.randomUUID().toString());
            purchase3.setOrderStatus(OrderStatus.BeingPrepared);
            purchase3.setOrderType(OrderType.Delivery);
            items3.put(item1, 1);
            items3.put(item2, 1);
            items3.put(item3, 1);
            items3.put(item4, 1);
            purchase3.setItems(items3);
            
            // Purchase 4
            purchase4.setDate(Date.valueOf("2022-03-11"));
            purchase4.setTime(Time.valueOf("10:30:00"));
            purchase4.setEmployee(employee3);
            purchase4.setId(UUID.randomUUID().toString());
            purchase4.setOrderStatus(OrderStatus.Completed);
            purchase4.setOrderType(OrderType.InPerson);
            items4.put(item1, 1);
            items4.put(item2, 1);
            purchase4.setItems(items4);
            
            // Customer
            customer.setEmail("customer1@email.com");
            customer.setUsername("John Smith");
            customer.setPassword("w34yfr1uy45324u");
            customer.setSalt(Utility.getSalt());
            customer.setDisabled(false);
            
            customerAddress.setFullName("John Smith");
            customerAddress.setId(UUID.randomUUID().toString());
            customerAddress.setStreetName("Street st");
            customerAddress.setStreetNumber(1000);
            customerAddress.setCity("Montreal");
            customerAddress.setPostalCode("A1A 2B2");
            customer.setAddress(customerAddress);
            
            purchases.add(purchase2);
            purchases.add(purchase3);
            customer.setPurchases(purchases);
        }
    }
}
