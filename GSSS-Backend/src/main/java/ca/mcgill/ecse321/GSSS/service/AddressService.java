package ca.mcgill.ecse321.GSSS.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.GSSS.dao.AddressRepository;
import ca.mcgill.ecse321.GSSS.model.Address;

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
	 * @param id Id of the address
	 * @return The new created Address
	 */
	@Transactional
	public Address createAddress(String fullName, String streetName, Integer streetNumber, String city, String postalCode, String id) {
		Address address = new Address();
		address.setFullName(fullName);
		address.setStreetName(streetName);
		address.setStreetNumber(streetNumber);
		address.setCity(city);
		address.setPostalCode(postalCode);
		address.setId(id);
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
		return addressRepository.findAddressById(id);
	}
	
	/**
	 * Find an address by its City
	 * 
	 * @author Enzo Benoit-Jeannin
	 * @param city City of the address
	 * @return The found address
	 */
	@Transactional
	public List<Address> getAddressByCity(String city) {
		return addressRepository.findAddressesByCity(city);
	}
	
	/**
	 * Find an address by its StreetName
	 * 
	 * @author Enzo Benoit-Jeannin
	 * @param streetName Name of the street of the address
	 * @return The found address
	 */
	@Transactional
	public List<Address> getAddressByStreetName(String streetName) {
		return addressRepository.findAddressesByStreetName(streetName);
	}
	
	/**
	 * Finds all the addresses stored in the database
	 * 
	 * @author Enzo Benoit-Jeannin
	 * @return A list of all the address stored in the database
	 */
	@Transactional
	public List<Address> getAllAddress(){
		return HelperClass.toList(addressRepository.findAll());
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
		Address address = addressRepository.findAddressById(id);
		address.setFullName(fullName);
		address.setStreetName(streetName);
		address.setStreetNumber(streetNumber);
		address.setCity(city);
		address.setPostalCode(postalCode);
		addressRepository.save(address);
		return address;
	}
}
