package ca.mcgill.ecse321.GSSS.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import ca.mcgill.ecse321.GSSS.model.OrderStatus;
import ca.mcgill.ecse321.GSSS.model.OrderType;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.Shift;

/**
 * This class tests the purchase repository.
 * 
 * @author Philippe Sarouphim Hochar.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestPurchasePersistence {
 
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    /**
     * This method gets executed after each test, and clears the relevant tables.
     * 
     * @author Philippe Sarouphim Hochar.
     */
    @AfterEach
    public void clearTables(){
        purchaseRepository.deleteAll();
        employeeRepository.deleteAll();
        itemRepository.deleteAll();
        itemCategoryRepository.deleteAll();
    }

    /**
     * This method helps create a Purchase, create its corresponding employee, and save both to the database.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param id Id of the purchase.
     * @param date Date of the purchase.
     * @param time Time of the purchase.
     * @param items Items purchased.
     * @param orderType Order type of the purchase.
     * @param orderStatus Order status of the purchase.
     * @param email Email of the employee.
     * @param username Username of the employee.
     * @param password Password of the employee.
     * @param disabled Whether the employee account is disabled.
     * @return The created Purchase instance.
     */
    private Purchase persist(String id, Date date, Time time, Map<Item, Integer> items, OrderType orderType, OrderStatus orderStatus, String email, String username, String password, boolean disabled){
        Employee employee = persistEmployee(email, username, password, disabled);
        Purchase purchase = persistPurchase(id, date, time, items, orderType, orderStatus, employee);

        return purchase;
    }


    /**
     * This method creates a Purchase from the specified paramters, and saves it to the database.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param id Id of the purchase.
     * @param date Date of the purchase.
     * @param time Time of the purchase.
     * @param items Items purchased.
     * @param orderType Order type of the purchase.
     * @param orderStatus Order status of the purchase.
     * @param employee Employee associated to the purchase.
     * @return The created Purchase instance.
     */
    private Purchase persistPurchase(String id, Date date, Time time, Map<Item, Integer> items, OrderType orderType, OrderStatus orderStatus, Employee employee){
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
     * This method creates an Employee from the specified parameters, and saves it to the database.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param email Email of the employee.
     * @param username Username of the employee.
     * @param password Password of the employee.
     * @param disabled Whether the employee account is disabled.
     * @return The created Employee instance.
     */
    private Employee persistEmployee(String email, String username, String password, boolean disabled){
        Employee employee = new Employee();
        employee.setEmail(email);
        employee.setUsername(username);
        employee.setPassword(password);
        employee.setDisabled(disabled);
        employee.setShifts(new HashSet<Shift>(0));

        employeeRepository.save(employee);

        return employee;
    }

    /**
     * This method helps create an Item, create its corresponding category, and save both to the database.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param name Name of the item.
     * @param description Description of the item.
     * @param imageUrl URL of the item's image.
     * @param price Price of the item.
     * @param remainingQuantity Remaining quantity of the item.
     * @param availableForOrder Availability for order of the item.
     * @param stillAvailable Availabilty in shop of the item.
     * @param categoryName Name of the category of the item.
     * @return The created Item instance.
     */
    public Item persist(String name, String description, String imageUrl, double price, int remainingQuantity, boolean availableForOrder, boolean stillAvailable, String categoryName){
        ItemCategory category = persistItemCategory(categoryName);
        Item item = persistItem(name, description, imageUrl, price, remainingQuantity, availableForOrder, stillAvailable, category);

        return item;
    }

    /**
     * This method creates an ItemCategory from the specified name, and saves it to the database.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param categoryName Name of the category.
     * @return The created ItemCategory instance.
     */
    private ItemCategory persistItemCategory(String categoryName){
        ItemCategory category = new ItemCategory();
        category.setName(categoryName);

        itemCategoryRepository.save(category);

        return category;
    }
    
    /**
     * This method creates an Item from the specified parameters, and saves it to the database.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param name Name of the item.
     * @param description Description of the item.
     * @param imageUrl URL of the item's image.
     * @param price Price of the item.
     * @param remainingQuantity Remaining quantity of the item.
     * @param availableForOrder Availability for order of the item.
     * @param stillAvailable Availability in shop of the item.
     * @param category Category of the item.
     * @return The created Item instance.
     */
    private Item persistItem(String name, String description, String imageUrl, double price, int remainingQuantity, boolean availableForOrder, boolean stillAvailable, ItemCategory category){
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
     * This method asserts whether the actual list of Purchase matches the expected one.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param expected Expected list of purchases.
     * @param actual Actual list of purchase.
     */
    private void verify(List<Purchase> expected, List<Purchase> actual){
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        
        for(Purchase e: expected){
            boolean found = false;
            for(Purchase a: actual){
                if(e.getId().equals(a.getId())){
                    verify(e, a);
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
    }

    /**
     * This method tests the create and read functionnalities of Purchase by id.
     * 
     * @author Philippe Sarouphim Hochar.
     */
    @Test
    public void testById(){
        HashMap<Item, Integer> items = new HashMap<>();
        items.put(persist("Pasta", "Italian dish.", "imgur.com/pasta", 5.0, 2, true, true, "Food"), 1);
        items.put(persist("Pizza", "Italian dish.", "imgur.com/pizza", 4.0, 3, true, false, "Food"), 3);

        String id = UUID.randomUUID().toString();

        Purchase expected = persist(
            id,
            Date.valueOf(LocalDate.now()),
            Time.valueOf(LocalTime.now()),
            items,
            OrderType.Delivery,
            OrderStatus.OutForDelivery,
            "employee@gsss.com",
            "employee",
            "password",
            false
        );

        Purchase actual = purchaseRepository.findPurchaseById(id);

        verify(expected, actual);
    }

    /**
     * This method tests the create and read functionnalities of Purchase by date.
     * 
     * @author Philippe Sarouphim Hochar.
     */
    @Test
    public void testByDate(){
        HashMap<Item, Integer> items = new HashMap<>();
        items.put(persist("Pasta", "Italian dish.", "imgur.com/pasta", 5.0, 2, true, true, "Food"), 1);
        items.put(persist("Pizza", "Italian dish.", "imgur.com/pizza", 4.0, 3, true, false, "Food"), 3);

        List<Purchase> expected = new ArrayList<Purchase>(3);
        expected.add(persist(
            UUID.randomUUID().toString(),
            Date.valueOf(LocalDate.now()),
            Time.valueOf(LocalTime.now()),
            items,
            OrderType.InPerson,
            OrderStatus.Completed,
            "employee@gsss.com",
            "employee",
            "password",
            false
        ));

        persist(
            UUID.randomUUID().toString(),
            Date.valueOf("2000-01-01"),
            Time.valueOf(LocalTime.parse("09:00:00")),
            items,
            OrderType.Delivery,
            OrderStatus.OutForDelivery,
            "employee@gsss.com",
            "employee",
            "password",
            false
        );

        expected.add(persist(
            UUID.randomUUID().toString(),
            Date.valueOf(LocalDate.now()),
            Time.valueOf(LocalTime.parse("09:00:00")),
            items,
            OrderType.Pickup,
            OrderStatus.OutForDelivery,
            "employee@gsss.com",
            "employee",
            "password",
            false
        ));

        List<Purchase> actual = purchaseRepository.findPurchasesByDate(Date.valueOf(LocalDate.now()));

        verify(expected, actual);
    }

    /**
     * This method tests the create and read functionnalities of Purchase by date and time.
     * 
     * @author Philippe Sarouphim Hochar.
     */
    @Test
    public void testByDateAndTime(){
        HashMap<Item, Integer> items = new HashMap<>();
        items.put(persist("Pasta", "Italian dish.", "imgur.com/pasta", 5.0, 2, true, true, "Food"), 1);
        items.put(persist("Pizza", "Italian dish.", "imgur.com/pizza", 4.0, 3, true, false, "Food"), 3);

        List<Purchase> expected = new ArrayList<Purchase>(3);
        persist(
            UUID.randomUUID().toString(),
            Date.valueOf(LocalDate.now()),
            Time.valueOf(LocalTime.now()),
            items,
            OrderType.InPerson,
            OrderStatus.Completed,
            "employee@gsss.com",
            "employee",
            "password",
            false
        );

        persist(
            UUID.randomUUID().toString(),
            Date.valueOf("2000-01-01"),
            Time.valueOf(LocalTime.parse("09:00:00")),
            items,
            OrderType.Delivery,
            OrderStatus.OutForDelivery,
            "employee@gsss.com",
            "employee",
            "password",
            false
        );

        expected.add(persist(
            UUID.randomUUID().toString(),
            Date.valueOf(LocalDate.now()),
            Time.valueOf(LocalTime.parse("09:00:00")),
            items,
            OrderType.Pickup,
            OrderStatus.OutForDelivery,
            "employee@gsss.com",
            "employee",
            "password",
            false
        ));

        List<Purchase> actual = purchaseRepository.findPurchasesByDateAndTime(Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.parse("09:00:00")));

        verify(expected, actual);
    }

    /**
     * This method tests the create and read functionnalities of Purchase by employee.
     * 
     * @author Philippe Sarouphim Hochar.
     */
    @Test
    public void testByEmployee(){
        HashMap<Item, Integer> items = new HashMap<>();
        items.put(persist("Pasta", "Italian dish.", "imgur.com/pasta", 5.0, 2, true, true, "Food"), 1);
        items.put(persist("Pizza", "Italian dish.", "imgur.com/pizza", 4.0, 3, true, false, "Food"), 3);

        Employee employee = persistEmployee("worker@gsss.com", "woker", "password", false);

        List<Purchase> expected = new ArrayList<Purchase>(3);
        expected.add(persist(
            UUID.randomUUID().toString(),
            Date.valueOf(LocalDate.now()),
            Time.valueOf(LocalTime.now()),
            items,
            OrderType.InPerson,
            OrderStatus.Completed,
            employee.getEmail(),
            employee.getUsername(),
            employee.getPassword(),
            employee.isDisabled()
        ));

        persist(
            UUID.randomUUID().toString(),
            Date.valueOf("2000-01-01"),
            Time.valueOf(LocalTime.parse("09:00:00")),
            items,
            OrderType.Delivery,
            OrderStatus.OutForDelivery,
            "employee@gsss.com",
            "employee",
            "password",
            false
        );

        expected.add(persist(
            UUID.randomUUID().toString(),
            Date.valueOf(LocalDate.now()),
            Time.valueOf(LocalTime.parse("09:00:00")),
            items,
            OrderType.Pickup,
            OrderStatus.OutForDelivery,
            employee.getEmail(),
            employee.getUsername(),
            employee.getPassword(),
            employee.isDisabled()
        ));

        List<Purchase> actual = purchaseRepository.findPurchasesByEmployee(employee);

        verify(expected, actual);
    }

}
