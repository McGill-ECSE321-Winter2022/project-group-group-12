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
  public AccountDto() {}

  /**
   * Full constructor
   *
   * @author Wassim Jabbour
   * @param username The name of the account
   * @param email The email of the account
   * @param password The password of the account (hashed and salted)
   * @param salt The salt used for the above password
   * @param disabled Whether the account is disabled
   * @param address The address of the account
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
   * @author Wassim Jabbour
   * @return The username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Email getter
   *
   * @author Wassim Jabbour
   * @return The email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Password getter
   *
   * @author Wassim Jabbour
   * @return The password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Salt getter
   *
   * @author Wassim Jabbour
   * @return The salt
   */
  public String getSalt() {
    return salt;
  }

  /**
   * Disabled getter
   *
   * @author Wassim jabbour
   * @return The disabled boolean
   */
  public boolean isDisabled() {
    return disabled;
  }

  /**
   * Address getter
   *
   * @author Wassim jabbour
   * @return The address
   */
  public AddressDto getAddress() {
    return address;
  }
}
