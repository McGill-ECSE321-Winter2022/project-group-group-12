package ca.mcgill.ecse321.GSSS.service;

import ca.mcgill.ecse321.GSSS.dao.ItemCategoryRepository;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import java.util.List;
import java.util.NoSuchElementException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is a service class meant to provide an interface to perform actions on ItemCategory
 * objects.
 *
 * @author Philippe Sarouphim Hochar.
 */
@Service
public class ItemCategoryService {

  @Autowired
  private ItemCategoryRepository itemCategoryRepository;

  /**
   * This service creates a new item category and adds it to the database.
   *
   * @param name Name of the category.
   * @return The newly added category, or null if the saving did not succeed.
   * @author Philippe Sarouphim Hochar
   */
  @Transactional
  public ItemCategory createCategory(String name) {
    // Input validation
    if (name == null || name.trim().length() == 0) {
      throw new IllegalArgumentException("Item category name cannot be empty!");
    }

    // Create and attempt to add new category to database
    ItemCategory category = new ItemCategory();
    category.setName(name);

    return itemCategoryRepository.save(category);
  }

  /**
   * This service fetches all available categories from the database.
   *
   * @return List of all categories.
   * @author Philippe Sarouphim Hochar.
   */
  @Transactional
  public List<ItemCategory> getAll() {
    return Utility.toList(itemCategoryRepository.findAll());
  }

  /**
   * This service fetches an item category from the database by name.
   *
   * @param name Name of the category.
   * @return The saved category from the database, or null if it does not exist.
   * @author Philippe Sarouphim Hochar.
   */
  @Transactional
  public ItemCategory getCategoryByName(String name) {
    // Input validation
    if (name == null || name.trim().length() == 0) {
      throw new IllegalArgumentException("Item category name cannot be empty!");
    }

    ItemCategory itemcategory = itemCategoryRepository.findItemCategoryByName(name);
    if (itemcategory == null) {
      throw new NoSuchElementException("No item category with name " + name + " exists!");
    }
    return itemcategory;
  }

  /**
   * This service deletes an item category from the database by name.
   *
   * @param name Name of the category.
   * @author Philippe Sarouphim Hochar.
   */
  @Transactional
  public void deleteCategory(String name) {
    // Input validation
    if (name == null || name.trim().length() == 0) {
      throw new IllegalArgumentException("Item category name cannot be empty!");
    }

    itemCategoryRepository.deleteById(name);
  }
}
