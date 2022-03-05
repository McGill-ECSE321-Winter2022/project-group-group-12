package ca.mcgill.ecse321.GSSS.controller;


import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.GSSS.dto.BusinessHourDto;
import ca.mcgill.ecse321.GSSS.dto.ItemCategoryDto;
import ca.mcgill.ecse321.GSSS.dto.ItemDto;
import ca.mcgill.ecse321.GSSS.model.BusinessHour;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;
import ca.mcgill.ecse321.GSSS.model.Weekday;
import ca.mcgill.ecse321.GSSS.service.BusinessHourService;
import ca.mcgill.ecse321.GSSS.service.ItemCategoryService;
import ca.mcgill.ecse321.GSSS.service.ItemService;


@CrossOrigin(origins = "*")
@RestController
public class GsssRestController {

  // Will merge everything here later
}
