package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;

@Entity
public class Employee extends FireableUser{

    private Set<Shift> shifts;

    public Set<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(Set<Shift> shifts) {
        this.shifts = shifts;
    }
    
}
