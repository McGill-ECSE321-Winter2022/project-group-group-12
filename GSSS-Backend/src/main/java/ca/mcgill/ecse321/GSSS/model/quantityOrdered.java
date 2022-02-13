package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;

@Entity
public class quantityOrdered {
    
    private Integer quantityOrdered;

    public Integer getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }
    
}
