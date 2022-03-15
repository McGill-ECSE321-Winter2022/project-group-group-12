package ca.mcgill.ecse321.GSSS.dto;

public class AddressDto {
  private String id;
  private String fullName;
  private String streetName;
  private Integer streetNumber;
  private String city;
  private String postalCode;

  /**
   * Empty constructor for the Address DTO
   *
   * @author Enzo Benoit-Jeannin
   */
  public AddressDto() {}

  /**
   * Constructor for the Address DTO
   *
   * @author Enzo Benoit-Jeannin
   * @param id Id of the Adress DTO
   * @param fullName Full name of the Address DTO
   * @param streetName Street name of the Address DTO
   * @param streetNumber Street number of the Address DTO
   * @param city City of the Address DTO
   * @param postalCode Postal code of the Address DTO
   */
  public AddressDto(
      String id,
      String fullName,
      String streetName,
      Integer streetNumber,
      String city,
      String postalCode) {
    this.fullName = fullName;
    this.streetName = streetName;
    this.streetNumber = streetNumber;
    this.city = city;
    this.postalCode = postalCode;
    this.id = id;
  }
  /**
   * Method to get the id of the Address DTO
   *
   * @author Enzo Benoit-Jeannin
   * @return id of the Address DTO
   */
  public String getId() {
    return id;
  }

  /**
   * Method to get the full name of the Address DTO
   *
   * @author Enzo Benoit-Jeannin
   * @return full name of the Address DTO
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * Method to get the street name of the Address DTO
   *
   * @author Enzo Benoit-Jeannin
   * @return street name of the Address DTO
   */
  public String getStreetName() {
    return streetName;
  }

  /**
   * Method to get the street number of the Address DTO
   *
   * @author Enzo Benoit-Jeannin
   * @return street number of the Address DTO
   */
  public Integer getStreetNumber() {
    return streetNumber;
  }

  /**
   * Method to get the city of the Address DTO
   *
   * @author Enzo Benoit-Jeannin
   * @return city of the Address DTO
   */
  public String getCity() {
    return city;
  }

  /**
   * Method to get the postal code of the Address DTO
   *
   * @author Enzo Benoit-Jeannin
   * @return postal code of the Address DTO
   */
  public String getPostalCode() {
    return postalCode;
  }
}
