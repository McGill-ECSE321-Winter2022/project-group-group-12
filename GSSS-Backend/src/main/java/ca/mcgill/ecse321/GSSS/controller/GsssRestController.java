package ca.mcgill.ecse321.GSSS.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class GsssRestController {

  @Autowired
  private ItemService itemService;
  @Autowired
  private ItemCategoryService itemCategoryService;

  /**
   * method to convert from type item to type itemDto
   * 
   * @author Habib Jarweh
   * @param i item we want to convert
   * @return item converted to type itemDto
   */
  private ItemDto convertToDto(Item i, ItemCategory ic) {
    if (i == null) {
      throw new IllegalArgumentException("There is no such Item!");
    }
    if (ic == null) {
      throw new IllegalArgumentException("There is no such Item Category!");
    }
    ItemDto itemDto =
        new ItemDto(i.getName(), i.getDescription(), i.getImageUrl(), i.getRemainingQuantity(),
            i.getPrice(), i.isAvailableForOrder(), i.isStillAvailable(), convertToDto(ic));
    return itemDto;
  }

  /**
   * method to convert from type itemCategory to type itemCategoryDto
   * 
   * @author Habib Jarweh
   * @param ic item category we want to convert
   * @return item converted to type itemDto
   */
  private ItemCategoryDto convertToDto(ItemCategory ic) {
    if (ic == null) {
      throw new IllegalArgumentException("There is no such Item Category!");
    }
    ItemCategoryDto itemCategoryDto = new ItemCategoryDto(ic.getName());
    return itemCategoryDto;
  }

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
    return convertToDto(item, item.getCategory());
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
      itemDtos.add(convertToDto(item, item.getCategory()));
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
    return convertToDto(item, itemCategory);
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
    return convertToDto(item, itemCategory);
  }
}
