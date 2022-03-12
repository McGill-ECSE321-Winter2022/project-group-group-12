package ca.mcgill.ecse321.GSSS.service;

import ca.mcgill.ecse321.GSSS.dao.AddressRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.service.TestCustomerService.MockDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Class to test the Address Service class
 *
 * @author Theo Ghanem
 */
@ExtendWith(MockitoExtension.class)
public class TestAddressService {

  @Mock
  private AddressRepository addressRepository;

  @InjectMocks
  private AddressService addressService;

  @BeforeEach
  public void setMockOutput() {
    // Set each CRUD method to its mock
    lenient().when(addressRepository.findAddressById(anyString()))
        .thenAnswer(MockRepository::findAddressById);
    lenient().when(addressRepository.findAddressesByCity(anyString()))
        .thenAnswer(MockRepository::findAddressesByCity);
    lenient().when(addressRepository.findAddressesByStreetName(anyString()))
        .thenAnswer(MockRepository::findAddressesByStreetName);
    lenient().when(addressRepository.findAll()).thenAnswer(MockRepository::findAll);
    lenient().when(addressRepository.save(any(Address.class))).thenAnswer(MockRepository::save);
  }

  /** This class holds all the mock methods of the CRUD repository. */
  class MockRepository {

    static Address findAddressById(InvocationOnMock invocation) {
      String id = (String) invocation.getArgument(0);
      if (id.equals(MockDatabase.address1.getId()))
        return MockDatabase.address1;
      if (id.equals(MockDatabase.address2.getId()))
        return MockDatabase.address2;
      if (id.equals(MockDatabase.address3.getId()))
        return MockDatabase.address3;
      if (id.equals(MockDatabase.address_m.getId()))
        return MockDatabase.address_m;
      return null;
    }

    static List<Address> findAddressesByCity(InvocationOnMock invocation) {
      String city = (String) invocation.getArgument(0);
      List<Address> addresses = new ArrayList<Address>();
      if (city.equals(MockDatabase.address1.getCity()))
        addresses.add(MockDatabase.address1);
      if (city.equals(MockDatabase.address2.getCity()))
        addresses.add(MockDatabase.address2);
      if (city.equals(MockDatabase.address3.getCity()))
        addresses.add(MockDatabase.address3);
      if (city.equals(MockDatabase.address_m.getCity()))
        addresses.add(MockDatabase.address_m);
      return addresses;
    }

    static List<Address> findAddressesByStreetName(InvocationOnMock invocation) {
      String streetName = (String) invocation.getArgument(0);
      List<Address> addresses = new ArrayList<Address>();
      if (streetName.equals(MockDatabase.address1.getStreetName()))
        addresses.add(MockDatabase.address1);
      if (streetName.equals(MockDatabase.address2.getStreetName()))
        addresses.add(MockDatabase.address2);
      if (streetName.equals(MockDatabase.address3.getStreetName()))
        addresses.add(MockDatabase.address3);
      if (streetName.equals(MockDatabase.address_m.getStreetName()))
        addresses.add(MockDatabase.address_m);
      return addresses;
    }

    static Address save(InvocationOnMock invocation) {
      return (Address) invocation.getArgument(0);
    }

    static List<Address> findAll(InvocationOnMock invocation) {
      List<Address> addresses = new ArrayList<Address>();
      addresses.add(MockDatabase.address1);
      addresses.add(MockDatabase.address2);
      addresses.add(MockDatabase.address3);
      addresses.add(MockDatabase.address_m);
      return addresses;
    }
  }

  @Test
  public void testCreateAddress_Success() {
    Address address = new Address();
    address.setFullName("John Appleseed");
    address.setStreetName("cool street");
    address.setStreetNumber(12);
    address.setCity("Montreal");
    address.setPostalCode("H3A 1S3");
    address.setId(UUID.randomUUID().toString());
    Address created = addressService.createAddress(address.getFullName(), address.getStreetName(),
        address.getStreetNumber(), address.getCity(), address.getPostalCode());
    assertNotNull(created);
    assertEquals(address.getFullName(), created.getFullName());
    assertEquals(address.getStreetName(), created.getStreetName());
    assertEquals(address.getStreetNumber(), created.getStreetNumber());
    assertEquals(address.getCity(), created.getCity());
    assertEquals(address.getPostalCode(), created.getPostalCode());
  }

  @Test
  public void testCreateAddress_NullFullName() {
    try {
      addressService.createAddress(null, "nice street", 33, "Montreal", "H3A 1S2");
    } catch (IllegalArgumentException e) {
      assertEquals("Address full name cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateAddress_EmptyFullName() {
    try {
      addressService.createAddress("   ", "nice street", 33, "Montreal", "H3A 1S2");
    } catch (IllegalArgumentException e) {
      assertEquals("Address full name cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateAddress_NullStreetName() {
    try {
      addressService.createAddress("Elon musk", null, 33, "Montreal", "H3A 1S2");
    } catch (IllegalArgumentException e) {
      assertEquals("Address street name cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateAddress_EmptyStreetName() {
    try {
      addressService.createAddress("Elon musk", "    ", 33, "Montreal", "H3A 1S2");
    } catch (IllegalArgumentException e) {
      assertEquals("Address street name cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateAddress_NullStreetNumber() {
    try {
      addressService.createAddress("Elon musk", "nice street", null, "Montreal", "H3A 1S2");
    } catch (IllegalArgumentException e) {
      assertEquals("Address street number cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateAddress_NullCity() {
    try {
      addressService.createAddress("Elon musk", "nice street", 32, null, "H3A 1S2");
    } catch (IllegalArgumentException e) {
      assertEquals("Address city cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateAddress_EmptyCity() {
    try {
      addressService.createAddress("Elon musk", "nice street", 32, "    ", "H3A 1S2");
    } catch (IllegalArgumentException e) {
      assertEquals("Address city cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateAddress_NullPostalCode() {
    try {
      addressService.createAddress("Elon musk", "nice street", 32, "Montreal", null);
    } catch (IllegalArgumentException e) {
      assertEquals("Address postal code cannot be empty! Postal Code is not valid! ",
          e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateAddress_EmptyPostalCode() {
    try {
      addressService.createAddress("Elon musk", "nice street", 32, "Montreal", "    ");
    } catch (IllegalArgumentException e) {
      assertEquals("Address postal code cannot be empty! Postal Code is not valid! ",
          e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateAddress_InvalidPostalCode() {
    try {
      addressService.createAddress("Elon musk", "nice street", 32, "Montreal", "Banana");
    } catch (IllegalArgumentException e) {
      assertEquals("Postal Code is not valid! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateAddress_NullAll() {
    try {
      addressService.createAddress(null, null, null, null, null);
    } catch (IllegalArgumentException e) {
      assertEquals(
          "Address full name cannot be empty! Address street name cannot be empty! Address street number cannot be empty! Address city cannot be empty! Address postal code cannot be empty! Postal Code is not valid! ",
          e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testCreateAddress_EmptyAll() {
    try {
      addressService.createAddress("    ", "  ", null, "   ", "   ");
    } catch (IllegalArgumentException e) {
      assertEquals(
          "Address full name cannot be empty! Address street name cannot be empty! Address street number cannot be empty! Address city cannot be empty! Address postal code cannot be empty! Postal Code is not valid! ",
          e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyAddress_Success() {
    Address modified = addressService.modifyAddress(MockDatabase.address_m.getFullName(),
        MockDatabase.address_m.getStreetName(), MockDatabase.address_m.getStreetNumber(),
        MockDatabase.address_m.getCity(), MockDatabase.address_m.getPostalCode(),
        MockDatabase.address_m.getId());
    assertNotNull(modified);
    assertEquals(MockDatabase.address_m.getFullName(), modified.getFullName());
    assertEquals(MockDatabase.address_m.getStreetName(), modified.getStreetName());
    assertEquals(MockDatabase.address_m.getStreetNumber(), modified.getStreetNumber());
    assertEquals(MockDatabase.address_m.getCity(), modified.getCity());
    assertEquals(MockDatabase.address_m.getPostalCode(), modified.getPostalCode());
  }

  @Test
  public void testModifyAddress_NullFullName() {
    try {
      addressService.modifyAddress(null, "nice street", 33, "Montreal", "H3A 1S2",
          MockDatabase.address_m.getId());
    } catch (IllegalArgumentException e) {
      assertEquals("Address full name cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyAddress_EmptyFullName() {
    try {
      addressService.modifyAddress("   ", "nice street", 33, "Montreal", "H3A 1S2",
          MockDatabase.address_m.getId());
    } catch (IllegalArgumentException e) {
      assertEquals("Address full name cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyAddress_NullStreetName() {
    try {
      addressService.modifyAddress("Elon musk", null, 33, "Montreal", "H3A 1S2",
          MockDatabase.address_m.getId());
    } catch (IllegalArgumentException e) {
      assertEquals("Address street name cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyAddress_EmptyStreetName() {
    try {
      addressService.modifyAddress("Elon musk", "    ", 33, "Montreal", "H3A 1S2",
          MockDatabase.address_m.getId());
    } catch (IllegalArgumentException e) {
      assertEquals("Address street name cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyAddress_NullStreetNumber() {
    try {
      addressService.modifyAddress("Elon musk", "nice street", null, "Montreal", "H3A 1S2",
          MockDatabase.address_m.getId());
    } catch (IllegalArgumentException e) {
      assertEquals("Address street number cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyAddress_NullCity() {
    try {
      addressService.modifyAddress("Elon musk", "nice street", 32, null, "H3A 1S2",
          MockDatabase.address_m.getId());
    } catch (IllegalArgumentException e) {
      assertEquals("Address city cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyAddress_EmptyCity() {
    try {
      addressService.modifyAddress("Elon musk", "nice street", 32, "    ", "H3A 1S2",
          MockDatabase.address_m.getId());
    } catch (IllegalArgumentException e) {
      assertEquals("Address city cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyAddress_NullPostalCode() {
    try {
      addressService.modifyAddress("Elon musk", "nice street", 32, "Montreal", null,
          MockDatabase.address_m.getId());
    } catch (IllegalArgumentException e) {
      assertEquals("Address postal code cannot be empty! Postal Code is not valid! ",
          e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyAddress_EmptyPostalCode() {
    try {
      addressService.modifyAddress("Elon musk", "nice street", 32, "Montreal", "    ",
          MockDatabase.address_m.getId());
    } catch (IllegalArgumentException e) {
      assertEquals("Address postal code cannot be empty! Postal Code is not valid! ",
          e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyAddress_InvalidPostalCode() {
    try {
      addressService.modifyAddress("Elon musk", "nice street", 32, "Montreal", "Banana",
          MockDatabase.address_m.getId());
    } catch (IllegalArgumentException e) {
      assertEquals("Postal Code is not valid! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyAddress_NullAll() {
    try {
      addressService.modifyAddress(null, null, null, null, null, MockDatabase.address_m.getId());
    } catch (IllegalArgumentException e) {
      assertEquals(
          "Address full name cannot be empty! Address street name cannot be empty! Address street number cannot be empty! Address city cannot be empty! Address postal code cannot be empty! Postal Code is not valid! ",
          e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyAddress_EmptyAll() {
    try {
      addressService.modifyAddress("    ", "  ", null, "   ", "   ",
          MockDatabase.address_m.getId());
    } catch (IllegalArgumentException e) {
      assertEquals(
          "Address full name cannot be empty! Address street name cannot be empty! Address street number cannot be empty! Address city cannot be empty! Address postal code cannot be empty! Postal Code is not valid! ",
          e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyAddress_NullId() {
    try {
      addressService.modifyAddress(MockDatabase.address_m.getFullName(),
          MockDatabase.address_m.getStreetName(), MockDatabase.address_m.getStreetNumber(),
          MockDatabase.address_m.getCity(), MockDatabase.address_m.getPostalCode(), null);
    } catch (IllegalArgumentException e) {
      assertEquals("Address id cannot be empty! ", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testModifyAddress_NotInDb() {
    try {
      addressService.modifyAddress(MockDatabase.address_m.getFullName(),
          MockDatabase.address_m.getStreetName(), MockDatabase.address_m.getStreetNumber(),
          MockDatabase.address_m.getCity(), MockDatabase.address_m.getPostalCode(), "123");
    } catch (NoSuchElementException e) {
      assertEquals("No address with id 123 exits!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testDeleteAddress_Success() {
    addressService.deleteAddress(MockDatabase.address_m.getId());

    verify(addressRepository, times(1))
        .deleteById(argThat((String i) -> MockDatabase.address_m.getId().equals(i)));
    verify(addressRepository, times(0))
        .deleteById(argThat((String i) -> !MockDatabase.address_m.getId().equals(i)));
  }

  @Test
  public void testDeleteAddress_NullId() {
    try {
      addressService.deleteAddress(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Address id cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testDeleteAddress_EmptyId() {
    try {
      addressService.deleteAddress("   ");
    } catch (IllegalArgumentException e) {
      assertEquals("Address id cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetAddressById_Success() {
    Address fetched = addressService.getAddress(MockDatabase.address2.getId());
    assertNotNull(fetched);
    assertEquals(MockDatabase.address2, fetched);
  }

  @Test
  public void testGetAddressById_NullId() {
    try {
      addressService.getAddress(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Address id cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetAddressById_EmptyId() {
    try {
      addressService.getAddress("   ");
    } catch (IllegalArgumentException e) {
      assertEquals("Address id cannot be empty!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetAddressById_NotInDb() {
    try {
      addressService.getAddress("123");
    } catch (NoSuchElementException e) {
      assertEquals("No address with id 123 exits!", e.getMessage());
      return;
    }
    fail();
  }

  @Test
  public void testGetAllAddresses_Success() {
    List<Address> fetched = addressService.getAllAddresses();
    List<Address> expected = new ArrayList<Address>();
    expected.add(MockDatabase.address1);
    expected.add(MockDatabase.address2);
    expected.add(MockDatabase.address3);
    expected.add(MockDatabase.address_m);
    assertNotNull(fetched);
    assertEquals(expected.size(), fetched.size());
    for (Address e : fetched)
      assertTrue(expected.contains(e));
  }



  /** This class mock data for tests. */
  static final class MockDatabase {

    static Address address1 = new Address();

    static Address address2 = new Address();

    static Address address3 = new Address();

    static Address address_m = new Address();

    static {
      address1.setFullName("John Smith");
      address1.setId(UUID.randomUUID().toString());
      address1.setStreetName("Street st");
      address1.setStreetNumber(1000);
      address1.setCity("Montreal");
      address1.setPostalCode("A1A 2B2");

      address2.setFullName("John Doe");
      address2.setId(UUID.randomUUID().toString());
      address2.setStreetName("Drive st");
      address2.setStreetNumber(2000);
      address2.setCity("Montreal");
      address2.setPostalCode("A2A 1B1");

      address3.setFullName("John Smith");
      address3.setId(UUID.randomUUID().toString());
      address3.setStreetName("Avenue st");
      address3.setStreetNumber(3000);
      address3.setCity("Toronto");
      address3.setPostalCode("B1B 2A2");

      address_m.setFullName("John Modifiable");
      address_m.setId(UUID.randomUUID().toString());
      address_m.setStreetName("Boulevard st");
      address_m.setStreetNumber(3000);
      address_m.setCity("Vancouver");
      address_m.setPostalCode("B1B 2A2");
    }
  }
}
