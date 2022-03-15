package ca.mcgill.ecse321.GSSS.dto;

import java.util.List;

/**
 * DTO equivalent of the Customer class to pass to the frontend
 *
 * @author Wassim Jabbour
 */
public class CustomerDto extends AccountDto {

  private List<PurchaseDto> purchases;

  /**
   * Default constructor
   *
   * @author Wassim Jabbour
   */
  public CustomerDto() {
  }

  /**
   * Full constructor
   *
   * @param username The name of the customer
   * @param email    The email of the customer
   * @param password The password of the customer (hashed and salted)
   * @param salt     The salt used for the above password
   * @param disabled Whether the customer is disabled
   * @param address  The address of the customer
   * @param The      purchases of the customer
   * @author Wassim Jabbour
   */
  public CustomerDto(
      String username,
      String email,
      String password,
      String salt,
      boolean disabled,
      AddressDto address,
      List<PurchaseDto> purchases) {
    super(username, email, password, salt, disabled, address);
    this.purchases = purchases;
  }

  /**
   * Purchases getter
   *
   * @return The purchases of the customer
   * @author Wassim Jabbour
   */
  public List<PurchaseDto> getPurchases() {
    return purchases;
  }
}
