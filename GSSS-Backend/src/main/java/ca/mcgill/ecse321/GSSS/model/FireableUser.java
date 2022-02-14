package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.MappedSuperclass;

/**
 * This class is an abstract class representing Users that are fireable.
 * It is a mapped superclass and will therefore not be present in the database.
 * 
 * @author Philippe Sarouphim Hochar.
 */
@MappedSuperclass
public abstract class FireableUser extends User {

  private boolean isDisabled;

  public boolean isDisabled() {
    return isDisabled;
  }

  public void setDisabled(boolean isDisabled) {
    this.isDisabled = isDisabled;
  }

}
