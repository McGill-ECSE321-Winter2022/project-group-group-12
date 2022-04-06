package ca.mcgill.ecse321.GSSS.dao;

import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import org.springframework.data.repository.CrudRepository;

/**
 * This interface defines the repository for the CRUD functionalities relating to the ItemCategory.
 *
 * @author Chris Hatoum
 */
public interface ItemCategoryRepository extends CrudRepository<ItemCategory, String> {

  /**
   * this method finds an item category based on its name
   *
   * @param name Name of the category
   * @return Category we are trying to find
   * @author Chris Hatoum
   */
  ItemCategory findItemCategoryByName(String name);
}
