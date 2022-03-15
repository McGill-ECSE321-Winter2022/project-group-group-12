package ca.mcgill.ecse321.GSSS.dao;

import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * This interface defines the repository for the CRUD functionalities relating to the Items.
 *
 * @author Philippe Sarouphim Hochar
 */
public interface ItemRepository extends CrudRepository<Item, String> {

  /**
   * This method queries the database for the unique item associated to the specified name.
   *
   * @author Philippe Sarouphim Hochar.
   * @param name Name of the Item to return (unique).
   * @return The queried item.
   */
  Item findItemByName(String name);

  /**
   * This method queries the database for all of the items by availability for order.
   *
   * @author Philippe Sarouphim Hochar.
   * @param availableForOrder Availability for order.
   * @return List of queries items.
   */
  List<Item> findItemsByAvailableForOrder(boolean availableForOrder);

  /**
   * This method queries the database for all of the items by availability in the shop.
   *
   * @author Philippe Sarouphim Hochar.
   * @param stillAvailable Availability of items.
   * @return List of queried items.
   */
  List<Item> findItemsByStillAvailable(boolean stillAvailable);

  /**
   * This method queries the database for all of the items of a specified category.
   *
   * @author Philippe Sarouphim Hochar.
   * @param category Category of items.
   * @return List of queried items.
   */
  List<Item> findItemsByCategory(ItemCategory category);

  /**
   * This method queries the database for all of the items with specified category, and availability
   * for order, and availability in shop.
   *
   * @author Philippe Sarouphim Hochar.
   * @param category Category of items.
   * @param availableForOrder Availability for orders.
   * @param stillAvailable Availability in shop.
   * @return List of queried items.
   */
  List<Item> findItemsByCategoryAndAvailableForOrderAndStillAvailable(
      ItemCategory category, boolean availableForOrder, boolean stillAvailable);
}
