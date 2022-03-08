package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.dto.AddressDto;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*")
@RestController
public class AddressRestController {

    @Autowired
    private AddressService addressService;

    /**
     * Method to return the address associated to a certain id
     *
     * @author Theo Ghanem
     * @param id id of the address we want to get
     * @return an address
     * @throws IllegalArgumentException, NoSuchElementException
     */
    @GetMapping(value = {"/address/{id}", "/address/{id}/"})
    public AddressDto getAddress(@PathVariable("id") String id) throws NoSuchElementException, IllegalArgumentException {
        Address addressDto = addressService.getAddress(id);
        return DtoConversion.convertToDto(addressDto);
    }

    /**
     * Method to get all the addresses
     *
     * @author Theo Ghanem
     * @return a list of all the addresses
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/addresses", "/addresses/"})
    public List<AddressDto> getAllAddress() {
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address address : addressService.getAllAddress()) {
            addressDtos.add(DtoConversion.convertToDto(address));
        }
        return addressDtos;
    }

    /**
     * Method to create a new address
     *
     * @author Theo Ghanem
     * @param fullName FullName associated to the address
     * @param streetName Name of the street of the address
     * @param streetNumber Street number of the address
     * @param city City of the address
     * @param postalCode PostalCode of the address
     * @return The new created Address
     * @throws IllegalArgumentException
     */
    @PostMapping(value = {"/address", "/address/"})
    public AddressDto createAddress(@RequestParam(name = "fullName") String fullName,
                              @RequestParam(name = "streetName") String streetName,
                              @RequestParam(name = "streetNumber") Integer streetNumber,
                              @RequestParam(name = "city") String city,
                              @RequestParam(name = "postalCode") String postalCode)
                              throws IllegalArgumentException {
        Address address = addressService.createAddress(fullName,streetName,streetNumber,city,postalCode);
        return DtoConversion.convertToDto(address);
    }

    /**
     * Method to modify/update an address
     *
     * @author Theo Ghanem
     * @param fullName New full name associated with the Address
     * @param streetName New street name of the address
     * @param streetNumber New street number of the address
     * @param city New city of the address
     * @param postalCode New postal code of the address
     * @param id Id of the address to modify
     * @return New saved address
     * @throws IllegalArgumentException
     */
    @PostMapping(value = {"/address/{id}", "/address/{id}/"})
    public AddressDto modifyAddress(@PathVariable("id") String id,
                                 @RequestParam(name = "fullName") String fullName,
                                 @RequestParam(name = "streetName") String streetName,
                                 @RequestParam(name = "streetNumber") Integer streetNumber,
                                 @RequestParam(name = "city") String city,
                                 @RequestParam(name = "postalCode") String postalCode)
            throws IllegalArgumentException {
        Address address = addressService.modifyAddress(fullName,streetName,streetNumber,city,postalCode, id);
        return DtoConversion.convertToDto(address);
    }

    @DeleteMapping(value = {"/address/{id}", "/address/{id}/"})
    public void deleteAddress(@PathVariable("id") String id) throws IllegalArgumentException {
        addressService.deleteAddress(id);
    }






}
