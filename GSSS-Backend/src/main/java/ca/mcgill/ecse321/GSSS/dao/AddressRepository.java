package ca.mcgill.ecse321.GSSS.dao;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GSSS.model.Address;

public interface AddressRepository extends CrudRepository<Address, String> {

  /**
   * 
   * @author Wassim Jabbour
   * @param id
   * @return
   */
  Address findAddressById(String id);
  
  /**
   * 
   * @author Habib Jarweh
   */
  List<Address> findAddressesByCity(String city);
  
  /**
   * 
   * @author Wassim Jabbour
   */
  List<Address> findAddressesByStreetName(String streetName);

}
