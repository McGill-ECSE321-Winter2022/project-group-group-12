package ca.mcgill.ecse321.GSSS.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.GSSS.model.ItemCategory;

public interface ItemCategoryRepository extends CrudRepository<ItemCategory, String> {

  ItemCategory findItemCategoryByName(String name);

}
