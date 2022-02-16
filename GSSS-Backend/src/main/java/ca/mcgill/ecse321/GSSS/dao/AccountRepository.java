package ca.mcgill.ecse321.GSSS.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GSSS.model.Account;
import ca.mcgill.ecse321.GSSS.model.Address;

/**
 * 
 * Repository that has the query methods for the Account class
 * 
 * @author Wassim Jabbour
 *
 */
public interface AccountRepository extends CrudRepository<Account, String> {

  /**
   * 
   * this method finds an account based on the email associated to it
   * 
   * @author Wassim Jabbour
   * @param email email of the account
   * @return account
   */
  Account findAccountByEmail(String email);

  /**
   * this method finds a list of accounts based on the username associated to them
   * 
   * @author Wassim Jabbour
   * @param username
   * @return
   */
  List<Account> findAccountsByUsername(String username);

  /**
   * this method finds an account based on the address associated to it
   * 
   * @author Wassim Jabbour
   * @param address address of user of the account
   * @return account
   */
  Account findAccountByAddress(Address address);

  /**
   * this method returns list of accounts that are disabled
   * 
   * @author Wassim Jabbour
   * @param disabled boolean to signify if an account is disabled
   * @return list of accounts
   */
  List<Account> findAccountByDisabled(boolean disabled);

}
