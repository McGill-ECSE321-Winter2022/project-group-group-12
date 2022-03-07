package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;

/**
 * This class represents the Owner of the grocery store. Its primary key is its email in its
 * superclass User.
 * 
 * @author Philippe Sarouphim Hochar.
 */
@Entity
public class Owner extends Account {
  
  // The SYSTEM'S information (Stored in the owner class cause the system only has 1 owner)
  String storeCity;
  double outOfTownDeliveryFee;
  
  public String getStoreCity() {
    return storeCity;
  }
  public void setStoreCity(String storeCity) {
    this.storeCity = storeCity;
  }
  public double getOutOfTownDeliveryFee() {
    return outOfTownDeliveryFee;
  }
  public void setOutOfTownDeliveryFee(double outOfTownDeliveryFee) {
    this.outOfTownDeliveryFee = outOfTownDeliveryFee;
  }

}
