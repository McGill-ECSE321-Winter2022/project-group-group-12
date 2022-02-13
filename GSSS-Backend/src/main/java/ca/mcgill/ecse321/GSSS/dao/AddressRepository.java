package ca.mcgill.ecse321.GSSS.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.GSSS.model.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
    
    Address findAddressById(long id);

}
