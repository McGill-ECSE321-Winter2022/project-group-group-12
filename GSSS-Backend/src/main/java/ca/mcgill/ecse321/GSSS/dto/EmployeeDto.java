package ca.mcgill.ecse321.GSSS.dto;

import java.util.Set;

public class EmployeeDto extends AccountDto {

  private Set<ShiftDto> shifts;

  public EmployeeDto() {

  }

  public EmployeeDto(Set<ShiftDto> shifts) {
    super();
    this.shifts = shifts;
  }

  public Set<ShiftDto> getShifts() {
    return shifts;
  }



}
