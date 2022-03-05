package ca.mcgill.ecse321.GSSS.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.GSSS.dto.BusinessHourDto;
import ca.mcgill.ecse321.GSSS.dto.PurchaseDto;
import ca.mcgill.ecse321.GSSS.model.BusinessHour;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.service.BusinessHourService;
import ca.mcgill.ecse321.GSSS.service.PurchaseService;

@CrossOrigin(origins = "*")
@RestController
public class PurchaseRestController {

  @Autowired
  PurchaseService purchaseService;

  /**
   * Helper method that converts a PurchaseDto to its domain model equivalent
   * 
   * @author Wassim Jabbour
   * @param businessHourDto The DTO to convert
   * @return The domain model object
   */
  private Purchase convertToDomainObject(PurchaseDto purchaseDto) throws IllegalArgumentException {

    // Checking the input is non null
    if (purchaseDto == null)
      throw new IllegalArgumentException("There is no such purchase!");

    // Getting all the purchases in the system
    List<Purchase> allPurchases = purchaseService.getAllPurchases();

    // Cycling through them till we find the one with the desired weekday and returning it
    for (Purchase purchase : allPurchases) {

      if (purchase.getId() == purchaseDto.getId()) {
        return purchase;
      }

    }

    // Return null if no purchase with that ID was found
    return null;

  }

  private PurchaseDto convertToDto(Purchase purchase) throws IllegalArgumentException {

    // Checking the input is non null
    if (purchase == null)
      throw new IllegalArgumentException("There is no such purchase!");

    
    PurchaseDto purchaseDto =
        new PurchaseDto(purchase.getId(), purchase.getOrderType(), purchase.getOrderStatus(),
            purchase.getDate(), purchase.getTime(), purchase.getItems(), purchase.getEmployee());;


  }

}
