package ca.mcgill.ecse321.GSSS.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
   * @throws illegal argument exception when inputted name is either null or empty
   * @throws no such element exception when there is no item with the inputed name
   */
  @GetMapping(value = {"/item/{name}", "/item/{name}/"})
  public ItemDto getItemByName(@PathVariable("name") String name) throws IllegalArgumentException, NoSuchElementException {
    Item item = itemService.getItemByName(name);
    return DtoUtility.convertToDto(item, item.getCategory());
  }

  /**
   * method to get itemdtos by their category
   * 
   * @author Habib Jarweh
   * @param categoryName category name of items we want
   * @return List<ItemDto> itemDto's we want to get
   */
  @GetMapping(value = {"/item/category/{categoryName}", "/item/category/{categoryName}/"})
  public List<ItemDto> getItemsByCategory(@PathVariable("categoryName") String categoryName) {
    ItemCategory itemCategory = itemCategoryService.getCategoryByName(categoryName);
    List<Item> items = itemService.getItemsByCategory(itemCategory);
    List<ItemDto> itemDtos = new ArrayList<ItemDto>();
    for (Item i : items) {
      itemDtos.add(DtoUtility.convertToDto(i));
    }
    return itemDtos;
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
      itemDtos.add(DtoUtility.convertToDto(item, item.getCategory()));
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
   * @throws illegal argument exception when any or all inputed strings are null or wrong, or when
   *         remaining quantity or price are negative, or when item category is null
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
    return DtoUtility.convertToDto(item, itemCategory);
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
   * @throws illegal argument exception when any or all inputed strings are null or wrong, or when
   *         remaining quantity or price are negative, or when item category is null
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
    return DtoUtility.convertToDto(item, itemCategory);
  }


  /**
   * method to delete item
   * 
   * @author Habib Jarweh
   * @param name
   * @throws illegal argument exception when name is null or empty
   */
  @DeleteMapping(value = {"/item/{name}", "/item/{name}/"})
  public void deleteItem(@PathVariable("name") String name) throws IllegalArgumentException {
    itemService.deleteItem(name);
  }

}
