package ca.mcgill.ecse321.GSSS.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.GSSS.dao.QuantityOrderedRepository;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import ca.mcgill.ecse321.GSSS.model.QuantityOrdered;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestQuantityOrderedPersistence {

  @Autowired
  QuantityOrderedRepository quantityOrderedRepository;
  @Autowired
  ItemRepository itemRepository;
  @Autowired
  ItemCategoryRepository itemCategoryRepository;


  private QuantityOrdered persistQuantityOrdered(String id, int quantity, Item item) {
    QuantityOrdered quantityOrdered = new QuantityOrdered();
    quantityOrdered.setId(id);
    quantityOrdered.setQuantityOrdered(quantity);
    quantityOrdered.setItemOrdered(item);

    quantityOrderedRepository.save(quantityOrdered);

    return quantityOrdered;
  }

  private Item persistItem(String name, String description, String imageUrl, double price,
      int remainingQuantity, boolean availableForOrder, boolean stillAvailable,
      ItemCategory category) {
    Item item = new Item();
    item.setName(name);
    item.setDescription(description);
    item.setImageUrl(imageUrl);
    item.setPrice(price);
    item.setRemainingQuantity(remainingQuantity);
    item.setAvailableForOrder(availableForOrder);
    item.setStillAvailable(stillAvailable);
    item.setCategory(category);

    itemRepository.save(item);

    return item;
  }

  private ItemCategory persistItemCategory(String categoryName) {
    ItemCategory category = new ItemCategory();
    category.setName(categoryName);

    itemCategoryRepository.save(category);

    return category;
  }

  @Test
  public void testPersistAndLoadQuantityOrderedById() {

    ItemCategory category = persistItemCategory("Food");
    Item item =
        persistItem("Pasta", "Italian dish.", "imgur.com/pasta", 5.0, 4, true, true, category);

    QuantityOrdered quantityOrdered1 =
        persistQuantityOrdered(UUID.randomUUID().toString(), 3, item);
    QuantityOrdered quantityOrdered2 =
        quantityOrderedRepository.findQuantityOrderedById(quantityOrdered1.getId());

    assertNotNull(quantityOrdered2);
    assertTrue(quantityOrdered1.equals(quantityOrdered2));

  }

  @Test
  public void testPersistAndLoadQuantityOrderedByItemOrdered() {

    ItemCategory category = persistItemCategory("Food");
    Item item =
        persistItem("Pasta", "Italian dish.", "imgur.com/pasta", 5.0, 4, true, true, category);

    QuantityOrdered quantityOrdered1 =
        persistQuantityOrdered(UUID.randomUUID().toString(), 3, item);
    QuantityOrdered quantityOrdered2 =
        persistQuantityOrdered(UUID.randomUUID().toString(), 1, item);

    List<QuantityOrdered> list1 = new ArrayList<QuantityOrdered>();
    list1.add(quantityOrdered1);
    list1.add(quantityOrdered2);

    List<QuantityOrdered> list2 =
        quantityOrderedRepository.findQuantitiesOrderedByItemOrdered(item);

    assertNotNull(list2);
    assertEquals(list1.size(), list2.size());
    for (int i = 0; i < list1.size(); i++) {
      assertTrue(list2.contains(list1.get(i)));
    }

  }

}
