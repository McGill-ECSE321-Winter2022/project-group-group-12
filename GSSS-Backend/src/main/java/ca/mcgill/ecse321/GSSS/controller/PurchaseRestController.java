package ca.mcgill.ecse321.GSSS.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.GSSS.dto.ItemDto;
import ca.mcgill.ecse321.GSSS.dto.PurchaseDto;
import ca.mcgill.ecse321.GSSS.model.Employee;
import ca.mcgill.ecse321.GSSS.model.Item;
import ca.mcgill.ecse321.GSSS.model.OrderStatus;
import ca.mcgill.ecse321.GSSS.model.OrderType;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.service.EmployeeService;
import ca.mcgill.ecse321.GSSS.service.PurchaseService;

@CrossOrigin(origins = "*")
@RestController
public class PurchaseRestController {

  @Autowired
  PurchaseService purchaseService;

  @Autowired
  EmployeeService employeeService;

  /**
   * Returns all purchases
   * 
   * @author Wassim Jabbour
   * @return All the purchases in the system
   */
  @GetMapping(value = {"/purchases", "/purchases/"})
  public List<PurchaseDto> getAllPurchases() {

    List<Purchase> allPurchases = purchaseService.getAllPurchases();

    List<PurchaseDto> allPurchaseDtos = new ArrayList<PurchaseDto>();

    for (Purchase purchase : allPurchases) {

      allPurchaseDtos.add(DtoConversion.convertToDto(purchase));

    }

    return allPurchaseDtos;

  }

  /**
   * Creates a purchase and returns its equivalent dto
   * 
   * @author Wassim Jabbour
   * @param orderType The type of purchase
   * @param orderStatus The status of purchase
   * @param data The map of itemDtos and their quantities
   * @return The Dto corresponding to the created object
   * @throws IllegalArgumentException In case the input is invalid
   */
  @PostMapping(value = {"/purchase", "/purchase/"})
  public PurchaseDto createPurchase(@RequestParam(name = "ordertype") String orderType,
      @RequestParam(name = "orderstatus") String orderStatus,
      @RequestBody HashMap<ItemDto, Integer> data) throws IllegalArgumentException {

    OrderType actualOrderType = DtoConversion.findOrderTypeByName(orderType);
    // Checking that it is not null
    if (actualOrderType == null)
      throw new IllegalArgumentException("Invalid order type!");

    OrderStatus actualOrderStatus = DtoConversion.findOrderStatusByName(orderStatus);
    // Checking that it is not null
    if (actualOrderStatus == null)
      throw new IllegalArgumentException("Invalid order status!");

    Employee employee = employeeService.getClosestEmployee();
    HashMap<Item, Integer> items = new HashMap<Item, Integer>();

    for (Map.Entry<ItemDto, Integer> entry : data.entrySet()) {
      items.put(DtoConversion.convertToDomainObject(entry.getKey()), entry.getValue());
    }

    Purchase purchase =
        purchaseService.createPurchase(actualOrderType, employee, actualOrderStatus, items);

    return DtoConversion.convertToDto(purchase);

  }

  /**
   * Modifies the status of a purchase
   * 
   * @author Wassim Jabbour
   * @param purchaseId The id of the purchase
   * @param orderStatus The new status
   * @return The new equivalent dto
   * @throws IllegalArgumentException In case of invalid inputs
   */
  @PostMapping(value = {"purchase/modify/{purchaseid}/{orderstatus}/",
      "/purchase/modify/{purchaseId}/{orderstatus}"})
  public PurchaseDto modifyPurchaseOrderStatus(@PathVariable(name = "purchaseid") String purchaseId,
      @PathVariable(name = "orderstatus") String orderStatus) throws IllegalArgumentException {

    OrderStatus actualOrderStatus = DtoConversion.findOrderStatusByName(orderStatus);
    
    Purchase purchase = purchaseService.modifyPurchaseStatus(actualOrderStatus, purchaseId);
    
    return DtoConversion.convertToDto(purchase);
  }

  /**
   * Modifies the employee of a purchase
   * 
   * @author Wassim Jabbour
   * @param purchaseId The id of the purchase
   * @param employeeEmail The email of the employee
   * @return The new equivalent dto
   * @throws IllegalArgumentException In case of invalid inputs
   */
  @PostMapping(value = {"purchase/modify/{purchaseid}/{employeeemail}/",
      "/purchase/modify/{purchaseId}/{orderstatus}"})
  public PurchaseDto modifyPurchaseEmployee(@PathVariable(name = "purchaseid") String purchaseId,
      @PathVariable(name = "employeeemail") String employeeEmail) throws IllegalArgumentException {

    Employee employee = employeeService.getEmployee(employeeEmail);

    Purchase purchase = purchaseService.modifyPurchaseEmployee(employee, purchaseId);

    return DtoConversion.convertToDto(purchase);
  }

}
