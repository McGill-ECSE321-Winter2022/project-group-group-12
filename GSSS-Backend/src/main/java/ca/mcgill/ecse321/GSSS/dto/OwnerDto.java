package ca.mcgill.ecse321.GSSS.dto;

/**
 * DTO equivalent of the Owner class to pass to the frontend
 *
 * @author Wassim Jabbour
 */
public class OwnerDto extends AccountDto {

  private String storeCity;
  private double outOfTownDeliveryFee;
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
      AddressDto address,
      String storeCity,
      double outOfTownDeliveryFee) {
    super(username, email, password, salt, disabled, address);
    this.storeCity = storeCity;
    this.outOfTownDeliveryFee = outOfTownDeliveryFee;
  }

  public String getStoreCity() {
    return storeCity;
  }

  public void setStoreCity(String storeCity) {
    this.storeCity = storeCity;
  }

  public double getOutOfTownDeliveryFee() {
    return outOfTownDeliveryFee;
  }

  public void setOutOfTownDeliveryFee(double outOfTownDeliveryFee) {
    this.outOfTownDeliveryFee = outOfTownDeliveryFee;
  }
}
