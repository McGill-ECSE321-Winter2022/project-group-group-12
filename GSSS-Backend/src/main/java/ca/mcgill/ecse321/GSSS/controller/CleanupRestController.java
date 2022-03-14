package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.dto.BusinessHourDto;
import ca.mcgill.ecse321.GSSS.model.Weekday;
import ca.mcgill.ecse321.GSSS.service.CleanupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@CrossOrigin(origins = "*")
@RestController
public class CleanupRestController {

    @Autowired
    CleanupService cleanupService;

    @DeleteMapping(value = {"/cleanupdatabase", "/cleanupdatabase/"})
    public void cleanupDatabase() {
        cleanupService.cleanupDatabase();
    }
}
