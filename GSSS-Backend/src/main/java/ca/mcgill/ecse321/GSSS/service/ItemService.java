package ca.mcgill.ecse321.GSSS.service;

import ca.mcgill.ecse321.GSSS.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.GSSS.dao.ItemRepository;
import ca.mcgill.ecse321.GSSS.model.Item;

import java.util.List;

/**
 * Services of the item class
 *
 * @author Theo Ghanem
 * @author Habib Jarweh
 * @author Chris Hatoum
 *
 */
@Service
public class ItemService {

  //CRUD repositories
  @Autowired
  ItemRepository itemRepository;


  //GET Methods

  /**
   *Get the item based on its name
   *
   * @author Theo Ghanem
   * @author Chris Hatoum
   * @param name name of the item we want to get
   * @return the item we are looking for
   */
  @Transactional
public Item getItemByName(String name){
  Item item = itemRepository.findItemByName(name);
  return item;
}


  //CREATE Methods

  /**
   * Method to create a new item and saves it into the database
   *
   * @author Theo Ghanem
   * @author Chris Hatoum
   * @param name primary key of the item
   * @param description description of the item
   * @param imageUrl image URL of the item
   * @param remainingQuantity remaining quantity of the item
   * @param price price of the item
   * @param availableForOrder boolean to see if the item is still available for order
   * @param stillAvailable boolean to see if the item is still available
   * @return the item we want to create
   */
  @Transactional
  public Item createItem(String name, String description, String imageUrl, int remainingQuantity,
                         double price, boolean availableForOrder, boolean stillAvailable) {
    Item item = new Item();
    item.setName(name);
    item.setDescription(description);
    item.setImageUrl(imageUrl);
    item.setRemainingQuantity(remainingQuantity);
    item.setPrice(price);
    item.setAvailableForOrder(availableForOrder);
    item.setStillAvailable(stillAvailable);
    itemRepository.save(item);
    return item;
  }

  //DELETE Methods

  /**
   * Deletes the item based on the specified id
   *
   * @author Theo Ghanem
   * @author Chris Hatoum
   * @param name name of the item we want to delete
   */
  @Transactional
  public void deleteItem(String name) {
    itemRepository.deleteById(name);
  }


  //MODIFY Methods

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
   */
  @Transactional
  public Item modifyItem(String name, String description, String imageUrl, int remainingQuantity,
      double price, boolean availableForOrder, boolean stillAvailable) {
    Item item = itemRepository.findItemByName(name);
    item.setDescription(description);
    item.setImageUrl(imageUrl);
    item.setRemainingQuantity(remainingQuantity);
    item.setPrice(price);
    item.setAvailableForOrder(availableForOrder);
    item.setStillAvailable(stillAvailable);
    itemRepository.save(item); 
    return item;
  }

}
