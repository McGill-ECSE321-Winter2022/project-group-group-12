package ca.mcgill.ecse321.GSSS.dto;

import java.util.List;

public class CustomerDto extends AccountDto {

  private List<PurchaseDto> purchases;

  public CustomerDto() {
    
  }
  
  public CustomerDto(List<PurchaseDto> purchases) {
    super();
    this.purchases = purchases;
  }

  public List<PurchaseDto> getPurchases() {
    return purchases;
  }
  
  
  
  

}
