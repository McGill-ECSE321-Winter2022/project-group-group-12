package ca.mcgill.ecse321.GSSS.service;

import ca.mcgill.ecse321.GSSS.dao.AddressRepository;
import ca.mcgill.ecse321.GSSS.dao.BusinessHourRepository;
import ca.mcgill.ecse321.GSSS.dao.CustomerRepository;
import ca.mcgill.ecse321.GSSS.dao.EmployeeRepository;
import ca.mcgill.ecse321.GSSS.dao.ItemCategoryRepository;
import ca.mcgill.ecse321.GSSS.dao.ItemRepository;
import ca.mcgill.ecse321.GSSS.dao.OwnerRepository;
import ca.mcgill.ecse321.GSSS.dao.PurchaseRepository;
import ca.mcgill.ecse321.GSSS.dao.ShiftRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service that contains a method to clean up the database Note: Not tested in the integration tests
 * cause it is use to cleanup after those
 *
 * @author Wassim Jabbour
 */
@Service
public class CleanupService {

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private BusinessHourRepository businessHourRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private ItemCategoryRepository itemCategoryRepository;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private OwnerRepository ownerRepository;

  @Autowired
  private PurchaseRepository purchaseRepository;

  @Autowired
  private ShiftRepository shiftRepository;

  /*
   * this method is to clear everything in the database
   */
  @Transactional
  public void cleanupDatabase() {
    customerRepository.deleteAll();
    purchaseRepository.deleteAll();
    employeeRepository.deleteAll();
    shiftRepository.deleteAll();
    ownerRepository.deleteAll();
    addressRepository.deleteAll();
    itemRepository.deleteAll();
    itemCategoryRepository.deleteAll();
    businessHourRepository.deleteAll();
  }
}
