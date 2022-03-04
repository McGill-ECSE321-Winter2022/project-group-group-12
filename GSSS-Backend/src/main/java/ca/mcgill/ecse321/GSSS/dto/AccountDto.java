package ca.mcgill.ecse321.GSSS.dto;


public abstract class AccountDto {
  
  private String username;
  private String email;
  private String password; // Hashed and salted
  private String salt; // The salt used for the above encryption
  private boolean disabled;
  private AddressDto address;
  
  public AccountDto() {
  }
  
  public AccountDto(String username, String email, String password, String salt, boolean disabled,
      AddressDto address) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.salt = salt;
    this.disabled = disabled;
    this.address = address;
  }
  
  public String getUsername() {
    return username;
  }
  public String getEmail() {
    return email;
  }
  public String getPassword() {
    return password;
  }
  public String getSalt() {
    return salt;
  }
  public boolean isDisabled() {
    return disabled;
  }
  public AddressDto getAddress() {
    return address;
  }
  
  

}
