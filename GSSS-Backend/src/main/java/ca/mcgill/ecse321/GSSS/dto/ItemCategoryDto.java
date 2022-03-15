package ca.mcgill.ecse321.GSSS.dto;

/**
 * Data Transfer Object Class for ItemCategory Class
 *
 * @author Theo Ghanem
 */
public class ItemCategoryDto {

  private String name;

  /**
   * Default constructor
   *
   * @author Theo Ghanem
   */
  public ItemCategoryDto() {}

  public ItemCategoryDto(String name) {

    this.name = name;
  }

  public String getName() {
    return name;
  }
}
