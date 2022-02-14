package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;

@Entity
public class Order extends Purchase {
  private OrderType orderType;
  private OrderStatus orderStatus;


  public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
  }

  public OrderType getOrderType() {
    return this.orderType;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public OrderStatus getOrderStatus() {
    return this.orderStatus;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Order other = (Order) obj;
    if (orderStatus != other.orderStatus)
      return false;
    if (orderType != other.orderType)
      return false;
    return true;
  }

}
