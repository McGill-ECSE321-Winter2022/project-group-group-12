package ca.mcgill.ecse321.GSSS.dao;

import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Owner;
import org.springframework.data.repository.CrudRepository;

/**
 * To find the owner of the system. We did not write the findOwnerByDisabled method because the
 * system only has one owner.
 *
 * @author Wassim Jabbour
 */
public interface OwnerRepository extends CrudRepository<Owner, String> {

  /**
   * Find the owner by his email
   *
   * @param email The owner's email
   * @return The owner we want to find
   * @author Wassim Jabbour
   */
  Owner findOwnerByEmail(String email);

  /**
   * Find the owner by his address
   *
   * @param address The owner's address
   * @return The owner we want to find
   * @author Wassim Jabbour
   */
  Owner findOwnerByAddress(Address address);
}
