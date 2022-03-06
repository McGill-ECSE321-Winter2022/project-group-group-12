package ca.mcgill.ecse321.GSSS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GSSS.dto.OwnerDto;
import ca.mcgill.ecse321.GSSS.service.OwnerService;

@CrossOrigin(origins = "*")
@RestController
public class OwnerRestController {
	
	@Autowired
	private OwnerService ownerService;
	
	 /**
     * Method to get the owner DTO
     * 
     * @author Enzo Benoit-Jeannin
     * 
     * @param email Email of the employee to fetch.
     * @return DTO of the employee corresponding to the email.
     */
    @GetMapping(value = { "/owner", "/owner/" })
    public OwnerDto getOwner(){
        return DtoConversion.convertToDto(ownerService.getOwner());
    }
    
    
}
