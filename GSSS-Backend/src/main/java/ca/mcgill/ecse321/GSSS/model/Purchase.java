package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
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
	private long id;
	private Employee assignedEmployee;
	
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
	
	@OneToMany
	public Set<QuantityOrdered> getQuantitiesOrdered() {
        return quantitiesOrdered;
    }
	
    public void setQuantitiesOrdered(Set<QuantityOrdered> quantitiesOrdered) {
        this.quantitiesOrdered = quantitiesOrdered;
    }
    
    @ManyToOne
	public Employee getAssignedEmployee() {
		return assignedEmployee;
	}

	public void setAssignedEmployee(Employee assignedEmployee) {
		this.assignedEmployee = assignedEmployee;
	}
	
    
}
