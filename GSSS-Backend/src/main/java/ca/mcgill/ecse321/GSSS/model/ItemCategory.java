package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This item represents a Category of items. Its primary key is its only field: name.
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ItemCategory other = (ItemCategory) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }


}
