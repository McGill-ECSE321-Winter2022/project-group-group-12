package ca.mcgill.ecse321.GSSS.model;

import java.sql.Date;

import javax.persistence.Entity;

@Entity
public class Shift extends TimeInterval {

    private Date date;

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }
}
