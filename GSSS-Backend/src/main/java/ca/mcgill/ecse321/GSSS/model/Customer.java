package ca.mcgill.ecse321.GSSS.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * This class represents the customer.
 * Its primary key is its email in its superclass User.
 * 
 * @author Philippe Sarouphim Hochar.
 */
@Entity
public class Customer extends FireableUser {

  private Set<Order> pastOrders;
  private Order cart;

  @OneToMany
  public Set<Order> getPastOrders() {
    return pastOrders;
  }

  public void setPastOrders(Set<Order> pastOrders) {
    this.pastOrders = pastOrders;
  }

  @OneToOne(optional = true)
  public Order getCart() {
    return cart;
  }

  public void setCart(Order cart) {
    this.cart = cart;
  }

}
