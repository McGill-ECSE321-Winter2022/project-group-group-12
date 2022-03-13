package ca.mcgill.ecse321.GSSS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.GSSS.dao.ItemRepository;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Services of the item class
 * 
 * @author Chris Hatoum
 * @author Habib Jarweh
 *
 */
@Service
public class ItemService {

  // CRUD repositories
  @Autowired
  ItemRepository itemRepository;


  // GET Methods
  /**
   * @author Chris Hatoum
   * @param name Name of the item
   * @return The item we want
   * @throws illegal argument exception when inputted name is either null or empty
   * @throws no such element exception when there is no item with the inputed name
   */
  @Transactional
  public Item getItemByName(String name) {
    if (name == null || name.trim().length() == 0)
      throw new IllegalArgumentException("Name cannot be empty!");
    if (itemRepository.findItemByName(name) == null)
      throw new NoSuchElementException("The item with name" + name + "does not exist!");
    return itemRepository.findItemByName(name);

  }

  /**
   * method to get all items with the inputted category
   * 
   * @author Habib Jarweh
   * @param itemCategory of items we want to fetch
   * @return list of items of the sam category
   */
  @Transactional
  public List<Item> getItemsByCategory(ItemCategory itemCategory) {
    List<Item> items = itemRepository.findItemsByCategory(itemCategory);
    return items;
  }

  /**
   * Finds all the items stored in the database This method uses a method defined in HelperClass
   * 
   * @author Chris Hatoum
   * @return A list of all the items stored in the database
   */
  @Transactional
  public List<Item> getAllItems() {
    return Utility.toList(itemRepository.findAll());
  }


  // CREATE Methods

  /**
   *
   * This method creates an item given the parameters below
   *
   * @author Chris Hatoum
   * @param name The item's name
   * @param description The item's description
   * @param imageUrl The URL for the item's image
   * @param remainingQuantity The amount of the item left in the inventory
   * @param price price of the item
   * @param availableForOrder If the item id available for online order or not
   * @param stillAvailable Item's availability
   * @return The item that has been created
   * @throws illegal argument exception when any or all inputed strings are null or wrong, or when
   *         remaining quantity or price are negative, or when item category is null
   */
  @Transactional
  public Item createItem(String name, String description, String imageUrl, int remainingQuantity,
      double price, boolean availableForOrder, boolean stillAvailable, ItemCategory itemCategory) {
    // Input validation
    String error = "";
    if (name == null || name.trim().length() == 0)
      error += "Item's name cannot be empty! ";
    if (description == null || description.trim().length() == 0)
      error += "Item's description cannot be empty! ";
    if (imageUrl == null || imageUrl.trim().length() == 0)
      error += "Item's image URL cannot be empty! ";
    if (remainingQuantity < 0)
      error += "Item's remaining quantity cannot be less than 0! ";
    if (price < 0)
      error += "Item's price cannot be less than 0! ";
    if (itemCategory == null)
      error += "Item's category cannot be empty!";
    if (error.length() > 0)
      throw new IllegalArgumentException(error);

    Item item = new Item();
    item.setName(name);
    item.setDescription(description);
    item.setImageUrl(imageUrl);
    item.setRemainingQuantity(remainingQuantity);
    item.setPrice(price);
    item.setAvailableForOrder(availableForOrder);
    item.setStillAvailable(stillAvailable);
    item.setCategory(itemCategory);
    itemRepository.save(item);
    return item;

  }

  // DELETE Methods

  /**
   * This method deletes an item
   * 
   * @author Chris Hatoum
   * @param name Delete the item with the name ("name")
   * @throws illegal argument exception when name is null or empty
   */
  @Transactional
  public void deleteItem(String name) {
    if (name == null || name.trim().length() == 0)
      throw new IllegalArgumentException("Name cannot be empty!");
    Item item = itemRepository.findItemByName(name);
    itemRepository.delete(item);
  }



  // MODIFY Methods

  /**
   * method to edit/modify item
   * 
   * @author Habib Jarweh
   * @param name pk of item
   * @param description description of item
   * @param imageUrl image URL of item
   * @param remainingQuantity remaining quantity of item
   * @param price price of item
   * @param availableForOrder boolean if item is still available for order
   * @param stillAvailable boolean to see if item is still available
   * @return item we want to update
   * @throws illegal argument exception when any or all inputed strings are null or wrong, or when
   *         remaining quantity or price are negative, or when item category is null
   */
  @Transactional
  public Item modifyItem(String name, String description, String imageUrl, int remainingQuantity,
      double price, boolean availableForOrder, boolean stillAvailable, ItemCategory itemCategory) {
    // Input validation
    String error = "";
    if (name == null || name.trim().length() == 0)
      error += "Item's name cannot be empty! ";
    if (description == null || description.trim().length() == 0)
      error += "Item's description cannot be empty! ";
    if (imageUrl == null || imageUrl.trim().length() == 0)
      error += "Item's image URL cannot be empty! ";
    if (remainingQuantity < 0)
      error += "Item's remaining quantity cannot be less than 0! ";
    if (price < 0)
      error += "Item's price cannot be less than 0! ";
    if (itemCategory == null)
      error += "Item's category cannot be empty!";
    if (error.length() > 0)
      throw new IllegalArgumentException(error);

    Item item = itemRepository.findItemByName(name);
    item.setDescription(description);
    item.setImageUrl(imageUrl);
    item.setRemainingQuantity(remainingQuantity);
    item.setPrice(price);
    item.setAvailableForOrder(availableForOrder);
    item.setStillAvailable(stillAvailable);
    item.setCategory(itemCategory);
    itemRepository.save(item);
    return item;
  }

}
