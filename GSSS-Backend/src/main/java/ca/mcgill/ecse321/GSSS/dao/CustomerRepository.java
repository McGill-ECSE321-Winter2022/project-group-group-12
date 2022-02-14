package ca.mcgill.ecse321.GSSS.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Order;

public interface CustomerRepository extends CrudRepository<Customer, String> {

  Customer findCustomerByEmail(String email);

  List<Customer> findCustomersByUsername(String username);

  Customer findCustomerByPastOrder(Order pastOrder);

  Customer findCustomerByAddress(Address address);

}
