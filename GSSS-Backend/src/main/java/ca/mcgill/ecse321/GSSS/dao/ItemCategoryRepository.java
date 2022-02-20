package ca.mcgill.ecse321.GSSS.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;

/**
 * This interface defines the repository for the CRUD functionalities relating
 * to the ItemCategory.
 * 
 * @author Chris Hatoum
 */
public interface ItemCategoryRepository extends CrudRepository<ItemCategory, String> {

	/**
	 * 
	 * this method finds an item category based on its name
	 * 
	 * @author Chris Hatoum
	 * @param name Name of the category
	 * @return Category we are trying to find
	 */
	ItemCategory findItemCategoryByName(String name);

}
