package ca.mcgill.ecse321.GSSS.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.GSSS.dao.CustomerRepository;
import ca.mcgill.ecse321.GSSS.dao.PurchaseRepository;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Purchase;

@Service
public class CustomerService {

  @Autowired
  CustomerRepository customerRepository;
  @Autowired
  PurchaseRepository purchaseRepository;

  private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }

  /**
   * Method to get order history of a customer by their email
   * 
   * @author Habib Jarweh
   * @param customerEmail email of the customer
   * @return list of purchases of customer
   */
  @Transactional
  public List<Purchase> getOrderHistory(String customerEmail) {
    Customer customer = customerRepository.findCustomerByEmail(customerEmail);
    return toList(purchaseRepository.findPurchasesByCustomerAndOrderType(customer));
  }


}
