package ca.mcgill.ecse321.GSSS.model;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * This class represents a purchase. Its primary key is a UUID.
 *
 * @author Philippe Sarouphim Hochar.
 */
@Entity
public class Purchase {

  private String id;
  private OrderType orderType;
  private OrderStatus orderStatus;
  private Date date;
  private Time time;
  private Map<Item, Integer> items = new HashMap<Item, Integer>();
  private Employee employee;

  @ManyToOne
  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  @Id
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Time getTime() {
    return time;
  }

  public void setTime(Time time) {
    this.time = time;
  }

  @ElementCollection(fetch = FetchType.EAGER)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "purchase_id")
  public Map<Item, Integer> getItems() {
    return items;
  }

  public void setItems(Map<Item, Integer> items) {
    this.items = items;
  }

  /**
   * Overrode the equals method to use it in tests
   *
   * @param obj The object to compare
   * @return True if it's the same object, false otherwise
   * @author Philippe Sarouphim Hochar
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Purchase other = (Purchase) obj;
    if (date == null) {
      if (other.date != null) {
        return false;
      }
    } else if (!date.equals(other.date)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (items == null) {
      if (other.items != null) {
        return false;
      }
    } else if (!items.equals(other.items)) {
      return false;
    }
    if (orderStatus != other.orderStatus) {
      return false;
    }
    if (orderType != other.orderType) {
      return false;
    }
    if (time == null) {
      if (other.time != null) {
        return false;
      }
    } else if (!time.equals(other.time)) {
      return false;
    }
    return true;
  }
}
