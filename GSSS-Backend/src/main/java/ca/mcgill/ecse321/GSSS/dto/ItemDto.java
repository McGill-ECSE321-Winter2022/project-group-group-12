package ca.mcgill.ecse321.GSSS.dto;

import ca.mcgill.ecse321.GSSS.model.ItemCategory;

/**
 *Data Transfer Object Class for Item Class
 *
 * @author Theo Ghanem
 */
public class ItemDto {

  private String name;
  private String description;
  private String imageUrl;
  private int remainingQuantity;
  private double price;
  private boolean availableForOrder;
  private boolean stillAvailable;
  private ItemCategory category;

  /**
   * Default constructor
   *
   * @author Theo Ghanem
   */
  public ItemDto() {
  }
  public ItemDto(String name, String description, String imageUrl, int remainingQuantity, double price, boolean availableForOrder, boolean stillAvailable, ItemCategory category) {

    this.name = name;
    this.description = description;
    this.imageUrl =imageUrl;
    this.remainingQuantity = remainingQuantity;
    this.price = price;
    this.availableForOrder = availableForOrder;
    this.stillAvailable = stillAvailable;
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public int getRemainingQuantity() {
    return remainingQuantity;
  }

  public double getPrice() {
    return price;
  }

  public boolean isAvailableForOrder() {
    return availableForOrder;
  }

  public boolean isStillAvailable() {
    return stillAvailable;
  }

  public ItemCategory getCategory() {
    return category;
  }

}
