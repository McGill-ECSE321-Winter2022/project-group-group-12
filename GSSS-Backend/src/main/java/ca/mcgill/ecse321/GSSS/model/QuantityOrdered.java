package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class QuantityOrdered {
    
    private long id;
    private int quantityOrdered;
    private Item itemOrdered;

   @ManyToOne
    public Item getItemOrdered() {
        return itemOrdered;
    }

    public void setItemOrdered(Item itemOrdered) {
        this.itemOrdered = itemOrdered;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }
    
}
