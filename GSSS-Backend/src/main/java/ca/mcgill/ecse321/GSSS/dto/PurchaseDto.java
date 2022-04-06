package ca.mcgill.ecse321.GSSS.dto;

import ca.mcgill.ecse321.GSSS.model.OrderStatus;
import ca.mcgill.ecse321.GSSS.model.OrderType;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the DTO-equivalent class of Purchase.
 *
 * @author Philippe Sarouphim Hochar.
 */
public class PurchaseDto {

  private String id;
  private OrderType orderType;
  private OrderStatus orderStatus;
  private Date date;
  private Time time;
  private Map<String, Integer> items = new HashMap<String, Integer>();
  private EmployeeDto employee;

  public PurchaseDto() {
  }

  public PurchaseDto(
      String id,
      OrderType orderType,
      OrderStatus orderStatus,
      Date date,
      Time time,
      Map<String, Integer> items,
      EmployeeDto employee) {
    this.id = id;
    this.orderType = orderType;
    this.orderStatus = orderStatus;
    this.date = date;
    this.time = time;
    this.items = items;
    this.employee = employee;
  }

  public String getId() {
    return id;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public Date getDate() {
    return date;
  }

  public Time getTime() {
    return time;
  }

  public Map<String, Integer> getItems() {
    return items;
  }

  public EmployeeDto getEmployee() {
    return employee;
  }
}
