package ca.mcgill.ecse321.GSSS.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.QuantityOrdered;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestPurchasePersistence {
  
  @Autowired
  private PurchaseRepository purchaseRepository;
  Date date = new Date(System.currentTimeMillis());
  Time time = new Time(System.currentTimeMillis());
  @Autowired
  private QuantityOrderedRepository quantityOrderedRepository;

  
  @Test
  public void testPersistAndLoadPurchaseById() {
      // First example for object save/load
      Purchase purchase = new Purchase();
      long id = purchase.getId();
      // First example for attribute save/load
      purchaseRepository.save(purchase);

      purchase = null;

      purchase = purchaseRepository.findPurchaseById(id);
      assertNotNull(purchase);
      assertEquals(id, purchase.getId());
  }

@Test
public void testPersistAndLoadPurchaseByQuantitiesOrdered() {
    QuantityOrdered quantityOrdered1 = new QuantityOrdered();
    long id1  = quantityOrdered1.getId();
    quantityOrdered1.setQuantityOrdered(50);
    quantityOrderedRepository.save(quantityOrdered1);
    
    QuantityOrdered quantityOrdered2 = new QuantityOrdered();
    long id2  = quantityOrdered2.getId();
    quantityOrdered2.setQuantityOrdered(100);
    quantityOrderedRepository.save(quantityOrdered2);
    
    Set set = new HashSet<QuantityOrdered>();
    set.add(quantityOrdered1);
    set.add(quantityOrdered2);
    
    // First example for object save/load
    Purchase purchase = new Purchase();
    purchase.setQuantitiesOrdered(set);
    // First example for attribute save/load
    purchaseRepository.save(purchase);

    purchase = null;
    
    purchase = purchaseRepository.findPurchaseByQuantitiesOrdered(quantityOrdered1);
    assertNotNull(purchase.getQuantitiesOrdered());
    assertEquals(set, purchase.getQuantitiesOrdered());
}

@Test
public void testPersistAndLoadPurchaseByDate() {
  Purchase purchase = new Purchase();
  purchase.setDate(date);
  
  purchaseRepository.save(purchase);
  
  purchase = null;
  
  List<Purchase> listPurchase = new ArrayList<Purchase>();
  listPurchase = purchaseRepository.findPurchasesByDate(date);
  
  assertNotNull(listPurchase);
  assertEquals(date, listPurchase.get(0).getDate());
  
}

@Test
public void testPersistAndLoadPurchaseByTime() {
  Purchase purchase = new Purchase();
  purchase.setTime(time);
  
  purchaseRepository.save(purchase);
  
  purchase = null;
  
  List<Purchase> listPurchase = new ArrayList<Purchase>();
  listPurchase = purchaseRepository.findPurchasesByDate(date);
  
  assertNotNull(listPurchase);
  assertEquals(time, listPurchase.get(0).getTime());
  
}
  
  
}
