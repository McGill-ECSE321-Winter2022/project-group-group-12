package ca.mcgill.ecse321.GSSS.dto;

public class OwnerDto extends AccountDto{

  public OwnerDto() {
    
  }
  
  public OwnerDto(String username, String email, String password, String salt, boolean disabled,
      AddressDto address) {
    super(username, email, password, salt, disabled, address);
  }

}
