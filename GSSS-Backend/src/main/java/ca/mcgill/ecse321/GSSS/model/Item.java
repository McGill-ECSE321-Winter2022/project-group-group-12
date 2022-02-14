package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * This class represents a shop Item.
 * Its primary key is its name.
 * 
 * @author Philippe Sarouphim Hochar.
 */
@Entity
public class Item {

  private String name;
  private String description;
  private String imageUrl;
  private int remainingQuantity;
  private double price;
  private boolean availableForOrder;
  private boolean stillAvailable;
  private ItemCategory category;

  @Id
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public int getRemainingQuantity() {
    return remainingQuantity;
  }

  public void setRemainingQuantity(int remainingQuantity) {
    this.remainingQuantity = remainingQuantity;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public boolean isAvailableForOrder() {
    return availableForOrder;
  }

  public void setAvailableForOrder(boolean availableForOrder) {
    this.availableForOrder = availableForOrder;
  }

  public boolean isStillAvailable() {
    return stillAvailable;
  }

  public void setStillAvailable(boolean stillAvailable) {
    this.stillAvailable = stillAvailable;
  }

  @ManyToOne
  public ItemCategory getCategory() {
    return category;
  }

  public void setCategory(ItemCategory category) {
    this.category = category;
  }


}
