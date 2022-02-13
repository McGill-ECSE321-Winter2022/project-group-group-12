package ca.mcgill.ecse321.GSSS.model;

import javax.persistence.Entity;

@Entity
public abstract class FireableUser extends User{

    private boolean isDisabled;

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }
    
}
