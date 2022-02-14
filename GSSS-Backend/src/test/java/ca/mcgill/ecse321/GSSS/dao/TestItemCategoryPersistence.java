package ca.mcgill.ecse321.GSSS.dao;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.AfterEach;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestItemCategoryPersistence {

  @Autowired
  ItemCategoryRepository itemCategoryRepository;

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

  // @AfterEach
  // public void clearDatabase() {
  //
  // // Deleting all the database contents of item category
  // itemCategoryRepository.deleteAll();
  //
  // }


}
