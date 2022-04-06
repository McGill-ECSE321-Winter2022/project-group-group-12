package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * This class represents a shop Item. Its primary key is its name.
 *
 * @author Chris Hatoum
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

  // Setters and getters
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
    Item other = (Item) obj;
    if (availableForOrder != other.availableForOrder) {
      return false;
    }
    if (category == null) {
      if (other.category != null) {
        return false;
      }
    } else if (!category.equals(other.category)) {
      return false;
    }
    if (description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!description.equals(other.description)) {
      return false;
    }
    if (imageUrl == null) {
      if (other.imageUrl != null) {
        return false;
      }
    } else if (!imageUrl.equals(other.imageUrl)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price)) {
      return false;
    }
    if (remainingQuantity != other.remainingQuantity) {
      return false;
    }
    if (stillAvailable != other.stillAvailable) {
      return false;
    }
    return true;
  }
}
