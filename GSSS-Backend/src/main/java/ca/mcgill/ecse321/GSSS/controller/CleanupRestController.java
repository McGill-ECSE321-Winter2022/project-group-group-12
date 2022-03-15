package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.service.CleanupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class CleanupRestController {

  @Autowired CleanupService cleanupService;

  @DeleteMapping(value = {"/cleanupdatabase", "/cleanupdatabase/"})
  public void cleanupDatabase() {
    cleanupService.cleanupDatabase();
  }
}
