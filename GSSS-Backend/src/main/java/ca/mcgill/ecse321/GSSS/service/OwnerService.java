package ca.mcgill.ecse321.GSSS.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.GSSS.dao.OwnerRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Owner;

/**
 * This is a service class for all owner-related services.
 * 
 * @author Philippe Sarouphim Hochar.
 */
@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    /**
     * This service gets the single owner.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @return The owner.
     */
    @Transactional
    public Owner getOwner(){
        return ownerRepository.findAll().iterator().next();
    }
    
    /**
     * This service updates the owner based on the inputs if they are not null.
     * 
     * @author Enzo Benoit-Jeannin
     * 
     * @param username Username.
     * @param password Password.
     * @param address Address.
     * @return The updated owner
     */
    @Transactional
    public Owner modifyOwner(String username, String password, Address address) {

		// Input validation
	    String error = "";
	    if(username == null || username.trim().length() == 0)
	      error += "Owner username cannot be empty! ";
	    if(password == null || password.trim().length() == 0)
		  error += "Owner password cannot be empty! ";
	    if(address == null)
	      error += "Owner address cannot be null! ";
	    if(error.length() > 0)
	      throw new IllegalArgumentException(error);
		
		Owner owner = getOwner();
		owner.setAddress(address);
		owner.setUsername(username);
		owner.setPassword(password);
		ownerRepository.save(owner);
		return owner;
    }
}
