package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This item represents a Category of items.
 * Its primary key is its only field: name.
 * 
 * @author Philippe Sarouphim Hochar.
 */
@Entity
public class ItemCategory {

  private String name;

  @Id
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }



}
