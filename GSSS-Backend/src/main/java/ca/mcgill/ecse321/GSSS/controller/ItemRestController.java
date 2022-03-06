package ca.mcgill.ecse321.GSSS.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.GSSS.dto.ItemCategoryDto;
import ca.mcgill.ecse321.GSSS.dto.ItemDto;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import ca.mcgill.ecse321.GSSS.service.ItemCategoryService;
import ca.mcgill.ecse321.GSSS.service.ItemService;

@CrossOrigin(origins = "*")
@RestController
public class ItemRestController {

  @Autowired
  private ItemService itemService;
  @Autowired
  private ItemCategoryService itemCategoryService;

  /**
   * method to get itemDto by name
   * 
   * @author Habib Jarweh
   * @param name name we want to find item by
   * @return itemDto item we want to find
   * @throws IllegalArgumentException
   */
  @GetMapping(value = {"/item/{name}", "/item/{name}/"})
  public ItemDto getItemByName(@PathVariable("name") String name) throws IllegalArgumentException {
    Item item = itemService.getItemByName(name);
    return DtoConversion.convertToDto(item, item.getCategory());
  }

  /**
   * method to get list of all itemDtos
   * 
   * @author Habib Jarweh
   * @return list of all itemDtos
   */
  @GetMapping(value = {"/items", "/items/"})
  public List<ItemDto> getAllItems() throws IllegalArgumentException {
    List<ItemDto> itemDtos = new ArrayList<>();
    for (Item item : itemService.getAllItems()) {
      itemDtos.add(DtoConversion.convertToDto(item, item.getCategory()));
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
   * @param itemCategory item category
   * @return itemDto we created
   * @throws IllegalArgumentException
   */
  @PostMapping(value = {"/item", "/item/"})
  public ItemDto createItem(@RequestParam(name = "name") String name,
      @RequestParam(name = "description") String description,
      @RequestParam(name = "imageUrl") String imageUrl,
      @RequestParam(name = "remainingQuantity") int remainingQuantity,
      @RequestParam(name = "price") double price,
      @RequestParam(name = "availableForOrder") boolean availableForOrder,
      @RequestParam(name = "stillAvailable") boolean stillAvailable,
      @RequestParam(name = "itemCategory") ItemCategoryDto itemCategoryDto)
      throws IllegalArgumentException {
    ItemCategory itemCategory = itemCategoryService.getCategoryByName(itemCategoryDto.getName());
    Item item = itemService.createItem(name, description, imageUrl, remainingQuantity, price,
        availableForOrder, stillAvailable, itemCategory);
    return DtoConversion.convertToDto(item, itemCategory);
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
   * @param itemCategory item category
   * @return itemDto we want to update
   * @throws IllegalArgumentException
   */
  @PostMapping(value = {"/item/{name}", "/item/{name}/"})
  public ItemDto modifyItem(@PathVariable("name") String name,
      @RequestParam(name = "description") String description,
      @RequestParam(name = "imageUrl") String imageUrl,
      @RequestParam(name = "remainingQuantity") int remainingQuantity,
      @RequestParam(name = "price") double price,
      @RequestParam(name = "availableForOrder") boolean availableForOrder,
      @RequestParam(name = "stillAvailable") boolean stillAvailable,
      @RequestParam(name = "itemCategory") ItemCategoryDto itemCategoryDto)
      throws IllegalArgumentException {
    ItemCategory itemCategory = itemCategoryService.getCategoryByName(itemCategoryDto.getName());
    Item item = itemService.modifyItem(name, description, imageUrl, remainingQuantity, price,
        availableForOrder, stillAvailable, itemCategory);
    return DtoConversion.convertToDto(item, itemCategory);
  }

  /**
   * method to change description and image url
   * 
   * @author Habib Jarweh
   * @param name pk of item
   * @param description of updated item
   * @param imageUrl of updated item
   * @return updated itemDto
   * @throws IllegalArgumentException
   */
  @PostMapping(value = {"/item/{name}", "/item/{name}/"})
  public ItemDto changeDescriptionAndImageUrl(@PathVariable("name") String name,
      @RequestParam(name = "description") String description,
      @RequestParam(name = "imageUrl") String imageUrl) throws IllegalArgumentException {
    Item item = itemService.getItemByName(name);
    Item updatedItem = itemService.modifyItem(name, description, imageUrl,
        item.getRemainingQuantity(), item.getPrice(), item.isAvailableForOrder(),
        item.isStillAvailable(), item.getCategory());
    return DtoConversion.convertToDto(updatedItem, item.getCategory());
  }

  /**
   * method to change remaining quantity of item
   * 
   * @author Habib Jarweh
   * @param name pk of item
   * @param remainingQuantity of updated item
   * @return updated itemDto
   * @throws IllegalArgumentException
   */
  @PostMapping(value = {"/item/{name}", "/item/{name}/"})
  public ItemDto changeRemainingQuantity(@PathVariable("name") String name,
      @RequestParam(name = "remainingQuantity") int remainingQuantity)
      throws IllegalArgumentException {
    Item item = itemService.getItemByName(name);
    Item updatedItem = itemService.modifyItem(name, item.getDescription(), item.getImageUrl(),
        remainingQuantity, item.getPrice(), item.isAvailableForOrder(), item.isStillAvailable(),
        item.getCategory());
    return DtoConversion.convertToDto(updatedItem, item.getCategory());
  }

  /**
   * method to update price of item
   * 
   * @author Habib Jarweh
   * @param name pk of item
   * @param price of updated item
   * @return updated itemDto
   * @throws IllegalArgumentException
   */
  @PostMapping(value = {"/item/{name}", "/item/{name}/"})
  public ItemDto changePrice(@PathVariable("name") String name,
      @RequestParam(name = "price") double price) throws IllegalArgumentException {
    Item item = itemService.getItemByName(name);
    Item updatedItem = itemService.modifyItem(name, item.getDescription(), item.getImageUrl(),
        item.getRemainingQuantity(), price, item.isAvailableForOrder(), item.isStillAvailable(),
        item.getCategory());
    return DtoConversion.convertToDto(updatedItem, item.getCategory());
  }

  /**
   * mehod to update availability of item for order
   * 
   * @author Habib Jarweh
   * @param name pk of item
   * @param availableForOrder boolean availability for order of updated item
   * @return updated itemDto
   * @throws IllegalArgumentException
   */
  @PostMapping(value = {"/item/{name}", "/item/{name}/"})
  public ItemDto changeAvailabilityForOrder(@PathVariable("name") String name,
      @RequestParam(name = "availableForOrder") boolean availableForOrder)
      throws IllegalArgumentException {
    Item item = itemService.getItemByName(name);
    Item updatedItem = itemService.modifyItem(name, item.getDescription(), item.getImageUrl(),
        item.getRemainingQuantity(), item.getPrice(), availableForOrder, item.isStillAvailable(),
        item.getCategory());
    return DtoConversion.convertToDto(updatedItem, item.getCategory());
  }

  /**
   * method to update availability of item
   * 
   * @author Habib Jarweh
   * @param name
   * @param stillAvailable availability of updated item
   * @return updated itemDto
   * @throws IllegalArgumentException
   */
  @PostMapping(value = {"/item/{name}", "/item/{name}/"})
  public ItemDto changeAvailability(@PathVariable("name") String name,
      @RequestParam(name = "stillAvailable") boolean stillAvailable)
      throws IllegalArgumentException {
    Item item = itemService.getItemByName(name);
    Item updatedItem = itemService.modifyItem(name, item.getDescription(), item.getImageUrl(),
        item.getRemainingQuantity(), item.getPrice(), item.isAvailableForOrder(), stillAvailable,
        item.getCategory());
    return DtoConversion.convertToDto(updatedItem, item.getCategory());
  }

  /**
   * method to update category of item
   * 
   * @author Habib Jarweh
   * @param name pk of item
   * @param itemCategoryName name of category of updated item
   * @return updated itemDto
   * @throws IllegalArgumentException
   */
  @PostMapping(value = {"/item/{name}", "/item/{name}/"})
  public ItemDto changeCategory(@PathVariable("name") String name,
      @RequestParam(name = "itemCategoryName") String itemCategoryName)
      throws IllegalArgumentException {
    ItemCategory itemCategory = itemCategoryService.getCategoryByName(itemCategoryName);
    Item item = itemService.getItemByName(name);
    Item updatedItem = itemService.modifyItem(name, item.getDescription(), item.getImageUrl(),
        item.getRemainingQuantity(), item.getPrice(), item.isAvailableForOrder(),
        item.isStillAvailable(), itemCategory);
    return DtoConversion.convertToDto(updatedItem, itemCategory);
  }


  /**
   * method to delete item
   * 
   * @author Habib Jarweh
   * @param name
   * @throws IllegalArgumentException
   */
  @DeleteMapping(value = {"/item/{name}", "/item/{name}/"})
  public void deleteItem(@PathVariable("name") String name) throws IllegalArgumentException {
    itemService.deleteItem(name);
  }

}
