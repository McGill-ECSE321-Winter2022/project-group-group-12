package ca.mcgill.ecse321.GSSS.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Employee extends FireableUser{

    private Set<Shift> shifts;
    private Purchase purchase;

    @OneToMany
    public Set<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(Set<Shift> shifts) {
        this.shifts = shifts;
    }
    
    @OneToMany
	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
    
}
