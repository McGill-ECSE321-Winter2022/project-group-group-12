package ca.mcgill.ecse321.GSSS.dto;

import java.util.List;

public class CustomerDto extends AccountDto {

  private List<PurchaseDto> purchases;

  public CustomerDto() {

  }

  public CustomerDto(String username, String email, String password, String salt, boolean disabled,
      AddressDto address, List<PurchaseDto> purchases) {
    super(username, email, password, salt, disabled, address);
    this.purchases = purchases;
  }

  public List<PurchaseDto> getPurchases() {
    return purchases;
  }



}
