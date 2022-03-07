package ca.mcgill.ecse321.GSSS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
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

  // item info
  private static final String ITEM_KEY = "TestItem";
  private static final String ITEM_DESCRIPTION = "very tasty";
  private static final String ITEM_IMAGEURL = "www.niceimage.com";
  private static final int ITEM_REMAININGQUANTITY = 50;
  private static final double ITEM_PRICE = 3.75;
  private static final boolean ITEM_AVAILABILITYFORORDER = true;
  private static final boolean ITEM_AVAILABILITY = true;

  // item category info
  private static final String ITEMCATEGORY_KEY = "Food";

  @BeforeEach
  public void setMockOutput() {
    lenient().when(itemDao.findItemByName(anyString()))
        .thenAnswer((InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(ITEM_KEY)) {
            ItemCategory itemCategory = new ItemCategory();
            itemCategory.setName(ITEMCATEGORY_KEY);

            Item item = new Item();
            item.setName(ITEM_KEY);
            item.setDescription(ITEM_DESCRIPTION);
            item.setImageUrl(ITEM_IMAGEURL);
            item.setRemainingQuantity(ITEM_REMAININGQUANTITY);
            item.setPrice(ITEM_PRICE);
            item.setAvailableForOrder(ITEM_AVAILABILITYFORORDER);
            item.setStillAvailable(ITEM_AVAILABILITY);
            item.setCategory(itemCategory);

            return item;
          } else {
            return null;
          }
        });
    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };
    lenient().when(itemCategoryDao.save(any(ItemCategory.class)))
        .thenAnswer(returnParameterAsAnswer);
    lenient().when(itemDao.save(any(Item.class))).thenAnswer(returnParameterAsAnswer);
  }

  @Test
  public void testCreateItem() {
    assertEquals(0, itemService.getAllItems().size());

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
  }

  @Test
  public void testCreateItemNull() {

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
  
  @Test
  public void testCreateItemEmpty() {
    String name = "";
    String description = "";
    String imageUrl = "";
    String error = null;
    Item item = null;
    try {
      item = itemService.createItem(name, description, imageUrl, -1, -1, false, false, null);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(item);
    // check error
    assertEquals(
        "Item's name cannot be empty! Item's description cannot be empty! Item's image URL cannot be empty! Item's remaining quantity cannot be less than 0! Item's price cannot be less than 0! Item's category cannot be empty!",
        error);
  }
  
  @Test
  public void testCreateItemSpaces() {
    String name = " ";
    String description = " ";
    String imageUrl = " ";
    String error = null;
    Item item = null;
    try {
      item = itemService.createItem(name, description, imageUrl, -1, -1, false, false, null);
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertNull(item);
    // check error
    assertEquals(
        "Item's name cannot be empty! Item's description cannot be empty! Item's image URL cannot be empty! Item's remaining quantity cannot be less than 0! Item's price cannot be less than 0! Item's category cannot be empty!",
        error);
  }


}
