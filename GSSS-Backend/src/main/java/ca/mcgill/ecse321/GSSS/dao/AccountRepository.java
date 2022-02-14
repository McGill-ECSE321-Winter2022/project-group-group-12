package ca.mcgill.ecse321.GSSS.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Account;

/**
 * 
 * @author Wassim Jabbour
 *
 */
public interface AccountRepository extends CrudRepository<Account, String> {
  
  Account findAccountByEmail(String email);

  List<Account> findAccountsByUsername(String username);

  Account findAccountByAddress(Address address);
  
  List<Account> findAccountByDisabled(boolean disabled);
  
}
