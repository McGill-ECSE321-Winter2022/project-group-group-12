package ca.mcgill.ecse321.GSSS.dto;

/**
 * DTO equivalent of the Owner class to pass to the frontend
 *
 * @author Wassim Jabbour
 */
public class OwnerDto extends AccountDto {

  /**
   * Default constructor
   *
   * @author Wassim Jabbour
   */
  public OwnerDto() {
  }

  /**
   * Full constructor
   *
   * @param username The name of the owner
   * @param email    The email of the owner
   * @param password The password of the owner (hashed and salted)
   * @param salt     The salt used for the above password
   * @param disabled Whether the owner is disabled
   * @param address  The address of the owner
   * @author Wassim Jabbour
   */
  public OwnerDto(
      String username,
      String email,
      String password,
      String salt,
      boolean disabled,
      AddressDto address) {
    super(username, email, password, salt, disabled, address);
  }
}
