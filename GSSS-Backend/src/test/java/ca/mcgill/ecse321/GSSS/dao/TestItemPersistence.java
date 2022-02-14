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

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestItemPersistence {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemCategoryRepository itemCategoryRepository;

    @AfterEach
    public void clearDatabase() {
        itemRepository.deleteAll();
        itemCategoryRepository.deleteAll();
    }

    private Item persist(String name, String description, String imageUrl, double price, int remainingQuantity, boolean availableForOrder, boolean stillAvailable, String categoryName){
        ItemCategory category = persistItemCategory(categoryName);
        Item item = persistItem(name, description, imageUrl, price, remainingQuantity, availableForOrder, stillAvailable, category);

        return item;
    }

    private ItemCategory persistItemCategory(String categoryName){
        ItemCategory category = new ItemCategory();
        category.setName(categoryName);

        itemCategoryRepository.save(category);

        return category;
    }
    
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

    private void verify(Item item1, Item item2){
        assertNotNull(item2);
        assertTrue(item1.equals(item2));
    }

    private void verify(List<Item> items1, List<Item> items2){
        assertNotNull(items2);
        assertEquals(items1.size(), items2.size());
        for(int i = 0; i < items1.size(); i++){
            assertTrue(items2.contains(items1.get(i)));
        }
    }

    @Test
    public void testLoadItemByName(){
        Item item1 = persist("Pasta", "Italian dish.", "imgur.com/pasta", 5.0, 4, true, true, "Food");
        
        Item item2 = itemRepository.findItemByName(item1.getName());

        verify(item1, item2);
    }

    @Test
    public void testLoadItemsByAvailabilityForOrder(){
        List<Item> items1 = new ArrayList<Item>(2);
        items1.add(persist("Naan", "Indian type of bread.", "imgur.com/naan", 1.5, 8, true, false, "Food"));
        items1.add(persist("Pasta", "Italian dish.", "imgur.com/pasta", 5, 4, true, true, "Food"));
        persist("Pizza", "Italian dish.", "imgur.com/pizza", 6, 6, false, true, "Food");

        List<Item> items2 = itemRepository.findItemsByAvailableForOrder(true);

        verify(items1, items2);
    }

    @Test
    public void testLoadItemsByStillAvailable(){
        List<Item> items1 = new ArrayList<Item>(1);
        items1.add(persist("Naan", "Indian type of bread.", "imgur.com/naan", 1.5, 8, true, false, "Food"));
        persist("Pasta", "Italian dish.", "imgur.com/pasta", 5, 4, true, true, "Food");
        persist("Pizza", "Italian dish.", "imgur.com/pizza", 6, 6, false, true, "Food");

        List<Item> items2 = itemRepository.findItemsByStillAvailable(false);

        verify(items1, items2);
    }

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
