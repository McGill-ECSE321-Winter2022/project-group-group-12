package ca.mcgill.ecse321.GSSS.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Order;
import ca.mcgill.ecse321.GSSS.model.OrderStatus;
import ca.mcgill.ecse321.GSSS.model.OrderType;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.QuantityOrdered;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestOrderPersistence {


  @Autowired
  private OrderRepository orderRepository;

  //Initialize variables that will be used to test an order
  // order
  OrderType OrderTypeEnum = OrderType.delivery;
  OrderStatus OrederStatusEnum = OrderStatus.outForDelivery;
  // purchase
  Date date = Date.valueOf(LocalDate.of(2022, Month.JANUARY,31));
  Time time = java.sql.Time.valueOf(LocalTime.of(13, 25)); 
  String purchaseId = UUID.randomUUID().toString();
  int quantityOrderedInt = 5;

  Set<QuantityOrdered> quantitiesOrdered = new HashSet<QuantityOrdered>(quantityOrderedInt);
  String quantityOrderedId = UUID.randomUUID().toString();


  // 

  /**
   * @author Chris Hatoum
   * Testing the persistence and loading for the Order Repository.
   * This tests the findOrderById method
   */

  @Test
  public void testfindOrderById(){


    Purchase purchase = new Purchase();
    purchase.setDate(date);
    purchase.setTime(time);
    purchase.setId(purchaseId);
    purchase.setQuantitiesOrdered(quantitiesOrdered);

    Order order = new Order();
    order.setOrderType(OrderTypeEnum);
    order.setOrderStatus(OrederStatusEnum);

    orderRepository.save(order);

    order=null;

    order = orderRepository.findOrderById(purchaseId);

    assertNotNull(order);
    assertEquals(order.getDate(), date);
    assertEquals(order.getTime(), time);
    assertEquals(order.getId(), purchaseId);
    assertEquals(order.getOrderStatus(), OrederStatusEnum);
    assertEquals(order.getOrderType(), OrderTypeEnum);
    assertEquals(order.getQuantitiesOrdered(), quantitiesOrdered);

  }
  
  /**
   * @author Chris Hatoum
   * Testing the persistence and loading for the Order Repository.
   * This tests the findOrderByQuantitiesOrdered method
   */
  @Test
  public void testfindOrderByQuantitiesOrdered(){


    Purchase purchase = new Purchase();
    purchase.setDate(date);
    purchase.setTime(time);
    purchase.setId(purchaseId);
    purchase.setQuantitiesOrdered(quantitiesOrdered);

    Order order = new Order();
    order.setOrderType(OrderTypeEnum);
    order.setOrderStatus(OrederStatusEnum);


    QuantityOrdered quantityOrdered = new QuantityOrdered();
    quantityOrdered.setId(quantityOrderedId);
    quantityOrdered.setQuantityOrdered(quantityOrderedInt);
    order.setQuantitiesOrdered(quantitiesOrdered);

    orderRepository.save(order);

    order=null;

    order = orderRepository.findOrderByQuantitiesOrdered(quantityOrdered);

    assertNotNull(order);
    assertEquals(order.getDate(), date);
    assertEquals(order.getTime(), time);
    assertEquals(order.getId(), purchaseId);
    assertEquals(order.getOrderStatus(), OrederStatusEnum);
    assertEquals(order.getOrderType(), OrderTypeEnum);
    assertEquals(order.getQuantitiesOrdered(), quantitiesOrdered);

  }

  /**
   * @author Chris Hatoum
   * Testing the persistence and loading for the Order Repository.
   * This tests the findOrdersByOrderType method
   */
  @Test
  public void testfindOrdersByOrderType(){


    Purchase purchase = new Purchase();
    purchase.setDate(date);
    purchase.setTime(time);
    purchase.setId(purchaseId);
    purchase.setQuantitiesOrdered(quantitiesOrdered);

    Order order = new Order();
    order.setOrderType(OrderTypeEnum);
    order.setOrderStatus(OrederStatusEnum);

    orderRepository.save(order);

    order=null;

    order = (Order) orderRepository.findOrdersByOrderType(OrderTypeEnum);

    assertNotNull(order);
    assertEquals(order.getDate(), date);
    assertEquals(order.getTime(), time);
    assertEquals(order.getId(), purchaseId);
    assertEquals(order.getOrderStatus(), OrederStatusEnum);
    assertEquals(order.getOrderType(), OrderTypeEnum);
    assertEquals(order.getQuantitiesOrdered(), quantitiesOrdered);

  }

  /**
   * @author Chris Hatoum
   * Testing the persistence and loading for the Order Repository.
   * This tests the findOrdersByOrderStatus method
   */
  @Test
  public void testfindOrdersByOrderStatus(){



    Purchase purchase = new Purchase();
    purchase.setDate(date);
    purchase.setTime(time);
    purchase.setId(purchaseId);
    purchase.setQuantitiesOrdered(quantitiesOrdered);

    Order order = new Order();
    order.setOrderType(OrderTypeEnum);
    order.setOrderStatus(OrederStatusEnum);

    orderRepository.save(order);

    order=null;

    order =  (Order) orderRepository.findOrdersByOrderStatus(OrederStatusEnum);

    assertNotNull(order);
    assertEquals(order.getDate(), date);
    assertEquals(order.getTime(), time);
    assertEquals(order.getId(), purchaseId);
    assertEquals(order.getOrderStatus(), OrederStatusEnum);
    assertEquals(order.getOrderType(), OrderTypeEnum);
    assertEquals(order.getQuantitiesOrdered(), quantitiesOrdered);

  }
}
