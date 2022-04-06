package ca.mcgill.ecse321.GSSS.dao;

import ca.mcgill.ecse321.GSSS.model.Address;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * To find the addresses in the system
 *
 * @author Wassim Jabbour
 * @author Habib Jarweh
 */
public interface AddressRepository extends CrudRepository<Address, String> {

  /**
   * this method finds an address based on its unique id
   *
   * @param id unique id given to the address
   * @return address we want to find given the id
   * @author Wassim Jabbour
   */
  Address findAddressById(String id);

  /**
   * this method finds a list of addresses based on the city they are in
   *
   * @param city city where the addresses are
   * @return list of addresses we want to find given the city
   * @author Habib Jarweh
   */
  List<Address> findAddressesByCity(String city);

  /**
   * this method returns a list of addresses based on their street
   *
   * @param streetName streetname where the addresses are
   * @return list of addresses we want to find given the street name
   * @author Wassim Jabbour
   */
  List<Address> findAddressesByStreetName(String streetName);
}
