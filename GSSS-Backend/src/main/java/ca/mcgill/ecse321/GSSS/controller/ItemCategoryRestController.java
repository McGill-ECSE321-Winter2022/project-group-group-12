package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.dto.ItemCategoryDto;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import ca.mcgill.ecse321.GSSS.service.ItemCategoryService;
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

/**
 * Controller methods for the item category class
 *
 * @author Chris Hatoum
 */
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
   * @param name of itemCategory
   * @return itemCategoryDto we created
   * @throws IllegalArgumentException
   * @author Chris Hatoum
   */
  @PostMapping(value = {"/itemCategory", "/itemCategory/"})
  public ItemCategoryDto createCategory(@RequestParam(name = "name") String name)
      throws IllegalArgumentException {
    ItemCategory itemCategory = itemCategoryService.createCategory(name);
    return DtoUtility.convertToDto(itemCategory);
  }

  /**
   * This method deletes the category
   *
   * @param name the name of the category we want to retrieve Does not return anything
   * @throws IllegalArgumentException
   * @author Chris Hatoum
   */
  @DeleteMapping(value = {"/itemCategory/{name}", "/itemCategory/{name}/"})
  public void deleteCategory(@PathVariable("name") String name) throws IllegalArgumentException {
    itemCategoryService.deleteCategory(name);
  }

  /**
   * Method to get list of all the itemCategoryDTO's
   *
   * @return list of all itemCategoryDTO's
   * @author Chris Hatoum
   */
  @GetMapping(value = {"/itemCategories", "/itemCategories/"})
  public List<ItemCategoryDto> getAllItemCategories() throws IllegalArgumentException {
    List<ItemCategoryDto> itemCategoryDtos = new ArrayList<>();
    for (ItemCategory itemCategory : itemCategoryService.getAll()) {
      itemCategoryDtos.add(DtoUtility.convertToDto(itemCategory));
    }
    return itemCategoryDtos;
  }
}
