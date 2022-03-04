package ca.mcgill.ecse321.GSSS.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.GSSS.dto.ItemDto;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.service.ItemService;

@CrossOrigin(origins = "*")
@RestController
public class GsssRestController {

  @Autowired
  private ItemService itemService;

  /**
   * method to convert from type item to type itemDto
   * 
   * @author Habib Jarweh
   * @param i item we want to convert
   * @return item converted to type itemDto
   */
  private ItemDto convertToDto(Item i) {
    if (i == null) {
      throw new IllegalArgumentException("There is no such Item!");
    }
    ItemDto itemDto =
        new ItemDto(i.getName(), i.getDescription(), i.getImageUrl(), i.getRemainingQuantity(),
            i.getPrice(), i.isAvailableForOrder(), i.isStillAvailable(), i.getCategory());
    return itemDto;
  }

  /**
   * method to get itemDto by name
   * 
   * @author Habib Jarweh
   * @param name name we want to find item by
   * @return itemDto item we want to find
   * @throws IllegalArgumentException
   */
  @GetMapping(value = {"/items/{name}", "/items/{name}/"})
  public ItemDto getItemByName(@PathVariable("name") String name) throws IllegalArgumentException {
    return convertToDto(itemService.getItemByName(name));
  }

  /**
   * method to get list of all itemDtos
   * 
   * @author Habib Jarweh
   * @return list of all itemDtos
   */
  @GetMapping(value = {"/items", "/items/"})
  public List<ItemDto> getAllItems() {
    List<ItemDto> itemDtos = new ArrayList<>();
    for (Item item : itemService.getAllItems()) {
      itemDtos.add(convertToDto(item));
    }
    return itemDtos;
  }

  /**
   * method to create itemDto with all parameters of item
   * 
   * @author Habib Jarweh
   * @param name of item
   * @param description of item
   * @param imageUrl of item
   * @param remainingQuantity of item
   * @param price of item
   * @param availableForOrder if item is available for order
   * @param stillAvailable if item is still available
   * @return itemDto we created
   * @throws IllegalArgumentException
   */
  @PostMapping(value = {
      "/items/{name}{description}{imageUrl}{remainingQuantity}{price}{availableForOrder}{stillAvailable}",
      "/items/{name}{description}{imageUrl}{remainingQuantity}{price}{availableForOrder}{stillAvailable}/"})
  public ItemDto createItem(@PathVariable("name") String name,
      @PathVariable("description") String description, @PathVariable("imageUrl") String imageUrl,
      @PathVariable("remainingQuantity") int remainingQuantity, @PathVariable("price") double price,
      @PathVariable("availableForOrder") boolean availableForOrder,
      @PathVariable("stillAvailable") boolean stillAvailable) throws IllegalArgumentException {
    Item item = itemService.createItem(name, description, imageUrl, remainingQuantity, price,
        availableForOrder, stillAvailable);
    return convertToDto(item);
  }

  /**
   * method to modify/update an item
   * 
   * @author Habib Jarweh
   * @param name of item we want to update, name is pk, does not change
   * @param description of item we want to update
   * @param imageUrl of item we want to update
   * @param remainingQuantity of item we want to update
   * @param price of item we want to update
   * @param availableForOrder availability for order of item we want to update
   * @param stillAvailable availability of item we want to update
   * @return itemDto we want to update
   * @throws IllegalArgumentException
   */
  @PostMapping(value = {"/items/{name}", "/items/{name}/"})
  public ItemDto modifyItem(@PathVariable("name") String name, String description, String imageUrl,
      int remainingQuantity, double price, boolean availableForOrder, boolean stillAvailable)
      throws IllegalArgumentException {
    Item item = itemService.createItem(name, description, imageUrl, remainingQuantity, price,
        availableForOrder, stillAvailable);
    return convertToDto(item);
  }
}
