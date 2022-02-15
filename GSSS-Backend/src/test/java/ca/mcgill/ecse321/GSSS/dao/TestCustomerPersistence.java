package ca.mcgill.ecse321.GSSS.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.GSSS.model.Address;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Purchase;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestCustomerPersistence {
	
	@Autowired
    private CustomerRepository customerRepository;
    private AddressRepository addressRepository;
    private PurchaseRepository purchaseRepository;
    
    // Initialize variables to create customer, an address and a purchase
    String name = "Tyler The Creator";
    String email = "whodatboy@gmail.com";
    String password = "tylerpassword";
    String fullName = "Tyler TheCreator";
    String streetName = "Lorne Crescent";
    String city = "Montreal";
    int streetNumber = 69;
    String postalCode = "H2X 2B3";
    String addressId = UUID.randomUUID().toString();
    Time purchaseTime = java.sql.Time.valueOf(LocalTime.of(13, 30));
    Date purchaseDate = Date.valueOf(LocalDate.of(2020, Month.FEBRUARY, 14));
    String purchaseId =  UUID.randomUUID().toString();
    
    @Test
    public void testPersistAndLoadCustomersByUsername(){
    	Address address = new Address();
        address.setFullName(fullName);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setId(addressId);

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setUsername(name);
        customer.setPassword(password);
        customer.setAddress(address);
        customer.setDisabled(false);
        
        Purchase purchase = new Purchase();
        purchase.setDate(purchaseDate);
        purchase.setTime(purchaseTime);
        purchase.setId(purchaseId);
        HashSet<Purchase> setOfPurchases = new HashSet<>();
        setOfPurchases.add(purchase);
        customer.setPurchases(setOfPurchases);
        
        customerRepository.save(customer);
        addressRepository.save(address);
        purchaseRepository.save(purchase);
        
        List<Customer> myCustomers = customerRepository.findCustomersByUsername(name);
        customer = myCustomers.get(0);
        
        assertNotNull(myCustomers);
        assertNotNull(customer);		// include that ?
        assertEquals(customer.getUsername(), name);
        assertEquals(customer.getEmail(), email);
        assertEquals(customer.getPassword(), password);
        assertEquals(customer.getAddress(), address);
        assertEquals(customer.isDisabled(), false);
        assertEquals(customer.getPurchases(), setOfPurchases);
    }
    
    @Test
    public void testPersistAndLoadCustomersByDisabled(){
    	Address address = new Address();
        address.setFullName(fullName);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setId(addressId);

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setUsername(name);
        customer.setPassword(password);
        customer.setAddress(address);
        customer.setDisabled(true);
        
        Purchase purchase = new Purchase();
        purchase.setDate(purchaseDate);
        purchase.setTime(purchaseTime);
        purchase.setId(purchaseId);
        HashSet<Purchase> setOfPurchases = new HashSet<>();
        setOfPurchases.add(purchase);
        customer.setPurchases(setOfPurchases);
        
        customerRepository.save(customer);
        addressRepository.save(address);
        purchaseRepository.save(purchase);
        
        List<Customer> myCustomers = customerRepository.findCustomersByDisabled(true);
        customer = myCustomers.get(0);
        
        assertNotNull(myCustomers);
        assertNotNull(customer);			// Include that ??
        assertEquals(customer.getUsername(), name);
        assertEquals(customer.getEmail(), email);
        assertEquals(customer.getPassword(), password);
        assertEquals(customer.getAddress(), address);
        assertEquals(customer.isDisabled(), true);
        assertEquals(customer.getPurchases(), setOfPurchases);
    }
    
    @Test
    public void testPersistAndLoadCustomersByEmail(){
    	Address address = new Address();
        address.setFullName(fullName);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setId(addressId);

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setUsername(name);
        customer.setPassword(password);
        customer.setAddress(address);
        customer.setDisabled(false);
        
        Purchase purchase = new Purchase();
        purchase.setDate(purchaseDate);
        purchase.setTime(purchaseTime);
        purchase.setId(purchaseId);
        HashSet<Purchase> setOfPurchases = new HashSet<>();
        setOfPurchases.add(purchase);
        customer.setPurchases(setOfPurchases);
        
        customerRepository.save(customer);
        addressRepository.save(address);
        purchaseRepository.save(purchase);
        
        customer = customerRepository.findCustomerByEmail(email);
        
        assertNotNull(customer);		
        assertEquals(customer.getUsername(), name);
        assertEquals(customer.getEmail(), email);
        assertEquals(customer.getPassword(), password);
        assertEquals(customer.getAddress(), address);
        assertEquals(customer.isDisabled(), false);
        assertEquals(customer.getPurchases(), setOfPurchases);
    }
    
    @Test
    public void testPersistAndLoadCustomersByAddress(){
    	Address address = new Address();
        address.setFullName(fullName);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setId(addressId);

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setUsername(name);
        customer.setPassword(password);
        customer.setAddress(address);
        customer.setDisabled(false);
        
        Purchase purchase = new Purchase();
        purchase.setDate(purchaseDate);
        purchase.setTime(purchaseTime);
        purchase.setId(purchaseId);
        HashSet<Purchase> setOfPurchases = new HashSet<>();
        setOfPurchases.add(purchase);
        customer.setPurchases(setOfPurchases);
        
        customerRepository.save(customer);
        addressRepository.save(address);
        purchaseRepository.save(purchase);
        
        customer = customerRepository.findCustomerByAddress(address);
        
        assertNotNull(customer);		
        assertEquals(customer.getUsername(), name);
        assertEquals(customer.getEmail(), email);
        assertEquals(customer.getPassword(), password);
        assertEquals(customer.getAddress(), address);
        assertEquals(customer.isDisabled(), false);
        assertEquals(customer.getPurchases(), setOfPurchases);
    }
    
    @Test
    public void testPersistAndLoadCustomersByPurchases(){
    	Address address = new Address();
        address.setFullName(fullName);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setId(addressId);

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setUsername(name);
        customer.setPassword(password);
        customer.setAddress(address);
        customer.setDisabled(false);
        
        Purchase purchase = new Purchase();
        purchase.setDate(purchaseDate);
        purchase.setTime(purchaseTime);
        purchase.setId(purchaseId);
        HashSet<Purchase> setOfPurchases = new HashSet<>();
        setOfPurchases.add(purchase);
        customer.setPurchases(setOfPurchases);
        
        customerRepository.save(customer);
        addressRepository.save(address);
        purchaseRepository.save(purchase);
        
        customer = customerRepository.findCustomerByPurchases(purchase);
        
        assertNotNull(customer);		
        assertEquals(customer.getUsername(), name);
        assertEquals(customer.getEmail(), email);
        assertEquals(customer.getPassword(), password);
        assertEquals(customer.getAddress(), address);
        assertEquals(customer.isDisabled(), false);
        assertEquals(customer.getPurchases(), setOfPurchases);
    }
}
