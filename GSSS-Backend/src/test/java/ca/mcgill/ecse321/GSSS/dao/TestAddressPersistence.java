package ca.mcgill.ecse321.GSSS.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.GSSS.model.Address;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAddressPersistence {

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
  private OrderRepository orderRepository;

  @Autowired
  private OwnerRepository ownerRepository;

  @Autowired
  private PurchaseRepository purchaseRepository;

  @Autowired
  private QuantityOrderedRepository quantityOrderedRepository;

  @Autowired
  private ShiftRepository shiftRepository;

  @AfterEach
  public void clearDatabase() {

    // Deleting all the database contents from the most dependent to most independent
    customerRepository.deleteAll();
    employeeRepository.deleteAll();
    ownerRepository.deleteAll();
    addressRepository.deleteAll();
    purchaseRepository.deleteAll();
    orderRepository.deleteAll();
    quantityOrderedRepository.deleteAll();
    itemRepository.deleteAll();
    itemCategoryRepository.deleteAll();
    shiftRepository.deleteAll();
    businessHourRepository.deleteAll();
  }

  @Test
  public void testPersistAndLoadAddressById() {

    String fullName = "Enzo Ferrari";
    String streetName = "Crescent";
    int streetNumber = 43;
    String city = "Montreal";
    String postalCode = "W4S 5I3";
    String id = UUID.randomUUID().toString();

    Address address = new Address();
    address.setFullName(fullName);
    address.setStreetName(streetName);
    address.setStreetNumber(streetNumber);
    address.setCity(city);
    address.setPostalCode(postalCode);
    address.setId(id);

    addressRepository.save(address);

    address = null;

    address = addressRepository.findAddressById(id);

    assertNotNull(address);
    assertEquals(address.getFullName(), fullName);
    assertEquals(address.getStreetName(), streetName);
    assertEquals(address.getStreetNumber(), streetNumber);
    assertEquals(address.getCity(), city);
    assertEquals(address.getPostalCode(), postalCode);
    assertEquals(address.getId(), id);

  }

}
