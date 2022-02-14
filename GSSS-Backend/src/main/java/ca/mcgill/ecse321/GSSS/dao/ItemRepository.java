package ca.mcgill.ecse321.GSSS.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;

/**
 * This interface defines the repository for the CRUD functionnalities relating to the Items.
 * 
 * @author Philippe Sarouphim Hochar
 */
public interface ItemRepository extends CrudRepository<Item, String> {

    /**
     * This method queries the database for the unique item associated to the specified name.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param name Name of the Item to return (unique).
     * @return The queried item.
     */
    Item findItemByName(String name);

    /**
     * This method queries the database for all of the items by availability for order.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param isAvailableForOrder Availability for order.
     * @return List of queries items.
     */
    List<Item> findItemsByIsAvailableForOrder(boolean isAvailableForOrder);

    /**
     * This method queries the database for all of the items by availability in the shop.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param isStillAvailable Availability of items.
     * @return List of queried items.
     */
    List<Item> findItemsByIsStillAvailabile(boolean isStillAvailable);

    /**
     * This method queries the database for all of the items of a specified category.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param category Category of items.
     * @return List of queried items.
     */
    List<Item> findItemsByCategory(ItemCategory category);

    /**
     * This method queries the database for all of the items with specified category, and
     * availability for order, and availability in shop.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param category Category of items.
     * @param isAvailableForOrder Availability for orders.
     * @param isStillAvailable Availability in shop.
     * @return List of queried items.
     */
    List<Item> findItemsByCategoryAndIsAvailabilityForOrderAndIsStillAvailable(ItemCategory category, boolean isAvailableForOrder, boolean isStillAvailable);
    
}
