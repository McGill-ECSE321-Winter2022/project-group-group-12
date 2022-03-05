package ca.mcgill.ecse321.GSSS.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping(value = {"/purchases", "/purchases/"})
  public List<PurchaseDto> getAllPurchases() throws IllegalArgumentException {

    List<Purchase> allPurchases = purchaseService.getAllPurchases();

    List<PurchaseDto> allPurchaseDtos = new ArrayList<PurchaseDto>();

    for (Purchase purchase : allPurchases) {

      allPurchaseDtos.add(DtoConversion.convertToDto(purchase));

    }

    return allPurchaseDtos;

  }

  @PostMapping(value = {"/purchase", "/purchase/"})
  public PurchaseDto createPurchase(@RequestParam(name = "ordertype") String orderType,
      @RequestParam(name = "orderstatus") String orderStatus,
      @RequestBody HashMap<ItemDto, Integer> data) throws IllegalArgumentException {
    
    OrderType actualOrderType = DtoConversion.findOrderTypeByName(orderType);
    OrderStatus actualOrderStatus = DtoConversion.findOrderStatusByName(orderStatus);
    Employee employee = employeeService.getClosestEmployee();
    HashMap<Item, Integer> items = new HashMap();
    
    for(Map.Entry<ItemDto, Integer> entry : data.entrySet()) {
      items.put(DtoConversion.convertToDomainObject(entry.getKey()), entry.getValue());
    }
    
    Purchase purchase = purchaseService.createPurchase(actualOrderType, employee, actualOrderStatus, items);
    
    return DtoConversion.convertToDto(purchase);

  }

}
