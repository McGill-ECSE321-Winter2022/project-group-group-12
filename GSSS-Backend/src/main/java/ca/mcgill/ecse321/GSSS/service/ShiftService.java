package ca.mcgill.ecse321.GSSS.service;

import ca.mcgill.ecse321.GSSS.dao.ShiftRepository;
import ca.mcgill.ecse321.GSSS.model.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Services of the shift class
 * 
 * @author Theo Ghanem
 *
 */

@Service
public class ShiftService {

    //Crud Repository

    @Autowired
    ShiftRepository shiftRepository;


    // GET methods

    /**
     * Get the shift associated to an id
     *
     * @author Theo Ghanem
     * @param id id of the shift we are looking for
     * @return the shift associated to that id
     */
    @Transactional
    public Shift getShift(String id) {
        if (id == null || id.trim().length() == 0) {
            throw new IllegalArgumentException("Shift id cannot be empty!");
        }
        Shift shift = shiftRepository.findShiftById(id);
        return shift;
    }

    /**
     * Get the list of shifts associated to a date
     *
     * @author Theo Ghanem
     * @param date The date we are looking for
     * @return a list of the shifts on that date
     */
    @Transactional
    public List<Shift> getShiftsByDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Shift date cannot be empty! ");
        }
        List<Shift> shifts = shiftRepository.findShiftsByDate(date);
        return shifts;
    }


    // CREATE method

    /**
     * Creates a new shift and saves it to the database
     *
     * @author Theo Ghanem
     * @param id The shift's id
     * @param date The shift's date
     * @param startTime The shift's start time
     * @param endTime The shift's end time
     * @return The newly created shift
     */
    @Transactional
    public Shift createShift (String id, Date date, Time startTime, Time endTime ){

        // Input validation
        String error = "";
        if (id == null || id.trim().length() == 0) {
            error = error + "Shift id cannot be empty! ";
        }
        if (date == null) {
            error = error + "Shift date cannot be empty! ";
        }
        if (startTime == null) {
            error = error + "Shift start time cannot be empty! ";
        }
        if (endTime == null) {
            error = error + "Shift end time cannot be empty! ";
        }
        if (endTime != null && startTime != null && endTime.before(startTime)) {
            error = error + "Shift end time cannot be before shift start time!";
        }
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        Shift shift = new Shift();
        shift.setId(id);
        shift.setDate(date);
        shift.setStartTime(startTime);
        shift.setEndTime(endTime);
        shiftRepository.save(shift);
        return shift;
    }

    // DELETE method

    /**
     * Deletes the shift associated to the particular id
     *
     * @author Theo Ghanem
     * @param id id of the shift we want to delete
     */
    @Transactional
    public void deleteShift(String id){
        if (id == null || id.trim().length() == 0) {
            throw new IllegalArgumentException("Shift id cannot be empty!");
        }
        shiftRepository.deleteById(id);
    }


    // MODIFY method

    /**
     * Modifies an existing shift and saves it to the database
     *
     * @author Theo Ghanem
     * @param id The shift's id
     * @param date The shift's date
     * @param startTime The shift's start time
     * @param endTime The shift's end time
     * @return The newly created shift
     */
    @Transactional
    public Shift modifyShift (String id, Date date, Time startTime, Time endTime ){
        // Input validation
        String error = "";
        if (id == null || id.trim().length() == 0) {
            error = error + "Shift id cannot be empty! ";
        }
        if (date == null) {
            error = error + "Shift date cannot be empty! ";
        }
        if (startTime == null) {
            error = error + "Shift start time cannot be empty! ";
        }
        if (endTime == null) {
            error = error + "Shift end time cannot be empty! ";
        }
        if (endTime != null && startTime != null && endTime.before(startTime)) {
            error = error + "Shift end time cannot be before shift start time!";
        }
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        Shift shift = shiftRepository.findShiftById(id);
        shift.setDate(date);
        shift.setStartTime(startTime);
        shift.setEndTime(endTime);
        shiftRepository.save(shift);
        return shift;
    }

}
