package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.dto.ShiftDto;
import ca.mcgill.ecse321.GSSS.model.Shift;
import ca.mcgill.ecse321.GSSS.service.ShiftService;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller methods of the shift class. Please note there are no create / delete methods because
 * those are included in the employee controller. In other words, there is never a need to create a
 * shift without adding it to an employee.
 *
 * @author Enzo Benoit-Jeannin
 * @author Wassim Jabbour
 */
@CrossOrigin(origins = "*")
@RestController
public class ShiftRestController {

  @Autowired
  private ShiftService shiftService;

  /**
   * Controller method to modify a shift
   *
   * @param shiftId The id of the shift to modify
   * @param date The new date of the shift
   * @param startTime The new start time of the shift
   * @param endTime The new end time of the shift
   * @return The Dto corresponding to the modified object
   * @throws IllegalArgumentException
   * @throws NoSuchElementException
   * @author Enzo Benoit-Jeannin
   * @author Wassim Jabbour
   */
  @PostMapping(value = {"/shift/modify/{shiftid}", "/shift/modify/{shiftid}/"})
  public ShiftDto modifyShift(@PathVariable(name = "shiftid") String shiftId,
      @RequestParam(name = "date") Date date,
      @RequestParam(name = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME,
          pattern = "HH:mm") LocalTime startTime,
      @RequestParam(name = "endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME,
          pattern = "HH:mm") LocalTime endTime)
      throws IllegalArgumentException, NoSuchElementException {

    Shift shift =
        shiftService.modifyShift(shiftId, date, Time.valueOf(startTime), Time.valueOf(endTime));	// Modifies the shift using the given parameters

    return DtoUtility.convertToDto(shift);	// returns the modified shift in DTO form
  }
}
