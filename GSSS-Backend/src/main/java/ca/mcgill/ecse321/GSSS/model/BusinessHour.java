package ca.mcgill.ecse321.GSSS.model;

public class BusinessHour extends TimeInterval{
    private Weekday dayOfWeek;


    public WeekDay getDayOfTheWeek(){
        return dayOfWeek;
    }
}
