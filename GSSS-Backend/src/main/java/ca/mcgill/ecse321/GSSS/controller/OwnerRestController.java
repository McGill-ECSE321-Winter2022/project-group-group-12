package ca.mcgill.ecse321.GSSS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GSSS.dto.AddressDto;
import ca.mcgill.ecse321.GSSS.dto.CustomerDto;
import ca.mcgill.ecse321.GSSS.dto.OwnerDto;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Owner;
import ca.mcgill.ecse321.GSSS.service.AddressService;
import ca.mcgill.ecse321.GSSS.service.OwnerService;

@CrossOrigin(origins = "*")
@RestController
public class OwnerRestController {
	
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private AddressService addressService;
	
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
    
    /**
	   * Method to modify/update the owner
	   * 
	   * @param email Email of the owner to update
	   * @param username Username we want to update
	   * @param password Password we want to update
	   * @param addressDto Address we want to update 
	   * @param disabled Change the disable state of the owner
	   * @return The modified owner as a DTO object
	   * @throws IllegalArgumentException
	   */
	  @PostMapping(value = {"/owner", "/owner/"})
	  public OwnerDto modifyOwner(@RequestParam(name = "username") String username,
	      @RequestParam(name = "password") String password,
	      @RequestParam(name = "address") AddressDto addressDto)
	      throws IllegalArgumentException {
		  Address address = addressService.getAddress(addressDto.getId());
		  Owner owner = ownerService.modifyOwner(username, password, address);
	    return DtoConversion.convertToDto(owner);
	  }
    
}
