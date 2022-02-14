package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * This class represents the ordered quantity for an item ordered
 * @author Theo Ghanem
 */
@Entity
public class QuantityOrdered {

  private String id;
  private int quantityOrdered;
  private Item itemOrdered;

  @ManyToOne
  public Item getItemOrdered() {
    return itemOrdered;
  }

  public void setItemOrdered(Item itemOrdered) {
    this.itemOrdered = itemOrdered;
  }

  @Id
  @GeneratedValue
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getQuantityOrdered() {
    return quantityOrdered;
  }

  public void setQuantityOrdered(int quantityOrdered) {
    this.quantityOrdered = quantityOrdered;
  }

}
