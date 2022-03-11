package ca.mcgill.ecse321.GSSS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import ca.mcgill.ecse321.GSSS.dao.ItemCategoryRepository;
import ca.mcgill.ecse321.GSSS.dao.ItemRepository;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;


@ExtendWith(MockitoExtension.class)
public class TestItemService {

  @Mock
  private ItemRepository itemDao;

  @Mock
  private ItemCategoryRepository itemCategoryDao;

  @InjectMocks
  private ItemService itemService;


  @BeforeEach
  public void setMockOutput() {
    lenient().when(itemDao.findItemByName(anyString())).thenAnswer(MockRepository::findItemByName);
    lenient().when(itemDao.findItemsByCategory(any(ItemCategory.class))).thenAnswer(MockRepository::findItemsByCategory);
    lenient().when(itemDao.findAll()).thenAnswer(MockRepository::findAll);
    lenient().when(itemDao.save(any(Item.class))).thenAnswer(MockRepository::save);
  }


  // ========================================================================
  // Get methods
  // ========================================================================

  /**
   * method that an item is fetched successfully
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testGetItemByName_Successful() {
    Item item = itemService.getItemByName(MockDatabase.item1.getName());
    assertNotNull(item);
    assertEquals(item.getName(), MockDatabase.item1.getName());
  }

  /**
   * method to check error is thrown when we input a null name when we try to fetch an item
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testGetItemByName_NullName() {
    Exception error =
        assertThrows(IllegalArgumentException.class, () -> itemService.getItemByName(null));
    assertEquals("Name cannot be empty!", error.getMessage());
  }

  /**
   * method to check error is thrown when we input an empty name when we try to fetch an item
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testGetItemByName_EmptyName() {
    Exception error =
        assertThrows(IllegalArgumentException.class, () -> itemService.getItemByName(" "));
    assertEquals("Name cannot be empty!", error.getMessage());
  }


  /**
   * method to check error is thrown when we try to fetch an item not in the database
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testGetItemByName_NotInDb() {
    String wrongName = "Glock 29";
    Exception error =
        assertThrows(NoSuchElementException.class, () -> itemService.getItemByName(wrongName));
    assertEquals("The item with name" + wrongName + "does not exist!", error.getMessage());
  }
  
  /**
   * method to check that all items with same category are fetched successfully
   * 
   * @author Habib Jarweh
   */
  @Test 
  public void testGetItemsByCategory_Successful() {
    List<Item> items = itemService.getItemsByCategory(MockDatabase.itemCategory2);
    List<Item> expected = new ArrayList<Item>();
    expected.add(MockDatabase.item2);
    expected.add(MockDatabase.item3);

    assertNotNull(items);
    assertEquals(expected.size(), items.size());
    for (Item i : items)
      assertTrue(expected.contains(i));
  }

  /**
   * method to check that all items in the databse are fetched successfully
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testGetAllItems_Successful() {
    List<Item> items = itemService.getAllItems();

    List<Item> expected = new ArrayList<Item>();
    expected.add(MockDatabase.item1);
    expected.add(MockDatabase.item2);
    expected.add(MockDatabase.item3);

    assertNotNull(items);
    assertEquals(expected.size(), items.size());
    for (Item i : items)
      assertTrue(expected.contains(i));
  }

  // ========================================================================
  // Create methods
  // ========================================================================


  /**
   * method to test that an item is created successfully
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testCreateItem_Successful() {
    assertEquals(3, itemService.getAllItems().size());

    String name = "Spaghetti";
    String description = "extremely tasty";
    String imageUrl = "www.pasta/spaghetti.com";
    int remainingQuantity = 58;
    double price = 6.5;
    boolean availableForOrder = false;
    boolean stillAvailable = true;

    String categoryName = "Dishes";
    ItemCategory category = new ItemCategory();
    category.setName(categoryName);

    Item item = null;
    try {
      item = itemService.createItem(name, description, imageUrl, remainingQuantity, price,
          availableForOrder, stillAvailable, category);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      fail();
    }
    assertNotNull(item);
    assertEquals(name, item.getName());
    assertEquals(description, item.getDescription());
    assertEquals(imageUrl, item.getImageUrl());
    assertEquals(remainingQuantity, item.getRemainingQuantity());
    assertEquals(availableForOrder, item.isAvailableForOrder());
    assertEquals(stillAvailable, item.isStillAvailable());
    assertEquals(category, item.getCategory());
  }

  /**
   * method to test that an error is thrown when remaining quantity is negative
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testCreateItem_WrongRemainingQuantity() {

    String error = null;
    Item item = null;
    ItemCategory itemCategory = new ItemCategory();
    itemCategory.setName("Dish");

    try {
      item = itemService.createItem("Pasta", "italian", "www.pasta.ca", -1, 0.0, false, false,
          itemCategory);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(item);
    // check error
    assertEquals("Item's remaining quantity cannot be less than 0! ", error);
  }

  /**
   * method to test that an error is thrown when price is negative
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testCreateItem_WrongPrice() {

    String error = null;
    Item item = null;
    ItemCategory itemCategory = new ItemCategory();
    itemCategory.setName("Dish");

    try {
      item = itemService.createItem("Pasta", "italian", "www.pasta.ca", 0, -1.0, false, false,
          itemCategory);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(item);
    // check error
    assertEquals("Item's price cannot be less than 0! ", error);
  }

  /**
   * method to test that an error is thrown when name is empty
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testCreateItem_NullName() {
    String name = null;
    String description = "exquisite";
    String imageUrl = "www.bestfood.com";
    String error = null;
    Item item = null;
    ItemCategory itemCategory = new ItemCategory();
    itemCategory.setName("Food");

    try {
      item = itemService.createItem(name, description, imageUrl, 0, 0, false, false, itemCategory);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(item);
    // check error
    assertEquals("Item's name cannot be empty! ", error);
  }

  /**
   * method to test that an error is thrown when description is empty
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testCreateItem_NullDescription() {
    String name = "Croissant";
    String description = null;
    String imageUrl = "www.bestfood.com";
    String error = null;
    Item item = null;
    ItemCategory itemCategory = new ItemCategory();
    itemCategory.setName("Food");

    try {
      item = itemService.createItem(name, description, imageUrl, 0, 0, false, false, itemCategory);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(item);
    // check error
    assertEquals("Item's description cannot be empty! ", error);
  }

  /**
   * method to test that an error is thrown when image URL is empty
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testCreateItem_NullImageUrl() {
    String name = "Croissant";
    String description = "french food";
    String imageUrl = null;
    String error = null;
    Item item = null;
    ItemCategory itemCategory = new ItemCategory();
    itemCategory.setName("Food");

    try {
      item = itemService.createItem(name, description, imageUrl, 0, 0, false, false, itemCategory);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(item);
    // check error
    assertEquals("Item's image URL cannot be empty! ", error);
  }

  /**
   * method to test that an error is thrown when name is just space
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testCreateItem_EmptyName() {
    String name = " ";
    String description = "exquisite";
    String imageUrl = "www.bestfood.com";
    String error = null;
    Item item = null;
    ItemCategory itemCategory = new ItemCategory();
    itemCategory.setName("Food");

    try {
      item = itemService.createItem(name, description, imageUrl, 0, 0, false, false, itemCategory);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(item);
    // check error
    assertEquals("Item's name cannot be empty! ", error);
  }

  /**
   * method to test that an error is thrown when description is just space
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testCreateItem_EmptyDescription() {
    String name = "Croissant";
    String description = " ";
    String imageUrl = "www.bestfood.com";
    String error = null;
    Item item = null;
    ItemCategory itemCategory = new ItemCategory();
    itemCategory.setName("Food");

    try {
      item = itemService.createItem(name, description, imageUrl, 0, 0, false, false, itemCategory);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(item);
    // check error
    assertEquals("Item's description cannot be empty! ", error);
  }

  /**
   * method to test that an error is thrown when image URL is just space
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testCreateItem_EmptyImageUrl() {
    String name = "Croissant";
    String description = "french food";
    String imageUrl = " ";
    String error = null;
    Item item = null;
    ItemCategory itemCategory = new ItemCategory();
    itemCategory.setName("Food");

    try {
      item = itemService.createItem(name, description, imageUrl, 0, 0, false, false, itemCategory);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(item);
    // check error
    assertEquals("Item's image URL cannot be empty! ", error);
  }


  /**
   * method to test that an error is thrown when item category is null
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testCreateItem_NullCategory() {
    String name = "Milk";
    String description = "beverage";
    String imageUrl = "www.cows/milk.com";
    String error = null;
    Item item = null;
    ItemCategory itemCategory = null;

    try {
      item = itemService.createItem(name, description, imageUrl, 0, 0, false, false, itemCategory);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(item);
    // check error
    assertEquals("Item's category cannot be empty!", error);
  }

  /**
   * method to test that an error is thrown when every parameter is either empty or wrong
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testCreateItem_AllNullOrWrong() {

    String error = null;
    Item item = null;

    try {
      item = itemService.createItem(null, null, null, -1, -1, false, false, null);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(item);
    // check error
    assertEquals(
        "Item's name cannot be empty! Item's description cannot be empty! Item's image URL cannot be empty! Item's remaining quantity cannot be less than 0! Item's price cannot be less than 0! Item's category cannot be empty!",
        error);
  }

  // ========================================================================
  // delete methods
  // ========================================================================

  /**
   * method to check that an item was deleted successfully
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testDeleteItem_Successful() {
    itemService.deleteItem(MockDatabase.item1.getName());

    verify(itemDao, times(1))
        .delete(argThat((Item i) -> MockDatabase.item1.getName() == i.getName()));
    verify(itemDao, times(0))
        .delete(argThat((Item i) -> MockDatabase.item1.getName() != i.getName()));
  }

  /**
   * method to check that error is thrown when we input a null name
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testDeleteItem_NullName() {
    Exception error =
        assertThrows(IllegalArgumentException.class, () -> itemService.deleteItem(null));
    assertEquals("Name cannot be empty!", error.getMessage());
    verify(itemDao, times(0)).delete(any(Item.class));
  }

  /**
   * method to check that error is thrown when we input an empty name
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testDeleteItem_EmptyName() {
    Exception error =
        assertThrows(IllegalArgumentException.class, () -> itemService.deleteItem(" "));
    assertEquals("Name cannot be empty!", error.getMessage());
    verify(itemDao, times(0)).delete(any(Item.class));
  }

  // ========================================================================
  // update methods
  // ========================================================================

  /**
   * method to see if item gets modified successfully
   * 
   * @author Habib Jarweh
   */
  public void testModifyItem_Successful() {

    String description = "not so tasty";
    String imageUrl = "www.pasta/spaghetti.com";
    int remainingQuantity = 58;
    double price = 6.5;
    boolean availableForOrder = false;
    boolean stillAvailable = true;
    String categoryName = "Dishes";
    ItemCategory category = new ItemCategory();
    category.setName(categoryName);


    Item item = itemService.modifyItem(MockDatabase.item1.getName(), description, imageUrl,
        remainingQuantity, price, availableForOrder, stillAvailable, category);
    assertNotNull(item);
    assertEquals(MockDatabase.item1.getName(), item.getName());
    assertEquals(description, item.getDescription());
    assertEquals(imageUrl, item.getImageUrl());
    assertEquals(remainingQuantity, item.getRemainingQuantity());
    assertEquals(price, item.getPrice());
    assertEquals(availableForOrder, item.isAvailableForOrder());
    assertEquals(stillAvailable, item.isStillAvailable());
    assertEquals(category, item.getCategory());
  }

  /**
   * method to test that an error is thrown when remaining quantity is negative
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testModifyItem_WrongRemainingQuantity() {

    String error = "";
    Item item = MockDatabase.item1;
    try {
      item = itemService.modifyItem(MockDatabase.item1.getName(), item.getDescription(),
          item.getImageUrl(), -1, item.getPrice(), item.isAvailableForOrder(),
          item.isStillAvailable(), item.getCategory());
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }

    assertNotNull(item);
    // check error
    assertEquals("Item's remaining quantity cannot be less than 0! ", error);
  }

  /**
   * method to test that an error is thrown when price is negative
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testModifyItem_WrongPrice() {

    String error = "";
    Item item = MockDatabase.item1;
    try {
      item = itemService.modifyItem(MockDatabase.item1.getName(), item.getDescription(),
          item.getImageUrl(), item.getRemainingQuantity(), -1.0, item.isAvailableForOrder(),
          item.isStillAvailable(), item.getCategory());
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }

    assertNotNull(item);
    // check error
    assertEquals("Item's price cannot be less than 0! ", error);
  }

  /**
   * method to test that an error is thrown when name is empty
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testModifyItem_NullName() {

    String error = "";
    Item item = MockDatabase.item1;
    try {
      item = itemService.modifyItem(null, item.getDescription(), item.getImageUrl(),
          item.getRemainingQuantity(), item.getPrice(), item.isAvailableForOrder(),
          item.isStillAvailable(), item.getCategory());
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }

    assertNotNull(item);
    // check error
    assertEquals("Item's name cannot be empty! ", error);
  }

  /**
   * method to test that an error is thrown when description is empty
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testModifyItem_NullDescription() {
    String error = "";
    Item item = MockDatabase.item1;
    try {
      item = itemService.modifyItem(MockDatabase.item1.getName(), null, item.getImageUrl(),
          item.getRemainingQuantity(), item.getPrice(), item.isAvailableForOrder(),
          item.isStillAvailable(), item.getCategory());
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }

    assertNotNull(item);
    // check error
    assertEquals("Item's description cannot be empty! ", error);
  }

  /**
   * method to test that an error is thrown when image URL is empty
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testModifyItem_NullImageUrl() {
    String error = "";
    Item item = MockDatabase.item1;
    try {
      item = itemService.modifyItem(MockDatabase.item1.getName(), item.getDescription(), null,
          item.getRemainingQuantity(), item.getPrice(), item.isAvailableForOrder(),
          item.isStillAvailable(), item.getCategory());
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }

    assertNotNull(item);
    // check error
    assertEquals("Item's image URL cannot be empty! ", error);
  }

  /**
   * method to test that an error is thrown when name is just space
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testModifyItem_EmptyName() {
    String error = "";
    Item item = MockDatabase.item1;
    try {
      item = itemService.modifyItem(" ", item.getDescription(), item.getImageUrl(),
          item.getRemainingQuantity(), item.getPrice(), item.isAvailableForOrder(),
          item.isStillAvailable(), item.getCategory());
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }

    assertNotNull(item);
    // check error
    assertEquals("Item's name cannot be empty! ", error);
  }

  /**
   * method to test that an error is thrown when description is just space
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testModifyItem_EmptyDescription() {
    String error = "";
    Item item = MockDatabase.item1;
    try {
      item = itemService.modifyItem(MockDatabase.item1.getName(), " ", item.getImageUrl(),
          item.getRemainingQuantity(), item.getPrice(), item.isAvailableForOrder(),
          item.isStillAvailable(), item.getCategory());
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }

    assertNotNull(item);
    // check error
    assertEquals("Item's description cannot be empty! ", error);
  }

  /**
   * method to test that an error is thrown when image URL is just space
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testModifyItem_EmptyImageUrl() {
    String error = "";
    Item item = MockDatabase.item1;
    try {
      item = itemService.modifyItem(MockDatabase.item1.getName(), item.getDescription(), " ",
          item.getRemainingQuantity(), item.getPrice(), item.isAvailableForOrder(),
          item.isStillAvailable(), item.getCategory());
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }

    assertNotNull(item);
    // check error
    assertEquals("Item's image URL cannot be empty! ", error);
  }


  /**
   * method to test that an error is thrown when item category is null
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testModifyItem_NullCategory() {
    String error = "";
    Item item = MockDatabase.item1;
    try {
      item = itemService.modifyItem(MockDatabase.item1.getName(), item.getDescription(),
          item.getImageUrl(), item.getRemainingQuantity(), item.getPrice(),
          item.isAvailableForOrder(), item.isStillAvailable(), null);
    } catch (IllegalArgumentException e) {
      // Check that no error occurred
      error = e.getMessage();
    }

    assertNotNull(item);
    // check error
    assertEquals("Item's category cannot be empty!", error);
  }

  /**
   * method to test that an error is thrown when every parameter is either empty or wrong
   * 
   * @author Habib Jarweh
   */
  @Test
  public void testModifyItem_AllNullOrWrong() {

    String error = null;
    Item item = MockDatabase.item1;

    try {
      item = itemService.modifyItem(null, null, null, -1, -1, false, false, null);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNotNull(item);
    // check error
    assertEquals(
        "Item's name cannot be empty! Item's description cannot be empty! Item's image URL cannot be empty! Item's remaining quantity cannot be less than 0! Item's price cannot be less than 0! Item's category cannot be empty!",
        error);
  }

  class MockRepository {

    static Item findItemByName(InvocationOnMock invocation) {
      String name = (String) invocation.getArgument(0);
      if (name.equals(MockDatabase.item1.getName()))
        return MockDatabase.item1;
      if (name.equals(MockDatabase.item2.getName()))
        return MockDatabase.item2;
      return null;
    }

    static Item save(InvocationOnMock invocation) {
      return (Item) invocation.getArgument(0);
    }
    
    static List<Item> findItemsByCategory(InvocationOnMock invocation) {
      List<Item> items = new ArrayList<Item>();
      items.add(MockDatabase.item2);
      items.add(MockDatabase.item3);
      return items;
    }

    static List<Item> findAll(InvocationOnMock invocation) {
      List<Item> items = new ArrayList<Item>();
      items.add(MockDatabase.item1);
      items.add(MockDatabase.item2);
      items.add(MockDatabase.item3);
      return items;
    }
  }

  final static class MockDatabase {

    private static final String ITEM_KEY1 = "TestItem1";
    private static final String ITEM_DESCRIPTION1 = "very tasty";
    private static final String ITEM_IMAGEURL1 = "www.niceimage.com";
    private static final int ITEM_REMAININGQUANTITY1 = 50;
    private static final double ITEM_PRICE1 = 3.75;
    private static final boolean ITEM_AVAILABILITYFORORDER1 = true;
    private static final boolean ITEM_AVAILABILITY1 = true;

    private static final String ITEMCATEGORY_KEY1 = "Food";

    // item 2 info
    private static final String ITEM_KEY2 = "TestItem2";
    private static final String ITEM_DESCRIPTION2 = "extremely tasty";
    private static final String ITEM_IMAGEURL2 = "www.veryniceimage.com";
    private static final int ITEM_REMAININGQUANTITY2 = 10;
    private static final double ITEM_PRICE2 = 13.99;
    private static final boolean ITEM_AVAILABILITYFORORDER2 = false;
    private static final boolean ITEM_AVAILABILITY2 = true;

    private static final String ITEMCATEGORY_KEY2 = "Hygiene";
    
    private static final String ITEM_KEY3 = "TestItem3";
    private static final String ITEM_DESCRIPTION3 = "extremely tasty";
    private static final String ITEM_IMAGEURL3 = "www.veryniceimage/product/hygiene.com";
    private static final int ITEM_REMAININGQUANTITY3 = 76;
    private static final double ITEM_PRICE3 = 8.99;
    private static final boolean ITEM_AVAILABILITYFORORDER3= true;
    private static final boolean ITEM_AVAILABILITY3 = true;


    static Item item1 = new Item();
    static ItemCategory itemCategory1 = new ItemCategory();

    static Item item2 = new Item();
    static ItemCategory itemCategory2 = new ItemCategory();
    
    static Item item3 = new Item();

    static {

      itemCategory1.setName(ITEMCATEGORY_KEY1);

      itemCategory2.setName(ITEMCATEGORY_KEY2);

      item1.setName(ITEM_KEY1);
      item1.setDescription(ITEM_DESCRIPTION1);
      item1.setImageUrl(ITEM_IMAGEURL1);
      item1.setRemainingQuantity(ITEM_REMAININGQUANTITY1);
      item1.setPrice(ITEM_PRICE1);
      item1.setAvailableForOrder(ITEM_AVAILABILITYFORORDER1);
      item1.setStillAvailable(ITEM_AVAILABILITY1);
      item1.setCategory(itemCategory1);

      item2.setName(ITEM_KEY2);
      item2.setDescription(ITEM_DESCRIPTION2);
      item2.setImageUrl(ITEM_IMAGEURL2);
      item2.setRemainingQuantity(ITEM_REMAININGQUANTITY2);
      item2.setPrice(ITEM_PRICE2);
      item2.setAvailableForOrder(ITEM_AVAILABILITYFORORDER2);
      item2.setStillAvailable(ITEM_AVAILABILITY2);
      item2.setCategory(itemCategory2);
      
      item3.setName(ITEM_KEY3);
      item3.setDescription(ITEM_DESCRIPTION3);
      item3.setImageUrl(ITEM_IMAGEURL3);
      item3.setRemainingQuantity(ITEM_REMAININGQUANTITY3);
      item3.setPrice(ITEM_PRICE3);
      item3.setAvailableForOrder(ITEM_AVAILABILITYFORORDER3);
      item3.setStillAvailable(ITEM_AVAILABILITY3);
      item3.setCategory(itemCategory2);
      
    }

  }

}
