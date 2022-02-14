package ca.mcgill.ecse321.GSSS.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.QuantityOrdered;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestPurchasePersistence {

  @Autowired
  private PurchaseRepository purchaseRepository;
  @Autowired
  private QuantityOrderedRepository quantityOrderedRepository;
  @Autowired
  private ItemRepository itemRepository;
  @Autowired
  private ItemCategoryRepository itemCategoryRepository;

  // private void verify(Set<QuantityOrdered> quantitiesOrdered1,
  // Set<QuantityOrdered> quantitiesOrdered2) {
  // assertNotNull(quantitiesOrdered2);
  // assertEquals(quantitiesOrdered1.size(), quantitiesOrdered2.size());
  // for (QuantityOrdered q : quantitiesOrdered1) {
  // assertTrue(quantitiesOrdered2.contains(q));
  // }
  // }

  private void verify(List<Purchase> purchases1, List<Purchase> purchases2) {
    assertNotNull(purchases2);
    assertEquals(purchases1.size(), purchases2.size());
    for (int i = 0; i < purchases1.size(); i++) {
      assertTrue(purchases2.contains(purchases1.get(i)));
    }
  }

  private Purchase persist(Date date, Time time, Set<QuantityOrdered> quantitiesOrdered,
      String id) {
    Purchase purchase = new Purchase();
    purchase.setDate(date);
    purchase.setTime(time);
    purchase.setId(id);

    purchaseRepository.save(purchase);

    return purchase;
  }

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
  public void testPersistAndLoadPurchaseById() {
    // First example for object save/load
    // attributes of purchase
    // Date date = Date.valueOf("2022-01-01");
    // Time time = Time.valueOf("13:45:20");
    // String id = UUID.randomUUID().toString();
    // Set set1 = new HashSet<QuantityOrdered>();
    // QuantityOrdered quantityOrdered = new QuantityOrdered();
    //
    // // attributes of item category
    //
    //
    // Purchase purchase = persist(Date.valueOf("2022-01-01"), Time.valueOf("13:45:20"), set1,
    // UUID.randomUUID().toString());
    //
    // // First example for attribute save/load
    //
    // purchase = null;
    //
    // purchase = purchaseRepository.findPurchaseById(id);
    // assertNotNull(purchase);
    // assertEquals(time, purchase.getTime());
    // assertEquals(date, purchase.getDate());
    // assertEquals(id, purchase.getId());

    ItemCategory category = persistItemCategory("Food");
    Item item =
        persistItem("Pasta", "Italian dish.", "imgur.com/pasta", 5.0, 4, true, true, category);
    QuantityOrdered quantityOrdered = persistQuantityOrdered(UUID.randomUUID().toString(), 3, item);
    Set<QuantityOrdered> set = new HashSet<QuantityOrdered>();
    set.add(quantityOrdered);

    Purchase purchase1 = persist(Date.valueOf("2022-01-01"), Time.valueOf("13:45:20"), set,
        UUID.randomUUID().toString());

    Purchase purchase2 = purchaseRepository.findPurchaseById(purchase1.getId());

    assertNotNull(purchase2);
    assertTrue(purchase1.equals(purchase2));


  }

  @Test
  public void testPersistAndLoadPurchaseByQuantitiesOrdered() {
    // QuantityOrdered quantityOrdered1 = new QuantityOrdered();
    // String id1 = UUID.randomUUID().toString();
    // quantityOrdered1.setQuantityOrdered(50);
    // quantityOrdered1.setId(id1);
    // quantityOrderedRepository.save(quantityOrdered1);
    //
    // QuantityOrdered quantityOrdered2 = new QuantityOrdered();
    // String id2 = UUID.randomUUID().toString();
    // quantityOrdered2.setQuantityOrdered(100);
    // quantityOrdered2.setId(id2);
    // quantityOrderedRepository.save(quantityOrdered2);
    //
    // Set set1 = new HashSet<QuantityOrdered>();
    // set1.add(quantityOrdered1);
    // set1.add(quantityOrdered2);
    //
    // // First example for object save/load
    // Purchase purchase = new Purchase();
    // purchase.setQuantitiesOrdered(set1);
    // // First example for attribute save/load
    // purchaseRepository.save(purchase);
    //
    // purchase = null;
    //
    // purchase = purchaseRepository.findPurchaseByQuantitiesOrdered(quantityOrdered1);
    //
    // verify(set1, purchase.getQuantitiesOrdered());

    ItemCategory category = persistItemCategory("Food");
    Item item =
        persistItem("Pasta", "Italian dish.", "imgur.com/pasta", 5.0, 4, true, true, category);
    QuantityOrdered quantityOrdered = persistQuantityOrdered(UUID.randomUUID().toString(), 3, item);
    Set<QuantityOrdered> set = new HashSet<QuantityOrdered>();
    set.add(quantityOrdered);

    Purchase purchase1 = persist(Date.valueOf("2022-01-01"), Time.valueOf("13:45:20"), set,
        UUID.randomUUID().toString());

    Purchase purchase2 =
        purchaseRepository.findPurchaseByQuantitiesOrdered(purchase1.getQuantitiesOrdered());

    assertNotNull(purchase2);
    assertTrue(purchase1.equals(purchase2));
  }

  @Test
  public void testPersistAndLoadPurchaseByDate() {

    ItemCategory category = persistItemCategory("Food");
    Item item =
        persistItem("Pasta", "Italian dish.", "imgur.com/pasta", 5.0, 4, true, true, category);
    QuantityOrdered quantityOrdered = persistQuantityOrdered(UUID.randomUUID().toString(), 3, item);
    Set<QuantityOrdered> set = new HashSet<QuantityOrdered>();
    set.add(quantityOrdered);

    Purchase purchase1 = persist(Date.valueOf("2022-01-01"), Time.valueOf("13:45:20"), set,
        UUID.randomUUID().toString());

    Purchase purchase2 = persist(Date.valueOf("2022-01-01"), Time.valueOf("9:00:20"), set,
        UUID.randomUUID().toString());

    List<Purchase> list1 = new ArrayList<Purchase>();
    list1.add(purchase1);
    list1.add(purchase2);

    List<Purchase> list2 = purchaseRepository.findPurchasesByDate(purchase1.getDate());

    assertNotNull(list2);
    assertEquals(list1.size(), list2.size());
    for (int i = 0; i < list1.size(); i++) {
      assertTrue(list2.contains(list1.get(i)));
    }

  }

  @Test
  public void testPersistAndLoadPurchaseByTime() {

    ItemCategory category = persistItemCategory("Food");
    Item item =
        persistItem("Pasta", "Italian dish.", "imgur.com/pasta", 5.0, 4, true, true, category);
    QuantityOrdered quantityOrdered = persistQuantityOrdered(UUID.randomUUID().toString(), 3, item);
    Set<QuantityOrdered> set = new HashSet<QuantityOrdered>();
    set.add(quantityOrdered);

    Purchase purchase1 = persist(Date.valueOf("2022-01-01"), Time.valueOf("13:45:20"), set,
        UUID.randomUUID().toString());

    Purchase purchase2 = persist(Date.valueOf("2021-09-09"), Time.valueOf("13:45:20"), set,
        UUID.randomUUID().toString());

    List<Purchase> list1 = new ArrayList<Purchase>();
    list1.add(purchase1);
    list1.add(purchase2);

    List<Purchase> list2 = purchaseRepository.findPurchasesByTime(purchase1.getTime());

    assertNotNull(list2);
    assertEquals(list1.size(), list2.size());
    for (int i = 0; i < list1.size(); i++) {
      assertTrue(list2.contains(list1.get(i)));
    }

  }


}
