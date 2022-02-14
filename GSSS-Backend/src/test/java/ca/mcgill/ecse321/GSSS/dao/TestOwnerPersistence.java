package ca.mcgill.ecse321.GSSS.dao;

import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Owner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestOwnerPersistence {

    @Autowired
    private OwnerRepository ownerRepository;
    private AddressRepository addressRepository;

    //Initialize variables that will be used to successfully create a complete owner
    String username = "OwnerDude";
    String email = "owner@email.com";
    String password = "coolowner";
    String fullName = "Jeff Owner";
    String streetName = "Boss";
    String city = "Montreal";
    int streetNumber = 40;
    String postalCode = "W2S L0S";
    String addressId = UUID.randomUUID().toString();

    /**
     * Testing the persistence and loading for the Owner Repository.
     * This tests the findOwnerByEmail method
     * @author 
     */
    @Test
    public void testPersistAndLoadOwnerByEmail(){
        Address address = new Address();
        address.setFullName(fullName);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setId(addressId);

        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setUsername(username);
        owner.setPassword(password);
        HashSet<Address> setOfAddresses = new HashSet<>();
        owner.setAddresses(setOfAddresses);

        addressRepository.save(address);
        ownerRepository.save(owner);
        
        owner=null;

        owner = ownerRepository.findOwnerByEmail(email);

        assertNotNull(owner);
        assertEquals(owner.getUsername(), username);
        assertEquals(owner.getEmail(), email);
        assertEquals(owner.getPassword(), password);
        assertEquals(owner.getAddresses(), setOfAddresses);

    }

    /**
     * Testing the persistence and loading for the Owner Repository.
     * This tests the findOwnerByAddress method
     * @author
     */
    @Test
    public void testPersistAndLoadOwnerByAddress(){
        Address address = new Address();
        address.setFullName(fullName);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setId(addressId);

        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setUsername(username);
        owner.setPassword(password);
        HashSet<Address> setOfAddresses = new HashSet<>();
        owner.setAddresses(setOfAddresses);

        addressRepository.save(address);
        ownerRepository.save(owner);

        owner=null;

        owner = ownerRepository.findOwnerByAddresses(address);

        assertNotNull(owner);
        assertEquals(owner.getUsername(), username);
        assertEquals(owner.getEmail(), email);
        assertEquals(owner.getPassword(), password);
        assertEquals(owner.getAddresses(), setOfAddresses);

    }
        
}
