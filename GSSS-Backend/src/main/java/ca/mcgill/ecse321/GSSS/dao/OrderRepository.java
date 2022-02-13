package ca.mcgill.ecse321.GSSS.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.GSSS.model.Order;
import ca.mcgill.ecse321.GSSS.model.OrderStatus;
import ca.mcgill.ecse321.GSSS.model.OrderType;
import ca.mcgill.ecse321.GSSS.model.QuantityOrdered;

public interface OrderRepository extends CrudRepository<Order, Long> {

  /**
   * Returns an order based on its ID
   * 
   * @author Wassim Jabbour
   * @param id The ID of the order to find
   * @return The Order we are trying to find
   */
  Order findOrderById(Long id);

  /**
   * Returns an order based on one of its QuantityOrdered associated objects
   * 
   * @author Wassim Jabbour
   * @param quantityOrdered The instance of QuantityOrdered of the object we are trying to order
   * @return The Order we are trying to find
   */
  Order findOrderByQuantityOrdered(QuantityOrdered quantityOrdered);

  /**
   * Returns a list of orders based on their types
   * 
   * @author Wassim Jabbour
   * @param orderType The type of order we want to find
   * @return The Order we are trying to find
   */
  List<Order> findOrdersByOrderType(OrderType orderType);

  /**
   * Returns an order based on its ID
   * 
   * @author Wassim Jabbour
   * @param id The ID of the order to find
   * @return The Order we are trying to find
   */
  List<Order> findOrdersByOrderStatus(OrderStatus orderStatus);

}
