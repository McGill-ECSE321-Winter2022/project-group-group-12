package ca.mcgill.ecse321.GSSS.service;

import ca.mcgill.ecse321.GSSS.dao.OwnerRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Owner;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
   * @return The owner.
   * @author Philippe Sarouphim Hochar.
   */
  @Transactional
  public Owner getOwner() {
    // since we only have one owner, then we return the first element of the list returned by findAll()
    Iterator<Owner> iterator = ownerRepository.findAll().iterator();
    if (iterator.hasNext()) {
      return iterator.next();
    }
    return null;
  }

  /**
   * This service method creates an owner if they don't exist and throws an error if they already do
   * since we can only have 1 owner at all times
   *
   * @param username The username of the owner
   * @param email The email of the owner
   * @param password The password of the owner
   * @param address The address of the owner
   * @return The created Owner
   */
  @Transactional
  public Owner createOwner(String username, String email, String password, Address address) {
    // Input validation
    String error = "";
    if (username == null || username.trim().length() == 0) {
      error += "Owner username cannot be empty! ";
    }
    if (email == null || email.trim().length() == 0) {
      error += "Owner email cannot be empty! ";
    }
    if (!Utility.isEmailValid(email)) {
      error += "Email not valid! ";
    }
    if (password == null || password.length() < 6 || password.trim().length() == 0) {
      error += "Password has to be at least 6 characters! ";
    }
    if (address == null) {
      error += "Owner address cannot be null! ";
    }

    if (error.length() > 0) {
      throw new IllegalArgumentException(error);
    }

    // throw exception if owner already exists in the system
    Owner owner = getOwner();
    if (owner != null) {
      throw new IllegalArgumentException("Owner already exists in the system!");
    }

    owner = new Owner();
    owner.setEmail(email);
    owner.setUsername(username);
    owner.setSalt(Utility.getSalt());
    owner.setPassword(Utility.hashAndSaltPassword(password, owner.getSalt()));
    owner.setAddress(address);
    ownerRepository.save(owner);
    return owner;
  }

  /**
   * This service updates the owner based on the inputs if the owner exists
   *
   * @param username Username.
   * @param password Password.
   * @param address Address.
   * @return The updated owner
   * @author Enzo Benoit-Jeannin
   */
  @Transactional
  public Owner modifyOwner(String username, String password, Address address) {

    // Input validation
    String error = "";
    if (username == null || username.trim().length() == 0) {
      error += "Owner username cannot be empty! ";
    }
    if (password == null || password.length() < 6 || password.trim().length() == 0) {
      error += "Password has to be at least 6 characters! ";
    }
    if (address == null) {
      error += "Owner address cannot be null! ";
    }
    if (error.length() > 0) {
      throw new IllegalArgumentException(error);
    }

    // throw exception if owner we want to modify does not exist
    Owner owner = getOwner();
    if (owner == null) {
      throw new NoSuchElementException("Owner doesn't exist!");
    }

    owner.setAddress(address);
    owner.setUsername(username);
    owner.setPassword(password);
    ownerRepository.save(owner);
    return owner;
  }

  /**
   * Modifies the system information held in the owner
   *
   * @param city The city the store is in
   * @param outOfTownFee The fee for out of town deliveries
   * @return The modified owner
   * @author Wassim Jabbour
   */
  @Transactional
  public Owner modifySystemInformation(String city, double outOfTownFee) {

    // Input validation
    if (city == null || city.trim().length() == 0) {
      throw new IllegalArgumentException("City name cannot be empty!");
    }

    // throw exception if owner does not exist
    Owner owner = getOwner();
    if (owner == null) {
      throw new NoSuchElementException("Owner doesn't exist!");
    }

    owner.setStoreCity(city);
    owner.setOutOfTownDeliveryFee(outOfTownFee);
    ownerRepository.save(owner);
    return owner;
  }

}
