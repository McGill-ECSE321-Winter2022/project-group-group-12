package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.dto.AddressDto;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/address/{id}", "/address/{id}/"})
    public AddressDto getAddress(@PathVariable("id") String id) throws IllegalArgumentException {
        Address addressDto = addressService.getAddress(id);
        return DtoConversion.convertToDto(addressDto);
    }

    /**
     * Method to return a list of all the addresses in a certain city
     *
     * @author Theo Ghanem
     * @param city
     * @return a list of all the addresses in city
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/address/{city}", "/address/{city}/"})
    public List<AddressDto> getAddressByCity(@PathVariable("city") String city)  throws IllegalArgumentException {
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address address : AddressService.getAddressByCity(city)) {
            addressDtos.add(DtoConversion.convertToDto(address));
        }
        return addressDtos;
    }

    /**
     * Method to return a list of all the addresses in a certain street
     *
     * @author Theo Ghanem
     * @param streetName name of the street
     * @return a list of all the addresses in a certain street
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/address/{streetName}", "/address/{streetName}/"})
    public List<AddressDto> getAddressByStreetName(@PathVariable("streetName") String streetName)  throws IllegalArgumentException {
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address address : AddressService.getAddressByStreetName(streetName)) {
            addressDtos.add(DtoConversion.convertToDto(address));
        }
        return addressDtos;
    }

    /**
     * Method to get all the addresses
     *
     * @author Theo Ghanem
     * @return a list of all the addresses
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/address", "/address/"})
    public List<AddressDto> getAllAddress()  throws IllegalArgumentException {
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address address : AddressService.getAllAddress()) {
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
