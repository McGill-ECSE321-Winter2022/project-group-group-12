package ca.mcgill.ecse321.GSSS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.GSSS.dao.BusinessHourRepository;
import ca.mcgill.ecse321.GSSS.model.BusinessHour;
import ca.mcgill.ecse321.GSSS.model.Weekday;

@ExtendWith(MockitoExtension.class)
public class TestBusinessHourService {
	@Mock
	BusinessHourRepository businessHourRepository;
	
	@InjectMocks
	private BusinessHourService businessHourService;
	
	 @BeforeEach
    public void setMockOutput(){
        // Set each CRUD method to its mock
        lenient().when(businessHourRepository.findBusinessHourByWeekday(any(Weekday.class))).thenAnswer(MockRepository::findBusinessHourByWeekday);
        lenient().when(businessHourRepository.findAll()).thenAnswer(MockRepository::findAll);
        lenient().when(businessHourRepository.save(any(BusinessHour.class))).thenAnswer(MockRepository::save);
	}
	 
	// ========================================================================
	// Get methods
    // ========================================================================
  
	 /**
	   * Method to check that all businesshours in the database are fetched successfully
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testGetAllBusinessHour_Successful() {
	    List<BusinessHour> businessHours = businessHourService.getAllBusinessHours();

	    List<BusinessHour> expected = new ArrayList<BusinessHour>();
	    expected.add(MockDatabase.businessHour1);
	    expected.add(MockDatabase.businessHour2);

	    assertNotNull(businessHours);
	    assertEquals(expected.size(), businessHours.size());
	    for (BusinessHour b : businessHours)
	      assertTrue(expected.contains(b));
	  }
	  
	  /**
	   * Method to check if the businessHour is fetched 
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testGetBuisnessHourByWeekday_Successful() {
	    BusinessHour businesshour = businessHourService.getBusinessHourByWeekday(MockDatabase.businessHour1.getWeekday());
	    assertNotNull(businesshour);
	    assertEquals(businesshour.getWeekday(), MockDatabase.businessHour1.getWeekday());
	  }
	  
	  /**
	   * Method to check if an error is thrown if we look for a business hour from a null Weekday
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testGetBuisnessHourByWeekday_NullWeekday() {
		  Exception error = assertThrows(IllegalArgumentException.class, () -> businessHourService.getBusinessHourByWeekday(null));
		  assertEquals("Weekday of business hour cannot be null! ", error.getMessage());
	  }
	  
	  /**
	   * Method to check if an error is thrown when we try to fetch a businessHour not in the database
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testGetBusinessHourByWeekday_NotInDb() {
	    Weekday wrongWeekDay = Weekday.Wednesday;
	    Exception error =
	        assertThrows(NoSuchElementException.class, () -> businessHourService.getBusinessHourByWeekday(wrongWeekDay));
	    assertEquals("No businessHour with weekday "+ wrongWeekDay + " exits!", error.getMessage());
	  }
	  
	// ========================================================================
    // Create method
    // ========================================================================
	  
	  

	  /**
	   * Method to test that a business hour is created successfully
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testCreateBusinessHour_Successful() {
	   
		assertEquals(2, businessHourService.getAllBusinessHours().size());

	    Weekday weekday = Weekday.Thursday;
	    Time startTime = Time.valueOf("8:30:00");
	    Time endTime = Time.valueOf("17:30:00");

	    BusinessHour businessHour = null;
	    try {
	      businessHour = businessHourService.createBusinessHour(weekday, startTime, endTime);
	    } catch (IllegalArgumentException e) {
	      fail();
	    }
	    
	    assertNotNull(businessHour);
	    assertEquals(weekday, businessHour.getWeekday());
	    assertEquals(startTime, businessHour.getStartTime());
	    assertEquals(endTime, businessHour.getEndTime());
	    
	  }

	  /**
	   * Method to test that an error is thrown when weekday is empty when creating business hour
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testCreateBusinessHour_NullWeekday() {
		  
		Weekday weekday = null;
		Time startTime = Time.valueOf("8:30:00");
		Time endTime = Time.valueOf("17:30:00");

		   
	    String error = null;
	    BusinessHour businessHour = null;

	    try {
	      businessHour = businessHourService.createBusinessHour(weekday, startTime, endTime);
	    } catch (IllegalArgumentException e) {
	      error = e.getMessage();
	    }

	    assertNull(businessHour);
	    
	    // check error
	    assertEquals("Weekday of business hour cannot be null! ", error);

	  }
	  
	  /**
	   * Method to test that an error is thrown when Start Time is empty when creating business hour
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testCreateBusinessHour_NullStartTime() {
		  
		Weekday weekday = Weekday.Thursday;
		Time startTime = null;
		Time endTime = Time.valueOf("17:30:00");

	    String error = null;
	    BusinessHour businessHour = null;

	    try {
	      businessHour = businessHourService.createBusinessHour(weekday, startTime, endTime);
	    } catch (IllegalArgumentException e) {
	      error = e.getMessage();
	    }

	    assertNull(businessHour);
	    
	    // check error
	    assertEquals("Business hour start time cannot be null! ", error);

	  }

	  /**
	   * Method to test that an error is thrown when End Time is empty when creating business hour
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testCreateBusinessHour_NullEndTime() {
		  
		Weekday weekday = Weekday.Thursday;
		Time startTime = Time.valueOf("8:30:00");
		Time endTime = null;

	    String error = null;
	    BusinessHour businessHour = null;

	    try {
	      businessHour = businessHourService.createBusinessHour(weekday, startTime, endTime);
	    } catch (IllegalArgumentException e) {
	      error = e.getMessage();
	    }

	    assertNull(businessHour);
	    
	    // check error
	    assertEquals("Business hour end time cannot be null! ", error);

	  }
	  
	  /**
	   * Method to test that an error is thrown when End Time is lower than Start Time when creating business hour
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testCreateBusinessHour_StartTimeAfterEndTime() {
	   
	    Weekday weekday = Weekday.Thursday;
	    Time startTime = Time.valueOf("17:30:00");
	    Time endTime = Time.valueOf("8:30:00");
	    
	    String error = null;
	    BusinessHour businessHour = null;
	    try {
	      businessHour = businessHourService.createBusinessHour(weekday, startTime, endTime);
	    } catch (IllegalArgumentException e) {
	      error = e.getMessage();
	    }
	    
	    assertNull(businessHour);
	    // check error
	    assertEquals(
	        "Business hour end time cannot be before business hour start time!", error);
	  }
	  
	  /**
	   * Method to test that an error is thrown when every parameter is null when creating business hour
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testCreateBusinessHour_AllNull() {

	    String error = null;
	    BusinessHour businessHour = null;

	    try {
	      businessHour = businessHourService.createBusinessHour(null, null, null);
	    } catch (IllegalArgumentException e) {
	      error = e.getMessage();
	    }

	    assertNull(businessHour);
	    // check error
	    assertEquals(
	        "Weekday of business hour cannot be null! Business hour start time cannot be null! Business hour end time cannot be null! ", error);
	  }
	  
	// ========================================================================
	// Update methods
	// ========================================================================
	  
	  /**
	   * Method to see if businessHour gets modified successfully
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testModifyBusinessHour_Successful() {

		Time newStartTime = Time.valueOf("9:30:00");
		Time newEndTime = Time.valueOf("22:30:00");

	    BusinessHour businessHour = businessHourService.modifyBusinessHour(MockDatabase.businessHour1.getWeekday(), newStartTime, newEndTime);
	   
	    assertNotNull(businessHour);
	    assertEquals(MockDatabase.businessHour1.getWeekday(), businessHour.getWeekday());
	    assertEquals(newStartTime, businessHour.getStartTime());
	    assertEquals(newEndTime, businessHour.getEndTime());
	  }
	  
	  /**
	   * Method to see if an error is thrown when we want to modify a businessHour using an empty weekday
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testModifyBusinessHour_NullWeekday() {
		    String error = null;
		    
		    BusinessHour businessHour = MockDatabase.businessHour1;
		    try {
		    	businessHour = businessHourService.modifyBusinessHour(null, businessHour.getStartTime(), businessHour.getEndTime());
		    } catch (IllegalArgumentException e) {
		      // Check that no error occurred
		      error = e.getMessage();
		    }

		    assertNotNull(businessHour);
		    // check error
		    assertEquals("Weekday of business hour cannot be null! ", error);
	  }
	  
	  /**
	   * Method to see if an error is thrown when we want to modify a businessHour using an empty start time
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testModifyBusinessHour_NullStartTime() {
		    String error = null;
		    BusinessHour businessHour = MockDatabase.businessHour1;
		    try {
		    	businessHour = businessHourService.modifyBusinessHour(businessHour.getWeekday(), null, businessHour.getEndTime());
		    } catch (IllegalArgumentException e) {
		      // Check that no error occurred
		      error = e.getMessage();
		    }

		    assertNotNull(businessHour);
		    // check error
		    assertEquals("Business hour start time cannot be null! ", error);
	  }
	  
	  /**
	   * Method to see if an error is thrown when we want to modify a businessHour using an empty end time
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testModifyBusinessHour_NullEndTime() {
		    String error = null;
		    BusinessHour businessHour = MockDatabase.businessHour1;
		    try {
		    	businessHour = businessHourService.modifyBusinessHour(businessHour.getWeekday(), businessHour.getStartTime(), null);
		    } catch (IllegalArgumentException e) {
		      // Check that no error occurred
		      error = e.getMessage();
		    }

		    // check error
		    assertEquals("Business hour end time cannot be null! ", error);
	  }
	  
	  /**
	   * Method to see if an error is thrown when we want to modify a businessHour using a start time lower than the end time
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testModifyBusinessHour_StartTimeAfterEndTime() {
		    String error = null;
		    BusinessHour businessHour = MockDatabase.businessHour1;
		    Time newStartTime = Time.valueOf("17:30:00");
			Time newEndTime = Time.valueOf("8:30:00");
		    
		    try {
		    	businessHour = businessHourService.modifyBusinessHour(businessHour.getWeekday(), newStartTime, newEndTime);
		    } catch (IllegalArgumentException e) {
		      // Check that no error occurred
		      error = e.getMessage();
		    }

		    assertNotNull(businessHour);
		    // check error
		    assertEquals("Business hour end time cannot be before business hour start time!", error);
	  }
	  
	  /**
	   * Method to test that an error is thrown when every parameter is null when modifying a businessHour
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testModifyBusinessHour_AllNull() {
	    String error = null;
	    BusinessHour businessHour = MockDatabase.businessHour1;

	    try {
	    	businessHour = businessHourService.modifyBusinessHour(null, null, null);
	    } catch (IllegalArgumentException e) {
	      error = e.getMessage();
	    }

	    assertNotNull(businessHour);
	    // check error
	    assertEquals(
	        "Weekday of business hour cannot be null! Business hour start time cannot be null! Business hour end time cannot be null! ",
	        error);
	  }
	  
	  
	// ========================================================================
	// Delete methods
	// ========================================================================
	  

	  /**
	   * Method to check that a business hour was deleted successfully
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testDeleteBusinessHour_Successful() {
	    businessHourService.deleteBusinessHour(MockDatabase.businessHour1.getWeekday());

	    verify(businessHourRepository, times(1))
	        .delete(argThat((BusinessHour b) -> MockDatabase.businessHour1.getWeekday() == b.getWeekday()));
	    verify(businessHourRepository, times(0))
	        .delete(argThat((BusinessHour b) -> MockDatabase.businessHour1.getWeekday() != b.getWeekday()));
	  }
	  
	  /**
	   * Method to check that error is thrown when we input a null weekday to delete BusinessHour
	   * 
	   * @author Enzo Benoit-Jeannin
	   */
	  @Test
	  public void testDeleteBusinessHour_NullWeekday() {
	    Exception error = assertThrows(IllegalArgumentException.class, () -> businessHourService.deleteBusinessHour(null));
	    assertEquals("Weekday of business hour cannot be null! ", error.getMessage());
	    verify(businessHourRepository, times(0)).delete(any(BusinessHour.class));
	  }
	    

	  
	  /**
	   * This class holds all of the mock methods of the CRUD repository.
	   */
	  class MockRepository {
		    static BusinessHour findBusinessHourByWeekday(InvocationOnMock invocation) {
		      Weekday weekday = (Weekday) invocation.getArgument(0);
		      if (weekday.equals(MockDatabase.businessHour1.getWeekday()))
		        return MockDatabase.businessHour1;
		      if (weekday.equals(MockDatabase.businessHour2.getWeekday()))
		        return MockDatabase.businessHour2;
		      return null;
		    }

		    static BusinessHour save(InvocationOnMock invocation) {
		      return (BusinessHour) invocation.getArgument(0);
		    }

		    static List<BusinessHour> findAll(InvocationOnMock invocation) {
		      List<BusinessHour> businessHours = new ArrayList<BusinessHour>();
		      businessHours.add(MockDatabase.businessHour1);
		      businessHours.add(MockDatabase.businessHour2);
		      return businessHours;
		    }
	  }
	  
	  final static class MockDatabase {
		  // BusinessHour1 information
		  private static final Weekday weekday1 = Weekday.Monday;
		  private static final Time startTime1 = Time.valueOf("8:00:00");
		  private static final Time endTime1 = Time.valueOf("17:00:00");
		  
		  // BusinessHour2 information
		  private static final Weekday weekday2 = Weekday.Tuesday;
		  private static final Time startTime2 = Time.valueOf("8:00:00");
		  private static final Time endTime2 = Time.valueOf("17:00:00");
		  
		  static BusinessHour businessHour1 = new BusinessHour();
		  static BusinessHour businessHour2 = new BusinessHour();

		  static {
			  businessHour1.setWeekday(weekday1);
			  businessHour1.setStartTime(startTime1);
			  businessHour1.setEndTime(endTime1);
			  
			  businessHour2.setEndTime(endTime2);
			  businessHour2.setStartTime(startTime2);
			  businessHour2.setWeekday(weekday2);
		  }
	  }
}
