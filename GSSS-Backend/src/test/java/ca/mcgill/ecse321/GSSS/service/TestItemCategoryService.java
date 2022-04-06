package ca.mcgill.ecse321.GSSS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.mcgill.ecse321.GSSS.dao.ItemCategoryRepository;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests for the item categories
 *
 * @author Chris Hatoum
 */
@ExtendWith(MockitoExtension.class)
public class TestItemCategoryService {

  @Mock
  private ItemCategoryRepository itemCategoryRepository;

  @InjectMocks
  private ItemCategoryService itemCategoryService;

  @BeforeEach

  public void setMockOutput() {
    // Set each CRUD method to its mock
    lenient().when(itemCategoryRepository.findItemCategoryByName(anyString())).thenAnswer(
        ca.mcgill.ecse321.GSSS.service.TestItemCategoryService.MockRepository::findItemCategoryByName);
    lenient().when(itemCategoryRepository.findAll())
        .thenAnswer(TestItemCategoryService.MockRepository::getAll);
    lenient().when(itemCategoryRepository.save(any(ItemCategory.class)))
        .thenAnswer(ca.mcgill.ecse321.GSSS.service.TestItemCategoryService.MockRepository::save);
  }

  /**
   * Tests a success case of creating an item category
   *
   * @author Chris Hatoum
   */
  @Test
  public void testCreateCategory_Success() {
    ItemCategory itemCategory = new ItemCategory();
    itemCategory.setName("Dairy");
    ItemCategory created = itemCategoryService.createCategory(itemCategory.getName());
    assertNotNull(created);
    assertEquals(itemCategory.getName(), created.getName());
  }

  /**
   * Tests if a category name is null
   *
   * @author Chris Hatoum
   */
  @Test
  public void testCreateCategory_NullName() {
    try {
      itemCategoryService.createCategory(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Item category name cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Tests if a category name is empty
   *
   * @author Chris Hatoum
   */
  @Test
  public void testCreateCategory_EmptyName() {
    try {
      itemCategoryService.createCategory("  ");
    } catch (IllegalArgumentException e) {
      assertEquals("Item category name cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Tests a success case of creating an item category
   *
   * @author Chris Hatoum
   */
  @Test
  public void testGetCategoryByName_Success() {
    ItemCategory fetched = itemCategoryService.getCategoryByName(MockDatabase.category1.getName());
    assertNotNull(fetched);
    assertEquals(TestItemCategoryService.MockDatabase.category1, fetched);
  }


  /**
   * Tests if we want to get a category with a null name
   *
   * @author Chris Hatoum
   */
  @Test
  public void testGetCategoryByName_NullName() {
    try {
      itemCategoryService.getCategoryByName(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Item category name cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Tests if we want to get a category with an empty name
   *
   * @author Chris Hatoum
   */
  @Test
  public void testGetCategoryByName_EmptyName() {
    try {
      itemCategoryService.getCategoryByName("  ");
    } catch (IllegalArgumentException e) {
      assertEquals("Item category name cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Tests if the category with that name is in the database
   *
   * @author Chris Hatoum
   */
  @Test
  public void testGetCategoryByName_NotInDb() {

    try {
      itemCategoryService.getCategoryByName("Not a category name");
    } catch (NoSuchElementException e) {
      assertEquals("No item category with name Not a category name exists!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Delete an item category successfully
   *
   * @author Chris Hatoum
   */
  @Test
  public void testDeleteCategory_Success() {
    itemCategoryService.deleteCategory("Vegetables");

    verify(itemCategoryRepository, times(1))
        .deleteById(argThat((String i) -> MockDatabase.category1.getName().equals(i)));
    verify(itemCategoryRepository, times(0))
        .deleteById(argThat((String i) -> !MockDatabase.category1.getName().equals(i)));
  }


  /**
   * Delete an item category with a null name
   *
   * @author Chris Hatoum
   */
  @Test
  public void testDeleteCategory_NullName() {
    try {
      itemCategoryService.deleteCategory(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Item category name cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }


  /**
   * Delete an item category with an empty name
   *
   * @author Chris Hatoum
   */
  @Test
  public void testDeleteCategory_EmptyName() {
    try {
      itemCategoryService.deleteCategory("  ");
    } catch (IllegalArgumentException e) {
      assertEquals("Item category name cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Test to make sure that the get all method works correctly
   *
   * @author Theo Ghanem
   */
  @Test
  public void testGetAllItemCategories_Successful() {
    List<ItemCategory> fetched = itemCategoryService.getAll();
    List<ItemCategory> expected = new ArrayList<ItemCategory>();
    expected.add(MockDatabase.category1);
    expected.add(MockDatabase.category2);
    expected.add(MockDatabase.category3);
    expected.add(MockDatabase.category4);
    assertNotNull(fetched);
    assertEquals(expected.size(), fetched.size());
    for (ItemCategory i : fetched) {
      assertTrue(expected.contains(i));
    }
  }

  /**
   * This class holds all of the mock methods of the CRUD repository.
   *
   * @author Chris Hatoum
   */
  class MockRepository {

    /**
     * Method used to find the item category by name
     *
     * @param invocation
     * @return
     * @author Chris Hatoum
     */
    static ItemCategory findItemCategoryByName(InvocationOnMock invocation) {
      String name = (String) invocation.getArgument(0);
      if (name.equals(
          ca.mcgill.ecse321.GSSS.service.TestItemCategoryService.MockDatabase.category1.getName())) {
        return ca.mcgill.ecse321.GSSS.service.TestItemCategoryService.MockDatabase.category1;
      }
      if (name.equals(
          ca.mcgill.ecse321.GSSS.service.TestItemCategoryService.MockDatabase.category2.getName())) {
        return ca.mcgill.ecse321.GSSS.service.TestItemCategoryService.MockDatabase.category2;
      }
      if (name.equals(
          ca.mcgill.ecse321.GSSS.service.TestItemCategoryService.MockDatabase.category3.getName())) {
        return ca.mcgill.ecse321.GSSS.service.TestItemCategoryService.MockDatabase.category3;
      }
      if (name.equals(
          ca.mcgill.ecse321.GSSS.service.TestItemCategoryService.MockDatabase.category4.getName())) {
        return ca.mcgill.ecse321.GSSS.service.TestItemCategoryService.MockDatabase.category4;
      }
      return null;
    }

    /**
     * Method used to save sn item category
     *
     * @param invocation
     * @return
     * @author Chris Hatoum
     */
    static ItemCategory save(InvocationOnMock invocation) {
      return (ItemCategory) invocation.getArgument(0);
    }

    /**
     * Method used in the  get all itemCatgeories test
     *
     * @param invocation
     * @return item categories
     * @author Theo Ghanem
     */
    static List<ItemCategory> getAll(InvocationOnMock invocation) {
      List<ItemCategory> itemCategories = new ArrayList<ItemCategory>();
      itemCategories.add(MockDatabase.category1);
      itemCategories.add(MockDatabase.category2);
      itemCategories.add(MockDatabase.category3);
      itemCategories.add(MockDatabase.category3);
      return itemCategories;
    }

  }


  /**
   * @author Chris Hatoum
   * <p>
   * This class mock data for tests.
   */
  final static class MockDatabase {

    static ItemCategory category1 = new ItemCategory();
    static ItemCategory category2 = new ItemCategory();
    static ItemCategory category3 = new ItemCategory();
    static ItemCategory category4 = new ItemCategory();


    static {
      category1.setName("Vegetables");
      category2.setName("Meat");
      category3.setName("Bread");
      category4.setName("Drinks");

    }
  }
}

