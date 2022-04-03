package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.dto.AddressDto;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.service.AddressService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller methods for the address class
 *
 * @author Wassim Jabbour
 */
@CrossOrigin(origins = "*")
@RestController
public class AddressRestController {

  @Autowired
  private AddressService addressService;

  /**
   * Method to return the address associated to a certain id
   *
   * @param id id of the address we want to get
   * @return an address
   * @throws IllegalArgumentException, NoSuchElementException
   * @author Theo Ghanem
   */
  @GetMapping(value = {"/address/{id}", "/address/{id}/"})
  public ResponseEntity<?> getAddress(@PathVariable("id") String id){
	try {
	    Address addressDto = addressService.getAddress(id);
	    return ResponseEntity.ok(DtoUtility.convertToDto(addressDto));
	}catch(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage()); 
	}
  }

  /**
   * Method to get all the addresses
   *
   * @return a list of all the addresses
   * @throws IllegalArgumentException
   * @author Theo Ghanem
   */
  @GetMapping(value = {"/addresses", "/addresses/"})
  public List<AddressDto> getAllAddress() {
    List<AddressDto> addressDtos = new ArrayList<>();
    for (Address address : addressService.getAllAddresses()) {
      addressDtos.add(DtoUtility.convertToDto(address));
    }
    return addressDtos;
  }

  /**
   * Method to create a new address
   *
   * @param fullName FullName associated to the address
   * @param streetName Name of the street of the address
   * @param streetNumber Street number of the address
   * @param city City of the address
   * @param postalCode PostalCode of the address
   * @return The new created Address
   * @throws IllegalArgumentException
   * @author Theo Ghanem
   */
  @PostMapping(value = {"/address", "/address/"})
  public ResponseEntity<?> createAddress(@RequestParam(name = "fullName") String fullName,
      @RequestParam(name = "streetName") String streetName,
      @RequestParam(name = "streetNumber") Integer streetNumber,
      @RequestParam(name = "city") String city,
      @RequestParam(name = "postalCode") String postalCode) throws IllegalArgumentException {
	  try{
		  Address address = addressService.createAddress(fullName, streetName, streetNumber, city, postalCode);
		 return ResponseEntity.ok(DtoUtility.convertToDto(address));
	  }catch(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage()); 
	  }
  }

  /**
   * Method to modify/update an address
   *
   * @param fullName New full name associated with the Address
   * @param streetName New street name of the address
   * @param streetNumber New street number of the address
   * @param city New city of the address
   * @param postalCode New postal code of the address
   * @param id Id of the address to modify
   * @return New saved address
   * @throws IllegalArgumentException
   * @author Theo Ghanem
   */
  @PostMapping(value = {"/address/{id}", "/address/{id}/"})
  public ResponseEntity<?> modifyAddress(@PathVariable("id") String id,
      @RequestParam(name = "fullName") String fullName,
      @RequestParam(name = "streetName") String streetName,
      @RequestParam(name = "streetNumber") Integer streetNumber,
      @RequestParam(name = "city") String city,
      @RequestParam(name = "postalCode") String postalCode) throws IllegalArgumentException {
   try {
	  Address address =
        addressService.modifyAddress(fullName, streetName, streetNumber, city, postalCode, id);
    return ResponseEntity.ok(DtoUtility.convertToDto(address));
   }catch(Exception e) {
	   return ResponseEntity.badRequest().body(e.getMessage());
   }
  }

  @DeleteMapping(value = {"/address/{id}", "/address/{id}/"})
  public void deleteAddress(@PathVariable("id") String id) throws IllegalArgumentException {
    addressService.deleteAddress(id);
  }
}
