package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Item {

    private String name;
    private String description;
    private String imageUrl;
    private int remainingQuantity;
    private double price;
    private boolean isAvailableForOrder;
    private boolean isStillAvailable;
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
        return isAvailableForOrder;
    }

    public void setAvailableForOrder(boolean isAvailableForOrder) {
        this.isAvailableForOrder = isAvailableForOrder;
    }

    public boolean isStillAvailable() {
        return isStillAvailable;
    }

    public void setStillAvailable(boolean isStillAvailable) {
        this.isStillAvailable = isStillAvailable;
    }

    @ManyToOne
    public ItemCategory getCategory() {
        return category;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }
    
    
}
