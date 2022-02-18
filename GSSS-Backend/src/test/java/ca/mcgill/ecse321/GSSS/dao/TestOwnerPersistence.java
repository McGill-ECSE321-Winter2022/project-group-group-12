package ca.mcgill.ecse321.GSSS.dao;

import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Owner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestOwnerPersistence {

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private AddressRepository addressRepository;

	/**
	 * This method is executed after each test and clears the concerned tables.
	 * 
	 * @author Enzo Benoit-Jeannin
	 */
	@AfterEach
	public void clearDatabase() {
		ownerRepository.deleteAll();
		addressRepository.deleteAll();
	}

	/**
	 * Method to create customer Address object from the given parameters.
	 * 
	 * @author Enzo Benoit-Jeannin
	 * 
	 * @param fullName     full name of the owner's address
	 * @param streetName   name of the owner's street
	 * @param streetNumber owner's street number
	 * @param city         owner's city
	 * @param postalCode   owner's postal code
	 * @param iD           owner's address' iD
	 * @return address save the address in repository and return it
	 */
	private Address persistAddress(String fullName, String streetName, int streetNumber, String city, String postalCode,
			String iD) {
		Address address = new Address();
		address.setFullName(fullName);
		address.setStreetName(streetName);
		address.setStreetNumber(streetNumber);
		address.setCity(city);
		address.setPostalCode(postalCode);
		address.setId(iD);

		addressRepository.save(address);

		return address;
	}

	/**
	 * Method to create the customer object given the parameters.
	 * 
	 * @author Enzo Benoit-Jeannin
	 * 
	 * @param email    email of the owner
	 * @param username username of the owner
	 * @param password password of the owner
	 * @param address  address of the owner
	 * @param disabled boolean specifying if the owner is disabled
	 * @return customer
	 */
	private Owner persist(String email, String username, String password, Address address, boolean disabled) {
		Owner owner = new Owner();
		owner.setEmail(email);
		owner.setUsername(username);
		owner.setPassword(password);
		owner.setAddress(address);
		owner.setDisabled(disabled);

		ownerRepository.save(owner);
		return owner;
	}

	/**
	 * Method asserts whether the given customers given match.
	 * 
	 * @author Enzo Benoit-Jeannin
	 * 
	 * @param expected Expected owner
	 * @param actual   Actual owner
	 * 
	 */
	private void verify(Owner expected, Owner actual) {
		assertNotNull(actual);
		assertEquals(expected.getUsername(), actual.getUsername());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getPassword(), actual.getPassword());
		assertEquals(expected.getAddress(), actual.getAddress());
		assertEquals(expected.isDisabled(), actual.isDisabled());

	}

	/**
	 * Testing the persistence and loading for the Owner Repository. This tests the
	 * findOwnerByEmail method
	 * 
	 * @author Enzo Benoit-Jeannin
	 */
	@Test
	public void testPersistAndLoadOwnerByEmail() {
		String addressId = UUID.randomUUID().toString();
		Address address = persistAddress("Jeff Bezos", "Boss Avenue", 40, "Montreal", "W2S L0S", addressId);

		String email = "jeff@gmail.com";
		Owner expected = persist(email, "jeffrey", "coolOwner", address, false);
		persist("SteveJobs@gmail.com", "steve", "poorOwner", address, false);

		Owner actual = ownerRepository.findOwnerByEmail(email);
		verify(expected, actual);
	}

	/**
	 * Testing the persistence and loading for the Owner Repository. This tests the
	 * findOwnerByAddress method
	 * 
	 * @author Enzo Benoit-Jeannin
	 */
	@Test
	public void testPersistAndLoadOwnerByAddress() {
		String addressId1 = UUID.randomUUID().toString();
		String addressId2 = UUID.randomUUID().toString();

		Address address1 = persistAddress("Jeff Bezos", "Boss Avenue", 40, "Montreal", "W2S L0S", addressId1);
		Address address2 = persistAddress("Steve Jobs", "Poor Boss Avenue", 43, "Montreal", "W2S L0S", addressId2);

		Owner expected = persist("jeff@gmail.com", "jeffrey", "coolOwner", address1, false);
		persist("SteveJobs@gmail.com", "steve", "poorOwner", address2, false);

		Owner actual = ownerRepository.findOwnerByAddress(address1);
		verify(expected, actual);

	}

}
