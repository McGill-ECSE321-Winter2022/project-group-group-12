package ca.mcgill.ecse321.GSSS.controller;

import ca.mcgill.ecse321.GSSS.model.Account;
import ca.mcgill.ecse321.GSSS.model.Customer;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Owner;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtility {

    private static final String PERMISSIONS_CLAIM_KEY = "permissions";
    private static final String PERMISSIONS_OWNER = "owner";
    private static final String PERMISSIONS_EMPLOYEE = "employee";
    private static final String PERMISSIONS_CUSTOMER = "customer";
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private static String secret;

    /**
     * This method generates a JWT associated to an account and gives it appropriate permission.
     *
     * @author Philippe Sarouphim Hochar.
     *
     * @param account Account to generate JWT for.
     * @return The generated JWT.
     */
    public static String generateJWT(Account account){
        Map<String, Object> claims = new HashMap<String, Object>();
        if(account instanceof Owner) claims.put(PERMISSIONS_CLAIM_KEY, PERMISSIONS_OWNER);
        else if(account instanceof Employee) claims.put(PERMISSIONS_CLAIM_KEY, PERMISSIONS_EMPLOYEE);
        else if(account instanceof Customer) claims.put(PERMISSIONS_CLAIM_KEY, PERMISSIONS_CUSTOMER);
        else throw new IllegalArgumentException("Specified account is neither an owner, employee, nor a customer");
        return doGenerateToken(claims, account.getEmail());
    }

    /**
     * This method is a helper method which performs the generation of the JWT given payloads and an email.
     *
     * @author Philippe Sarouphim Hochar.
     *
     * @param claims Payload key-value pairs.
     * @param subject Email associated to the account.
     * @return The generated JWT.
     */
    private static String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * This methods verify whether the JWT is valid and if it's signature is valid.
     *
     * @author Philippe Sarouphim Hochar.
     *
     * @param token JWT.
     * @return Whether the token is valid or not.
     */
    public static boolean validateToken(String token) {
        try{
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
        } catch(Exception e){
            return false;
        }
        return false;
    }

    /**
     * This method extracts the "permissions" field from the body of the JWT and returns it.
     * It only does so if the JWT is valid.
     *
     * @author Philippe Sarouphim Hochar.
     *
     * @param token JWT.
     * @return The permission associated to the JWT.
     */
    public static String getPermission(String token){
        if(!validateToken(token)) return "none";
        String permission = (String) Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .get(PERMISSIONS_CLAIM_KEY);

        return permission;
    }

    /**
     * This method verifies whether the JWT has owner permissions.
     *
     * @author Philippe Sarouphim Hochar.
     *
     * @param token JWT.
     * @return Whether the JWT has owner permissions.
     */
    public static boolean getOwnerPermission(String token){
        return getPermission(token).equals(PERMISSIONS_OWNER);
    }

    /**
     * This method verifies whether the JWT has employee permissions.
     *
     * @author Philippe Sarouphim Hochar.
     *
     * @param token JWT.
     * @return Whether the JWT has employee permissions.
     */
    public static boolean getEmployeePermission(String token){
        return getPermission(token).equals(PERMISSIONS_EMPLOYEE);
    }

    /**
     * This method verifies whether the JWT has customer permissions.
     *
     * @author Philippe Sarouphim Hochar.
     *
     * @param token JWT.
     * @return Whether the JWT has customer permissions.
     */
    public static boolean getCustomerPermission(String token){
        return getPermission(token).equals(PERMISSIONS_CUSTOMER);
    }

    /**
     * This method verifies whether a JWT belongs to a specific account.
     *
     * @author Philippe Sarouphim Hochar.
     *
     * @param token JWT.
     * @param email Account email.
     * @return Whether JWT belongs to specified email.
     */
    public static boolean getAccountPermission(String token, String email){
        if(!validateToken(token)) return false;
        String sub = (String) Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .get("subject");

        return email.equals(sub);
    }
}
