package ca.mcgill.ecse321.GSSS.service;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class containing the common methods to all services
 * 
 * @author Wassim Jabbour
 *
 */
public class HelperClass {

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
}
