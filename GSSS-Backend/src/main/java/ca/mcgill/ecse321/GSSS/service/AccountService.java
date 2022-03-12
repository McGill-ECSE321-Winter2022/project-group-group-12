package ca.mcgill.ecse321.GSSS.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GSSS.dao.CustomerRepository;
import ca.mcgill.ecse321.GSSS.dao.EmployeeRepository;
import ca.mcgill.ecse321.GSSS.dao.OwnerRepository;
import ca.mcgill.ecse321.GSSS.model.Account;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Owner;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;


@Service
public class AccountService {

	private static final String PERMISSIONS_CLAIM_KEY = "permissions";
	private static final String PERMISSIONS_OWNER = "owner";
	private static final String PERMISSIONS_EMPLOYEE = "employee";
	private static final String PERMISSIONS_CUSTOMER = "customer";

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	@Value("${jwt.secret}")
	private String secret;

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	OwnerRepository ownerRepository;
	
	
	@Transactional
	public Account authenticate(String email, String password) {
		
		// Input validation
	    String error = "";
	    if(email == null || email.trim().length() == 0)
	      error += "Account email cannot be empty! ";
	    if(password == null || password.trim().length() == 0)
          error += "Account password cannot be empty! ";
	    if(!(error.length() == 0))
	      throw new IllegalArgumentException(error);
	    
	    Customer customer = customerRepository.findCustomerByEmail(email);
	    if (customer != null) {
	    	if (customer.getPassword().equals(Utility.hashAndSaltPassword(password, customer.getSalt()))) {
	    		return customer;
	    	}else {
	    		throw new IllegalArgumentException("Invalid password");
	    	}
	    	
	    }
	    
	    Employee employee = employeeRepository.findEmployeeByEmail(email);
	    if (employee != null) {
	    	if (employee.getPassword().equals(Utility.hashAndSaltPassword(password, employee.getSalt()))) {
	    		return employee;
	    	}else {
	    		throw new IllegalArgumentException("Invalid password");
	    	}
	    }

	    Owner owner = ownerRepository.findOwnerByEmail(email);
	    if(owner != null) {
	    	if (owner.getPassword().equals(Utility.hashAndSaltPassword(password, owner.getSalt()))) {
	    		return owner;
	    	}else {
	    		throw new IllegalArgumentException("Invalid password");
	    	}
	    }
	    
	    if(customer == null && employee == null && owner == null)
	    	throw new NoSuchElementException("No account with email "+email+" exists!");
	    
	    return null;
	    
	    
	    
	}

	public String generateJWT(Account account){
		Map<String, Object> claims = new HashMap<String, Object>();
		if(account instanceof Owner) claims.put(PERMISSIONS_CLAIM_KEY, PERMISSIONS_OWNER);
		else if(account instanceof Employee) claims.put(PERMISSIONS_CLAIM_KEY, PERMISSIONS_EMPLOYEE);
		else if(account instanceof Customer) claims.put(PERMISSIONS_CLAIM_KEY, PERMISSIONS_CUSTOMER);
		else throw new IllegalArgumentException("Specified account is neither an owner, employee, nor a customer");
		return doGenerateToken(claims, account.getEmail());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public boolean validateToken(String token) {
		try{
			Jwts.parser().parse(token);
		} catch(Exception e){
			return false;
		}
		return false;
	}

	public String getPermission(String token){
		if(!validateToken(token)) return "none";
		String permission = (String) Jwts.parser()
			.setSigningKey(secret)
			.parseClaimsJws(token)
			.getBody()
			.get(PERMISSIONS_CLAIM_KEY);

		return permission;
	}

	public boolean getOwnerPermission(String token){
		return getPermission(token).equals(PERMISSIONS_OWNER);
	}

	public boolean getEmployeePermission(String token){
		return getPermission(token).equals(PERMISSIONS_EMPLOYEE);
	}

	public boolean getCustomerPermission(String token){
		return getPermission(token).equals(PERMISSIONS_CUSTOMER);
	}

}
