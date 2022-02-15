package ca.mcgill.ecse321.GSSS.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;

/**
 * This interface defines the repository for the CRUD functionalities relating to the ItemCategory.
 * 
 */
public interface ItemCategoryRepository extends CrudRepository<ItemCategory, String> {

  /**
   * 
   * this method finds an item category based on its name
   * @param name name of the category
   * @return
   */
  ItemCategory findItemCategoryByName(String name);

}
