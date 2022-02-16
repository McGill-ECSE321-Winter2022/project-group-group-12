package ca.mcgill.ecse321.GSSS.dao;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * 
 * this class is to test persistence of itemCategory, with find by name (its primary key)
 * 
 * @author Habib Jarweh
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestItemCategoryPersistence {

  @Autowired
  ItemCategoryRepository itemCategoryRepository;

  /**
   * 
   * this method is to test if item category found by name is the same as actual one
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testPersistAndLoadItemCategoryByName() {

    String name = "Vegetables";
    ItemCategory itemCategory = new ItemCategory();
    itemCategory.setName(name);

    itemCategoryRepository.save(itemCategory);

    itemCategory = null;

    itemCategory = itemCategoryRepository.findItemCategoryByName(name);

    assertNotNull(itemCategory);
    assertEquals(name, itemCategory.getName());

  }



}
