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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (isDisabled ? 1231 : 1237);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    FireableUser other = (FireableUser) obj;
    if (isDisabled != other.isDisabled)
      return false;
    return true;
  }

}
