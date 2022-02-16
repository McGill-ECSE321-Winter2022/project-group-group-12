package ca.mcgill.ecse321.GSSS.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CommonTests {

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

  /**
   * Deletes all the database contents. Goes from the independent to the dependent classes to avoid
   * exceptions being thrown when deleting
   * 
   * @author Wassim Jabbour
   */
  @AfterEach
  public void clearDatabase() {
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
