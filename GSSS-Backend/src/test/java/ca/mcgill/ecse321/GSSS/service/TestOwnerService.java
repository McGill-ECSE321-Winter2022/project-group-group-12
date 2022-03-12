package ca.mcgill.ecse321.GSSS.service;

import ca.mcgill.ecse321.GSSS.dao.ItemCategoryRepository;
import ca.mcgill.ecse321.GSSS.dao.OwnerRepository;
import ca.mcgill.ecse321.GSSS.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

/**
 * @author Chris Hatoum
 */
    @ExtendWith(MockitoExtension.class)
    public class TestOwnerService {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerService ownerService;

    @BeforeEach
    public void setMockOutput() {
        // Set each CRUD method to its mock
        lenient().when(ownerRepository.findOwnerByEmail(anyString())).thenAnswer(MockRepository::findOwnerByEmail);
        lenient().when(ownerRepository.findOwnerByAddress(any(Address.class))).thenAnswer(MockRepository::findOwnerByAddress);
        lenient().when(ownerRepository.findAll()).thenAnswer(MockRepository::findAll);
        lenient().when(ownerRepository.save(any(Owner.class))).thenAnswer(MockRepository::save);
    }

    /**
     * @author Chris Hatoum
     *
     * Method to test if we can get the owner succesfully
     */
    @Test
    public void testGetOwner_Successful() {
        Owner fetched = ownerService.getOwner();
        Owner expected = MockDatabase.owner1;
       assertNotNull(fetched);
        assertEquals( expected, fetched);
    }

    /**
     * @author Chris Hatoum
     *
     * Method to test if we can modify the owner succesfully
     */
    @Test
    public void testModifyOwner_Successful() {

        Owner modified = ownerService.modifyOwner(MockDatabase.owner1.getUsername(),
                MockDatabase.owner1.getPassword(), MockDatabase.address1);


        assertNotNull(modified);

        assertEquals(MockDatabase.owner1.getUsername(), modified.getUsername());
        assertEquals(MockDatabase.owner1.getPassword(), modified.getPassword());
        assertEquals(MockDatabase.address1, modified.getAddress());

        }
    /**
     * @author Chris Hatoum
     *
     * Method to test null username
     */
    @Test
    public void testModifyOwner_NullUsername() {
        try {
            ownerService.modifyOwner(null, "new pw", new Address());
        } catch (IllegalArgumentException e) {
            assertEquals("Owner username cannot be empty! ", e.getMessage());
            return;
        }
        fail();
    }
    /**
     * @author Chris Hatoum
     *
     * Method to test empty username
     */
    @Test
    public void testModifyOwner_EmptyUsername() {
        try {
            ownerService.modifyOwner("   ", "new pw", new Address());
        } catch (IllegalArgumentException e) {
            assertEquals("Owner username cannot be empty! ", e.getMessage());
            return;
        }
        fail();
    }

    /**
     * @author Chris Hatoum
     *
     * Method to test null password
     */
    @Test
    public void testModifyOwner_NullPassword() {
        try {
            ownerService.modifyOwner("username", null,  new Address());
        } catch (IllegalArgumentException e) {
            assertEquals("Password has to be at least 6 characters! ", e.getMessage());
            return;
        }
        fail();
    }
    /**
     * @author Chris Hatoum
     *
     * Method to test empty password
     */
    @Test
    public void testModifyOwner_EmptyPassword() {
        try {
            ownerService.modifyOwner("username", "   ", new Address());
        } catch (IllegalArgumentException e) {
            assertEquals("Password has to be at least 6 characters! ", e.getMessage());
            return;
        }
        fail();
    }

    /**
     * @author Chris Hatoum
     *
     * Method to test password too short
     */
    @Test
    public void testModifyOwner_TooShortPassword() {
        try {
            ownerService.modifyOwner("username", "12345", new Address());
        } catch (IllegalArgumentException e) {
            assertEquals("Password has to be at least 6 characters! ", e.getMessage());
            return;
        }
        fail();
    }
    /**
     * @author Chris Hatoum
     *
     * Method to test if address is null
     */
    @Test
    public void testModifyOwner_NullAddress() {
        try {
            ownerService.modifyOwner("username", "new pw",  null);
        } catch (IllegalArgumentException e) {
            assertEquals("Owner address cannot be null! ", e.getMessage());
            return;
        }
        fail();
    }
    /**
     * @author Chris Hatoum
     *
     * Method to test if the system information can be modified successfully
     */
    @Test
    public void testModifySystemInformation_Success() {

        Owner modified  =   ownerService.modifySystemInformation("Quebec City", 20);

        assertNotNull(modified);

        assertEquals("Quebec City", modified.getUsername());
        assertEquals(20, modified.getOutOfTownDeliveryFee());

    }
    /**
     * @author Chris Hatoum
     *
     * Method to test null city
     */
    @Test
    public void testModifySystemInformation_NullCity() {
        try {
            ownerService.modifySystemInformation(null, 20);
        } catch (IllegalArgumentException e) {
            assertEquals("City name cannot be empty!", e.getMessage());
            return;
        }
        fail();
    }
    /**
     * @author Chris Hatoum
     *
     * Method to test if the city is empty
     */
    @Test
    public void testModifySystemInformation_EmptyCity() {
        try {
            ownerService.modifySystemInformation("   ", 20);
        } catch (IllegalArgumentException e) {
            assertEquals("City name cannot be empty!", e.getMessage());
            return;
        }
        fail();
    }

    class MockRepository {

        static Owner findOwnerByEmail(InvocationOnMock invocation) {
            String email = (String) invocation.getArgument(0);
            if (email.equals(TestAccountService.MockDatabase.owner1.getEmail()))
                return TestAccountService.MockDatabase.owner1;
            return null;
        }

        static Owner findOwnerByAddress(InvocationOnMock invocation) {
            Address address = (Address) invocation.getArgument(0);

            if (address.equals(MockDatabase.owner1.getAddress())) {
                return MockDatabase.owner1;
            }
            return null;
        }
        static Owner findAll(InvocationOnMock invocation) {
            List<Owner> owner = new ArrayList<Owner>();
            owner.add(MockDatabase.owner1);
            return owner.get(0);
        }

        static Owner save(InvocationOnMock invocation) {
            return (Owner) invocation.getArgument(0);
        }

    }
        /**
         * This class mocks data for tests.
         */
        final static class MockDatabase {

            static Owner owner1 = new Owner();
            static Address address1 = new Address();

            static {

                address1.setFullName("John Smith");
                address1.setId(UUID.randomUUID().toString());
                address1.setStreetName("Street st");
                address1.setStreetNumber(1000);
                address1.setCity("Montreal");
                address1.setPostalCode("A1A 2B2");

                owner1.setAddress(address1);
                owner1.setEmail("owner1@email.com");
                owner1.setUsername("John Smithest");
                owner1.setSalt(Utility.getSalt());
                owner1.setPassword(Utility.hashAndSaltPassword("password", owner1.getSalt()));
                owner1.setDisabled(false);

            }

        }
    }

