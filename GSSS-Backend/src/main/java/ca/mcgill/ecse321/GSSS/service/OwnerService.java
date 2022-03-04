package ca.mcgill.ecse321.GSSS.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.GSSS.dao.OwnerRepository;
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

}
