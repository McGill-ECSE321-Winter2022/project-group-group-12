package ca.mcgill.ecse321.GSSS.dao;
import java.util.List;
import org.hibernate.annotations.Where;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GSSS.model.Address;

/**
 * 
 * To find the addresses in the system
 *
 */

public interface AddressRepository extends CrudRepository<Address, String> {

  /**
   * 
   * this method finds an address based on its unique id
   * @author Wassim Jabbour
   * @param id unique id given to the address
   * @return address
   */
  Address findAddressById(String id);
  
  /**
   * this method finds a list of addresses based on the city they are in
   * 
   * @author Habib Jarweh
   * @param city city {@link Where} the addresses are
   * @return list of addresses
   */
  List<Address> findAddressesByCity(String city);
  
  /**
   * 
   * this method returns a list of addresses based on their street
   * @author Wassim Jabbour
   * @param streetName streetname where the addresses are
   * @return list of addresses
   * 
   */
  List<Address> findAddressesByStreetName(String streetName);

}
