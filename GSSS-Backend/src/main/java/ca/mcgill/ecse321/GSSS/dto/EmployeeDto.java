package ca.mcgill.ecse321.GSSS.dto;

import java.util.Set;

public class EmployeeDto extends AccountDto {

  private Set<ShiftDto> shifts;

  public EmployeeDto() {

  }

  public EmployeeDto(String username, String email, String password, String salt, boolean disabled,
      AddressDto address, Set<ShiftDto> shifts) {
    super(username, email, password, salt, disabled, address);
    this.shifts = shifts;
  }

  public Set<ShiftDto> getShifts() {
    return shifts;
  }



}
