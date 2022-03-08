package ca.mcgill.ecse321.GSSS.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.GSSS.dao.AddressRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
	
	// CRUD Repositories
	
	@Autowired
	AddressRepository addressRepository;
	
	// CREATE Method
	/**
	 * Method to create a new Address
	 *
	 * @author Enzo Benoit-Jeannin
	 * @param fullName FullName associated to the address
	 * @param streetName Name of the street of the address
	 * @param streetNumber Street number of the address
	 * @param city City of the address
	 * @param postalCode PostalCode of the address
	 * @return The new created Address
	 */
	@Transactional
	public Address createAddress(String fullName, String streetName, Integer streetNumber, String city, String postalCode) {
		// Input validation
	    String error = "";
	    if(fullName == null || fullName.trim().length() == 0)
	      error += "Address full name cannot be empty! ";
	    if(streetName == null || streetName.trim().length() == 0)
	      error += "Address street name cannot be empty! ";
	    if(streetNumber == null)
	      error += "Address street number cannot be empty! ";
	    if(city == null || city.trim().length() == 0)
	      error += "Address city cannot be empty! ";
	    if(postalCode == null || postalCode.trim().length() == 0)
		      error += "Address postal code cannot be empty! ";
		if (!Utility.isPostalCodeValid(postalCode))
			error += "Postal Code is not valid! ";
	    if(error.length() > 0)
	      throw new IllegalArgumentException(error);
		
		Address address = new Address();
		address.setFullName(fullName);
		address.setStreetName(streetName);
		address.setStreetNumber(streetNumber);
		address.setCity(city);
		address.setPostalCode(postalCode);
		address.setId(UUID.randomUUID().toString());
		addressRepository.save(address);
		return address;
	}
	
	// GET Methods
	
	/**
	 * Find an address by its ID
	 * 
	 * @author Enzo Benoit-Jeannin
	 * @param id ID of the address
	 * @return The found address
	 */
	@Transactional
	public Address getAddress(String id) {
		// Input validation
	    if(id == null || id.trim().length() == 0)
	      throw new IllegalArgumentException("Address id cannot be empty!");
	    
		Address address = addressRepository.findAddressById(id);
		
		if (address == null) {
			throw new NoSuchElementException("No address with id "+ id+ " exits!");
		}
		return address;
	}
	
	
	/**
	 * Finds all the addresses stored in the database
	 * 
	 * @author Enzo Benoit-Jeannin
	 * @return A list of all the address stored in the database
	 */
	@Transactional
	public List<Address> getAllAddresses(){
		return Utility.toList(addressRepository.findAll());
	}
	
	// DELETE Method
	
	/**
	 * Deletes an address using its id
	 * 
	 * @auhtor Enzo Benoit-Jeannin
	 * @param id The Id of the address to be deleted
	 */
	@Transactional
	public void deleteAddress(String id) {
		// Input validation
	    if(id == null || id.trim().length() == 0)
	      throw new IllegalArgumentException("Address id cannot be empty!");
	    
		addressRepository.deleteById(id);
	}
	
	// OTHER Methods
	
	/**
	 * Method that modifies a stored address, we pass all arguments
	 * 
	 * @author Enzo Benoit-Jeannin
	 * @param fullName New full name associated with the Address
	 * @param streetName New street name of the address
	 * @param streetNumber New street number of the address
	 * @param city New city of the address
	 * @param postalCode New postal code of the address
	 * @param id Id of the address to modify
	 * @return New saved address
	 */
	@Transactional
	public Address modifyAddress(String fullName, String streetName, Integer streetNumber, String city, String postalCode, String id) {
		
		// Input validation
	    String error = "";
	    if(fullName == null || fullName.trim().length() == 0)
	      error += "Address full name cannot be empty! ";
	    if(streetName == null || streetName.trim().length() == 0)
	      error += "Address street name cannot be empty! ";
	    if(streetNumber == null)
	      error += "Address street number cannot be empty! ";
	    if(city == null || city.trim().length() == 0)
	      error += "Address city cannot be empty! ";
	    if(postalCode == null || postalCode.trim().length() == 0)
		      error += "Address postal code cannot be empty! ";
	    if(id == null || id.trim().length() == 0)
		      error += "Address id cannot be empty! ";
		if (!Utility.isPostalCodeValid(postalCode))
			error += "Postal Code is not valid! ";
	    if(error.length() > 0)
	      throw new IllegalArgumentException(error);
		
		Address address = getAddress(id);
		address.setFullName(fullName);
		address.setStreetName(streetName);
		address.setStreetNumber(streetNumber);
		address.setCity(city);
		address.setPostalCode(postalCode);
		addressRepository.save(address);
		return address;
	}
}
