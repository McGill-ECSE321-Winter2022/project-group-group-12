package ca.mcgill.ecse321.GSSS.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.GSSS.dao.ItemCategoryRepository;
import ca.mcgill.ecse321.GSSS.model.ItemCategory;

/**
 * This is a service class meant to provide an interface to perform actions on ItemCategory objects.
 * 
 * @author Philippe Sarouphim Hochar.
 */
@Service
public class ItemCategoryService {

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    /**
     * This service creates a new item category and adds it to the database.
     * 
     * @author Philippe Sarouphim Hochar
     * 
     * @param name Name of the category.
     * @return The newly added category, or null if the saving did not succeed.
     */
    @Transactional
    public ItemCategory createCategory(String name){
        // Check if category already exists in database
        if(itemCategoryRepository.findItemCategoryByName(name) != null) return null;

        // Createand attempt to add new category to database
        ItemCategory category = new ItemCategory();
        category.setName(name);

        return itemCategoryRepository.save(category);
    }

    /**
     * This service fetches all available categories from the database.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @return List of all categories.
     */
    @Transactional
    public List<ItemCategory> getAll(){
        List<ItemCategory> categories = new ArrayList<ItemCategory>();
        for(ItemCategory ic: itemCategoryRepository.findAll()) categories.add(ic);
        return categories;
    }

    /**
     * This service fetches an item category from the database by name.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param name Name of the category.
     * @return The saved category from the database, or null if it does not exist.
     */
    @Transactional
    public ItemCategory getCategoryByName(String name){
        return itemCategoryRepository.findItemCategoryByName(name);
    }

    /**
     * This service deletes an item category from the database by name.
     * 
     * @author Philippe Sarouphim Hochar.
     * 
     * @param name Name of the category.
     */
    @Transactional
    public void deleteCategory(String name){
        itemCategoryRepository.deleteById(name);
    }

}
