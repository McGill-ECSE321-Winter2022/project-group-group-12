package ca.mcgill.ecse321.GSSS.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.Map.Entry;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.Shift;

/**
 * Test the loading and persistence of the Customer repository
 * @author Enzo Benoit-Jeannin
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestCustomerPersistence {
	
	@Autowired
	  private AddressRepository addressRepository;

	  @Autowired
	  private BusinessHourRepository businessHourRepository;

	  @Autowired
	  private CustomerRepository customerRepository;

	  @Autowired
	  private EmployeeRepository employeeRepository;

	  @Autowired
	  private ItemCategoryRepository itemCategoryRepository;

	  @Autowired
	  private ItemRepository itemRepository;

	  @Autowired
	  private OwnerRepository ownerRepository;

	  @Autowired
	  private PurchaseRepository purchaseRepository;

	  @Autowired
	  private ShiftRepository shiftRepository;

	  @Autowired
	  private AccountRepository accountRepository;
	
	
    /**
     * This method is executed after each test and clears the concerned tables.
     * 
     * @author Enzo Benoit-Jeannin
     */
    @AfterEach
    public void clearDatabase() {
    	accountRepository.deleteAll();
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
    
    // Initialize variables to create customer and its address 
    String name = "Tyler The Creator";
    String email = "whodatboy@gmail.com";
    String password = "tylerpassword";
    String fullName = "Tyler TheCreator";
    String streetName = "Lorne Crescent";
    String city = "Montreal";
    int streetNumber = 69;
    String postalCode = "H2X 2B3";
    String addressId = UUID.randomUUID().toString();
    
    // Initialize variables to create employee and its address 

    String employeeFullName = "Asap Rocky";
    String employeeStreetName = "Univeristy Street";

    String employeeCity = "Montreal";
    int employeeStreetNumber = 12;
    String employeePostalCode = "H2Y 4X3";
    String employeeAddressId = UUID.randomUUID().toString();
    String employeeName = "ASAP Rocky";
    String employeeEmail = "praisethelord@gmail.com";
    String employeePassword = "asaprocky";
    String employeeId = UUID.randomUUID().toString();
    
    // Initialiaze variables to create purchase
    Time purchaseTime = java.sql.Time.valueOf(LocalTime.of(13, 30));
    Date purchaseDate = Date.valueOf(LocalDate.of(2020, Month.FEBRUARY, 14));
    String purchaseId =  UUID.randomUUID().toString();
    
    //Initialize variables to create ItemCatgeroy and items
    String itemName = "Pizza";
    String itemDescription = "Italian dish";
    String itemURL = "imgur.com/pizza";
    double itemPrice = 5.0;
    int itemQuantityRemaining = 3;
    boolean itemAvailibility = true;
    boolean itemOrderAvailability = true;
    String categoryName = "Food";
    int itemQuantityDesired = 3;

    /**
     * Method to create customer Address variable
     * @return customer address
     * @author Enzo Benoit-Jeannin
     */
    private Address persistAddress() {
    	Address address = new Address();
        address.setFullName(fullName);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setId(addressId);
        return address;
    }
    /**
     * Method to create employee Address variable
     * @return employee address
     * @author Enzo Benoit-Jeannin
     */
    private Address persistEmployeeAddress() {
        Address employeeAddress = new Address();
        employeeAddress.setFullName(employeeFullName);
        employeeAddress.setStreetName(employeeStreetName);
        employeeAddress.setStreetNumber(employeeStreetNumber);
        employeeAddress.setCity(employeeCity);
        employeeAddress.setPostalCode(employeePostalCode);
        employeeAddress.setId(employeeAddressId);
        return employeeAddress;
    }
    
    /**
     * Method to create the customer object
     * @param customerAddress
     * @param setOfPurchase
     * @param disabled
     * @return customer
     * @author Enzo Benoit-Jeannin
     */
    private Customer persist(Address customerAddress, Set<Purchase> setOfPurchase, boolean disabled) {
    	// Create customer
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setUsername(name);
        customer.setPassword(password);
        customer.setAddress(customerAddress);
        customer.setDisabled(disabled);
        customer.setPurchases(setOfPurchase);
        return customer;
    }
    
    /**
     *  Method to create the employee object
     * @param employeeAddress2
     * @return employee
     * @author Enzo Benoit-Jeannin
     */
    private Employee persistEmployee (Address employeeAddress2) {
    	// Create employee 
        Employee employee = new Employee();
        employee.setAddress(employeeAddress2);
        employee.setEmail(employeeEmail);
        employee.setPassword(employeePassword);
        employee.setDisabled(false);
        employee.setShifts(new HashSet<Shift>(0));
        return employee;
    }
    
    /**
     * Method to create ItemCategory object
     * @return an item category
     * @author Enzo Benoit-Jeannin
     */
    private ItemCategory persistItemCategory() {
    	// Create ItemCategory
        ItemCategory category = new ItemCategory();
        category.setName(categoryName);
        return category;
    }
    
    /**
     * Method to create Item object
     * @param itemcategory
     * @return an item
     * @author Enzo Benoit-Jeannin
     */
    private Item persistItem(ItemCategory itemcategory) {
    	 // Create Item and put it in Hashmap
        Item item = new Item();
        item.setName(name);
        item.setDescription(itemDescription);
        item.setImageUrl(itemURL);
        item.setPrice(itemPrice);
        item.setRemainingQuantity(itemQuantityRemaining);
        item.setAvailableForOrder(itemAvailibility);
        item.setStillAvailable(itemOrderAvailability);
        item.setCategory(itemcategory);
        return item;
    }
    
    /**
     * Method to create a purchase object
     * @param employee
     * @param items
     * @return a purchase
     * @author Enzo Benoit-Jeannin
     */
    private Purchase persistPurchase(Employee employee, HashMap<Item, Integer> items) {
    	// Create Purchase and add it to customer's set of purchase
        Purchase purchase = new Purchase();
        purchase.setDate(purchaseDate);
        purchase.setTime(purchaseTime);
        purchase.setId(purchaseId);
        purchase.setEmployee(employee);
        purchase.setItems(items);
        return purchase;
    }
    
    /**
     * This method asserts whether the actual Purchase matches the expected one.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param expected Expected purchase.
     * @param actual Actual purchase.
     */
    private void verify(Purchase expected, Purchase actual){
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected.getTime(), actual.getTime());
        assertEquals(expected.getOrderStatus(), actual.getOrderStatus());
        assertEquals(expected.getOrderType(), actual.getOrderType());
        assertEquals(expected.getEmployee().getEmail(), actual.getEmployee().getEmail());

        assertNotNull(actual.getItems());
        assertEquals(expected.getItems().size(), actual.getItems().size());
        for(Entry<Item, Integer> a: actual.getItems().entrySet()){
            boolean contains = false;
            for(Entry<Item, Integer> e: expected.getItems().entrySet()){
                if(e.getKey().equals(a.getKey())){
                    if(e.getValue().equals(a.getValue())){
                        contains = true;
                        break;
                    }
                }
            }
            assertTrue(contains);
        }
    }
    
    /**
     * Test the findCustomerByUsername() method
     * @author Enzo Benoit-Jeannin
     */
    @Test
    public void testPersistAndLoadCustomersByUsername(){
        
    	Address customerAddress = persistAddress();
    	Address employeeAddress = persistEmployeeAddress();
    	Employee employee = persistEmployee(employeeAddress);
    	ItemCategory itemCategory = persistItemCategory();
    	Item item = persistItem(itemCategory);
    	HashMap<Item, Integer> items = new HashMap<>();
        items.put(item, itemQuantityDesired);
    	Purchase purchase = persistPurchase(employee, items);
    	Set<Purchase> setOfPurchases = new HashSet<Purchase>();
        setOfPurchases.add(purchase);
        Customer customer = persist(customerAddress, setOfPurchases, false);
        
        itemCategoryRepository.save(itemCategory);
        addressRepository.save(employeeAddress);
        employeeRepository.save(employee);
        addressRepository.save(customerAddress);
        itemRepository.save(item);
        purchaseRepository.save(purchase);
        customerRepository.save(customer);
        
        List<Customer> myCustomers = customerRepository.findCustomersByUsername(name);
        customer = myCustomers.get(0);
        
        assertNotNull(myCustomers);
        assertNotNull(customer);		
        assertEquals(customer.getUsername(), name);
        assertEquals(customer.getEmail(), email);
        assertEquals(customer.getPassword(), password);
        assertEquals(customer.getAddress(), customerAddress);
        assertEquals(customer.isDisabled(), false);
        assertNotNull(customer.getPurchases());
        
        assertEquals(customer.getPurchases().size(), setOfPurchases.size());
        
        for(Purchase p1 : setOfPurchases) {
        	boolean contains = false;
        	for(Purchase p2 : customer.getPurchases()) {
        		if(p1.getId().equals(p2.getId())){
                    verify(p1, p2);
                    contains = true;
                    break;
                }
        	}
        	assertTrue(contains);
        }
    }
    
    /**
     * Test the findCustomerByDisabled() method
     * @author Enzo Benoit-Jeannin
     */
    @Test
    public void testPersistAndLoadCustomersByDisabled(){
    	Address customerAddress = persistAddress();
    	Address employeeAddress = persistEmployeeAddress();
    	Employee employee = persistEmployee(employeeAddress);
    	ItemCategory itemCategory = persistItemCategory();
    	Item item = persistItem(itemCategory);
    	HashMap<Item, Integer> items = new HashMap<>();
        items.put(item, itemQuantityDesired);
    	Purchase purchase = persistPurchase(employee, items);
    	Set<Purchase> setOfPurchases = new HashSet<Purchase>();
        setOfPurchases.add(purchase);
        Customer customer = persist(customerAddress, setOfPurchases, true);
        
        itemCategoryRepository.save(itemCategory);
        addressRepository.save(employeeAddress);
        employeeRepository.save(employee);
        addressRepository.save(customerAddress);
        itemRepository.save(item);
        purchaseRepository.save(purchase);
        customerRepository.save(customer);
        
        List<Customer> myCustomers = customerRepository.findCustomersByDisabled(true);
        customer = myCustomers.get(0);
        
        assertNotNull(myCustomers);
        assertNotNull(customer);			
        assertEquals(customer.getUsername(), name);
        assertEquals(customer.getEmail(), email);
        assertEquals(customer.getPassword(), password);
        assertEquals(customer.getAddress(), customerAddress);
        assertEquals(customer.isDisabled(), true);
        assertNotNull(customer.getPurchases());
        
        assertEquals(customer.getPurchases().size(), setOfPurchases.size());
        
        for(Purchase p1 : setOfPurchases) {
        	boolean contains = false;
        	for(Purchase p2 : customer.getPurchases()) {
        		if(p1.getId().equals(p2.getId())){
                    verify(p1, p2);
                    contains = true;
                    break;
                }
        	}
        	assertTrue(contains);
        }
    }
    
    /**
     * Test the findCustomerByEmail() method
     * @author Enzo Benoit-Jeannin
     */
    @Test
    public void testPersistAndLoadCustomersByEmail(){
    	Address customerAddress = persistAddress();
    	Address employeeAddress = persistEmployeeAddress();
    	Employee employee = persistEmployee(employeeAddress);
    	ItemCategory itemCategory = persistItemCategory();
    	Item item = persistItem(itemCategory);
    	HashMap<Item, Integer> items = new HashMap<>();
        items.put(item, itemQuantityDesired);
    	Purchase purchase = persistPurchase(employee, items);
    	Set<Purchase> setOfPurchases = new HashSet<Purchase>();
        setOfPurchases.add(purchase);
        Customer customer = persist(customerAddress, setOfPurchases, false);
        
        itemCategoryRepository.save(itemCategory);
        addressRepository.save(employeeAddress);
        employeeRepository.save(employee);
        addressRepository.save(customerAddress);
        itemRepository.save(item);
        purchaseRepository.save(purchase);
        customerRepository.save(customer);
        
        customer = customerRepository.findCustomerByEmail(email);
        
        assertNotNull(customer);		
        assertEquals(customer.getUsername(), name);
        assertEquals(customer.getEmail(), email);
        assertEquals(customer.getPassword(), password);
        assertEquals(customer.getAddress(), customerAddress);
        assertEquals(customer.isDisabled(), false);
        assertNotNull(customer.getPurchases());
        
        assertEquals(customer.getPurchases().size(), setOfPurchases.size());
        
        for(Purchase p1 : setOfPurchases) {
        	boolean contains = false;
        	for(Purchase p2 : customer.getPurchases()) {
        		if(p1.getId().equals(p2.getId())){
                    verify(p1, p2);
                    contains = true;
                    break;
                }
        	}
        	assertTrue(contains);
        }
    }
    
    /**
     * Test the findCustomerByAddress() method
     * @author Enzo Benoit-Jeannin
     */
    @Test
    public void testPersistAndLoadCustomersByAddress(){
    	Address customerAddress = persistAddress();
    	Address employeeAddress = persistEmployeeAddress();
    	Employee employee = persistEmployee(employeeAddress);
    	ItemCategory itemCategory = persistItemCategory();
    	Item item = persistItem(itemCategory);
    	HashMap<Item, Integer> items = new HashMap<>();
        items.put(item, itemQuantityDesired);
    	Purchase purchase = persistPurchase(employee, items);
    	Set<Purchase> setOfPurchases = new HashSet<Purchase>();
        setOfPurchases.add(purchase);
        Customer customer = persist(customerAddress, setOfPurchases, false);
        
        itemCategoryRepository.save(itemCategory);
        addressRepository.save(employeeAddress);
        employeeRepository.save(employee);
        addressRepository.save(customerAddress);
        itemRepository.save(item);
        purchaseRepository.save(purchase);
        customerRepository.save(customer);
        
        customer = customerRepository.findCustomerByAddress(customerAddress);
        
        assertNotNull(customer);		
        assertEquals(customer.getUsername(), name);
        assertEquals(customer.getEmail(), email);
        assertEquals(customer.getPassword(), password);
        assertEquals(customer.getAddress(), customerAddress);
        assertEquals(customer.isDisabled(), false);
        assertNotNull(customer.getPurchases());
        
        assertEquals(customer.getPurchases().size(), setOfPurchases.size());
        
        for(Purchase p1 : setOfPurchases) {
        	boolean contains = false;
        	for(Purchase p2 : customer.getPurchases()) {
        		if(p1.getId().equals(p2.getId())){
                    verify(p1, p2);
                    contains = true;
                    break;
                }
        	}
        	assertTrue(contains);
        }
    }
    
    /**
     * Test the findCustomerByPurchases() method
     * @author Enzo Benoit-Jeannin
     */
    @Test
    public void testPersistAndLoadCustomersByPurchases(){
    	Address customerAddress = persistAddress();
    	Address employeeAddress = persistEmployeeAddress();
    	Employee employee = persistEmployee(employeeAddress);
    	ItemCategory itemCategory = persistItemCategory();
    	Item item = persistItem(itemCategory);
    	HashMap<Item, Integer> items = new HashMap<>();
        items.put(item, itemQuantityDesired);
    	Purchase purchase = persistPurchase(employee, items);
    	Set<Purchase> setOfPurchases = new HashSet<Purchase>();
        setOfPurchases.add(purchase);
        Customer customer = persist(customerAddress, setOfPurchases, false);
        
        itemCategoryRepository.save(itemCategory);
        addressRepository.save(employeeAddress);
        employeeRepository.save(employee);
        addressRepository.save(customerAddress);
        itemRepository.save(item);
        purchaseRepository.save(purchase);
        customerRepository.save(customer);
        
        customer = null;
        
        customer = customerRepository.findCustomerByPurchases(purchase);
        
        assertNotNull(customer);		
        assertEquals(customer.getUsername(), name);
        assertEquals(customer.getEmail(), email);
        assertEquals(customer.getPassword(), password);
        assertEquals(customer.getAddress(), customerAddress);
        assertEquals(customer.isDisabled(), false);
        assertNotNull(customer.getPurchases());
        
        assertEquals(customer.getPurchases().size(), setOfPurchases.size());
        
        for(Purchase p1 : setOfPurchases) {
        	boolean contains = false;
        	for(Purchase p2 : customer.getPurchases()) {
        		if(p1.getId().equals(p2.getId())){
                    verify(p1, p2);
                    contains = true;
                    break;
                }
        	}
        	assertTrue(contains);
        }
    }
}
