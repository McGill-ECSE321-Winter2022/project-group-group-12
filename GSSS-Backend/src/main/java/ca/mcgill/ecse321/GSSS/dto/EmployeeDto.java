package ca.mcgill.ecse321.GSSS.dto;

import java.util.List;

/**
 * DTO equivalent of the Employee class to pass to the frontend
 *
 * @author Wassim Jabbour
 */
public class EmployeeDto extends AccountDto {

  private List<ShiftDto> shifts;

  /**
   * Default constructor
   *
   * @author Wassim Jabbour
   */
  public EmployeeDto() {}

  /**
   * Full constructor
   *
   * @author Wassim Jabbour
   * @param username The name of the employee
   * @param email The email of the employee
   * @param password The password of the employee (hashed and salted)
   * @param salt The salt used for the above password
   * @param disabled Whether the employee is disabled
   * @param address The address of the employee
   * @param shifts The shifts of the employee
   */
  public EmployeeDto(
      String username,
      String email,
      String password,
      String salt,
      boolean disabled,
      AddressDto address,
      List<ShiftDto> shifts) {
    super(username, email, password, salt, disabled, address);
    this.shifts = shifts;
  }

  /**
   * Shifts getter
   *
   * @author Wassim Jabbour
   * @return The shifts of the employe
   */
  public List<ShiftDto> getShifts() {
    return shifts;
  }
}
