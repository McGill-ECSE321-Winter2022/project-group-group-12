package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class QuantityOrdered {
    
    private int id;
    private int quantityOrdered;

   
    @Id
    public int getId() {
        return id;
    }
    
    @OneToMany
    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }
    
}
