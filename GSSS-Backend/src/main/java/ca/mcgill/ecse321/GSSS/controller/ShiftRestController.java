package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.dto.ShiftDto;
import ca.mcgill.ecse321.GSSS.model.Shift;
import ca.mcgill.ecse321.GSSS.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*")
@RestController
public class ShiftRestController {

  @Autowired private ShiftService shiftService;

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
   */
  @PostMapping(value = {"/shift/modify/{shiftid}", "/shift/modify/{shiftid}/"})
  public ShiftDto modifyShift(
      @PathVariable(name = "shiftid") String shiftId,
      @RequestParam(name = "date") Date date,
      @RequestParam(name = "startTime")
          @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm")
          LocalTime startTime,
      @RequestParam(name = "endTime")
          @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm")
          LocalTime endTime)
      throws IllegalArgumentException, NoSuchElementException {

    Shift shift =
        shiftService.modifyShift(shiftId, date, Time.valueOf(startTime), Time.valueOf(endTime));

    return DtoUtility.convertToDto(shift);
  }
}
