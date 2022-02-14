package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Purchase {
  private Date date;
  private Time time;
  private Set<QuantityOrdered> quantitiesOrdered;
  private String id;

  @Id
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Time getTime() {
    return time;
  }

  public void setTime(Time time) {
    this.time = time;
  }

  @OneToMany
  public Set<QuantityOrdered> getQuantitiesOrdered() {
    return quantitiesOrdered;
  }

  public void setQuantitiesOrdered(Set<QuantityOrdered> quantitiesOrdered) {
    this.quantitiesOrdered = quantitiesOrdered;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((date == null) ? 0 : date.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((quantitiesOrdered == null) ? 0 : quantitiesOrdered.hashCode());
    result = prime * result + ((time == null) ? 0 : time.hashCode());
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
    Purchase other = (Purchase) obj;
    if (date == null) {
      if (other.date != null)
        return false;
    } else if (!date.equals(other.date))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (quantitiesOrdered == null) {
      if (other.quantitiesOrdered != null)
        return false;
    } else if (!quantitiesOrdered.equals(other.quantitiesOrdered))
      return false;
    if (time == null) {
      if (other.time != null)
        return false;
    } else if (!time.equals(other.time))
      return false;
    return true;
  }

}
