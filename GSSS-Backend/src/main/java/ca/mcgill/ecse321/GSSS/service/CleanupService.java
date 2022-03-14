package ca.mcgill.ecse321.GSSS.service;

import ca.mcgill.ecse321.GSSS.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CleanupService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BusinessHourRepository businessHourRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Transactional
    public void cleanupDatabase() {
        customerRepository.deleteAll();
        purchaseRepository.deleteAll();
        employeeRepository.deleteAll();
        shiftRepository.deleteAll();
        ownerRepository.deleteAll();
        addressRepository.deleteAll();
        itemRepository.deleteAll();
        itemCategoryRepository.deleteAll();
        businessHourRepository.deleteAll();
    }
}
