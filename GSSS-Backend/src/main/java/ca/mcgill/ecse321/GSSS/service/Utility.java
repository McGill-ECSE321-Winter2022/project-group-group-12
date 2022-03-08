package ca.mcgill.ecse321.GSSS.service;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class containing the common methods to all services
 * 
 * @author Wassim Jabbour
 *
 */
public class Utility {

  /**
   * Used to hash and salt a password
   * 
   * @author Wassim Jabbour, inspired by howtodoinjava.com
   * @param passwordToHash The password to hash
   * @param salt The salt of the password to hash
   * @return The hashed and salted password (Or null in case of an exception)
   */
  static String hashAndSaltPassword(String passwordToHash, String salt) {

    // String to hold the hashed and salted password
    String generatedPassword = null;

    // Hashing and salting section
    try {

      // Creating an MD5 MessageDigest instance
      MessageDigest md = MessageDigest.getInstance("MD5");

      // Adding the salt bytes to the digest
      md.update(salt.getBytes());

      // Getting the hashed password's byte array
      byte[] bytes = md.digest(passwordToHash.getBytes());

      // Converting the hash from decimal to hexadecimal for more security
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }

      // Get complete hashed password in hex format
      generatedPassword = sb.toString();

    } catch (Exception e) {
      return null;
    }

    // Returning the hashed and salted password
    return generatedPassword;
  }

  /**
   * Method that generates a salt to improve a password's security
   * 
   * @author Wassim Jabbour, inspired by howtodoinjava.com
   * @return The generated salt (Or null in the case of an exception)
   */
  static String getSalt() {

    // Using a SecureRandom instance to generate the salt
    SecureRandom sr;
    try {
      sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
    } catch (Exception e) {
      return null;
    }
    byte[] salt = new byte[16];
    sr.nextBytes(salt);
    return salt.toString();

  }

  /**
   * Method that converts an iterable to a list. Useful when using findAll()
   * 
   * @author Wassim Jabbour, inspired from the tutorial notes
   * @param <T> The type of the iterable we are converting
   * @param iterable The iterable we are converting
   * @return The list after the conversion
   */
  static <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }

  /**
   * Method to check if an email is valid or invalid
   *
   * @author Theo Ghanem & inspired from https://mailtrap.io/blog/java-email-validation/
   * @param email email to check if valid or invalid
   * @return true if email is valid, and false if email is invalid
   */
  static boolean isEmailValid(String email) {
    if(email == null) return false;
    
    String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; // OWASP validation regex 

    //initialize the Pattern object
    Pattern pattern = Pattern.compile(regex);

    //searching for occurrences of regex
    Matcher matcher = pattern.matcher(email);

    if(matcher.matches()) return true; //return true if email is valid
    return false; //return false if email is invalid
  }

  /**
   * Method to check if a postal code is valid or invalid
   *
   * @author Theo Ghanem
   * @param postalCode postalCode to check if valid or invalid
   * @return true if postalCode is valid, and false if postalCode is invalid
   */
  static boolean isPostalCodeValid(String postalCode) {
    if(postalCode == null) return false;

    String regex = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$"; //regex for canadian postal code validation

    //initialize the Pattern object
    Pattern pattern = Pattern.compile(regex);

    //searching for occurrences of regex
    Matcher matcher = pattern.matcher(postalCode);

    if(matcher.matches()) return true; //return true if postalCode is valid
    return false; //return false if postalCode is invalid
  }

}
