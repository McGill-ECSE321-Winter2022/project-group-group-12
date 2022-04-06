package ca.mcgill.ecse321.GSSS.dto;

/**
 * DTO equivalent of the Account class to pass to the frontend
 *
 * @author Wassim Jabbour
 */
public abstract class AccountDto {

  private String username;
  private String email;
  private String password; // Hashed and salted
  private String salt; // The salt used for the above encryption
  private boolean disabled;
  private AddressDto address;

  /**
   * Default constructor
   *
   * @author Wassim Jabbour
   */
  public AccountDto() {
  }

  /**
   * Full constructor
   *
   * @param username The name of the account
   * @param email    The email of the account
   * @param password The password of the account (hashed and salted)
   * @param salt     The salt used for the above password
   * @param disabled Whether the account is disabled
   * @param address  The address of the account
   * @author Wassim Jabbour
   */
  public AccountDto(
      String username,
      String email,
      String password,
      String salt,
      boolean disabled,
      AddressDto address) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.salt = salt;
    this.disabled = disabled;
    this.address = address;
  }

  /**
   * Username getter
   *
   * @return The username
   * @author Wassim Jabbour
   */
  public String getUsername() {
    return username;
  }

  /**
   * Email getter
   *
   * @return The email
   * @author Wassim Jabbour
   */
  public String getEmail() {
    return email;
  }

  /**
   * Password getter
   *
   * @return The password
   * @author Wassim Jabbour
   */
  public String getPassword() {
    return password;
  }

  /**
   * Salt getter
   *
   * @return The salt
   * @author Wassim Jabbour
   */
  public String getSalt() {
    return salt;
  }

  /**
   * Disabled getter
   *
   * @return The disabled boolean
   * @author Wassim jabbour
   */
  public boolean isDisabled() {
    return disabled;
  }

  /**
   * Address getter
   *
   * @return The address
   * @author Wassim jabbour
   */
  public AddressDto getAddress() {
    return address;
  }
}
