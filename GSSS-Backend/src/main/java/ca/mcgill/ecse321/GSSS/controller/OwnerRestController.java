package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.dto.OwnerDto;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Owner;
import ca.mcgill.ecse321.GSSS.service.AddressService;
import ca.mcgill.ecse321.GSSS.service.OwnerService;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller methods for the owner class
 *
 * @author Wassim Jabbour
 * @author Enzo Benoit-Jeannin
 */
@CrossOrigin(origins = "*")
@RestController
public class OwnerRestController {

  @Autowired
  private OwnerService ownerService;

  @Autowired
  private AddressService addressService;

  /**
   * Method to create an owner the first time (Returns the existing owner's DTO otherwise)
   *
   * @param username  The owner's username
   * @param email     The owner's email
   * @param password  The owner's password
   * @param addressId The address' ID
   * @return The created/retrieved owner DTO
   * @throws IllegalArgumentException If the inputs are invalid
   * @author Enzo Benoit-Jeannin
   */
  @PostMapping(value = {"/createowner", "/createowner/"})
  public OwnerDto createOwner(
      @RequestParam(name = "username") String username,
      @RequestParam(name = "email") String email,
      @RequestParam(name = "password") String password,
      @RequestParam(name = "address") String addressId)
      throws IllegalArgumentException {
    Owner owner = null;
    try {
      Address address = addressService.getAddress(addressId);
      owner = ownerService.createOwner(username, email, password, address);
    } catch (IllegalArgumentException e) {
      if (e.getMessage().contains("Owner already exists in the system!")) {
        owner = ownerService.getOwner();
      }
    }
    return DtoUtility.convertToDto(owner);
  }

  /**
   * Method to get the owner DTO
   *
   * @return DTO of the employee corresponding to the email.
   * @author Enzo Benoit-Jeannin
   */
  @GetMapping(value = {"/owner", "/owner/"})
  public OwnerDto getOwner() {
    return DtoUtility.convertToDto(ownerService.getOwner());
  }

  /**
   * Method to modify/update the owner if they exist (Throws an error otherwise)
   *
   * @param username  Username we want to update
   * @param password  Password we want to update
   * @param addressId Address we want to update
   * @return The modified owner as a DTO object
   * @throws IllegalArgumentException If the inputs are invalid
   * @throws NoSuchElementException   If the owner doesn't exist
   * @author Enzo Benoit-Jeannin
   */
  @PostMapping(value = {"/owner", "/owner/"})
  public OwnerDto modifyOwner(
      @RequestParam(name = "username") String username,
      @RequestParam(name = "password") String password,
      @RequestParam(name = "address") String addressId)
      throws IllegalArgumentException, NoSuchElementException {
    Address address = addressService.getAddress(addressId);
    Owner owner = ownerService.modifyOwner(username, password, address);
    return DtoUtility.convertToDto(owner);
  }

  /**
   * Method to modify the system information
   *
   * @param city The new city
   * @param fee  The new fee
   * @return The new owner with modified info
   * @author Wassim Jabbour
   */
  @PostMapping(value = {"/info", "/info/"})
  public OwnerDto modifySystemInformation(
      @RequestParam(name = "city") String city, @RequestParam(name = "fee") double fee)
      throws IllegalArgumentException, NoSuchElementException {

    Owner owner = ownerService.modifySystemInformation(city, fee);
    return DtoUtility.convertToDto(owner);
  }
}
