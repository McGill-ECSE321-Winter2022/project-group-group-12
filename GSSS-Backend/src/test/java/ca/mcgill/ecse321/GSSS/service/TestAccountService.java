package ca.mcgill.ecse321.GSSS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.GSSS.dao.AddressRepository;
import ca.mcgill.ecse321.GSSS.dao.CustomerRepository;
import ca.mcgill.ecse321.GSSS.dao.EmployeeRepository;
import ca.mcgill.ecse321.GSSS.dao.OwnerRepository;
import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.service.TestEmployeeService.MockRepository;

@ExtendWith(MockitoExtension.class)
public class TestAccountService {
	
	@Mock private CustomerRepository customerRepository;
	
	@Mock private EmployeeRepository employeeRepository;
	
	@Mock private OwnerRepository ownerRepository;

	@InjectMocks private AccountService accountService;

	@BeforeEach
	public void setMockOutput(){
		lenient().when(employeeRepository.findEmployeeByEmail(anyString())).thenAnswer(MockRepository::findEmployeeByEmail);
		lenient().when(customerRepository.findCustomerByEmail(anyString())).thenAnswer(MockRepository::findCustomerByEmail);
		lenient().when(ownerRepository.findOwnerByEmail(anyString())).thenAnswer(MockRepository::findOwnerByEmail);
	}
	
	@Test
    public void testLogIn(){
      
    }
}
