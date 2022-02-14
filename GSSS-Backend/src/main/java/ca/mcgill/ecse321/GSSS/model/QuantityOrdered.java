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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    QuantityOrdered other = (QuantityOrdered) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (itemOrdered == null) {
      if (other.itemOrdered != null)
        return false;
    } else if (!itemOrdered.equals(other.itemOrdered))
      return false;
    if (quantityOrdered != other.quantityOrdered)
      return false;
    return true;
  }

}
