package ca.mcgill.ecse321.GSSS.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;

/**
 * This class tests the item repository.
 * 
 * @author Philippe Sarouphim Hochar.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestItemPersistence {
    
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
        itemRepository.deleteAll();
        itemCategoryRepository.deleteAll();
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
     * This method asserts whether the actual Item matches the expected one.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param expected Expected item.
     * @param actual Actual item.
     */
    private void verify(Item expected, Item actual){
        assertNotNull(actual);
        assertTrue(expected.equals(actual));
    }

    /**
     * This method asserts whether the actual list of items matches the expected one.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param expected Expected item list.
     * @param actual Actual item list.
     */
    private void verify(List<Item> expected, List<Item> actual){
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        for(int i = 0; i < expected.size(); i++){
            assertTrue(actual.contains(expected.get(i)));
        }
    }

    /**
     * This method tests the create and read functionnalities for item by name.
     * 
     * @author Philippe Sarouphim Hochar.
     */
    @Test
    public void testLoadItemByName(){
        Item item1 = persist("Pasta", "Italian dish.", "imgur.com/pasta", 5.0, 4, true, true, "Food");
        
        Item item2 = itemRepository.findItemByName(item1.getName());

        verify(item1, item2);
    }

    /**
     * This method tests the create and read functionnalities for item by availability for order.
     * 
     * @author Philippe Sarouphim Hochar.
     */
    @Test
    public void testLoadItemsByAvailabilityForOrder(){
        List<Item> items1 = new ArrayList<Item>(2);
        items1.add(persist("Naan", "Indian type of bread.", "imgur.com/naan", 1.5, 8, true, false, "Food"));
        items1.add(persist("Pasta", "Italian dish.", "imgur.com/pasta", 5, 4, true, true, "Food"));
        persist("Pizza", "Italian dish.", "imgur.com/pizza", 6, 6, false, true, "Food");

        List<Item> items2 = itemRepository.findItemsByAvailableForOrder(true);

        verify(items1, items2);
    }

    /**
     * This method tests the create and read functionnalities for item by availability in shop.
     * 
     * @author Philippe Sarouphim Hochar.
     */
    @Test
    public void testLoadItemsByStillAvailable(){
        List<Item> items1 = new ArrayList<Item>(1);
        items1.add(persist("Naan", "Indian type of bread.", "imgur.com/naan", 1.5, 8, true, false, "Food"));
        persist("Pasta", "Italian dish.", "imgur.com/pasta", 5, 4, true, true, "Food");
        persist("Pizza", "Italian dish.", "imgur.com/pizza", 6, 6, false, true, "Food");

        List<Item> items2 = itemRepository.findItemsByStillAvailable(false);

        verify(items1, items2);
    }

    /**
     * This method tests the create and read functionnalities for item by cateogry.
     * 
     * @author Philippe Sarouphim Hochar.
     */
    @Test
    public void testLoadItemsByCategory(){
        ItemCategory category = persistItemCategory("Food");

        List<Item> items1 = new ArrayList<Item>(2);
        items1.add(persist("Naan", "Indian type of bread.", "imgur.com/naan", 1.5, 8, true, false, "Food"));
        items1.add(persist("Pasta", "Italian dish.", "imgur.com/pasta", 5, 4, true, true, "Food"));
        persist("Coke can", "Cola.", "imgur.com/coke", 2, 5, false, true, "Beverage");

        List<Item> items2 = itemRepository.findItemsByCategory(category);

        verify(items1, items2);
    }

    /**
     * This method tests the create and read functionnalities for item by catgory, availability for
     * order, and availability in shop..
     * 
     * @author Philippe Sarouphim Hochar.
     */
    @Test
    public void testLoadItemsByCategoryAndAvailableForOrderAndStillAvailable(){
        ItemCategory category = persistItemCategory("Beverage");

        List<Item> items1 = new ArrayList<Item>(1);
        persist("Naan", "Indian type of bread.", "imgur.com/naan", 1.5, 8, true, false, "Food");
        persist("Pasta", "Italian dish.", "imgur.com/pasta", 5, 4, true, true, "Food");
        items1.add(persist("Coke can", "Cola.", "imgur.com/coke", 2, 5, false, true, "Beverage"));

        List<Item> items2 = itemRepository.findItemsByCategoryAndAvailableForOrderAndStillAvailable(category, false, true);
        List<Item> items3 = itemRepository.findItemsByCategoryAndAvailableForOrderAndStillAvailable(category, false, false);

        verify(items1, items2);
        verify(new ArrayList<Item>(), items3);
    }


}
