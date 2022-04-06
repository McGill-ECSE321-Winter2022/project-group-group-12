package ca.mcgill.ecse321.GSSS.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import ca.mcgill.ecse321.GSSS.model.OrderStatus;
import ca.mcgill.ecse321.GSSS.model.OrderType;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.Shift;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test the loading and persistence of the Customer repository.
 *
 * @author Enzo Benoit-Jeannin
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

  /**
   * This method is executed after each test and clears the concerned tables.
   *
   * @author Enzo Benoit-Jeannin
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

  /**
   * Method to create customer Address object from the given parameters.
   *
   * @param fullName     Full name of the customer's address
   * @param streetName   Name of the customer's street
   * @param streetNumber Customer's street number
   * @param city         Customer's city
   * @param postalCode   Customer's postal code
   * @param iD           Customer's address' iD
   * @return address Save the address in repository and return it
   * @author Enzo Benoit-Jeannin
   */
  private Address persistAddress(String fullName, String streetName, int streetNumber, String city,
      String postalCode,
      String iD) {
    Address address = new Address();
    address.setFullName(fullName);
    address.setStreetName(streetName);
    address.setStreetNumber(streetNumber);
    address.setCity(city);
    address.setPostalCode(postalCode);
    address.setId(iD);

    addressRepository.save(address);

    return address;
  }

  /**
   * Method to create the customer object given the parameters.
   *
   * @param email           Customer's email
   * @param username        Customer's username
   * @param password        Customer's password
   * @param customerAddress Customer's address
   * @param setOfPurchase   Customer's purchases
   * @param disabled        value specifying if the customer is disabled
   * @return customer Save the customer in repository and return it
   * @author Enzo Benoit-Jeannin
   */
  private Customer persist(String email, String username, String password, Address customerAddress,
      Set<Purchase> setOfPurchase, boolean disabled) {
    Customer customer = new Customer();
    customer.setEmail(email);
    customer.setUsername(username);
    customer.setPassword(password);
    customer.setAddress(customerAddress);
    customer.setDisabled(disabled);
    customer.setPurchases(setOfPurchase);

    customerRepository.save(customer);
    return customer;
  }

  /**
   * Method to create the employee object given the parameters.
   *
   * @param email    Employee's email
   * @param password Employee's password
   * @param username Employee's username
   * @param address  Employee's address
   * @param disabled Value specifying if the employee is disabled
   * @return employee Save the employee in repository and return it
   * @author Enzo Benoit-Jeannin
   */
  private Employee persistEmployee(String email, String password, String username, Address address,
      boolean disabled) {
    Employee employee = new Employee();
    employee.setAddress(address);
    employee.setEmail(email);
    employee.setPassword(password);
    employee.setDisabled(false);
    employee.setShifts(new HashSet<Shift>(0));

    employeeRepository.save(employee);
    return employee;
  }

  /**
   * Method to create ItemCategory object given the parameters.
   *
   * @param name Name of the ItemCategory
   * @return category Save the category on the repository and return it
   * @author Enzo Benoit-Jeannin
   */
  private ItemCategory persistItemCategory(String name) {
    ItemCategory category = new ItemCategory();
    category.setName(name);

    itemCategoryRepository.save(category);

    return category;
  }

  /**
   * Method to create Item object given the parameters.
   *
   * @param name              Item's name
   * @param description       Item's description
   * @param imageUrl          Item's url
   * @param price             Item's price
   * @param remainingQuantity Item's remaining quantity
   * @param availableForOrder Value specifying if the item is available for orders
   * @param stillAvailable    Value specifying if the item is available
   * @param category          Item's category
   * @return item Save the item on repository and return it
   * @author Enzo Benoit-Jeannin
   */
  private Item persistItem(String name, String description, String imageUrl, double price,
      int remainingQuantity,
      boolean availableForOrder, boolean stillAvailable, ItemCategory category) {
    Item item = new Item();
    item.setName(name);
    item.setDescription(description);
    item.setImageUrl(imageUrl);
    item.setPrice(price);
    item.setRemainingQuantity(remainingQuantity);
    item.setAvailableForOrder(availableForOrder);
    item.setStillAvailable(stillAvailable);
    item.setCategory(category);

    itemRepository.save(item);

    return item;
  }

  /**
   * Method to create a purchase object given the parameters.
   *
   * @param id          Purchase's id
   * @param date        Purchase's date
   * @param time        Purchase's time
   * @param items       Purchase's items
   * @param orderType   Purchase's order type
   * @param orderStatus Purchase's order status
   * @param employee    Purchase's assigned employee
   * @return purchase Save the purchase on repository and return it
   * @author Enzo Benoit-Jeannin
   */
  private Purchase persistPurchase(String id, Date date, Time time, Map<Item, Integer> items,
      OrderType orderType,
      OrderStatus orderStatus, Employee employee) {
    Purchase purchase = new Purchase();
    purchase.setId(id);
    purchase.setDate(date);
    purchase.setTime(time);
    purchase.setItems(items);
    purchase.setOrderType(orderType);
    purchase.setOrderStatus(orderStatus);
    purchase.setEmployee(employee);

    purchaseRepository.save(purchase);

    return purchase;
  }

  /**
   * This method asserts whether the actual Purchase matches the expected one.
   *
   * @param expected Expected purchase.
   * @param actual   Actual purchase.
   * @author Philippe Sarouphim Hochar.
   */
  private void verifyPurchase(Purchase expected, Purchase actual) {
    assertNotNull(actual);
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getDate(), actual.getDate());
    assertEquals(expected.getTime(), actual.getTime());
    assertEquals(expected.getOrderStatus(), actual.getOrderStatus());
    assertEquals(expected.getOrderType(), actual.getOrderType());
    assertEquals(expected.getEmployee().getEmail(), actual.getEmployee().getEmail());

    assertNotNull(actual.getItems());
    assertEquals(expected.getItems().size(), actual.getItems().size());
    for (Entry<Item, Integer> a : actual.getItems().entrySet()) {
      boolean contains = false;
      for (Entry<Item, Integer> e : expected.getItems().entrySet()) {
        if (e.getKey().equals(a.getKey())) {
          if (e.getValue().equals(a.getValue())) {
            contains = true;
            break;
          }
        }
      }
      assertTrue(contains);
    }
  }

  /**
   * Method asserts whether the given customers given match.
   *
   * @param expected Expected customer
   * @param actual   Actual customer
   * @author Enzo Benoit-Jeannin
   */
  private void verify(Customer expected, Customer actual) {
    assertNotNull(actual);
    assertEquals(expected.getUsername(), actual.getUsername());
    assertEquals(expected.getEmail(), actual.getEmail());
    assertEquals(expected.getPassword(), actual.getPassword());
    assertEquals(expected.getAddress(), actual.getAddress());
    assertEquals(expected.isDisabled(), actual.isDisabled());

    assertNotNull(expected.getPurchases());
    assertEquals(expected.getPurchases().size(), actual.getPurchases().size());

    for (Purchase p1 : actual.getPurchases()) {
      boolean contains = false;
      for (Purchase p2 : expected.getPurchases()) {
        if (p1.getId().equals(p2.getId())) {
          verifyPurchase(p1, p2);
          contains = true;
          break;
        }
      }
      assertTrue(contains);
    }
  }

  /**
   * Method evaluates whether the two given lists of customers contain the same customer objects.
   *
   * @param expected Expected list of customers retrieved.
   * @param actual   Actual list of customers retrieved
   * @author Enzo Benoit-Jeannin
   */
  private void verify(List<Customer> expected, List<Customer> actual) {
    assertNotNull(actual);
    assertEquals(expected.size(), actual.size());

    for (Customer c1 : expected) {
      boolean found = false;
      for (Customer c2 : actual) {
        if (c1.getEmail().equals(c2.getEmail())) {
          verify(c1, c2);
          found = true;
          break;
        }
      }
      assertTrue(found);
    }
  }

  /**
   * Test the findCustomerByUsername() method
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testPersistAndLoadCustomersByUsername() {

    String addressId1 = UUID.randomUUID().toString();
    String addressId2 = UUID.randomUUID().toString();
    String addressId3 = UUID.randomUUID().toString();

    String employeeAddressId = UUID.randomUUID().toString();
    String purchaseId1 = UUID.randomUUID().toString();
    String purchaseId2 = UUID.randomUUID().toString();
    String purchaseId3 = UUID.randomUUID().toString();

    String name1 = "Tyler The Creator";
    String name2 = "Drake";

    Address employeeAddress = persistAddress("ASAP Rocky", "Uni Street", 5, "Ottawa", "H6F 7U8",
        employeeAddressId);
    Employee employee = persistEmployee("rock@gm.com", "5678", "ASAP Rocky", employeeAddress,
        false);

    ItemCategory itemCategory = persistItemCategory("Food");
    HashMap<Item, Integer> items = new HashMap<>();

    items.put(persistItem("Steak", "vegan", "imgur.com/steak", 20.0, 3, true, true, itemCategory),
        3);

    Set<Purchase> setOfPurchases = new HashSet<Purchase>();
    setOfPurchases.add(
        persistPurchase(purchaseId1, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()),
            items, OrderType.Delivery, OrderStatus.OutForDelivery, employee));

    Set<Purchase> setOfPurchases2 = new HashSet<Purchase>();
    setOfPurchases.add(
        persistPurchase(purchaseId2, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()),
            items, OrderType.Delivery, OrderStatus.OutForDelivery, employee));

    Set<Purchase> setOfPurchases3 = new HashSet<Purchase>();
    setOfPurchases.add(
        persistPurchase(purchaseId3, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()),
            items, OrderType.Delivery, OrderStatus.OutForDelivery, employee));

    Address customerAddress1 = persistAddress("Tyler TheCreator", "Lorne Crescent", 4, "Montreal",
        "H2X 2B3",
        addressId1);
    Address customerAddress2 = persistAddress("Tyler TheCreator", "Montreal street", 8, "Montreal",
        "H2X 4B3",
        addressId2);
    Address customerAddress3 = persistAddress("Tyler TheCreator", "Unknown street", 22, "Montreal",
        "H2X 4H3",
        addressId3);

    List<Customer> expected = new ArrayList<Customer>(2);
    expected.add(
        persist("whodatboy@gm.com", name1, "1234", customerAddress1, setOfPurchases, false));
    expected.add(
        persist("whodatboy2@gm.com", name1, "5678", customerAddress2, setOfPurchases2, false));
    persist("drake@gm.com", name2, "90123", customerAddress3, setOfPurchases3, false);
    List<Customer> actual = customerRepository.findCustomersByUsername(name1);
    verify(expected, actual);
  }

  /**
   * Test the findCustomerByDisabled() method
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testPersistAndLoadCustomersByDisabled() {

    String addressId1 = UUID.randomUUID().toString();
    String addressId2 = UUID.randomUUID().toString();
    String addressId3 = UUID.randomUUID().toString();

    String employeeAddressId = UUID.randomUUID().toString();
    String purchaseId1 = UUID.randomUUID().toString();
    String purchaseId2 = UUID.randomUUID().toString();
    String purchaseId3 = UUID.randomUUID().toString();

    Address employeeAddress = persistAddress("ASAP Rocky", "Uni Street", 5, "Ottawa", "H6F 7U8",
        employeeAddressId);
    Employee employee = persistEmployee("rock@gm.com", "5678", "ASAP Rocky", employeeAddress,
        false);

    ItemCategory itemCategory = persistItemCategory("Food");
    HashMap<Item, Integer> items = new HashMap<>();

    items.put(persistItem("Steak", "vegan", "imgur.com/steak", 20.0, 3, true, true, itemCategory),
        3);

    Set<Purchase> setOfPurchases = new HashSet<Purchase>();
    setOfPurchases.add(
        persistPurchase(purchaseId1, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()),
            items, OrderType.Delivery, OrderStatus.OutForDelivery, employee));

    Set<Purchase> setOfPurchases2 = new HashSet<Purchase>();
    setOfPurchases.add(
        persistPurchase(purchaseId2, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()),
            items, OrderType.Delivery, OrderStatus.OutForDelivery, employee));

    Set<Purchase> setOfPurchases3 = new HashSet<Purchase>();
    setOfPurchases.add(
        persistPurchase(purchaseId3, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()),
            items, OrderType.Delivery, OrderStatus.OutForDelivery, employee));

    Address customerAddress1 = persistAddress("Tyler TheCreator", "Lorne Crescent", 4, "Montreal",
        "H2X 2B3",
        addressId1);
    Address customerAddress2 = persistAddress("Tyler TheCreator", "Montreal street", 8, "Montreal",
        "H2X 4B3",
        addressId2);
    Address customerAddress3 = persistAddress("Drake", "Unknown street", 22, "Montreal", "H2X 4H3",
        addressId3);

    List<Customer> expected = new ArrayList<Customer>(2);
    expected.add(
        persist("whodatboy@gm.com", "Tyler TheCreator", "1234", customerAddress1, setOfPurchases,
            true));
    expected.add(
        persist("whodatboy2@gm.com", "Tyler", "5678", customerAddress2, setOfPurchases2, true));

    persist("drake@gm.com", "Drake", "90123", customerAddress3, setOfPurchases3, false);

    List<Customer> actual = customerRepository.findCustomersByDisabled(true);
    verify(expected, actual);
  }

  /**
   * Test the findCustomerByEmail() method
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testPersistAndLoadCustomersByEmail() {

    String addressId1 = UUID.randomUUID().toString();
    String addressId2 = UUID.randomUUID().toString();
    String employeeAddressId = UUID.randomUUID().toString();
    String purchaseId1 = UUID.randomUUID().toString();
    String purchaseId2 = UUID.randomUUID().toString();
    String email = "whodatboy@gm.com";
    Address employeeAddress = persistAddress("ASAP Rocky", "Uni Street", 5, "Ottawa", "H6F 7U8",
        employeeAddressId);
    Employee employee = persistEmployee("rock@gm.com", "5678", "ASAP Rocky", employeeAddress,
        false);
    ItemCategory itemCategory = persistItemCategory("Food");
    HashMap<Item, Integer> items = new HashMap<>();
    items.put(persistItem("Steak", "vegan", "imgur.com/steak", 20.0, 3, true, true, itemCategory),
        3);

    Set<Purchase> setOfPurchases = new HashSet<Purchase>();
    setOfPurchases.add(
        persistPurchase(purchaseId1, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()),
            items, OrderType.Delivery, OrderStatus.OutForDelivery, employee));

    Set<Purchase> setOfPurchases2 = new HashSet<Purchase>();
    setOfPurchases.add(
        persistPurchase(purchaseId2, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()),
            items, OrderType.Delivery, OrderStatus.OutForDelivery, employee));

    Address customerAddress1 = persistAddress("Tyler TheCreator", "Lorne Crescent", 4, "Montreal",
        "H2X 2B3",
        addressId1);
    Address customerAddress2 = persistAddress("Tyler TheCreator", "Montreal street", 8, "Montreal",
        "H2X 4B3",
        addressId2);

    Customer expected = persist(email, "Tyler TheCreator", "1234", customerAddress1, setOfPurchases,
        false);
    persist("whodatboy2@gm.com", "Tyler", "5678", customerAddress2, setOfPurchases2, false);

    Customer actual = customerRepository.findCustomerByEmail(email);
    verify(expected, actual);

  }

  /**
   * Test the findCustomerByAddress() method
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testPersistAndLoadCustomersByAddress() {

    String addressId1 = UUID.randomUUID().toString();
    String addressId2 = UUID.randomUUID().toString();
    String employeeAddressId = UUID.randomUUID().toString();
    String purchaseId1 = UUID.randomUUID().toString();
    String purchaseId2 = UUID.randomUUID().toString();
    Address employeeAddress = persistAddress("ASAP Rocky", "Uni Street", 5, "Ottawa", "H6F 7U8",
        employeeAddressId);
    Employee employee = persistEmployee("rock@gm.com", "5678", "ASAP Rocky", employeeAddress,
        false);
    ItemCategory itemCategory = persistItemCategory("Food");
    HashMap<Item, Integer> items = new HashMap<>();
    items.put(persistItem("Steak", "vegan", "imgur.com/steak", 20.0, 3, true, true, itemCategory),
        3);

    Set<Purchase> setOfPurchases = new HashSet<Purchase>();
    setOfPurchases.add(
        persistPurchase(purchaseId1, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()),
            items, OrderType.Delivery, OrderStatus.OutForDelivery, employee));

    Set<Purchase> setOfPurchases2 = new HashSet<Purchase>();
    setOfPurchases.add(
        persistPurchase(purchaseId2, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()),
            items, OrderType.Delivery, OrderStatus.OutForDelivery, employee));

    Address customerAddress1 = persistAddress("Tyler TheCreator", "Lorne Crescent", 4, "Montreal",
        "H2X 2B3",
        addressId1);
    Address customerAddress2 = persistAddress("Tyler TheCreator", "Montreal street", 8, "Montreal",
        "H2X 4B3",
        addressId2);

    Customer expected = persist("whodatboy@gm.com", "Tyler TheCreator", "1234", customerAddress1,
        setOfPurchases,
        false);
    persist("whodatboy2@gm.com", "Tyler", "5678", customerAddress2, setOfPurchases2, false);
    Customer actual = customerRepository.findCustomerByAddress(customerAddress1);
    verify(expected, actual);

  }

  /**
   * Test the findCustomerByPurchases() method
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testPersistAndLoadCustomersByPurchases() {
    String addressId1 = UUID.randomUUID().toString();
    String addressId2 = UUID.randomUUID().toString();
    String employeeAddressId = UUID.randomUUID().toString();
    String purchaseId1 = UUID.randomUUID().toString();
    String purchaseId2 = UUID.randomUUID().toString();
    Address employeeAddress = persistAddress("ASAP Rocky", "Uni Street", 5, "Ottawa", "H6F 7U8",
        employeeAddressId);
    Employee employee = persistEmployee("rock@gm.com", "5678", "ASAP Rocky", employeeAddress,
        false);
    ItemCategory itemCategory = persistItemCategory("Food");
    HashMap<Item, Integer> items = new HashMap<>();
    items.put(persistItem("Steak", "vegan", "imgur.com/steak", 20.0, 3, true, true, itemCategory),
        3);

    Set<Purchase> setOfPurchases = new HashSet<Purchase>();
    setOfPurchases.add(
        persistPurchase(purchaseId1, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()),
            items, OrderType.Delivery, OrderStatus.OutForDelivery, employee));

    Set<Purchase> setOfPurchases2 = new HashSet<Purchase>();
    Purchase purchase2 = persistPurchase(purchaseId2, Date.valueOf(LocalDate.now()),
        Time.valueOf(LocalTime.now()),
        items, OrderType.Delivery, OrderStatus.OutForDelivery, employee);
    setOfPurchases.add(purchase2);

    Address customerAddress1 = persistAddress("Tyler TheCreator", "Lorne Crescent", 4, "Montreal",
        "H2X 2B3",
        addressId1);
    Address customerAddress2 = persistAddress("Tyler TheCreator", "Montreal street", 8, "Montreal",
        "H2X 4B3",
        addressId2);

    Customer expected = persist("whodatboy@gm.com", "Tyler TheCreator", "1234", customerAddress1,
        setOfPurchases,
        false);
    persist("whodatboy2@gm.com", "Tyler", "5678", customerAddress2, setOfPurchases2, false);
    Customer actual = customerRepository.findCustomerByPurchases(purchase2);
    verify(expected, actual);
  }
}
