package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.dto.ItemCategoryDto;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import ca.mcgill.ecse321.GSSS.service.ItemCategoryService;
import ca.mcgill.ecse321.GSSS.service.ItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class  ItemCategoryRestController {

  @Autowired
  private ItemCategoryService itemCategoryService;

  @Autowired
  private ItemService itemService;

  
//  @GetMapping(value = {"/itemCategoryByName", "/itemCategoryByName/"})
//  public ItemCategoryDto getCategoryByName(@RequestParam(name = "name") String name) throws IllegalArgumentException, NoSuchElementException {
//    
//    ItemCategory itemCategory = itemCategoryService.getCategoryByName(name);
//    return DtoUtility.convertToDto(itemCategory);
//  }
  /**
   * method to create itemCategoryDto with a name
   *
   * @param name of itemCategory
   * @return itemCategoryDto we created if successful, else return the error
   * @author Chris Hatoum
   */
  @PostMapping(value = {"/itemCategory", "/itemCategory/"})
  public ResponseEntity<?> createCategory(@RequestParam(name = "name") String name){
	  try {
	    ItemCategory itemCategory = itemCategoryService.createCategory(name);
	    return ResponseEntity.ok(DtoUtility.convertToDto(itemCategory));
	  }catch(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	  }
  }

  /**
   * This method deletes the category
   *
   * @param name the name of the category we want to retrieve
   * @return null if successful, else return the error
   * @author Chris Hatoum
   */
  @DeleteMapping(value = {"/itemCategory/{name}", "/itemCategory/{name}/"})
  public ResponseEntity<?> deleteCategory(@PathVariable("name") String name){
	  try {
		  itemCategoryService.deleteCategory(name);
		  return ResponseEntity.ok(null);
	  }catch(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	  }
  	}

  /**
   * Method to get list of all the itemCategoryDTO's
   *
   * @return list of all itemCategoryDTO's
   * @author Chris Hatoum
   */
  @GetMapping(value = {"/itemCategories", "/itemCategories/"})
  public List<ItemCategoryDto> getAllItemCategories(){
    List<ItemCategoryDto> itemCategoryDtos = new ArrayList<>();
    for (ItemCategory itemCategory : itemCategoryService.getAll()) {
      itemCategoryDtos.add(DtoUtility.convertToDto(itemCategory));
    }
    return itemCategoryDtos;
  }

  /**
   * Method to modify itemCategoryDto
   *
   * @param oldName name of the itemCategory to modify
   * @param newName new name of the ItemCategory
   * @return itemCategoryDto we modified
   * @author Enzo Benoit-Jeannin
   */
  @PostMapping(value = {"/itemCategory/modify", "/itemCategory/modify/"})
  public ResponseEntity<?> modifyCategory(@RequestParam(name = "oldName") String oldName, @RequestParam(name = "newName") String newName){
	  try {
	    ItemCategory itemCategory = itemCategoryService.createCategory(newName);  // create a new category with the given new name
	
	    ItemCategory oldCategory = itemCategoryService.getCategoryByName(oldName);  // Get the old one using the old name
	   
	    List<Item> items = itemService.getItemsByCategory(oldCategory);     // For all items that were in the old category, set their new category with the newly created one
	    for (Item i : items){
	      i.setCategory(itemCategory);
	    }
	    itemCategoryService.deleteCategory(oldName);
	      
	    return ResponseEntity.ok(DtoUtility.convertToDto(itemCategory));
	  }catch(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	  }
  }
}
