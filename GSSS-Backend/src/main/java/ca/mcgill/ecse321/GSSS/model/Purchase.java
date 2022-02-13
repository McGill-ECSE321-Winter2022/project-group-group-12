package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;

@Entity
public class Purchase {
	private Date date;
	private Time time;
	private Set<QuantityOrdered1> quantitiesOrdered;
	private long id;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(int id) {
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
	
	public Set<QuantityOrdered1> getQuantitiesOrdered() {
        return quantitiesOrdered;
    }
	
    public void setQuantitiesOrdered(Set<QuantityOrdered1> quantitiesOrdered) {
        this.quantitiesOrdered = quantitiesOrdered;
    }
	
}
