package ca.mcgill.ecse321.GSSS.model;

import java.util.Set;

public class Customer extends FireableUser {
    
    private Set<Order> orders;
    private Order cart;

    public Set<Order> getOrders() {
        return orders;
    }
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
    public Order getCart() {
        return cart;
    }
    
    public void setCart(Order cart) {
        this.cart = cart;
    }

    

}
