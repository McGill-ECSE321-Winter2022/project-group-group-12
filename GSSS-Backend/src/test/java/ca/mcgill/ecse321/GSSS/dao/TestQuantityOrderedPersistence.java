package ca.mcgill.ecse321.GSSS.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.GSSS.dao.QuantityOrderedRepository;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.QuantityOrdered;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestQuantityOrderedPersistence {
  
 @Autowired
 QuantityOrderedRepository quantityOrderedRepository;
 int quantity = 50;
 
 @Autowired
 ItemRepository itemRepository;
 String name = "Tomato";
 
 @Test
 public void testPersistAndLoadQuantityOrderedById(){
   QuantityOrdered quantityOrdered = new QuantityOrdered();
   quantityOrdered.setQuantityOrdered(quantity);
   long id = quantityOrdered.getId();
   
   quantityOrderedRepository.save(quantityOrdered);
   quantityOrdered = null;
   
   quantityOrdered = quantityOrderedRepository.findQuantityOrderedById(id);
   
   assertNotNull(quantityOrdered);
   assertEquals(id, quantityOrdered.getId());
   assertEquals(quantity, quantityOrdered.getQuantityOrdered());
   
 }
 
 @Test
 public void testPersistAndLoadQuantityOrderedByItemOrdered(){
   
   Item item = new Item();
   item.setName(name);
   itemRepository.save(item);
   
   QuantityOrdered quantityOrdered = new QuantityOrdered();
   quantityOrdered.setQuantityOrdered(quantity);
   quantityOrdered.setItemOrdered(item);
   long id = quantityOrdered.getId();
   
   quantityOrderedRepository.save(quantityOrdered);
   quantityOrdered = null;
   
   List<QuantityOrdered> list = new ArrayList<QuantityOrdered>();
   list = quantityOrderedRepository.findQuantitiesOrderedByItemOrdered(item);
   
   assertNotNull(list);
   assertEquals(item.getName(), list.get(0).getItemOrdered().getName());
   
 }
 
}