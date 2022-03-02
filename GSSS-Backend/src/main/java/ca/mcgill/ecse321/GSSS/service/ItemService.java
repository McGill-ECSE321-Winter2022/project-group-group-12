package ca.mcgill.ecse321.GSSS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.GSSS.dao.ItemRepository;
import ca.mcgill.ecse321.GSSS.model.Item;

@Service
public class ItemService {

  @Autowired
  ItemRepository itemRepository;

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
