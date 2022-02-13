package ca.mcgill.ecse321.GSSS.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GSSS.model.Purchase;
import ca.mcgill.ecse321.GSSS.model.QuantityOrdered;


public interface PurchaseRepository extends CrudRepository<Purchase, Long>{

	Purchase findPurchasebyID(long id);
	
	Purchase findPurchaseByQuantityOrdered(QuantityOrdered quantityOrdered);
}
