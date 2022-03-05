package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.dto.ItemCategoryDto;
import ca.mcgill.ecse321.GSSS.dto.ItemDto;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import ca.mcgill.ecse321.GSSS.service.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
/**
 * ItemCategory controller methods
 * 
 * @author Chris Hatoum
 */
public class ItemCategoryRestController {

  @Autowired
  private ItemCategoryService itemCategoryService;


  /**
   * method to create itemCategoryDto with a name
   *
   * @author Chris Hatoum
   * @param name of itemCategory
   * @return itemCategoryDto we created
   * @throws IllegalArgumentException
   */
  @PostMapping(value = {"/itemCategory", "/itemCategory/"})
  public ItemCategoryDto createCategory(@RequestParam(name = "name") String name)
      throws IllegalArgumentException {
    ItemCategory itemCategory = itemCategoryService.createCategory(name);
    return DtoConversion.convertToDto(itemCategory);
  }

  /**
   * Method to get list of all the itemCategoryDTO's
   *
   * @author Chris Hatoum
   *
   * @return list of all itemCategoryDTO's
   */
  @GetMapping(value = {"/itemCategories", "/itemCategories/"})
  public List<ItemCategoryDto> getAllItemCategories() throws IllegalArgumentException {
    List<ItemCategoryDto> itemCategoryDtos = new ArrayList<>();
    for (ItemCategory itemCategory : itemCategoryService.getAll()) {
      itemCategoryDtos.add(DtoConversion.convertToDto(itemCategory));
    }
    return itemCategoryDtos;
  }

  /**
   * Method that returns a category using its name
   * 
   * @author Chris Hatoum
   * @param name the name of the category we want to retrieve
   * @return the category
   * @throws IllegalArgumentException
   */
  @GetMapping(value = {"/itemCategory/{name}", "/itemCategory/{name}/"})
  public ItemCategoryDto getCategoryByName(@PathVariable("name") String name)
      throws IllegalArgumentException {
    ItemCategory itemCategory1 = itemCategoryService.getCategoryByName(name);
    return DtoConversion.convertToDto(itemCategory1);
  }

  /**
   * This method deletes the category
   *
   * @author Chris Hatoum
   *
   * @param name the name of the category we want to retrieve Does not return anything
   * @throws IllegalArgumentException
   */
  @DeleteMapping(value = {"/itemCategory/{name}", "/itemCategory/{name}/"})
  public void deleteCategory(@PathVariable("name") String name) throws IllegalArgumentException {
    itemCategoryService.deleteCategory(name);
  }

}

